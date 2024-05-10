package com.capstone.producer.common.bindings.aero;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Operator object
 */
@Data
public class Operator {
    private String icao;

    private String iata;

    @JsonProperty("callsign")
    private String callSign;

    private String name;

    private String country;

    private String location;

    private String phone;

    private String shortname;

    private String url;

    @JsonProperty("wiki_url")
    private String wikiUrl;

    private Operator[] alternatives;

    private String fullJson;
}
