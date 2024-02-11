package com.capstone.producer.common.bindings.aviationstack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class FlightTest {

    private Flight flight;

    @Mock
    private Codeshared codeshared;

    @Before
    public void setUp() {
        flight = new Flight()
                .setNumber(1)
                .setIata("iata")
                .setIcao("icao")
                .setCodeshared(codeshared);
    }

    @Test
    public void toStringShouldContainAllInfo() {
        assertTrue(flight.toString().contains("number"));
        assertTrue(flight.toString().contains("icao"));
        assertTrue(flight.toString().contains("iata"));
        assertTrue(flight.toString().contains("codeshared"));
    }

    @Test
    public void shouldReturnValidString() {
        assertEquals(1, flight.getNumber());
        assertEquals("icao", flight.getIcao());
        assertEquals("iata", flight.getIata());
        assertEquals(codeshared, flight.getCodeshared());
    }
}