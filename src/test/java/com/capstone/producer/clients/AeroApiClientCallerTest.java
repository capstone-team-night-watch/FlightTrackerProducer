package com.capstone.producer.clients;

import com.capstone.producer.common.bindings.aero.FlightInfoFaid;
import com.capstone.producer.common.bindings.aero.Operator;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AeroApiClientCallerTest {

    private static final String BASE_URL = "http://localhost:9091/services";

    @Mock
    private RestTemplate client;

    @Mock
    private ResponseEntity<JsonNode> responseEntity;

    private AeroApiClientCaller aeroApiClientCaller;

    @Before
    public void setUp() {
        aeroApiClientCaller = new AeroApiClientCaller(client, BASE_URL, "key");
    }

    @Test
    public void getFlightFromNoFightIdShouldReturnNull () {
        when(client.exchange(anyString(), any(), any(), eq(JsonNode.class))).thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(null);

        FlightInfoFaid flightInfoFaId = aeroApiClientCaller.getFlightFromFaId(null);

        assertNull(flightInfoFaId);
    }

    @Test
    public void getFlightFromIndentShouldReturnNull() {
        when(client.exchange(anyString(), any(), any(), eq(JsonNode.class))).thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(null);

        String flightFromIndent = aeroApiClientCaller.getFlightFromIdent(null);

        assertNull(flightFromIndent);
    }

    @Test
    public void getOperatorFromIdShouldReturnNull() {
        when(client.exchange(anyString(), any(), any(), eq(JsonNode.class))).thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(null);

        Operator operator = aeroApiClientCaller.getOperatorFromId(null);

        assertNull(operator);
    }

    @Test
    public void getAllActiveFlightsWithLiveShouldReturnEmpty() {
        when(client.exchange(anyString(), any(), any(), eq(JsonNode.class))).thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(null);

        List<FlightInfoFaid> flightInfoFaIds = aeroApiClientCaller.getAllActiveFlightsWithLive();

        assertTrue(flightInfoFaIds.isEmpty());
    }

}