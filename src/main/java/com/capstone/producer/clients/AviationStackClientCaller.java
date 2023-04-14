package com.capstone.producer.clients;

import com.capstone.producer.common.bindings.FlightInfo;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @file
 * @author Nick Horihan
 * @version 1.0.0
 *
 * @section DESCRIPTION
 *
 * This class represents an AviationStack Client Caller
 */
public class AviationStackClientCaller {
    private static final Logger LOGGER = LoggerFactory.getLogger(AviationStackClientCaller.class);

    private static final String SERVICE_NAME = "/flights";


    private RestTemplate client;

    private String baseUrl;

    private String key;

    private ObjectMapper objectMapper;

    private HttpHeaders headers;

    /**
     * This is the contructor for a class that will call the Aviation
     * Stack API
     *
     * @param client  RestTemplate
     * @param baseUrl baseUrl of aviation stack
     * @param key     API key
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
     * This function is used to return a JSON NODE when given a flightICAO number
     *
     * @param flightIcao flight Icao number
     * @return JsonNode containing flight information
     */
    public JsonNode getFlightByIcao(String flightIcao) {
        UriComponents urlComponents = UriComponentsBuilder.fromHttpUrl(baseUrl + SERVICE_NAME)
                .queryParam("access_key", key)
                .queryParam("flight_icao", flightIcao)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            JsonNode response = client.exchange(urlComponents.toString(), HttpMethod.GET, requestEntity, JsonNode.class).getBody();
            return response;
        } catch (HttpClientErrorException e) {
            throw new RuntimeException(e);
        } catch (HttpServerErrorException e) {
            throw new RuntimeException(e);
        }

    }

    public List<FlightInfo> getAllActiveFlights() {
        UriComponents urlComponents = UriComponentsBuilder.fromHttpUrl(baseUrl + SERVICE_NAME)
                .queryParam("access_key", key)
                .queryParam("flight_status", "active")
                .build();

        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            JsonNode rootNode = client.exchange(urlComponents.toString(), HttpMethod.GET, requestEntity, JsonNode.class).getBody();

            if (rootNode == null) {
                return Collections.emptyList();
            }

            String dataNodeStr = rootNode.path("data").toString();

            try (JsonParser jsonParser = objectMapper.createParser(dataNodeStr)) {
                // Skipping the pagination block
                jsonParser.nextToken();

                List<FlightInfo> flightInfos = new ArrayList<>();

                while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
                    FlightInfo flightInfo = jsonParser.readValueAs(FlightInfo.class);
                    flightInfos.add(flightInfo);
                }

                if (flightInfos.isEmpty()) {
                    LOGGER.error("No active flights found. This shouldn't happen!");
                }

                return flightInfos;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<FlightInfo> getAllActiveFlightsWithLive() {
        List<FlightInfo> flightInfos = new ArrayList<>();
        int count = 0;
        int offsetVal = 0;


        do {
            UriComponents urlComponents = UriComponentsBuilder.fromHttpUrl(baseUrl + SERVICE_NAME)
                    .queryParam("access_key", key)
                    .queryParam("flight_status", "active")
                    .queryParam("offset", offsetVal)
                    .build();

            try {
                HttpEntity<String> requestEntity = new HttpEntity<>(headers);
                JsonNode rootNode = client.exchange(urlComponents.toString(), HttpMethod.GET, requestEntity, JsonNode.class).getBody();

                if (rootNode == null) {
                    return Collections.emptyList();
                }

                String dataNodeStr = rootNode.path("data").toString();

                try (JsonParser jsonParser = objectMapper.createParser(dataNodeStr)) {
                    // Skipping the pagination block
                    jsonParser.nextToken();


                    while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
                        FlightInfo flightInfo = jsonParser.readValueAs(FlightInfo.class);
                        if (flightInfo.getLive() != null) {
                            flightInfos.add(flightInfo);
                        }
                    }

                    if (flightInfos.isEmpty()) {
                        LOGGER.error("No active flights found. This shouldn't happen!");
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            offsetVal = ++count * 100;
        } while (flightInfos.isEmpty() || count < 3);

        return flightInfos;
    }
}
