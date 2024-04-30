package com.capstone.producer.shared;

import com.capstone.producer.common.bindings.aero.Origin;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class JsonHelperTest {

    private Origin origin;

    @Before
    public void setUp() {
        origin = new Origin();
        origin.setCode("origin-code");
        origin.setCodeIcao("origin-codeIcao");
        origin.setCodeIata("origin-codeIata");
        origin.setCodeLid("origin-codeLid");
        origin.setTimezone("timezone");
        origin.setName("origin-name");
        origin.setCity("origin-city");
        origin.setAirportInfoUrl("airport-info-url");
    }

    @Test
    public void should_convert_object_to_string() {
        String result = JsonHelper.toJson(origin);
        assertNotNull(result);
    }

    @Test
    public void should_covert_to_optional_string() {
        String jsonString = """
                    {
                      "code": "origin-code",
                      "code_icao": "origin-codeIcao",
                      "code_iata": "origin-codeIata",
                      "code_lid": "origin-codeLid",
                      "timezone": "timezone",
                      "name": "origin-name",
                      "city": "origin-city",
                      "airport_info_url": "airport-info-url"
                    }
                    """;
        Optional<Origin> result = JsonHelper.fromJson(jsonString, Origin.class);
        assertTrue(result.isPresent());
    }

}