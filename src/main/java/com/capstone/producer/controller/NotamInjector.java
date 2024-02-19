package com.capstone.producer.controller;

import com.capstone.producer.TfrHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class that handles all endpoints related to getting information from NOTAMs
 */
@RestController
@RequestMapping("/notam")
public class NotamInjector {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotamInjector.class);

    private final TfrHandler tfrHandler;

    public NotamInjector(TfrHandler tfrHandler) {
        this.tfrHandler = tfrHandler;
    }

    /**
     * Sets up the put mapping for injecting TFR NOTAMs
     *
     * @param tfrNotam The TFR NOTAM being injected
     * @return A String representing the current state of that NOTAM injection
     */
    @PutMapping("/tfr")
    public ResponseEntity<?> postTFR(@RequestBody String tfrNotam) throws InterruptedException {
        LOGGER.debug("Received TFR NOTAM: {}", tfrNotam);

        return new ResponseEntity<>(tfrHandler.handleTfrAddition(tfrNotam), HttpStatus.OK);
    }

}
