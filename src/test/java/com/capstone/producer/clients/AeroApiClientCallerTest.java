package com.capstone.producer.clients;

import com.capstone.producer.common.bindings.aero.FlightInfoFaid;
import com.capstone.producer.common.bindings.aero.Operator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AeroApiClientCallerTest {

    private static final String BASE_URL = "http://localhost:8080/services";
    private static final String FLIGHT_DATA_JSON = "flight_data.json";
    private static final String FLIGHTS_DATA_JSON = "flights_data.json";
    private static final String OPERATOR_JSON = "operator.json";

    @Mock
    private RestTemplate client;

    @Mock
    private ResponseEntity<JsonNode> responseEntity;

    @InjectMocks
    private AeroApiClientCaller aeroApiClientCaller;

    private FlightInfoFaid flightInfoFaid;
    private Operator operator;
    private JsonNode flightData;
    private JsonNode flightsData;
    private JsonNode operatorData;

    @BeforeEach
    public void setUp() throws IOException {
        aeroApiClientCaller = new AeroApiClientCaller(client, BASE_URL, "key");
        flightInfoFaid = readJson(FLIGHT_DATA_JSON, FlightInfoFaid.class);
        operator = readJson(OPERATOR_JSON, Operator.class);
        operatorData = readJsonNode(OPERATOR_JSON);
        flightData = readJsonNode(FLIGHT_DATA_JSON);
        flightsData = readJsonNode(FLIGHTS_DATA_JSON);
    }

    @Test
    public void getFlightFromNoFlightIdShouldReturnNull() {
        mockResponse(null);
        assertNull(aeroApiClientCaller.getFlightFromFaId(null));
    }

    @Test
    public void getFlightFromFaIdShouldReturnFlightInfoIdObject() {
        mockResponse(flightData);
        assertEquals(flightInfoFaid, aeroApiClientCaller.getFlightFromFaId("AIC102").setFullJson(null));
    }

    @Test
    public void getFlightFromIndentShouldReturnNull() {
        mockResponse(null);
        assertNull(aeroApiClientCaller.getFlightFromIdent(null));
    }

    @Test
    public void getFlightFromIndentShouldReturnValid() {
        mockResponse(flightsData);
        assertEquals(flightsData.path("flights").get(0).toString(), aeroApiClientCaller.getFlightFromIdent("THY29"));
    }

    @Test
    public void getOperatorFromIdShouldReturnNull() {
        mockResponse(null);
        assertNull(aeroApiClientCaller.getOperatorFromId(null));
    }

    @Test
    public void getOperatorFromIdShouldReturnOperator() throws IOException {
        mockResponse(operatorData);
        assertEquals(operator, aeroApiClientCaller.getOperatorFromId("THY29").setFullJson(null));
    }

    @Test
    public void getAllActiveFlightsWithLiveShouldReturnEmpty() {
        mockResponse(null);
        assertTrue(aeroApiClientCaller.getAllActiveFlightsWithLive().isEmpty());
    }

    @Test
    public void getAllActiveFlightsWithLiveShouldReturnList() {
        mockResponse(flightsData);
        assertEquals(5, aeroApiClientCaller.getAllActiveFlightsWithLive().size());
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
