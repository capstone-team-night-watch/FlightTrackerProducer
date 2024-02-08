package com.capstone.producer.clients;

import com.capstone.producer.common.bindings.aero.FlightInfoFa_Id;
import com.capstone.producer.common.bindings.aero.Operator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Nick Horihan
 * @version 1.0.0
 * @file
 * @section DESCRIPTION
 * <p>
 * Client Caller class that facilitates interaction with the FlightAware's Aero API
 */
public class AeroApiClientCaller {
    private static final Logger LOGGER = LoggerFactory.getLogger(AeroApiClientCaller.class);

    private static final String FLIGHTS_SERVICE_NAME = "/flights/search";

    /**
     * Spring provided RestTemplate Object which is responsible for making the connection the API
     */
    private final RestTemplate client;

    /**
     * The base aviation stack url used for making the connection
     */
    private final String baseUrl;

    /**
     * An ObjectMapper used for mapping JSON to Objects and vice-versa
     */
    private final ObjectMapper objectMapper;

    /**
     * The HttpHeaders Object used when making a request to the API
     */
    private final HttpHeaders headers;

    /**
     * Used when logging out flight information exception
     */
    private final String LIVE_FLIGHT_INFORMATION_EXCEPTION_MSG = "An Exception was caught while trying to acquire live flight information. Details: {}";

    /**
     * Used when logging out root messages
     */
    private final String ROOT_MSG = "root: {}";

