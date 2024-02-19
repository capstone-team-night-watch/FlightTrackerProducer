package com.capstone.producer;

import com.capstone.producer.ServiceHandler;
import com.capstone.producer.clients.AeroApiClientCaller;
import com.capstone.producer.clients.AviationStackClientCaller;
import com.capstone.producer.common.bindings.AirportGenerateRequest;
import com.capstone.producer.common.bindings.GenerateRequest;
import com.capstone.producer.common.bindings.aero.FlightInfoFaid;
import com.capstone.producer.common.bindings.aero.Position;
import com.capstone.producer.common.bindings.aviationstack.Airline;
import com.capstone.producer.common.bindings.aviationstack.AirportAviation;
import com.capstone.producer.common.bindings.aviationstack.FlightInfo;
import com.capstone.producer.common.bindings.aviationstack.Live;
import com.capstone.producer.exceptions.HttpException;
import com.capstone.producer.kafka.KafkaProducer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ServiceHandlerTest {
    @Mock
    private AviationStackClientCaller aviationCaller;

    @Mock
    private AeroApiClientCaller aeroCaller;

    @InjectMocks
    private ServiceHandler serviceHandler;


    @Test
    public void handleFlightIcao() {
        FlightInfo flightInfo = new FlightInfo();
        flightInfo.setAirline(new Airline().setName("NAME"));
        flightInfo.setLive(new Live());

        assertNotNull(flightInfo);
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

    @Test
    public void handleOperatorShouldReturnErrorString() throws InterruptedException {
        when(aeroCaller.getOperatorFromId(anyString())).thenReturn(null);

        String result = serviceHandler.handleOperator("Operator");

        assertTrue(result.contains("No relevant operator information could be found"));
    }

    @Test
    public void handleAirportRequestValidString() {
        AirportAviation airportAviation = new AirportAviation()
                .setLongitude(1f)
                .setLatitude(1f);
        when(aviationCaller.getAirportInfoFromName(anyString())).thenReturn(airportAviation);

        String result = serviceHandler.handleAirportRequest("AIRPORT");

        assertNotNull(result);
    }

    @Test
    public void handLiveRequest() {

        FlightInfoFaid flightInfo = new FlightInfoFaid();
        flightInfo.setLastPosition(new Position());
        flightInfo.setFaFlightId("FAID");
        when(aeroCaller.getAllActiveFlightsWithLive()).thenReturn(List.of(flightInfo));

        String result = serviceHandler.handleLiveRequest();

        assertEquals("{\"faids\":\"FAID\"}", result);
    }

    @Test
    public void handleGenerateRequest() throws InterruptedException, HttpException {
        GenerateRequest generateRequest = new GenerateRequest().setAirlineName("AIRLINE").setFlightIcao("ICAO");

        String toBeSent = serviceHandler.handleGenerateRequest(generateRequest);

        Assert.notNull(toBeSent, "The message to be sent should not be null");
    }

    @Test
    public void handleGenerateAirportRequest_ShouldReturnException_WhenProvidedAirportAreNotFound() throws InterruptedException, HttpException {
        AirportGenerateRequest generateRequest = new AirportGenerateRequest()
                .setDepartAirport("DepartAirport")
                .setArriveAirport("ArriveAirport");

        generateRequest
                .setAirlineName("AIRLINE")
                .setFlightIcao("ICAO")
                .setLongitude(1f)
                .setLatitude(1f);

        var exception = assertThrows(HttpException.class, () -> serviceHandler.handleGenerateRequest(generateRequest));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }


    @Test
    public void handleGenerateAirportRequest_ShouldThrowException_WhenDepartAirportIsMissing() throws InterruptedException, HttpException {
        AirportGenerateRequest generateRequest = new AirportGenerateRequest()
                .setArriveAirport("ArriveAirport");

        generateRequest
                .setAirlineName("AIRLINE")
                .setFlightIcao("ICAO")
                .setLongitude(1f)
                .setLatitude(1f);

        var exception = assertThrows(HttpException.class, () -> serviceHandler.handleGenerateRequest(generateRequest));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void handleGenerateRequest_ReturnNotEmptyString() throws InterruptedException, HttpException {
        var generateRequest = new GenerateRequest()
                .setAirlineName("AIRLINE")
                .setFlightIcao("ICAO")
                .setLongitude(1f)
                .setLatitude(1f);

        String result = serviceHandler.handleGenerateRequest(generateRequest);
        assertNotNull(result);
    }

    @Test
    public void updateFlightInfoAeroCallerCalledTwice() throws InterruptedException {
        serviceHandler.handleFlightFaId("faId");

        when(aeroCaller.getFlightFromFaId(anyString())).thenReturn(new FlightInfoFaid());

        serviceHandler.updateFlightInfo();

        verify(aeroCaller, times(2)).getFlightFromFaId(anyString());
    }

    @Test
    public void updateGeneratedFlightInfoShouldHaveTwoCalls() throws HttpException, InterruptedException {
        var generateRequest = new GenerateRequest().setAirlineName("AIRLINE").setFlightIcao("ICAO");

        MockedStatic<KafkaProducer> mockedStatic = mockStatic(KafkaProducer.class);

        mockedStatic.when(() -> KafkaProducer.runProducer(anyString(), anyString())).thenReturn(null);
        serviceHandler.handleGenerateRequest(generateRequest);
        serviceHandler.updateGeneratedFlightInfo();

        mockedStatic.verify(() -> KafkaProducer.runProducer(anyString(), anyString()), times(2));
        mockedStatic.close();
    }
}
