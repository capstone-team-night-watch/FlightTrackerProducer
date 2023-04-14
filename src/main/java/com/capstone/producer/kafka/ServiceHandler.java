package com.capstone.producer.kafka;

import com.capstone.producer.clients.AviationStackClientCaller;
import com.capstone.producer.common.bindings.Flight;
import com.capstone.producer.common.bindings.FlightInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceHandler.class);

    @Autowired
    private AviationStackClientCaller aviationStackClientCaller;

    private String flight_icao;

    public ServiceHandler() {
    }


    public String handleFlightIcao(String flight_icao) throws JsonProcessingException {
        LOGGER.debug("Received request: {}", flight_icao);
        this.flight_icao = flight_icao;
        ObjectMapper objectMapper = new ObjectMapper();

        //Validate request?

        JsonNode response = aviationStackClientCaller.getFlightByIcao(flight_icao);
        String jsonAsString = objectMapper.writeValueAsString(response);
        //Push response to topic?
        RecordMetadata metadata = KafkaProducerExample.runProducer(jsonAsString);
        LOGGER.info("Kafka metadata: {}", metadata);
        //Return response to user on page
        return jsonAsString;
    }

    public String handleLiveRequest() throws JsonProcessingException {
        this.flight_icao = "";
        List<FlightInfo> liveFlights = aviationStackClientCaller.getAllActiveFlightsWithLive();

        String liveFlightsStr = liveFlights.stream().map(FlightInfo::getFlight).map(Flight::getIcao).collect(Collectors.joining(","));

        //return objectMapper.convertValue(liveFlightsStr, JsonNode.class).toString();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("icaos", liveFlightsStr);

        return jsonObject.toString();
    }

    @Scheduled(fixedRate = 300000)
    public void updateFlightInfo() throws JsonProcessingException {
        if (this.flight_icao == null || this.flight_icao.isEmpty()) {
            return;
        }

        String matchedFlight = handleFlightIcao(this.flight_icao);

        RecordMetadata metadata = KafkaProducerExample.runProducer(matchedFlight);

        LOGGER.info("Kafka metadata: {}", metadata);

    }
}
