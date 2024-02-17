package com.capstone.producer.common.bindings.aviationstack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CodesharedTest {

    private CodeShared codeshared;

    @Before
    public void setUp() {
        codeshared = new CodeShared()
                .setAirlineName("airline_name")
                .setAirlineIata("airline_iata")
                .setAirlineIcao("airline_icao")
                .setFlightNumber(1)
                .setFlightIata("flight_iata")
                .setFlightIcao("flight_icao");

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
        assertEquals("airline_name", codeshared.getAirlineName());
        assertEquals("airline_iata", codeshared.getAirlineIata());
        assertEquals("airline_icao", codeshared.getAirlineIcao());
        assertEquals(1, codeshared.getFlightNumber());
        assertEquals("flight_iata", codeshared.getFlightIata());
        assertEquals("flight_icao", codeshared.getFlightIcao());
    }
}