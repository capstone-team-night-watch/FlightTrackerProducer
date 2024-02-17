package com.capstone.producer.clients;

import com.capstone.producer.common.bindings.aviationstack.FlightInfo;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AviationStackClientCallerTest {
    private static final String BASE_URL = "http://localhost:8080/services";

    @Mock
    private RestTemplate client;
    @Mock
    private ResponseEntity<JsonNode> responseEntity;

    private AviationStackClientCaller aviationStackClientCaller;

    @Before
    public void setUp() {
        aviationStackClientCaller = new AviationStackClientCaller(client, BASE_URL, "key");
    }

    @Test
    public void shouldReturnEmptyListWithNullRootNode(){
        when(client.exchange(anyString(), any(), any(), eq(JsonNode.class))).thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(null);

        List<FlightInfo> flightInfoList = aviationStackClientCaller.getAllActiveFlightsWithLive();

        assertEquals(Collections.emptyList(), flightInfoList);
    }
}
