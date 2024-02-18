package com.capstone.producer.common.bindings.aviationstack;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Class used as bindings for the Aviation Stack API responses.
 * This class corresponds to the response provided at the /flights endpoint.
 * Includes Objects from the other /flights bindings classes
 */
@Data
public class FlightInfo {
    @JsonProperty("flight_date")
    private String flightDate;

    @JsonProperty("flight_status")
    private String flightStatus;

    private Departure departure;

    private Arrival arrival;

    private Airline airline;

    private Flight flight;

    private Aircraft aircraft;

    private Live live;
}
