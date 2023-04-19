package com.capstone.producer.controller;

import com.capstone.producer.kafka.ServiceHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    @CrossOrigin("*")
    public String getICAO(@PathVariable("icao") String message) throws JsonProcessingException {
        //RecordMetadata metadata = KafkaProducerExample.runProducer(message);
        //return "Message Sent to Kafka Broker was: " + message + "\n" + "MetaData from send topic: " + metadata.topic() + " Partition: " + metadata.partition() + " Timestamp: " + metadata.timestamp() + " Offset: "+ metadata.offset();

        LOGGER.info("Received request with icao: {}", message);

        return serviceHandler.handleFlightIcao(message);
    }

    @RequestMapping(
            path = "/flighticao/getLive",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    @CrossOrigin("*")
    public String getLive() throws JsonProcessingException {
        //RecordMetadata metadata = KafkaProducerExample.runProducer(message);
        //return "Message Sent to Kafka Broker was: " + message + "\n" + "MetaData from send topic: " + metadata.topic() + " Partition: " + metadata.partition() + " Timestamp: " + metadata.timestamp() + " Offset: "+ metadata.offset();

        return serviceHandler.handleLiveRequest();
    }
}
