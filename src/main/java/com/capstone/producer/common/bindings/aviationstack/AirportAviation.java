package com.capstone.producer.common.bindings.aviationstack;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class used as bindings for the Aviation Stack API responses.
 * This class corresponds to the response provided at the /airports endpoint
 */
@Data
@EqualsAndHashCode
public class AirportAviation {
    private String id;
    private String gmt;

    @JsonProperty("airport_id")
    private String airportId;

    @JsonProperty("iata_code")
    private String iataCode;

    @JsonProperty("city_iata_code")
    private String cityIataCode;

    @JsonProperty("icao_code")
    private String icaoCode;

    @JsonProperty("country_iso2")
    private String countryIso2;

    @JsonProperty("geoname_id")
    private String geonameId;

    private float latitude;
    private float longitude;

    @JsonProperty("airport_name")
    private String airportName;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String timezone;
}
