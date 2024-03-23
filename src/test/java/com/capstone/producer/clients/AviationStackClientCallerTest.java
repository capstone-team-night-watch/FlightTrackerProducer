package com.capstone.producer.clients;

import com.capstone.producer.common.bindings.aviationstack.AirportAviation;
import com.capstone.producer.common.bindings.aviationstack.FlightInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AviationStackClientCallerTest {
    private final String BASE_URL = "http://localhost:8080/services";
    private static final String FLIGHT_INFO_DATA_JSON = "aviation_flight_info_data.json";
    private static final String FLIGHTS_INFO_DATA_JSON = "aviation_flights_info_data.json";
    private static final String AVIATION_AIRPORT_DATA_JSON = "aviation_airport_data.json";
    private static final String AVIATION_AIRPORTS_DATA_JSON = "aviation_airports_data.json";

    @Mock
    private RestTemplate client;

    @Mock
    private ResponseEntity<JsonNode> responseEntity;

    private AviationStackClientCaller aviationStackClientCaller;

    private FlightInfo flightInfo;
    private JsonNode flightInfoData;
    private JsonNode flightsInfoData;
    private AirportAviation aviationAirport;
    private JsonNode aviationAirportsData;

    @Before
    public void setUp() throws IOException {
        aviationStackClientCaller = new AviationStackClientCaller(client, BASE_URL, "key");
        flightInfo = readJson(FLIGHT_INFO_DATA_JSON, FlightInfo.class);
        flightInfoData = readJsonNode(FLIGHT_INFO_DATA_JSON);
        flightsInfoData = readJsonNode(FLIGHTS_INFO_DATA_JSON);
        aviationAirport = readJson(AVIATION_AIRPORT_DATA_JSON, AirportAviation.class);
        aviationAirportsData = readJsonNode(AVIATION_AIRPORTS_DATA_JSON);
    }

    @Test
    public void getAllActiveFlightsShouldReturnEmpty() {
        mockResponse(null);
        assertEquals(0, aviationStackClientCaller.getAllActiveFlightsWithLive().size());
    }

    @Test
    public void getAllActiveFlightsShouldReturnList() {
        mockResponse(flightsInfoData);
        assertEquals(5, aviationStackClientCaller.getAllActiveFlightsWithLive().size());
    }

    @Test
    public void getAirportInfoFromNameShouldReturnNull() {
        mockResponse(null);
        assertNull(aviationStackClientCaller.getAirportInfoFromName(null));
    }

    @Test
    public void getAirportInfoFromNameShouldReturnAirport() {
        mockResponse(aviationAirportsData);
        assertEquals(aviationAirport, aviationStackClientCaller.getAirportInfoFromName("Anaa"));
    }

    private <T> T readJson(String fileName, Class<T> valueType) throws IOException {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(fileName)) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(in, valueType);
        }
    }

    private JsonNode readJsonNode(String fileName) throws IOException {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(fileName)) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(in);
        }
    }

    private void mockResponse(JsonNode body) {
        when(client.exchange(anyString(), any(), any(), eq(JsonNode.class))).thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(body);
    }

}
