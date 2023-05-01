package com.capstone.producer.kafka;

import com.capstone.producer.ServiceHandler;
import com.capstone.producer.clients.AviationStackClientCaller;
import com.capstone.producer.common.bindings.*;
import com.capstone.producer.common.bindings.aviationstack.Airline;
import com.capstone.producer.common.bindings.aviationstack.Flight;
import com.capstone.producer.common.bindings.aviationstack.FlightInfo;
import com.capstone.producer.common.bindings.aviationstack.Live;
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

//    @Test
//    public void handleFlightIcao() throws JsonProcessingException, InterruptedException {
//        FlightInfo flightInfo = new FlightInfo();
//        flightInfo.setAirline(new Airline().setName("NAME"));
//        flightInfo.setLive(new Live());
//        when(caller.getFlightFromIcao(anyString())).thenReturn(flightInfo);
//
//        String result = serviceHandler.handleFlightIcao("JD123");
//
//        assertEquals("{\"icao\":\"JD123\",\"airline\":\"NAME\",\"live\":\"{\\\"updated\\\":\\\"null\\\",\\\"latitude\\\":0.00,\\\"longitude\\\":0.00,\\\"altitude\\\":0.00,\\\"direction\\\":0,\\\"speed_horizontal\\\":0.00,\\\"speed_vertical\\\":0.00,\\\"is_ground\\\":false}\"}", result);
//    }

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

    @Test
    public void handleGenerateRequest() throws InterruptedException {
        GenerateRequest generateRequest = new GenerateRequest().setAirlineName("AIRLINE").setFlightIcao("ICAO");

        String toBeSent = serviceHandler.handleGenerateRequest(generateRequest);

        assertEquals("{\"icao\":\"ICAO\",\"airline\":\"AIRLINE\",\"live\":\"{\\\"updated\\\":\\\"null\\\",\\\"latitude\\\":0.00,\\\"longitude\\\":0.00,\\\"altitude\\\":0.00,\\\"direction\\\":0,\\\"speed_horizontal\\\":0.00,\\\"speed_vertical\\\":0.00,\\\"is_ground\\\":false}\"}", toBeSent);
    }

}
