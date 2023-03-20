package com.capstone.producer.kafka;

import com.capstone.producer.clients.AviationStackClientCaller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceHandler.class);

    @Autowired
    private AviationStackClientCaller aviationStackClientCaller;


    public ServiceHandler() {}

    public String handle(String flight_icao) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        LOGGER.debug("Received request: {}", flight_icao);

        //Validate request?



        JsonNode response = aviationStackClientCaller.getFlight(flight_icao);
        String jsonAsString = objectMapper.writeValueAsString(response);
        //Push response to topic?
        RecordMetadata metadata = KafkaProducerExample.runProducer(jsonAsString);


        //Return response to user on page
        return jsonAsString;
    }


}
