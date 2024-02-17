package com.capstone.producer.common.bindings.aviationstack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class LiveTest {
    private ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    private Live live;

    @Before
    public void setUp() {
        live = new Live();
        live.setUpdated("updated");
        live.setLatitude(1f);
        live.setLongitude(1f);
        live.setAltitude(1f);
        live.setDirection(1);
        live.setSpeedHorizontal(1f);
        live.setSpeedVertical(1f);
        live.setGround(true);
    }

    @Test
    public void toStringShouldContainAllInfo() throws JsonProcessingException {
        var liveJson = objectWriter.writeValueAsString(live);

        assertTrue(liveJson.contains("updated"));
        assertTrue(liveJson.contains("latitude"));
        assertTrue(liveJson.contains("longitude"));
        assertTrue(liveJson.contains("altitude"));
        assertTrue(liveJson.contains("direction"));
        assertTrue(liveJson.contains("speed_horizontal"));
        assertTrue(liveJson.contains("speed_vertical"));
        assertTrue(liveJson.contains("is_ground"));
    }

    @Test
    public void shouldReturnValid() {
        assertEquals("updated", live.getUpdated());
        assertEquals(1f, live.getLatitude());
        assertEquals(1f, live.getLongitude());
        assertEquals(1f, live.getAltitude());
        assertEquals(1, live.getDirection());
        assertEquals(1f, live.getSpeedHorizontal());
        assertEquals(1f, live.getSpeedVertical());
        assertTrue(live.isGround());
    }

    @Test
    public void shouldNotBeEqual() {
        Live mockLive = new Live();
        mockLive.setUpdated("updated");
        mockLive.setLatitude(2f);
        mockLive.setLongitude(2f);
        mockLive.setAltitude(2f);
        mockLive.setDirection(2);
        mockLive.setSpeedHorizontal(2f);
        mockLive.setSpeedVertical(2f);
        mockLive.setGround(false);

        assertNotEquals(mockLive, live);
    }

}