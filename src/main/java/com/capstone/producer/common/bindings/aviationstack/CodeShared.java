package com.capstone.producer.common.bindings.aviationstack;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * Class used as bindings for the Aviation Stack API responses.
 * This class corresponds to the 'arrival' JSON Object included in the response provided at the /flights endpoint
 * Information in here isn't really used but is necessary to build FlightInfo Objects
 */
@Data
public class CodeShared {
    @JsonAlias("airline_name")
    private String airlineName;

    @JsonAlias("airline_iata")
    private String airlineIata;

    @JsonAlias("airline_icao")
    private String airlineIcao;

    @JsonAlias("flight_number")
    private int flightNumber;

    @JsonAlias("flight_iata")
    private String flightIata;

    @JsonAlias("flight_icao")
    private String flightIcao;
}
