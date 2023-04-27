package com.capstone.producer.controller;

import com.capstone.producer.kafka.ServiceHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProducerControllerTest {

    @Mock
    private ServiceHandler serviceHandler;

    @InjectMocks
    private ProducerController controller;

    @Test
    public void shouldCallGetICAO() throws JsonProcessingException, InterruptedException {
        when(serviceHandler.handleFlightIcao(anyString())).thenReturn("RESULT");

        String result = controller.getICAO("ICAO");

        assertEquals("RESULT", result);
    }

    @Test
    public void shouldCallService() throws JsonProcessingException {
        when(serviceHandler.handleLiveRequest()).thenReturn("RESULT");

        String result = controller.getLive();

        assertEquals("RESULT", result);
    }

}
