package com.capstone.producer.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AviationStackClientCallerTest {
    private static final String SERVICE_NAME = "/flights";
    private static final String BASE_URL = "http://localhost:9091/services";

    @Mock
    private RestTemplate client;

    private HttpHeaders headers;

    private AviationStackClientCaller aviationStackClientCaller;
    private ObjectMapper om;
    private class SampleDataObject {
        String field1;
        String field2;

        public SampleDataObject(String field1, String field2) {
            this.field1 = field1;
            this.field2 = field2;
        }

        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public String getField2() {
            return field2;
        }

        public void setField2(String field2) {
            this.field2 = field2;
        }
    }

    @Before
    public void setUp() {
        om = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        aviationStackClientCaller = new AviationStackClientCaller(client, BASE_URL, "key");
    }

    @Test
    public void aviationStackClientCallerTest() throws JsonProcessingException {
        JsonNode expectedResponse = om.readTree("{ \"field1\" : \"Fee\", \"field2\" : \"Fi\"}");

        String urlComponents = UriComponentsBuilder.fromHttpUrl(BASE_URL+SERVICE_NAME)
                .queryParam("access_key", "key")
                .queryParam("flight_icao", "CUS7777")
                .build().toString();

        when(client.exchange(urlComponents, HttpMethod.GET, new HttpEntity<>(headers), JsonNode.class)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        JsonNode actual = aviationStackClientCaller.getFlight("CUS7777");
        assertEquals(om.writeValueAsString(new SampleDataObject("Fee", "Fi")), om.writeValueAsString(actual));
    }
}
