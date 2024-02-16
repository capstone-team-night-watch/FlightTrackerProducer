package com.capstone.producer.common.bindings.aviationstack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class AirlineTest {

    Airline airline;

    @Before
    public void setUp() { airline = new Airline(); }

    @Test
    public void getAndSetAirlineNameShouldReturnAirline() {
        Airline testAirline = airline.setName("AIRLINE-NAME");

        String airlineName = airline.getName();

        assertEquals(testAirline.getName(), airlineName);
    }

    @Test
    public void getAndSetIataShouldReturnAirline() {
        Airline testAirline = airline.setIata("AIRLINE-IATA");

        String airlineIata = airline.getIata();

        assertEquals(testAirline.getIata(), airlineIata);
    }

    @Test
    public void getAndSetIcaoShoudlReturnAirline() {
        Airline testAirline = airline.setIcao("AIRLINE-ICAO");

        String airlineIcao = airline.getIcao();

        assertEquals(testAirline.getIcao(), airlineIcao);
    }

    @Test
    public void toStringShouldIncludeNameIcaoIata() {
        String airlineDesc = airline.toString();

        assertTrue(airlineDesc.contains("name"));
        assertTrue(airlineDesc.contains("icao"));
        assertTrue(airlineDesc.contains("iata"));
    }

}