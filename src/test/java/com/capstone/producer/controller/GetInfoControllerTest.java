package com.capstone.producer.controller;

import com.capstone.producer.ServiceHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetInfoControllerTest {
    @Mock
    private ServiceHandler serviceHandler;

    @InjectMocks
    private GetInfoController getInfoController;

    @Test
    public void shouldCallServiceGetLive() throws JsonProcessingException {
        when(serviceHandler.handleLiveRequest()).thenReturn("RESULT");

        String result = getInfoController.getLive();

        assertEquals("RESULT", result);
    }

    @Test
    public void shouldGetOperatorNotNull() throws InterruptedException {
        when(serviceHandler.handleOperator(anyString())).thenReturn(anyString());

        String operator = getInfoController.getOperator("OPERATOR");

        assertNotNull(operator);
    }

    @Test
    public void shouldGetIndentNotNull() throws InterruptedException {
        when(serviceHandler.handleFlightIdent(anyString())).thenReturn(anyString());

        String indent = getInfoController.getIdent("INDENT");

        assertNotNull(indent);
    }

    @Test
    public void shouldGetFaIdNotNull() throws InterruptedException {
        when(serviceHandler.handleFlightFaId(anyString())).thenReturn(anyString());

        String faId = getInfoController.getFaId("FAID");

        assertNotNull(faId);
    }

    @Test
    public void shouldGetAirPortNotNull() {
        when(serviceHandler.handleAirportRequest(anyString())).thenReturn(anyString());

        String airport = getInfoController.getAirport("AIRPORT");

        assertNotNull(airport);
    }
}
