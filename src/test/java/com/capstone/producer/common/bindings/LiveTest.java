package com.capstone.producer.common.bindings;

import com.capstone.producer.common.bindings.aviationstack.Live;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class LiveTest {

    private Live live;

    @Before
    public void setUp(){
        live = new Live();
    }

    @Test
    public void getAndSetUpdated(){
        live.setUpdated("UPDATED");
        String result = live.getUpdated();

        assertEquals("UPDATED", result);
    }

    @Test
    public void getAndSetLatitude(){
        live.setLatitude(1);
        float result = live.getLatitude();

        assertEquals(1, result);
    }

    @Test
    public void getAndSetLongitude(){
        live.setLongitude(1);
        float result = live.getLongitude();

        assertEquals(1, result);
    }

    @Test
    public void getAndSetAltitude(){
        live.setAltitude(1);
        float result = live.getAltitude();

        assertEquals(1, result);
    }

    @Test
    public void getAndSetDirection(){
        live.setDirection(1);
        int result = live.getDirection();

        assertEquals(1, result);
    }

    @Test
    public void getAndSetSpeed_horizontal(){
        live.setSpeed_horizontal(1);
        float result = live.getSpeed_horizontal();

        assertEquals(1, result);
    }

    @Test
    public void getAndSetSpeed_vertical(){
        live.setSpeed_vertical(1);
        float result = live.getSpeed_vertical();

        assertEquals(1, result);
    }

    @Test
    public void getAndSetIs_ground(){
        live.setIs_ground(true);
        boolean result = live.isIs_ground();

        assertEquals(true, result);
    }


}
