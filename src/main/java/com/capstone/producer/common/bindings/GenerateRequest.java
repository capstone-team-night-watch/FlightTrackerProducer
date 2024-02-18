package com.capstone.producer.common.bindings;

import lombok.Data;

import java.util.StringJoiner;

/**
 * Request Object that is received by the /airport/generate endpoint in this application.
 * Helps facilitate the generation of a Mock flight when given all the non-airport information
 */
@Data
public class GenerateRequest {
    protected String airlineName;
    protected   String flightIcao;

    protected float longitude;
    protected float latitude;
    protected float altitude;

    protected float longitudeChange;
    protected float latitudeChange;
    protected float altitudeChange;

    @Override
    public String toString() {
        return new StringJoiner(", ", GenerateRequest.class.getSimpleName() + "[", "]")
                .add("airlineName='" + airlineName + "'")
                .add("flightIcao='" + flightIcao + "'")
                .add("longitude=" + longitude)
                .add("latitude=" + latitude)
                .add("altitude=" + altitude)
                .add("longitudeChange=" + longitudeChange)
                .add("latitudeChange=" + latitudeChange)
                .add("altitudeChange=" + altitudeChange)
                .toString();
    }
}
