package com.capstone.producer.common.bindings.aviationstack;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Class used as bindings for the Aviation Stack API responses.
 * This class corresponds to the 'arrival' JSON Object included in the response provided at the /flights endpoint
 * Information in here isn't really used but is necessary to build FlightInfo Objects
 */
@Data
public class CodeShared {
    @JsonProperty("airline_name")
    private String airlineName;

    @JsonProperty("airline_iata")
    private String airlineIata;

    @JsonProperty("airline_icao")
    private String airlineIcao;

    @JsonProperty("flight_number")
    private int flightNumber;

    @JsonProperty("flight_iata")
    private String flightIata;

    @JsonProperty("flight_icao")
    private String flightIcao;
}
