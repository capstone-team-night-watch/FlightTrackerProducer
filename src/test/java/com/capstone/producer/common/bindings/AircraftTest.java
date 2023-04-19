package com.capstone.producer.common.bindings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class AircraftTest {

    Aircraft aircraft;

    @Before
    public void setUp(){
        aircraft = new Aircraft();
    }

    @Test
    public void getAndSetRegistration(){
        aircraft.setRegistration("REGISTRATION");
        String result = aircraft.getRegistration();

        assertEquals("REGISTRATION", result);
    }

    @Test
    public void getAndSetIata(){
        aircraft.setIata("IATA");
        String result = aircraft.getIata();

        assertEquals("IATA", result);
    }

    @Test
    public void getAndSetIcao(){
        aircraft.setIcao("ICAO");
        String result = aircraft.getIcao();

        assertEquals("ICAO", result);
    }

    @Test
    public void getAndSetIcao24(){
        aircraft.setIcao24("ICAO24");
        String result = aircraft.getIcao24();

        assertEquals("ICAO24", result);
    }

}
