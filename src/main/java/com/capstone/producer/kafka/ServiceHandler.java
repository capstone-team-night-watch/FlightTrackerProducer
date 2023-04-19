package com.capstone.producer.kafka;

import com.capstone.producer.clients.AviationStackClientCaller;
import com.capstone.producer.common.bindings.Flight;
import com.capstone.producer.common.bindings.FlightInfo;
import com.capstone.producer.common.bindings.GenerateRequest;
import com.capstone.producer.common.bindings.Live;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class ServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceHandler.class);

    @Autowired
    private AviationStackClientCaller aviationStackClientCaller;

    private String prev_flight_icao;
    private final List<String> flightIcaos;

    private boolean generationHappening;
    private final Map<String, GenerateRequest> generatedFlights;

    public ServiceHandler() {
        flightIcaos = new CopyOnWriteArrayList<>();
        generatedFlights = new ConcurrentHashMap<>();
    }


    public String handleFlightIcao(String flight_icao) {
        LOGGER.debug("Received request: {}", flight_icao);
        this.prev_flight_icao = flight_icao;
        this.flightIcaos.add(flight_icao);

        //JsonNode response = aviationStackClientCaller.getFlightByIcao(flight_icao);
        //String jsonAsString = objectMapper.writeValueAsString(response);
        //RecordMetadata metadata = KafkaProducerExample.runProducer(jsonAsString);

        FlightInfo response = aviationStackClientCaller.getFlightByIcao_flightInfo(flight_icao);


        if (response == null || response.getLive() == null) {
            return "NO DATA";
        }

        LOGGER.info(response.toString());

        String toBeSent = buildKafkaMessageFromFlightInfo(response, flight_icao);
        LOGGER.info("Sending: {}", toBeSent);

        RecordMetadata metadata = KafkaProducerExample.runProducer(toBeSent);
        LOGGER.info("Kafka metadata: {}", metadata);
        //Return response to user on page
        return toBeSent;
    }

    public String handleLiveRequest() {
        this.prev_flight_icao = "";
        List<FlightInfo> liveFlights = aviationStackClientCaller.getAllActiveFlightsWithLive();

        String liveFlightsStr = liveFlights.stream().map(FlightInfo::getFlight).map(Flight::getIcao).collect(Collectors.joining(","));

        //return objectMapper.convertValue(liveFlightsStr, JsonNode.class).toString();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("icaos", liveFlightsStr);

        return jsonObject.toString();
    }

    public String handleGenerateRequest(GenerateRequest generateRequest) {
        generationHappening = true;
        LOGGER.info(generateRequest.toString());
        String flightLabel = String.format("%s %s", generateRequest.getAirlineName().trim(), generateRequest.getFlightIcao().trim());
        generatedFlights.put(flightLabel, generateRequest);

        String toBeSent = buildKafkaMessageFromGenerate(generateRequest);
        LOGGER.info("Sending: {}", toBeSent);

        RecordMetadata metadata = KafkaProducerExample.runProducer(toBeSent);
        LOGGER.info("Kafka metadata: {}", metadata);

        //Return response to user on page
        return toBeSent;
    }

    private String buildKafkaMessageFromGenerate(GenerateRequest generateRequest) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("airline",generateRequest.getAirlineName());
        jsonObject.put("icao", generateRequest.getFlightIcao());

        Live liveObj = new Live();
        liveObj.setLongitude(generateRequest.getLongitude());
        liveObj.setLatitude(generateRequest.getLatitude());
        liveObj.setAltitude(generateRequest.getAltitude());

        jsonObject.put("live", liveObj);

        return jsonObject.toString();
    }

    private String buildKafkaMessageFromFlightInfo(FlightInfo flightInfo, String flightIcao) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("airline", flightInfo.getAirline().getName());
        jsonObject.put("icao", flightIcao);
        jsonObject.put("live", flightInfo.getLive());

        return jsonObject.toString();
    }

    @Scheduled(fixedRate = 30000, initialDelay = 60000)
    public void updateFlightInfo() {
        if (this.prev_flight_icao == null || this.prev_flight_icao.isEmpty() || this.flightIcaos.isEmpty()) {
            return;
        }

        for (String flightIcao : flightIcaos) {
            LOGGER.info("Updating Live information for {}", flightIcao);
            FlightInfo response = aviationStackClientCaller.getFlightByIcao_flightInfo(flightIcao);
            String toBeSent = buildKafkaMessageFromFlightInfo(response, flightIcao);
            LOGGER.info("Sending: {}", toBeSent);

            RecordMetadata metadata = KafkaProducerExample.runProducer(toBeSent);
            LOGGER.info("Kafka metadata: {}", metadata);
        }
    }

    @Scheduled(fixedRate = 30000, initialDelay = 60000)
    public void updateGeneratedFlightInfo() {
        if (!generationHappening || generatedFlights.isEmpty()) {
            return;
        }

        for (GenerateRequest generateRequest : generatedFlights.values()) {
            float longChange = generateRequest.getLatitudeChange();
            float latChange = generateRequest.getLatitudeChange();
            float altChange = generateRequest.getAltitudeChange();

            float currentLong = generateRequest.getLongitude();
            float currentLat = generateRequest.getLatitude();
            float currentAlt = generateRequest.getAltitude();

            generateRequest = generateRequest
                    .setLongitude(currentLong + longChange)
                    .setLatitude(currentLat + latChange)
                    .setAltitude(currentAlt + altChange);

            String toBeSent = buildKafkaMessageFromGenerate(generateRequest);
            LOGGER.info("Sending: {}", toBeSent);

            RecordMetadata metadata = KafkaProducerExample.runProducer(toBeSent);
            LOGGER.info("Kafka metadata: {}", metadata);
        }
    }
}
