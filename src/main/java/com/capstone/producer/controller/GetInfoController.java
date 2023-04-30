package com.capstone.producer.controller;

import com.capstone.producer.ServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class that handles all endpoints related to getting information from Aviation Stack
 */
@RestController
public class GetInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetInfoController.class);

    /**
     * Service Handler Object that facilitates the logic that needs to happen when a request is received
     */
    private final ServiceHandler serviceHandler;

    public GetInfoController(ServiceHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
    }

    /**
     * Sets up the request mapping for getting information about a specific flight
     * Cross Origin scripting setup allows requests from any cross-origin script
     *
     * @param flightIcao The flight ICAO number that will be used to acquire information about a flight
     * @return A String representing the message that was sent to the Kafka broker which is made up of
     * JSON that contains coordinate and other flight information
     */
    @CrossOrigin("*")
    @RequestMapping(
            path = "/flighticao/{icao}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String getICAO(@PathVariable("icao") String flightIcao) throws InterruptedException {
        LOGGER.info("Received request with icao: {}", flightIcao);

        return serviceHandler.handleFlightIcao(flightIcao);
    }

    /**
     * Sets up the request mapping for getting flights that contain live coordinate information
     * Cross Origin scripting setup allows requests from any cross-origin script
     *
     * @return A String representing the message that was sent to the Kafka broker which is made up of
     * JSON that contains a list of flight icao numbers
     */
    @CrossOrigin("*")
    @RequestMapping(
            path = "/flighticao/getLive",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String getLive() {
        return serviceHandler.handleLiveRequest();
    }

    /**
     * Sets up the request mapping for getting information about a specific airport
     * Cross Origin scripting setup allows requests from any cross-origin script
     *
     * @param airportName The name of the airport that information is being requested for
     * @return A String representing the message that was sent to the Kafka broker which is made up of
     * JSON that contains the coordinates for the request airport
     */
    @CrossOrigin("*")
    @RequestMapping(
            path = "/airportInfo/{name}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String getAirport(@PathVariable("name") String airportName) {
        return serviceHandler.handleAirportRequest(airportName);
    }
}
