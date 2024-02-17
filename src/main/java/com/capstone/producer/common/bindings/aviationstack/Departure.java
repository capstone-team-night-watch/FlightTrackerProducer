package com.capstone.producer.common.bindings.aviationstack;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Class used as bindings for the Aviation Stack API responses.
 * This class corresponds to the 'departure' JSON Object included in the response provided at the /flights endpoint
 */

@Data
public class Departure {
    private String airport;

    private String timezone;

    private String iata;

    private String icao;

    private String terminal;

    private String gate;

    private String delay;

    private String scheduled;

    private String estimated;

    private String actual;

    @JsonProperty("estimated_runway")
    private String estimatedRunway;

    @JsonProperty("actual_runway")
    private String actualRunway;
}
