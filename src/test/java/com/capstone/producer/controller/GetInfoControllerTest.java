package com.capstone.producer.controller;

import com.capstone.producer.ServiceHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class GetInfoControllerTest {
    @Mock
    private ServiceHandler serviceHandler;

    @InjectMocks
    private GetInfoController getInfoController;

    @Test
    public void shouldCallGetICAO() throws JsonProcessingException, InterruptedException {
        when(serviceHandler.handleFlightIcao(anyString())).thenReturn("RESULT");

        String result = getInfoController.getICAO("ICAO");

        assertEquals("RESULT", result);
    }

    @Test
    public void shouldCallServiceGetLive() throws JsonProcessingException {
        when(serviceHandler.handleLiveRequest()).thenReturn("RESULT");

        String result = getInfoController.getLive();

        assertEquals("RESULT", result);
    }
}
