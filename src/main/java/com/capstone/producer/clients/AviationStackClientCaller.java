package com.capstone.producer.clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

public class AviationStackClientCaller {
    private static final Logger LOGGER = LoggerFactory.getLogger(AviationStackClientCaller.class);

    private static final String SERVICE_NAME = "/flights";


    private RestTemplate client;

    private String baseUrl;

    private String key;
    public AviationStackClientCaller(RestTemplate client, String baseUrl, String key){
        this.client = client;
        this.baseUrl = baseUrl;
        this.key = key;
    }

    public JsonNode getFlight(String flightIcao) {
        UriComponents urlComponents = UriComponentsBuilder.fromHttpUrl(baseUrl+SERVICE_NAME)
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
}
