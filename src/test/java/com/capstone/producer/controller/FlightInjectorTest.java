package com.capstone.producer.controller;

import com.capstone.producer.FlightInjectionHandler;
import com.capstone.producer.ServiceHandler;
import com.capstone.producer.common.bindings.AirportGenerateRequest;
import com.capstone.producer.common.bindings.GenerateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlightInjectorTest {

    @Mock
    private FlightInjectionHandler flightInjectionHandler;

    @InjectMocks
    private FlightInjector flightInjector;

    @Test
    public void shouldCallFlightInjectionService() throws Exception {
        when(flightInjectionHandler.handleFlightInjection(isA(String.class))).thenReturn("RESULT");

        ResponseEntity<String> result = flightInjector.injectFlights("STRING");

        assertEquals(new ResponseEntity<>("RESULT", HttpStatus.OK), result);
    }

    @Test
    public void shouldCatchTheError() throws Exception {
        when(flightInjectionHandler.handleFlightInjection(isA(String.class))).thenThrow(JsonProcessingException.class);

        ResponseEntity<String> result = flightInjector.injectFlights("STRING");

        assertEquals(new ResponseEntity<>("Unknown processing error occured", HttpStatus.UNPROCESSABLE_ENTITY), result);
    }
}
