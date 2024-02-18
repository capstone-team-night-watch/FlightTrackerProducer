package com.capstone.producer.common.bindings.aero;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Airport {
    @JsonProperty("airport_code")
    private String airportCode;

    @JsonProperty("alternate_ident")
    private String alternateIdent;

    @JsonProperty("code_icao")
    private String codeIcao;

    @JsonProperty("code_iata")
    private String codeIata;

    @JsonProperty("code_lid")
    private String codeLid;

    private String name;

    private String type;

    private float elevation;

    private String city;

    private String state;

    private float longitude;

    private float latitude;

    private String timezone;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("wiki_url")
    private String wikiUrl;

    @JsonProperty("airport_info_url")
    private String airportInfoUrl;

    private String alternatives;
}
