package com.capstone.producer.common.bindings.aero;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class Destination {
    private String code;

    @JsonProperty("code_icao")
    private String codeIcao;

    @JsonProperty("code_iata")
    private String codeIata;

    @JsonProperty("code_lid")
    private String codeLid;

    private String timezone;

    private String name;

    private String city;

    @JsonProperty("airport_info_url")
    private String airportInfoUrl;
}
