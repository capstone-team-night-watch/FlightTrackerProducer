package com.capstone.producer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.capstone.producer.common.bindings.aero.FlightInfoFaid;
import com.capstone.producer.kafka.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FlightInjectionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlightInjectionHandler.class);

    /**
     * Facilitates the creation and tracking of TFRs
     *
     * @param tfrNotam Receives the full NOTAM to start parsing
     * @return The message that was sent through Kafka, or part was added to the list for parsing
     * later when all parts make it in
     * 
     * @throws InterruptedException Kafka can throw an interrupted exception.
     * @throws JsonProcessingException 
     */
    public String handleFlightInjection(String receivedJSON) throws JsonProcessingException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(receivedJSON).get("flights");
        boolean allSuccessful = true;
        if (jsonNode.isArray()) {
            for (JsonNode flight : jsonNode) {
                try {
                    FlightInfoFaid injectedFlight = objectMapper.convertValue(flight, FlightInfoFaid.class);
                    KafkaProducer.runProducer(objectMapper.writeValueAsString(injectedFlight), "FlightData");
                } catch (JsonProcessingException e) {
                    LOGGER.error("Error Processing flight {}", flight.textValue());
                    allSuccessful = false;
                } catch (IllegalArgumentException e) {
                    LOGGER.error("Error Processing flight {}", flight.textValue());
                    allSuccessful = false;
                }
            }
            if(allSuccessful){
                return "All flights processed successfully";
            } else {
                return "Not all flights processed successfully";
            }
        }
        
        return "Received not as an array of flight objects";
    }
}
