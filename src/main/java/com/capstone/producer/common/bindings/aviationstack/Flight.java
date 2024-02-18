package com.capstone.producer.common.bindings.aviationstack;

import lombok.Data;

/**
 * Class used as bindings for the Aviation Stack API responses.
 * This class corresponds to the 'flight' JSON Object included in the response provided at the /flights endpoint
 */

@Data
public class Flight {
    private int number;

    private String iata;

    private String icao;

    private CodeShared codeshared;
}
