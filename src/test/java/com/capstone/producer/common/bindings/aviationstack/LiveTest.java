package com.capstone.producer.common.bindings.aviationstack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class LiveTest {

    private Live live;

    @Before
    public void setUp() {
        live = new Live();
        live.setUpdated("updated");
        live.setLatitude(1f);
        live.setLongitude(1f);
        live.setAltitude(1f);
        live.setDirection(1);
        live.setSpeed_horizontal(1f);
        live.setSpeed_vertical(1f);
        live.setIs_ground(true);
    }

    @Test
    public void toStringShouldContainAllInfo() {
        assertTrue(live.toString().contains("updated"));
        assertTrue(live.toString().contains("latitude"));
        assertTrue(live.toString().contains("longitude"));
        assertTrue(live.toString().contains("altitude"));
        assertTrue(live.toString().contains("direction"));
        assertTrue(live.toString().contains("speed_horizontal"));
        assertTrue(live.toString().contains("speed_vertical"));
        assertTrue(live.toString().contains("is_ground"));
    }

    @Test
    public void shouldReturnValid() {
        assertEquals("updated", live.getUpdated());
        assertEquals(1f, live.getLatitude());
        assertEquals(1f, live.getLongitude());
        assertEquals(1f, live.getAltitude());
        assertEquals(1, live.getDirection());
        assertEquals(1f, live.getSpeed_horizontal());
        assertEquals(1f, live.getSpeed_vertical());
        assertTrue(live.isIs_ground());
    }

    @Test
    public void shouldNotBeEqual() {
        Live mockLive = new Live();
        mockLive.setUpdated("updated");
        mockLive.setLatitude(2f);
        mockLive.setLongitude(2f);
        mockLive.setAltitude(2f);
        mockLive.setDirection(2);
        mockLive.setSpeed_horizontal(2f);
        mockLive.setSpeed_vertical(2f);
        mockLive.setIs_ground(false);

        assertNotEquals(mockLive, live);
    }

}