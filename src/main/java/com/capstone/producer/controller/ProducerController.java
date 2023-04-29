package com.capstone.producer.controller;

import com.capstone.producer.common.bindings.AirportGenerateRequest;
import com.capstone.producer.common.bindings.GenerateRequest;
import com.capstone.producer.kafka.ServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProducerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerController.class);

    private final ServiceHandler serviceHandler;
    public ProducerController(ServiceHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
    }
    @RequestMapping(
            path = "/flighticao/{icao}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @CrossOrigin("*")
    public String getICAO(@PathVariable("icao") String message) throws InterruptedException {
        LOGGER.info("Received request with icao: {}", message);

        return serviceHandler.handleFlightIcao(message);
    }

    @RequestMapping(
            path = "/flighticao/getLive",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @CrossOrigin("*")
    public String getLive() {
        return serviceHandler.handleLiveRequest();
    }

    @RequestMapping(
            path = "/airportInfo/{name}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @CrossOrigin("*")
    public String getAirport(@PathVariable("name") String airportName) {
        return serviceHandler.handleAirportRequest(airportName);
    }


    @RequestMapping(
            path= "/custom/generate",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @CrossOrigin("*")
    public String generateCustomMockFlight(@RequestBody GenerateRequest generateRequest) throws InterruptedException {
        return serviceHandler.handleGenerateRequest(generateRequest);
    }

    @RequestMapping(
            path= "/airport/generate",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @CrossOrigin("*")
    public String generateMockFlight(@RequestBody AirportGenerateRequest generateRequest) throws InterruptedException {
        return serviceHandler.handleGenerateRequest(generateRequest);
    }
}
