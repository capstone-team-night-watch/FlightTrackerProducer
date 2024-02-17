package com.capstone.producer.controller;

import com.capstone.producer.ServiceHandler;
import com.capstone.producer.common.bindings.AirportGenerateRequest;
import com.capstone.producer.common.bindings.GenerateRequest;
import com.capstone.producer.exceptions.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class that handles all endpoints related to generating mock flights
 */
@RestController
public class GenerateController {
    /**
     * Service Handler Object that facilitates the logic that needs to happen when a request is received
     */
    private final ServiceHandler serviceHandler;
    public GenerateController(ServiceHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
    }

    /**
     * Sets up the request mapping for generating a custom (non-airport) mock flight
     * Cross Origin scripting setup allows requests from any cross-origin script
     *
     * @param generateRequest The GenerateRequest Object containing information about the mock flight
     * @return A string representing the message that was sent to the Kafka broker
     */
    @CrossOrigin("*")
    @PostMapping(
            path= "/custom/generate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String generateCustomMockFlight(@RequestBody GenerateRequest generateRequest) throws InterruptedException, HttpException {
        return serviceHandler.handleGenerateRequest(generateRequest);
    }

    /**
     * Sets up the request mapping for generating a mock flight with airport information
     * Cross Origin scripting setup allows requests from any cross-origin script
     *
     * @param generateRequest The AirportGenerateRequest Object containing information about the mock flight, including airport names
     * @return A string representing the message that was sent to the Kafka broker
     */
    @CrossOrigin("*")
    @PostMapping(
            path= "/airport/generate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String generateMockFlight(@RequestBody AirportGenerateRequest generateRequest) throws InterruptedException, HttpException {
        return serviceHandler.handleGenerateRequest(generateRequest);
    }
}
