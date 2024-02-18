package com.capstone.producer.common.bindings.aviationstack;

import lombok.Data;

/**
 * Class used as bindings for the Aviation Stack API Responses.
 * This class corresponds to the 'aircraft' JSON Object included in the response provided at the /flights endpoint
 */
@Data
public class Aircraft {
    private String registration;

    private String iata;

    private String icao;

    private String icao24;
}
