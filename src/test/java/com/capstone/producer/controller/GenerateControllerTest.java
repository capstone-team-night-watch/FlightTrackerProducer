package com.capstone.producer.controller;

import com.capstone.producer.ServiceHandler;
import com.capstone.producer.common.bindings.AirportGenerateRequest;
import com.capstone.producer.common.bindings.GenerateRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenerateControllerTest {

    @Mock
    private ServiceHandler serviceHandler;

    @InjectMocks
    private GenerateController generateController;

    @Test
    public void shouldCallServiceCustomMock() throws Exception {
        when(serviceHandler.handleGenerateRequest(isA(GenerateRequest.class))).thenReturn("RESULT");

        String result = generateController.generateCustomMockFlight(new GenerateRequest());

        assertEquals("RESULT", result);
    }

    @Test
    public void shouldCallServiceAirportMock() throws Exception {
        when(serviceHandler.handleGenerateRequest(isA(GenerateRequest.class))).thenReturn("RESULT");

        String result = generateController.generateMockFlight(new AirportGenerateRequest());

        assertEquals("RESULT", result);
    }
}
