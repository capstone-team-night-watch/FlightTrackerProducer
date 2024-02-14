package com.capstone.producer.kafka;

import com.capstone.producer.ServiceHandler;
import com.capstone.producer.clients.AeroApiClientCaller;
import com.capstone.producer.clients.AviationStackClientCaller;
import com.capstone.producer.common.bindings.GenerateRequest;
import com.capstone.producer.common.bindings.aero.FlightInfoFa_Id;
import com.capstone.producer.common.bindings.aero.Position;
import com.capstone.producer.common.bindings.aviationstack.Airline;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServiceHandlerTest {

    @Mock
    private AviationStackClientCaller aviationCaller;

    @Mock
    private AeroApiClientCaller aeroCaller;

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
    public void handleFlightIcao() throws JsonProcessingException, InterruptedException {
        FlightInfo flightInfo = new FlightInfo();
        flightInfo.setAirline(new Airline().setName("NAME"));
        flightInfo.setLive(new Live());
        //when(aviationCaller.getFlightFromIcao(anyString())).thenReturn(flightInfo);

        //String result = serviceHandler.handleFlightIcao("JD123");

        //assertEquals("{\"icao\":\"JD123\",\"airline\":\"NAME\",\"live\":\"{\\\"updated\\\":\\\"null\\\",\\\"latitude\\\":0.00,\\\"longitude\\\":0.00,\\\"altitude\\\":0.00,\\\"direction\\\":0,\\\"speed_horizontal\\\":0.00,\\\"speed_vertical\\\":0.00,\\\"is_ground\\\":false}\"}", result);
    }

    @Test
    public void handleFlightIndentShouldReturnValidString() {
        when(aeroCaller.getFlightFromIdent(anyString())).thenReturn("Flight");

        String result = serviceHandler.handleFlightIdent("INDENT");

        assertNotNull(result);
    }

    @Test
    public void handleFlightIndentShouldReturnErrorString() {
        when(aeroCaller.getFlightFromIdent(anyString())).thenReturn(null);

        String result = serviceHandler.handleFlightIdent("INDENT");

        assertTrue(result.contains("No relevant flight information could be found"));
    }

    @Test
    public void handleFlightFaIdShouldReturnErrorString() throws InterruptedException {
        when(aeroCaller.getFlightFromFaId(anyString())).thenReturn(null);

        String result = serviceHandler.handleFlightFaId("FaId");

        assertTrue(result.contains("No relevant flight information could be found"));
    }

    @Test void handleOperatorShouldReturnErrorString() throws InterruptedException {
        when(aeroCaller.getOperatorFromId(anyString())).thenReturn(null);

        String result = serviceHandler.handleOperator("Operator");

        assertTrue(result.contains("No relevant flight information could be found"));
    }

    @Test
    public void handLiveRequest() throws JsonProcessingException {

        FlightInfoFa_Id flightInfo = new FlightInfoFa_Id();
        flightInfo.setLast_position(new Position());
        flightInfo.setFa_flight_id("FAID");
        when(aeroCaller.getAllActiveFlightsWithLive()).thenReturn(List.of(flightInfo));

        String result = serviceHandler.handleLiveRequest();

        assertEquals("{\"faids\":\"FAID\"}", result);
    }

    @Test
    public void handleGenerateRequest() throws InterruptedException {
        GenerateRequest generateRequest = new GenerateRequest().setAirlineName("AIRLINE").setFlightIcao("ICAO");

        String toBeSent = serviceHandler.handleGenerateRequest(generateRequest);

        assertEquals("{\"icao\":\"ICAO\",\"airline\":\"AIRLINE\",\"generate\":true,\"live\":\"{\\\"updated\\\":\\\"null\\\",\\\"latitude\\\":0.00,\\\"longitude\\\":0.00,\\\"altitude\\\":0.00,\\\"direction\\\":0,\\\"speed_horizontal\\\":0.00,\\\"speed_vertical\\\":0.00,\\\"is_ground\\\":false}\"}", toBeSent);
    }

}
