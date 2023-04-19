package com.capstone.producer.kafka;

import com.capstone.producer.clients.AviationStackClientCaller;
import com.capstone.producer.common.bindings.Flight;
import com.capstone.producer.common.bindings.FlightInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServiceHandlerTest {

    @Mock
    private AviationStackClientCaller caller;

    @InjectMocks
    private ServiceHandler serviceHandler;

    private final static ObjectMapper om = new ObjectMapper();

    private static JsonNode JSON_NODE;

    static {
        try {
            JSON_NODE = om.readTree("{ \"field1\" : \"Fee\", \"field2\" : \"Fi\"}");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void handleFlightIcao() throws JsonProcessingException {
        when(caller.getFlightByIcao(anyString())).thenReturn(JSON_NODE);

        String result = serviceHandler.handleFlightIcao("JD123");

        assertEquals("{\"field1\":\"Fee\",\"field2\":\"Fi\"}", result);
    }

    @Test
    public void handLiveRequest() throws JsonProcessingException {
        Flight flight = new Flight();
        flight.setIcao("ICAO");

        FlightInfo flightInfo = new FlightInfo();
        flightInfo.setFlight(flight);
        when(caller.getAllActiveFlightsWithLive()).thenReturn(Arrays.asList(flightInfo));

        String result = serviceHandler.handleLiveRequest();

        assertEquals("{\"icaos\":\"ICAO\"}", result);
    }

}
