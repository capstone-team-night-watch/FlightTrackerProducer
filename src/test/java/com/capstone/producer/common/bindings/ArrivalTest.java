package com.capstone.producer.common.bindings;

import com.capstone.producer.common.bindings.aviationstack.Arrival;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ArrivalTest {

    Arrival arrival;

    String result;

    @Before
    public void setUp(){
        arrival = new Arrival();
    }

    @Test
    public void setAndGetAirport(){
        arrival.setAirport("AIRPORT");
        result = arrival.getAirport();

        assertEquals("AIRPORT", result);
    }

    @Test
    public void setAndGetTimezone(){
        arrival.setTimezone("TIMEZONE");
        result = arrival.getTimezone();

        assertEquals("TIMEZONE", result);
    }

    @Test
    public void setAndGetIata(){
        arrival.setIata("IATA");
        result = arrival.getIata();

        assertEquals("IATA", result);
    }

    @Test
    public void setAndGetIcao(){
        arrival.setIcao("ICAO");
        result = arrival.getIcao();

        assertEquals("ICAO", result);
    }

    @Test
    public void setAndGetTerminal(){
        arrival.setTerminal("TERMINAL");
        result = arrival.getTerminal();

        assertEquals("TERMINAL", result);
    }

    @Test
    public void setAndGetGate(){
        arrival.setGate("GATE");
        result = arrival.getGate();

        assertEquals("GATE", result);
    }

    @Test
    public void setAndGetBaggage(){
        arrival.setBaggage("BAGGAGE");
        result = arrival.getBaggage();

        assertEquals("BAGGAGE", result);
    }

    @Test
    public void setAndGetDelay(){
        arrival.setDelay("DELAY");
        result = arrival.getDelay();

        assertEquals("DELAY", result);
    }

    @Test
    public void setAndGetScheduled(){
        arrival.setScheduled("SCHEDULED");
        result = arrival.getScheduled();

        assertEquals("SCHEDULED", result);
    }

    @Test
    public void setAndGetEstimated(){
        arrival.setEstimated("ESTIMATED");
        result = arrival.getEstimated();

        assertEquals("ESTIMATED", result);
    }

    @Test
    public void setAndGetActual(){
        arrival.setActual("ACTUAL");
        result = arrival.getActual();

        assertEquals("ACTUAL", result);
    }

    @Test
    public void setAndGetEstimated_runway(){
        arrival.setEstimatedRunway("ESTIMATED_RUNWAY");
        result = arrival.getEstimatedRunway();

        assertEquals("ESTIMATED_RUNWAY", result);
    }

    @Test
    public void setAndGetActual_runway(){
        arrival.setActualRunway("ACTUAL_RUNWAY");
        result = arrival.getActualRunway();

        assertEquals("ACTUAL_RUNWAY", result);
    }
}
