package com.capstone.producer;

import com.capstone.producer.clients.AviationStackClientCaller;
import com.capstone.producer.common.bindings.AirportGenerateRequest;
import com.capstone.producer.common.bindings.GenerateRequest;
import com.capstone.producer.common.bindings.aviationstack.Airport;
import com.capstone.producer.common.bindings.aviationstack.Flight;
import com.capstone.producer.common.bindings.aviationstack.FlightInfo;
import com.capstone.producer.common.bindings.aviationstack.Live;
import com.capstone.producer.kafka.KafkaProducer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Class that facilitates acquiring information from AviationStack, generating mock flights, and 'tracking' live and mock flights
 */
@Service
public class ServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceHandler.class);

    @Autowired
    private AviationStackClientCaller aviationStackClientCaller;

    /**
     * Keeps track of the previous flight icao encountered
     */
    private String prev_flight_icao;

    /**
     * Keeps track of all flight icaos that have been encountered
     */
    private final List<String> flightIcaos;

    /**
     * Keeps track if mock at least one mock flight has been generated and is being updated
     */
    private boolean generationHappening;

    /**
     * Keeps track of all mock flights that have been generated and are being updated
     */
    private final Map<String, GenerateRequest> generatedFlights;

    /**
     * Keeps track of how many times each live flight has been updated. Used to prevent live flights from being tracked forever
     */
    private final Map<String, Integer> liveFlightUpdateRecord;

    /**
     * Keeps track of how many times each mock flight has been updated. Used to prevent mock flights from being 'tracked' forever
     */
    private final Map<String, Integer> mockFlightUpdateRecord;

    /**
     * Default altitude used when creating mock flights
     */
    private static final float DEFAULT_ALTITUDE = 20000;
    /**
     * Number of updates a live flight will receive before becoming ineligible for tracking
     */
    private static final int MAX_LIVE_FLIGHT_UPDATES = 30;
    /**
     * Number of updates a mock flight will receive before becoming ineligible for 'tracking'
     * Also used to calculate the incremental change of latitude and longitude in airport to airport mocked flights
     */
    private static final float MAX_MOCK_FLIGHT_UPDATES = 30.0f;

    /**
     * Constructor for the class. Sets up the Objects used for keeping track of flights
     */
    public ServiceHandler() {
        // Objects are initialized using concurrent implementations of the List and Map interfaces
        // This is due to methods being Scheduled. There is the potential where a Map or List being accessed
        // will also be getting updated at the same time OR different methods will be attempting to access/update at the same time
        flightIcaos = new CopyOnWriteArrayList<>();
        generatedFlights = new ConcurrentHashMap<>();
        liveFlightUpdateRecord = new ConcurrentHashMap<>();
        mockFlightUpdateRecord = new ConcurrentHashMap<>();

        // Initialize String to prevent null pointers
        this.prev_flight_icao = "";
    }

    /**
     * Acquires flight information for the specific flight that corresponds to the provided ICAO number.
     * The information is then parsed and a message is built and sent through Kafka
     *
     * @param flightIcao The provided flight ICAO number
     * @return The message that was sent to Kafka, which consists of live coordinates and other flight information
     * or an error response.
     * @throws InterruptedException Sending a message using Kafka can trigger an InterruptedException
     */
    public String handleFlightIcao(String flightIcao) throws InterruptedException {
        LOGGER.debug("Received request: {}", flightIcao);
        LOGGER.debug("Previous Flight ICAO: {} | Newly Received ICAO: {}", prev_flight_icao, flightIcao);

        // Sets the variables keeping track of icaos
        this.prev_flight_icao = flightIcao;
        this.flightIcaos.add(flightIcao);

        // Get the flight information from AviationStack
        FlightInfo response = aviationStackClientCaller.getFlightFromIcao(flightIcao);

        // Return an error response if there was an issue getting the response or the requested flight doesn't have live data
        if (response == null) {
            String errorResponse = String.format("No relevant flight information could be found with the provided ICAO: %s. " +
                    "Either the flight does not exist or AviationStack does not have the live information populated.");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", errorResponse);
            return jsonObject.toString();
        }

        LOGGER.info(response.toString());

        // Build message for kafka
        String toBeSent = buildKafkaMessageFromFlightInfo(response, flightIcao);
        LOGGER.info("Sending: {}", toBeSent);

        // Send message through kafka broker
        RecordMetadata metadata = KafkaProducer.runProducer(toBeSent);
        LOGGER.debug("Kafka metadata: {}", metadata.toString());

        return toBeSent;
    }

    /**
     * Gets the ICAO numbers of 3-5 flights containing live information.
     * The icaos are then put in a message and sent through Kafka
     *
     * @return String value that consists of a JSON Object containing a comma-separated list of flight icao numbers
     * or an error response.
     */
    public String handleLiveRequest() {
        List<FlightInfo> liveFlights = aviationStackClientCaller.getAllActiveFlightsWithLive();

        // Create a JsonObject containing the comma-separated list of icaos that will be easily parsable in the front-end
        // Will contain an error string if no live flights are found
        JSONObject jsonObject = new JSONObject();

        // Return an error response if no flights with live information were acquired
        if (liveFlights.isEmpty()) {
            jsonObject.put("error", "No flights with live data found. Most likely reason this is occurring is an" +
                    " environment setup issue or we are out of requests on our api key.");
            return jsonObject.toString();
        }

        // Get just the flight icaos from the list of FlightInfo Objects
        String liveFlightsStr = liveFlights.stream().map(FlightInfo::getFlight).map(Flight::getIcao).collect(Collectors.joining(","));

        jsonObject.put("icaos", liveFlightsStr);

        return jsonObject.toString();
    }

    /**
     * Facilitates the creation and tracking of a mock flight with information in the provided request
     *
     * @param generateRequest Either a GenerateRequest or AirportGenerateRequest Object that contains the
     *                        necessary information to create a mock flight
     * @return The message that was sent through Kafka, which consists mock live coordinates and other mock flight information
     * or an error response.
     * @throws InterruptedException Sending a message using Kafka can trigger an InterruptedException
     */
    public String handleGenerateRequest(GenerateRequest generateRequest) throws InterruptedException {
        generationHappening = true;
        LOGGER.info(generateRequest.toString());

        // This block handles all the specific logic relating to generating a mock flight from airport to airport
        if (generateRequest instanceof AirportGenerateRequest) {
            // Creating a new object so that I don't have to cast every time.
            // Should still be referencing the same Object though
            AirportGenerateRequest airportGenerate = (AirportGenerateRequest) generateRequest;

            // Making sure there are airport names in the request before making the calls to aviation stack
            if (StringUtils.hasText(airportGenerate.getArriveAirport()) && StringUtils.hasText(airportGenerate.getArriveAirport())) {
                Airport originAirport = aviationStackClientCaller.getAirportInfoFromName(airportGenerate.getArriveAirport());
                Airport destinationAirport = aviationStackClientCaller.getAirportInfoFromName(airportGenerate.getArriveAirport());

                // Return an error response if information for either the origin or destination airports could not be retrieved from AviationStack
                if (originAirport == null || destinationAirport == null) {
                    String errorResponse = String.format("Airport information could not found with provided airport names. " +
                                    "Provided name for origin: %s. Provided name for destination: %s",
                            airportGenerate.getDepartAirport(), airportGenerate.getArriveAirport());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("error", errorResponse);

                    return jsonObject.toString();
                }

                // Setting the starting coordinates of the mock flight
                airportGenerate.setLongitude(originAirport.getLongitude());
                airportGenerate.setLatitude(originAirport.getLatitude());
                airportGenerate.setAltitude(DEFAULT_ALTITUDE);

                // Setting what should be the final coordinates of the mock flight
                // Used for generating the incremental change applied during each scheduled update
                airportGenerate.setFinalLongitude(destinationAirport.getLongitude());
                airportGenerate.setFinalLatitude(destinationAirport.getLatitude());
            }

            // Below logic determines and sets how much the longitude will change on each mock flight update
            boolean longIsNeg = airportGenerate.getLongitude() < 0;
            boolean finalLongIsNeg = airportGenerate.getFinalLongitude() < 0;

            float difference = 0;

            if (longIsNeg && finalLongIsNeg) {
                difference = airportGenerate.getFinalLongitude() + airportGenerate.getLongitude();
            } else if (longIsNeg) {
                difference = Math.abs(airportGenerate.getLongitude() - airportGenerate.getFinalLongitude());
            } else if (finalLongIsNeg) {
                difference = airportGenerate.getFinalLongitude() - airportGenerate.getLongitude();
            }
            airportGenerate.setLongitudeChange(difference / MAX_MOCK_FLIGHT_UPDATES);

            // Below logic determines and sets how much the latitude will change on each mock flight update
            boolean latIsNeg = airportGenerate.getLatitude() < 0;
            boolean finalLatIsNeg = airportGenerate.getFinalLatitude() < 0;

            if (latIsNeg && finalLatIsNeg) {
                difference = airportGenerate.getFinalLongitude() + airportGenerate.getLongitude();
            } else if (latIsNeg) {
                difference = Math.abs(airportGenerate.getLongitude() - airportGenerate.getFinalLongitude());
            } else if (finalLatIsNeg) {
                difference = airportGenerate.getFinalLongitude() - airportGenerate.getLongitude();
            }
            airportGenerate.setLatitudeChange(difference / MAX_MOCK_FLIGHT_UPDATES);
        }

        // Creating a key and adding mock flight to tracking Map
        String flightLabel = String.format("%s %s", generateRequest.getAirlineName().trim(), generateRequest.getFlightIcao().trim());
        generatedFlights.put(flightLabel, generateRequest);

        // Build message for Kafka
        String toBeSent = buildKafkaMessageFromGenerate(generateRequest);
        LOGGER.info("Sending: {}", toBeSent);

        // Send message through Kafka broker
        RecordMetadata metadata = KafkaProducer.runProducer(toBeSent);
        LOGGER.debug("Kafka metadata: {}", metadata);

        return toBeSent;
    }

    /**
     * Acquires information about the specific airport that corresponds to the provided airport name
     *
     * @param airportName The provided airport name
     * @return String value that consists of a JSON Object containing the longitude and latitude of the requested airport
     * or an error response.
     */
    public String handleAirportRequest(String airportName) {
        Airport airport = aviationStackClientCaller.getAirportInfoFromName(airportName);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("longitude", airport.getLongitude());
        jsonObject.put("latitude", airport.getLatitude());

        return jsonObject.toString();
    }

    /**
     * Helper method used to reduce repeated code.
     * Builds a message that will be sent through Kafka using information contained in the provided request object
     *
     * @param generateRequest Either a GenerateRequest or AirportGenerateRequest Object that contains the
     *                        necessary information to create a mock flight
     * @return A String that is ready to be sent through Kafka
     */
    private String buildKafkaMessageFromGenerate(GenerateRequest generateRequest) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("airline", generateRequest.getAirlineName());
        jsonObject.put("icao", generateRequest.getFlightIcao());

        // Didn't feel the need to populate all the Live properties for a mock flight
        Live liveObj = new Live();
        liveObj.setLongitude(generateRequest.getLongitude());
        liveObj.setLatitude(generateRequest.getLatitude());
        liveObj.setAltitude(generateRequest.getAltitude());

        // Need to provide a string that can be parsed as a JSON object for it to work in the front-end
        jsonObject.put("live", liveObj.getJSON());

        return jsonObject.toString();
    }

    /**
     * Helper method used to reduce repeated code.
     * Builds a message that will be sent through Kafka using information contained in the provided FlightInfo object and flight ICAO number
     *
     * @param flightInfo The provided FlightInfo Object
     * @param flightIcao The provided Flight ICAO number
     * @return A String that is ready to be sent through Kafka
     */
    private String buildKafkaMessageFromFlightInfo(FlightInfo flightInfo, String flightIcao) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("airline", flightInfo.getAirline().getName());
        jsonObject.put("icao", flightIcao);
        jsonObject.put("live", flightInfo.getLive());

        return jsonObject.toString();
    }

    /**
     * Scheduled Method: Runs every 1 minute (60000ms) after an initial delay of 1 minute (60000ms) to account for AviationStack slowness
     * Facilitates the front-end updating of any number of tracked live flights and determines when they should no longer be tracked
     * Iteratively loops through all flight icaos that have been encountered and are still eligible for updating
     * Acquires new flight information from AviationStack and from that builds and sends a new message through Kafka
     *
     * @throws InterruptedException Sending a message using Kafka can trigger an InterruptedException
     */
    @Scheduled(fixedRate = 60000, initialDelay = 60000)
    public void updateFlightInfo() throws InterruptedException {
        // Don't attempt any updating if there is nothing to update
        if (!StringUtils.hasText(this.prev_flight_icao) || this.flightIcaos.isEmpty()) {
            return;
        }

        // A list to keep track of the Flights that need to be removed from tracking consideration
        List<String> toBeRemoved = new ArrayList<>();

        for (String flightIcao : flightIcaos) {
            int count;
            liveFlightUpdateRecord.putIfAbsent(flightIcao, 0);
            count = liveFlightUpdateRecord.get(flightIcao) + 1;

            LOGGER.debug("Live Flight ICAO: {} | Update Count: {}", flightIcao, count);

            if (count > MAX_LIVE_FLIGHT_UPDATES) {
                LOGGER.info("Count Threshold has been reached. Terminating flight tracking for: {}", flightIcao);
                toBeRemoved.add(flightIcao);
                liveFlightUpdateRecord.remove(flightIcao);
                continue;
            }

            LOGGER.info("Getting updated Live Flight information for {}", flightIcao);
            FlightInfo response = aviationStackClientCaller.getFlightFromIcao(flightIcao);

            String toBeSent = buildKafkaMessageFromFlightInfo(response, flightIcao);
            LOGGER.info("Sending: {}", toBeSent);

            RecordMetadata metadata = KafkaProducer.runProducer(toBeSent);
            LOGGER.debug("Kafka metadata: {}", metadata);

            liveFlightUpdateRecord.put(flightIcao, count);
        }

        flightIcaos.removeAll(toBeRemoved);
    }

    /**
     * Scheduled Method: Runs every 30 seconds (30000ms) after an initial delay 1 minute (60000ms) to account for the
     * time it takes to put data in the mock flight creation form
     * Facilitates the front-end updating of any number of 'tracked' mock flights and determining when they should no longer be 'tracked'
     * Iteratively loops through all GenerateRequests that have been received and are still eligible for updating
     * Changes flight coordinates based on the provided/calculated rate of change and from that builds and sends a new message through Kafka
     *
     * @throws InterruptedException Sending a message using Kafka can trigger an InterruptedException
     */
    @Scheduled(fixedRate = 30000, initialDelay = 60000)
    public void updateGeneratedFlightInfo() throws InterruptedException {
        // Don't attempt any updating if there is nothing to update
        if (!generationHappening || generatedFlights.isEmpty()) {
            return;
        }

        List<String> toBeRemoved = new ArrayList<>();

        for (GenerateRequest generateRequest : generatedFlights.values()) {
            int count;
            String flightLabel = String.format("%s %s", generateRequest.getAirlineName().trim(), generateRequest.getFlightIcao().trim());
            mockFlightUpdateRecord.putIfAbsent(flightLabel, 0);
            count = mockFlightUpdateRecord.get(flightLabel) + 1;

            LOGGER.debug("Mock Flight Label: {} | Update Count: {}", flightLabel, count);

            if (count > MAX_MOCK_FLIGHT_UPDATES) {
                LOGGER.info("Count Threshold has been reached. Terminating flight tracking for mock: {}", flightLabel);
                toBeRemoved.add(flightLabel);
                mockFlightUpdateRecord.remove(flightLabel);
                continue;
            }

            float longChange = generateRequest.getLatitudeChange();
            float latChange = generateRequest.getLatitudeChange();
            float altChange = generateRequest.getAltitudeChange();

            float currentLong = generateRequest.getLongitude();
            float currentLat = generateRequest.getLatitude();
            float currentAlt = generateRequest.getAltitude();

            // Could be uncommented if wanted later. Honestly should already be happening like this due to how generation is set up
            /*if (generateRequest instanceof AirportGenerateRequest) {
                // Determine if current coordinates match the expected final coordinates
                float absCurLong = Math.abs(currentLong);
                float absFinalLong = ((AirportGenerateRequest) generateRequest).getFinalLongitude();
                float absCurLat = Math.abs(currentLat);
                float absFinalLat = ((AirportGenerateRequest) generateRequest).getFinalLatitude();

                if (Math.abs(absCurLong - absFinalLong) < .1 && Math.abs(absCurLat - absFinalLat) < .1) {
                    LOGGER.info("Expected Final Coordinates have been reached. Terminating flight tracking for mock: {}", flightLabel);
                    toBeRemoved.add(flightLabel);
                    mockFlightUpdateRecord.remove(flightLabel);
                    continue;
                }
            }*/

            generateRequest = generateRequest
                    .setLongitude(currentLong + longChange)
                    .setLatitude(currentLat + latChange)
                    .setAltitude(currentAlt + altChange);

            String toBeSent = buildKafkaMessageFromGenerate(generateRequest);
            LOGGER.info("Sending: {}", toBeSent);

            RecordMetadata metadata = KafkaProducer.runProducer(toBeSent);
            LOGGER.debug("Kafka metadata: {}", metadata);

            mockFlightUpdateRecord.put(flightLabel, count);
        }

        // Removing all mocked flights that are no longer eligible for 'tracking' from the corresponding Map
        toBeRemoved.forEach(generatedFlights::remove);

        // Probably redundant but checking the status of a boolean value could be marginally better than checking if a list is empty
        if (generatedFlights.isEmpty()) {
            this.generationHappening = false;
        }
    }
}
