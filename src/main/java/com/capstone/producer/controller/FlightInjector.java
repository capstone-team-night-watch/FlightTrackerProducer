package com.capstone.producer.controller;

import com.capstone.producer.FlightInjectionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class that handles all endpoints related to getting information from NOTAMs
 */
@RestController
@RequestMapping("/flights")
public class FlightInjector {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlightInjector.class);

    private final FlightInjectionHandler flightHandler;

    public FlightInjector(FlightInjectionHandler flightHandler) {
        this.flightHandler = flightHandler;
    }

    /**
     * Sets up the put mapping for injecting Aero flightss
     *
     * @param aeroFlights The Aero flights being injected
     * @return A String representing the current state of that NOTAM injection
     */
    @PutMapping("/injectAero")
    public ResponseEntity<String> injectFlights(@RequestBody String aeroFlights) throws InterruptedException {
        LOGGER.debug("Received Aero Flights: {}", aeroFlights);
        try {
            return new ResponseEntity<>(flightHandler.handleFlightInjection(aeroFlights), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            LOGGER.error("JsonProcessingException Received: {}", e);
            return new ResponseEntity<>("Unknown processing error occured", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