    /**
     * This is the constructor for this client caller class
     *
     * @param client  RestTemplate Object
     * @param baseUrl The base aviation stack url used for making the connection
     * @param key     Aviation Stack API key
     */
    public AeroApiClientCaller(RestTemplate client, String baseUrl, String key) {
        this.client = client;
        this.baseUrl = baseUrl;
        objectMapper = new ObjectMapper();
        //objectMapper.enable();

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-apikey", key);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    }

    /**
     * Gets flight information from the API given a flight FaId
     *
     * @param flightFaId The provided flight aware id number
     * @return A FlightInfo_Id Object that corresponds to the flight with a matching FaId
     */
    public FlightInfoFa_Id getFlightFromFaId(String flightFaId) {

        try {
            String url = String.format("%s/flights/%s/position", baseUrl, flightFaId);


            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            JsonNode rootNode = client.exchange(url, HttpMethod.GET, requestEntity, JsonNode.class).getBody();

            if (rootNode == null) {
                return null;
            }

            LOGGER.info(ROOT_MSG, rootNode);

            // Creates a JSON parser from the JSON 'data' array Object
            try (JsonParser jsonParser = objectMapper.createParser(rootNode.toString())) {
                // Traversing through the JSON array by finding the beginning of JSON Objects
                while (jsonParser.nextToken() != JsonToken.START_OBJECT) {
                    // nextToken() will return null if there isn't a next token.
                    // The loop needs to be broken in that case otherwise, it would be infinite
                    if (jsonParser.nextToken() == null) {
                        break;
                    }
                }

                // Once a JSON Object is found, it can be mapped to a FlightInfo Object
                FlightInfoFa_Id flightInfo = jsonParser.readValueAs(FlightInfoFa_Id.class);

                // Makes sure a valid FlightInfo is obtained
                if (flightInfo == null || flightInfo.getFa_flight_id() == null || flightInfo.getLast_position() == null) {
                    LOGGER.error("No flight with live information found given this ICAO: {}", flightFaId);
                    return null;
                }

                flightInfo.setFullJson(rootNode.toString());
                return flightInfo;
            } catch (IOException e) {
                LOGGER.error("An IOException was caught while trying to process the API response in getFlightFromIcao(). " +
                        "Details: {}", e.toString());
                return null;
            }

        } catch (Exception e) {
            LOGGER.error(LIVE_FLIGHT_INFORMATION_EXCEPTION_MSG, e.toString());
            return null;
        }

    }

    /**
     * Gets flight information from the API given a flight ident (icao) number
     * @param flightIdent The ident code (icao) being requested
     * @return A String value corresponding the JSON response from the API
     */
    public String getFlightFromIdent(String flightIdent) {
        try {
            String url = String.format("%s/flights/%s", baseUrl, flightIdent);


            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            JsonNode rootNode = client.exchange(url, HttpMethod.GET, requestEntity, JsonNode.class).getBody();

            if (rootNode == null) {
                return null;
            }

            LOGGER.info(ROOT_MSG, rootNode);



            String flightsNode = rootNode.path("flights").get(0).toString();

            LOGGER.info("flightsNode: {}", flightsNode);

            return flightsNode;

        } catch (Exception e) {
            LOGGER.error(LIVE_FLIGHT_INFORMATION_EXCEPTION_MSG, e.toString());
            return null;
        }

    }

    /**
     * Gets operator (Airline) information from a given operator id
     * @param operatorId The provided operator id
     * @return An Operator object corresponding to the given id
     */
    public Operator getOperatorFromId(String operatorId) {
        try {
            String url = String.format("%s/operators/%s", baseUrl, operatorId);


            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            JsonNode rootNode = client.exchange(url, HttpMethod.GET, requestEntity, JsonNode.class).getBody();

            if (rootNode == null) {
                return null;
            }

            LOGGER.info(ROOT_MSG, rootNode);

            // Creates a JSON parser from the JSON 'data' array Object
            try (JsonParser jsonParser = objectMapper.createParser(rootNode.toString())) {
                // Traversing through the JSON array by finding the beginning of JSON Objects
                while (jsonParser.nextToken() != JsonToken.START_OBJECT) {
                    // nextToken() will return null if there isn't a next token.
                    // The loop needs to be broken in that case otherwise, it would be infinite
                    if (jsonParser.nextToken() == null) {
                        break;
                    }
                }

                // Once a JSON Object is found, it can be mapped to a FlightInfo Object
                Operator operatorInfo = jsonParser.readValueAs(Operator.class);

                // Makes sure a valid FlightInfo is obtained
                if (operatorInfo == null || !StringUtils.hasText(operatorInfo.getName())) {
                    LOGGER.error("No airport information found given this id: {}", operatorId);
                    return null;
                }

                operatorInfo.setFullJson(rootNode.toString());
                return operatorInfo;
            } catch (IOException e) {
                LOGGER.error("An IOException was caught while trying to process the API response in getFlightFromIcao(). " +
                        "Details: {}", e.toString());
                return null;
            }

        } catch (Exception e) {
            LOGGER.error(LIVE_FLIGHT_INFORMATION_EXCEPTION_MSG, e.toString());
            return null;
        }

    }

    /**
     * Gets flight information that correspond to flights that contain live information from the API
     *
     * @return A list of FlightInfo Objects that contain non-null Live Objects
     */
    public List<FlightInfoFa_Id> getAllActiveFlightsWithLive() {
        List<FlightInfoFa_Id> flightInfos = new ArrayList<>();

        // Pagination count
        int count = 0;

        do {
            UriComponents urlComponents = UriComponentsBuilder.fromHttpUrl(baseUrl + FLIGHTS_SERVICE_NAME)
                    .queryParam("query", "-aboveAltitude 50")
                    .build();

            try {

                String flightNodeStr = acquireDataNodeStringFromAPIResponse(urlComponents);

                // The response did not contain any usable JSON Objects
                if (!StringUtils.hasText(flightNodeStr)) {
                    LOGGER.error("No active flights with live information were found");
                    return Collections.emptyList();
                }

                LOGGER.debug("node: {}", flightNodeStr);

                // Creates a JSON parser from the JSON 'data' array Object
                try (JsonParser jsonParser = objectMapper.createParser(flightNodeStr)) {
                    // Traversing through the JSON array by finding the beginning of JSON Objects
                    // Skipping the array block
                    jsonParser.nextToken();
                    while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
                        // Once a JSON Object is found, it can be mapped to a FlightInfo Object
                        FlightInfoFa_Id flightInfo = jsonParser.readValueAs(FlightInfoFa_Id.class);

                        // Only add a flight to the list if it has live information
                        if (flightInfo.getLast_position() != null) {
                            LOGGER.debug("Live flight found: {}", flightInfo.getIdent());
                            flightInfos.add(flightInfo);
                        }
                    }

                    if (flightInfos.isEmpty()) {
                        LOGGER.debug("No active flights found for pagination {}", count);
                    }
                }

            } catch (Exception e) {
                LOGGER.error(LIVE_FLIGHT_INFORMATION_EXCEPTION_MSG, e.toString());
                return Collections.emptyList();
            }

        } while (flightInfos.size() < 3);

        // return only at max the first 5 live flights acquired.
        // Doing this to prevent clutter on the page. 5 flights is more than sufficient for testing
        return flightInfos.subList(0, Math.min(5, flightInfos.size()));
    }

    /**
     * Helper method to reduce duplicated code. Makes the GET request to AviationStack and parses the response.
     *
     * @param urlComponents UriComponents Object that contains information about the request
     * @return A String value representing the information contained in the 'data' JSON Object contained in the response
     */
    private String acquireDataNodeStringFromAPIResponse(UriComponents urlComponents) throws RestClientException {
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        JsonNode rootNode = client.exchange(urlComponents.toString(), HttpMethod.GET, requestEntity, JsonNode.class).getBody();

        if (rootNode == null) {
            LOGGER.error("Nothing parsable obtained from AviationStack");
            return null;
        }

        return rootNode.path("flights").toString();
    }

}
