package com.capstone.producer.common.bindings.aviationstack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ArrivalTest {

    Arrival arrival;

    @Before
    public void setUp() {
        arrival = new Arrival();
        arrival.setAirport("airport");
        arrival.setTimezone("timezone");
        arrival.setIata("iata");
        arrival.setIcao("icao");
        arrival.setTerminal("terminal");
        arrival.setGate("gate");
        arrival.setBaggage("baggage");
        arrival.setDelay("delay");
        arrival.setScheduled("scheduled");
        arrival.setEstimated("estimated");
        arrival.setActual("actual");
        arrival.setEstimated_runway("estimated_runway");
        arrival.setActual_runway("actual_runway");
    }

    @Test
    public void shouldReturnValidStrings() {
        assertEquals("airport", arrival.getAirport());
        assertEquals("timezone", arrival.getTimezone());
        assertEquals("iata", arrival.getIata());
        assertEquals("icao", arrival.getIcao());
        assertEquals("terminal", arrival.getTerminal());
        assertEquals("gate", arrival.getGate());
        assertEquals("baggage", arrival.getBaggage());
        assertEquals("delay", arrival.getDelay());
        assertEquals("scheduled", arrival.getScheduled());
        assertEquals("estimated", arrival.getEstimated());
        assertEquals("actual", arrival.getActual());
        assertEquals("estimated_runway", arrival.getEstimated_runway());
        assertEquals("actual_runway", arrival.getActual_runway());
    }

    @Test
    public void toStringShouldIncludeArrivalInfo() {
        assertTrue(arrival.toString().contains("airport"));
        assertTrue(arrival.toString().contains("timezone"));
        assertTrue(arrival.toString().contains("iata"));
        assertTrue(arrival.toString().contains("icao"));
        assertTrue(arrival.toString().contains("terminal"));
        assertTrue(arrival.toString().contains("gate"));
        assertTrue(arrival.toString().contains("baggage"));
        assertTrue(arrival.toString().contains("delay"));
        assertTrue(arrival.toString().contains("scheduled"));
        assertTrue(arrival.toString().contains("estimated"));
        assertTrue(arrival.toString().contains("actual"));
        assertTrue(arrival.toString().contains("estimated_runway"));
        assertTrue(arrival.toString().contains("actual_runway"));
    }
}