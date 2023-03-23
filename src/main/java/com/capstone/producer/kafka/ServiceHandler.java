package com.capstone.producer.kafka;

import com.capstone.producer.clients.AviationStackClientCaller;
import com.capstone.producer.common.bindings.FlightInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceHandler.class);

    @Autowired
    private AviationStackClientCaller aviationStackClientCaller;


    public ServiceHandler() {
    }

    public String handle(String flight_icao) {
        LOGGER.debug("Received request: {}", flight_icao);

        //Validate request?

        // Gets a FlightInfo Object from client
        //FlightInfo flightResp = aviationStackClientCaller.getFlightByIcao(flight_icao);
        //String flightRespStr = flightResp.toString();

        List<FlightInfo> flightInfos = aviationStackClientCaller.getAllActiveFlightsWithLive();

        //Push response to topic?
        //RecordMetadata metadata = KafkaProducerExample.runProducer(flightRespStr);
        //LOGGER.debug("Metadata: {}", metadata.toString());

        // Return toString of FlightInfo Object to user on page
        return flightInfos.stream().map(FlightInfo::toString).collect(Collectors.joining("\n\n"));
    }


}
