package com.capstone.producer.common.bindings.aviationstack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CodesharedTest {

    private Codeshared codeshared;

    @Before
    public void setUp() {
        codeshared = new Codeshared()
                .setAirline_name("airline_name")
                .setAirline_iata("airline_iata")
                .setAirline_icao("airline_icao")
                .setFlight_number(1)
                .setFlight_iata("flight_iata")
                .setFlight_icao("flight_icao");

    }

    @Test
    public void toStringShouldContainAllInfo() {
        assertTrue(codeshared.toString().contains("airline_name"));
        assertTrue(codeshared.toString().contains("airline_iata"));
        assertTrue(codeshared.toString().contains("airline_icao"));
        assertTrue(codeshared.toString().contains("flight_number"));
        assertTrue(codeshared.toString().contains("flight_iata"));
        assertTrue(codeshared.toString().contains("flight_icao"));
    }

    @Test
    public void shouldReturnValidString() {
        assertEquals("airline_name", codeshared.getAirline_name());
        assertEquals("airline_iata", codeshared.getAirline_iata());
        assertEquals("airline_icao", codeshared.getAirline_icao());
        assertEquals(1, codeshared.getFlight_number());
        assertEquals("flight_iata", codeshared.getFlight_iata());
        assertEquals("flight_icao", codeshared.getFlight_icao());
    }
}