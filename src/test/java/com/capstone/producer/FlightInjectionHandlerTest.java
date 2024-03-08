package com.capstone.producer;

import com.capstone.producer.kafka.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FlightInjectionHandlerTest {

    @InjectMocks
    private FlightInjectionHandler flightInjectionHandler;

    @Mock
    private MockedStatic<KafkaProducer> mockedStatic;


    private String flightsNotInArray = """
        {
            "flights": {
                "i_am_invalid": "INVALID, NOT ARRAY"
            },
            "links": {
                "next": "/flights/search?query=-aboveAltitude%2050&cursor=7db67bc91e2acd4"
            },
            "num_pages": 1
        }
            """;
    private String badFlightObject = """
        {
            "flights": [{
                "i_am_invalid": "INVALID, NOT ARRAY"
            }],
            "links": {
                "next": "/flights/search?query=-aboveAltitude%2050&cursor=7db67bc91e2acd4"
            },
            "num_pages": 1
        }
            """;

    private String validFlights = """
        {
            "flights": [
                {
                    "ident": "AIC102",
                    "ident_icao": "AIC102",
                    "ident_iata": "AI102",
                    "fa_flight_id": "AIC102-1709487870-schedule-1653p",
                    "actual_off": "2024-03-05T17:51:36Z",
                    "actual_on": null,
                    "foresight_predictions_available": true,
                    "predicted_out": null,
                    "predicted_off": null,
                    "predicted_on": null,
                    "predicted_in": null,
                    "predicted_out_source": null,
                    "predicted_off_source": null,
                    "predicted_on_source": null,
                    "predicted_in_source": null,
                    "origin": {
                        "code": "KJFK",
                        "code_icao": "KJFK",
                        "code_iata": "JFK",
                        "code_lid": "JFK",
                        "timezone": "America/New_York",
                        "name": "John F Kennedy Intl",
                        "city": "New York",
                        "airport_info_url": "/airports/KJFK"
                    },
                    "destination": {
                        "code": "VIDP",
                        "code_icao": "VIDP",
                        "code_iata": "DEL",
                        "code_lid": null,
                        "timezone": "Asia/Kolkata",
                        "name": "Indira Gandhi Int'l",
                        "city": "New Delhi",
                        "airport_info_url": "/airports/VIDP"
                    },
                    "waypoints": [
                        40.64,
                        -73.78,
                        28.57,
                        77.1
                    ],
                    "first_position_time": "2024-03-05T17:23:39Z",
                    "last_position": {
                        "fa_flight_id": "AIC102-1709487870-schedule-1653p",
                        "altitude": 330,
                        "altitude_change": "-",
                        "groundspeed": 500,
                        "heading": 136,
                        "latitude": 71.37379,
                        "longitude": 42.39663,
                        "timestamp": "2024-03-06T01:03:33Z",
                        "update_type": "P"
                    },
                    "bounding_box": [
                        76.42177,
                        -73.78644,
                        40.62222,
                        42.39663
                    ],
                    "ident_prefix": null,
                    "aircraft_type": "B77W"
                }
            ],
            "links": {
                "next": "/flights/search?query=-aboveAltitude%2050&cursor=7db67bc91e2acd4"
            },
            "num_pages": 1
        }
            """;

    @Test
    public void testInputNotInArray() throws InterruptedException, JsonProcessingException{
        String result = flightInjectionHandler.handleFlightInjection(flightsNotInArray);
        assertEquals("Received not as an array of flight objects", result);
    }

    @Test
    public void testInvalidFlightObject() throws InterruptedException, JsonProcessingException{
        String result = flightInjectionHandler.handleFlightInjection(badFlightObject);
        assertEquals("Not all flights processed successfully", result);
    }

    @Test
    public void testValidInput() throws InterruptedException, JsonProcessingException{
        mockedStatic.when(() -> KafkaProducer.emitFlightInformationUpdate(any())).thenCallRealMethod();
        mockedStatic.when(() -> KafkaProducer.runProducer(anyString(), anyString())).thenReturn(null);

        
        String result = flightInjectionHandler.handleFlightInjection(validFlights);
        assertEquals("All flights processed successfully", result);


        mockedStatic.verify(() -> KafkaProducer.runProducer(anyString(), anyString()), times(1));
        mockedStatic.close();
    }


}
