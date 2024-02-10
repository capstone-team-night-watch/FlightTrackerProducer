package com.capstone.producer.common.bindings.aviationstack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class DepartureTest {

    private Departure departure;
    @Before
    public void setUp() {
        departure = new Departure()
                .setAirport("airport")
                .setTimezone("timezone")
                .setIata("iata")
                .setIcao("icao")
                .setTerminal("terminal")
                .setGate("gate")
                .setDelay("delay")
                .setScheduled("scheduled")
                .setEstimated("estimated")
                .setActual("actual")
                .setEstimated_runway("estimated_runway")
                .setActual_runway("actual_runway");

    }

    @Test
    public void toStringShouldContainAllInfo() {
        assertTrue(departure.toString().contains("airport"));
        assertTrue(departure.toString().contains("timezone"));
        assertTrue(departure.toString().contains("iata"));
        assertTrue(departure.toString().contains("icao"));
        assertTrue(departure.toString().contains("terminal"));
        assertTrue(departure.toString().contains("gate"));
        assertTrue(departure.toString().contains("delay"));
        assertTrue(departure.toString().contains("scheduled"));
        assertTrue(departure.toString().contains("estimated"));
        assertTrue(departure.toString().contains("actual"));
        assertTrue(departure.toString().contains("estimated_runway"));
        assertTrue(departure.toString().contains("actual_runway"));
    }

    @Test
    public void shouldReturnValidString() {
        assertEquals("airport", departure.getAirport());
        assertEquals("timezone", departure.getTimezone());
        assertEquals("iata", departure.getIata());
        assertEquals("icao", departure.getIcao());
        assertEquals("terminal", departure.getTerminal());
        assertEquals("gate", departure.getGate());
        assertEquals("delay", departure.getDelay());
        assertEquals("scheduled", departure.getScheduled());
        assertEquals("estimated", departure.getEstimated());
        assertEquals("actual", departure.getActual());
        assertEquals("estimated_runway", departure.getEstimated_runway());
        assertEquals("actual_runway", departure.getActual_runway());
    }
}