package com.capstone.producer;


import com.capstone.producer.shared.bindings.FlightInformation;
import com.capstone.producer.shared.bindings.GeographicCoordinates3D;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.capstone.producer.common.bindings.aero.FlightInfoFaid;
import com.capstone.producer.common.bindings.aero.Position;
import com.capstone.producer.kafka.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FlightInjectionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlightInjectionHandler.class);

    /**
     * Facilitates the creation and tracking of Injected Flights
     *
     * @param receivedJSON Receives the full flight object to start parsing
     * @return The message that was sent through Kafka, or part was added to the list for parsing
     * later when all parts make it in
     * @throws InterruptedException    Kafka can throw an interrupted exception.
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

                    if(injectedFlight.getLastPosition() != null) {
                        KafkaProducer.emitFlightInformationUpdate(handleRealInjection(injectedFlight));
                    } else if (injectedFlight.getPositions() != null){
                        for(int index = 0; index < injectedFlight.getPositions().length; index++) {
                            KafkaProducer.emitFlightInformationUpdate(handleSimulationInjection(injectedFlight, index));
                            Thread.sleep(1000);
                        }
                    } else {
                        LOGGER.error("No position data for flight {}", flight.textValue());
                        return "No parsable position data";
                    }
                    
                    
                } catch (IllegalArgumentException e) {
                    LOGGER.error("Error Processing flight {}", flight.textValue());
                    allSuccessful = false;
                }
            }
            if (allSuccessful) {
                return "All flights processed successfully";
            } else {
                return "Not all flights processed successfully";
            }
        }

        return "Received not as an array of flight objects";
    }

    private FlightInformation handleSimulationInjection(FlightInfoFaid injectedFlight, int index) throws JsonProcessingException, InterruptedException {
        Position[] positions = injectedFlight.getPositions();

        return new FlightInformation()
                            .setFlightId("aero-sim-" + injectedFlight.getFaFlightId())
                            .setHeading(positions[index].getHeading())
                            .setLocation(new GeographicCoordinates3D()
                                    .setAltitude(positions[index].getAltitude())
                                    .setLatitude(positions[index].getLatitude())
                                    .setLongitude(positions[index].getLongitude()))
                            .setGroundSpeed(positions[index].getGroundSpeed());
    }

    private FlightInformation handleRealInjection(FlightInfoFaid injectedFlight) throws JsonProcessingException, InterruptedException {
        Position flightPosition = injectedFlight.getLastPosition();
        return new FlightInformation()
                            .setFlightId("aero-" + injectedFlight.getFaFlightId())
                            .setHeading(flightPosition.getHeading())
                            .setLocation(new GeographicCoordinates3D()
                                    .setAltitude(flightPosition.getAltitude())
                                    .setLatitude(flightPosition.getLatitude())
                                    .setLongitude(flightPosition.getLongitude())
                            ).setGroundSpeed(flightPosition.getGroundSpeed());
    }
}
