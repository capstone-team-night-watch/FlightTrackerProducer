package com.capstone.producer.common.bindings.aviationstack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class FlightInfoTest {

    private FlightInfo flightInfo;

    @Mock
    private Departure departure;

    @Mock
    private Arrival arrival;

    @Mock
    private Airline airline;

    @Mock
    private Flight flight;

    @Mock
    private Aircraft aircraft;

    @Mock
    private Live live;

    @Before
    public void setUp() {
        flightInfo = new FlightInfo();
        flightInfo.setFlight_date("flight_date");
        flightInfo.setFlight_status("flight_status");
        flightInfo.setDeparture(departure);
        flightInfo.setArrival(arrival);
        flightInfo.setAirline(airline);
        flightInfo.setFlight(flight);
        flightInfo.setAircraft(aircraft);
        flightInfo.setLive(live);
    }

    @Test
    public void toStringShouldContainAllInfo() {
        assertTrue(flightInfo.toString().contains("flight_date"));
        assertTrue(flightInfo.toString().contains("flight_status"));
        assertTrue(flightInfo.toString().contains("departure"));
        assertTrue(flightInfo.toString().contains("arrival"));
        assertTrue(flightInfo.toString().contains("flight"));
        assertTrue(flightInfo.toString().contains("aircraft"));
        assertTrue(flightInfo.toString().contains("live"));
    }

    @Test
    public void shouldReturnValidInfo() {
        assertEquals("flight_date", flightInfo.getFlight_date());
        assertEquals("flight_status", flightInfo.getFlight_status());
        assertEquals(departure, flightInfo.getDeparture());
        assertEquals(arrival, flightInfo.getArrival());
        assertEquals(airline, flightInfo.getAirline());
        assertEquals(flight, flightInfo.getFlight());
        assertEquals(aircraft, flightInfo.getAircraft());
        assertEquals(live, flightInfo.getLive());
    }


}