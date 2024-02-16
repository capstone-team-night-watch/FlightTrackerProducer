package com.capstone.producer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TfrHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TfrHandler.class);
    /**
     * Keeps track of all mock flights that have been generated and are being updated
     */
    private final Map<String, String> receivedNotams;

    public TfrHandler() {
        receivedNotams = new ConcurrentHashMap<>();
    }

    /**
     * Facilitates the creation and tracking of a mock flight with information in the provided request
     *
     * @param generateRequest Either a GenerateRequest or AirportGenerateRequest Object that contains the
     *                        necessary information to create a mock flight
     * @return The message that was sent through Kafka, which consists mock live coordinates and other mock flight information
     * or an error response.
     * @throws InterruptedException Sending a message using Kafka can trigger an InterruptedException
     */
    public String handleTfrAddition(String tfrNotam) throws InterruptedException {

        return tfrNotam;
    }
    
}
