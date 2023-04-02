package com.capstone.producer.controller;

import com.capstone.producer.kafka.KafkaProducerExample;
import com.capstone.producer.kafka.ServiceHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProducerController {

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
    public String controller(@PathVariable("icao") String message) throws JsonProcessingException {
        //RecordMetadata metadata = KafkaProducerExample.runProducer(message);
        //return "Message Sent to Kafka Broker was: " + message + "\n" + "MetaData from send topic: " + metadata.topic() + " Partition: " + metadata.partition() + " Timestamp: " + metadata.timestamp() + " Offset: "+ metadata.offset();

        return serviceHandler.handle(message);
    }

    @RequestMapping(
            path = "/liveflights",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public String controller() throws JsonProcessingException {
        //RecordMetadata metadata = KafkaProducerExample.runProducer(message);
        //return "Message Sent to Kafka Broker was: " + message + "\n" + "MetaData from send topic: " + metadata.topic() + " Partition: " + metadata.partition() + " Timestamp: " + metadata.timestamp() + " Offset: "+ metadata.offset();

        return serviceHandler.handleLiveFlights();
    }
}
