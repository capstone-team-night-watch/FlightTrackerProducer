package com.capstone.producer.common.bindings.aviationstack;

import com.fasterxml.jackson.annotation.JsonAlias;
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

    @JsonAlias("airport_id")
    private String airportId;

    @JsonAlias("iata_code")
    private String iataCode;

    @JsonAlias("city_iata_code")
    private String cityIataCode;

    @JsonAlias("icao_code")
    private String icaoCode;

    @JsonAlias("country_iso2")
    private String countryIso2;

    @JsonAlias("geoname_id")
    private String geonameId;

    private float latitude;
    private float longitude;

    @JsonAlias("airport_name")
    private String airportName;

    @JsonAlias("country_name")
    private String countryName;

    @JsonAlias("phone_number")
    private String phoneNumber;

    private String timezone;
}
