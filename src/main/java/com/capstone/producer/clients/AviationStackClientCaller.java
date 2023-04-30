package com.capstone.producer.clients;

import com.capstone.producer.common.bindings.aviationstack.Airport;
import com.capstone.producer.common.bindings.aviationstack.FlightInfo;
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
 * Client Caller class that facilitates interaction with the Aviation Stack API
 */
public class AviationStackClientCaller {
    private static final Logger LOGGER = LoggerFactory.getLogger(AviationStackClientCaller.class);

    private static final String FLIGHTS_SERVICE_NAME = "/flights";

    private static final String AIRPORTS_SERVICE_NAME = "/airports";

    /**
     * Spring provided RestTemplate Object which is responsible for making the connection the API
     */
    private final RestTemplate client;

    /**
     * The base aviation stack url used for making the connection
     */
    private final String baseUrl;

    /**
     * The key needed to make requests to the Aviation Stack API
     */
    private final String key;

    /**
     * An ObjectMapper used for mapping JSON to Objects and vice-versa
     */
    private final ObjectMapper objectMapper;

    /**
     * The HttpHeaders Object used when making a request to the API
     */
    private final HttpHeaders headers;

    /**
     * This is the constructor for this client caller class
     *
     * @param client  RestTemplate Object
     * @param baseUrl The base aviation stack url used for making the connection
     * @param key     Aviation Stack API key
     */
    public AviationStackClientCaller(RestTemplate client, String baseUrl, String key) {
        this.client = client;
        this.baseUrl = baseUrl;
        this.key = key;
        objectMapper = new ObjectMapper();

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    }

    /**
     * Gets flight information from the API given a flight ICAO number
     *
     * @param flightIcao The provided flight ICAO number
     * @return A FlightInfo Object that corresponds to the flight with a matching ICAO number
     */
    public FlightInfo getFlightFromIcao(String flightIcao) {
        UriComponents urlComponents = UriComponentsBuilder.fromHttpUrl(baseUrl + FLIGHTS_SERVICE_NAME)
                .queryParam("access_key", key)
                .queryParam("flight_icao", flightIcao)
                .build();

        try {
            String dataNodeStr = acquireDataNodeStringFromAPIResponse(urlComponents);

            // The response did not contain any usable JSON Objects
            if (!StringUtils.hasText(dataNodeStr)) {
                return null;
            }

            // Creates a JSON parser from the JSON 'data' array Object
            try (JsonParser jsonParser = objectMapper.createParser(dataNodeStr)) {
                // Traversing through the JSON array by finding the beginning of JSON Objects
                while (jsonParser.nextToken() != JsonToken.START_OBJECT) {
                    // nextToken() will return null if there isn't a next token.
                    // The loop needs to be broken in that case otherwise, it would be infinite
                    if (jsonParser.nextToken() == null) {
                        break;
                    }
                }

                // Once a JSON Object is found, it can be mapped to a FlightInfo Object
                FlightInfo flightInfo = jsonParser.readValueAs(FlightInfo.class);

                // Makes sure a valid FlightInfo is obtained
                if (flightInfo == null || flightInfo.getFlight() == null || flightInfo.getLive() == null) {
                    LOGGER.error("No flight with live information found given this ICAO: {}", flightIcao);
                    return null;
                }

                return flightInfo;
            } catch (IOException e) {
                LOGGER.error("An IOException was caught while trying to process the API response in getFlightFromIcao(). " +
                        "Details: {}", e.toString());
                return null;
            }

        } catch (Exception e) {
            LOGGER.error("An Exception was caught while trying to acquire live flight information. Details: {}", e.toString());
            return null;
        }

    }

    /**
     * Gets flight information that correspond to flights that contain live information from the API
     *
     * @return A list of FlightInfo Objects that contain non-null Live Objects
     */
    public List<FlightInfo> getAllActiveFlightsWithLive() {
        List<FlightInfo> flightInfos = new ArrayList<>();

        // Pagination count
        int count = 0;

        // Value actually used in the request to paginate through the responses
        // Can only get 100 responses at a time with api plan
        int offsetVal = 0;

        do {
            UriComponents urlComponents = UriComponentsBuilder.fromHttpUrl(baseUrl + FLIGHTS_SERVICE_NAME)
                    .queryParam("access_key", key)
                    .queryParam("flight_status", "active")
                    .queryParam("offset", offsetVal)
                    .build();

            try {

                String dataNodeStr = acquireDataNodeStringFromAPIResponse(urlComponents);

                // The response did not contain any usable JSON Objects
                if (!StringUtils.hasText(dataNodeStr)) {
                    LOGGER.error("No active flights with live information were found");
                    return Collections.emptyList();
                }

                // Creates a JSON parser from the JSON 'data' array Object
                try (JsonParser jsonParser = objectMapper.createParser(dataNodeStr)) {
                    // Traversing through the JSON array by finding the beginning of JSON Objects
                    while (jsonParser.nextToken() != JsonToken.START_OBJECT) {
                        // Once a JSON Object is found, it can be mapped to a FlightInfo Object
                        FlightInfo flightInfo = jsonParser.readValueAs(FlightInfo.class);

                        // Only add a flight to the list if it has live information
                        if (flightInfo.getLive() != null) {
                            flightInfos.add(flightInfo);
                        }
                    }

                    if (flightInfos.isEmpty()) {
                        LOGGER.debug("No active flights found for pagination {}", count);
                    }
                }

            } catch (Exception e) {
                LOGGER.error("An Exception was caught while trying to acquire live flight information. Details: {}", e.toString());
                return Collections.emptyList();
            }

            offsetVal = ++count * 100;
        } while (flightInfos.size() < 3);

        // return only at max the first 5 live flights acquired.
        // Doing this to prevent clutter on the page. 5 flights is more than sufficient for testing
        return flightInfos.subList(0, Math.max(5, flightInfos.size()));
    }

    /**
     * Get airport information from the API that matches the provided airport name
     *
     * @param airportName The provided airport name
     * @return An Airport Object that has latitude and longitude information
     */
    public Airport getAirportInfoFromName(String airportName) {
        UriComponents urlComponents = UriComponentsBuilder.fromHttpUrl(baseUrl + AIRPORTS_SERVICE_NAME)
                .queryParam("access_key", key)
                .queryParam("search", airportName)
                .build();

        try {
            String dataNodeStr = acquireDataNodeStringFromAPIResponse(urlComponents);

            // The response did not contain any usable JSON Objects
            if (!StringUtils.hasText(dataNodeStr)) {
                return null;
            }

            // Creates a JSON parser from the JSON 'data' array Object
            try (JsonParser jsonParser = objectMapper.createParser(dataNodeStr)) {
                // Traversing through the JSON array by finding the beginning of JSON Objects
                while (jsonParser.nextToken() != JsonToken.START_OBJECT) {
                    // nextToken() will return null if there isn't a next token.
                    // The loop needs to be broken in that case otherwise, it would be infinite
                    if (jsonParser.nextToken() == null) {
                        break;
                    }
                }

                // Once a JSON Object is found, it can be mapped to a FlightInfo Object
                Airport airportInfo = jsonParser.readValueAs(Airport.class);

                // Makes sure a valid Airport is obtained
                if (airportInfo == null || airportInfo.getLatitude() == 0 || airportInfo.getLongitude() == 0) {
                    LOGGER.error("No relevant airport information found.");
                    return null;
                }

                return airportInfo;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            LOGGER.error("Exception caught: {}", e.toString());
            throw e;
        }
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

        return rootNode.path("data").toString();
    }

}
