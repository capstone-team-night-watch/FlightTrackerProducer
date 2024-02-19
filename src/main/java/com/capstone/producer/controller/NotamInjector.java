package com.capstone.producer.controller;

import com.capstone.producer.TfrHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class that handles all endpoints related to getting information from Aviation Stack
 */
@RestController
@RequestMapping("/notam")
public class NotamInjector {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotamInjector.class);

    private final TfrHandler tfrHandler;

    public NotamInjector(TfrHandler tfrHandler) {
        this.tfrHandler = tfrHandler;
    }

    /**0
     * Sets up the request mapping for getting information about a specific flight
     * Cross Origin scripting setup allows requests from any cross-origin script
     *
     * @param operatorId The flight ICAO number that will be used to acquire information about a flight
     * @return A String representing the message that was sent to the Kafka broker which is made up of
     * JSON that contains coordinate and other flight information
     */
    @PutMapping("/tfr")
    public ResponseEntity<?> postTFR(@RequestBody String tfrNotam) throws InterruptedException {
        //LOGGER.info("Received TFR NOTAM: {}", tfrNotam);

        return new ResponseEntity<>(tfrHandler.handleTfrAddition(tfrNotam), HttpStatus.OK);
    }

}
