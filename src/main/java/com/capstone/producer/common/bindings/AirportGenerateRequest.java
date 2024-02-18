package com.capstone.producer.common.bindings;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.StringJoiner;

/**
 * Request Object that is received by the /airport/generate endpoint in this application.
 * Helps facilitate the generation of a Mock flight when only given airport names
 * Airport information is the additional functionality added on top of the GenerateRequest class
 */
@Setter
@Getter
public class AirportGenerateRequest extends GenerateRequest {
    @NotEmpty
    private String departAirport;

    @NotEmpty
    private String arriveAirport;

    private float finalLongitude;
    private float finalLatitude;

    @Override
    public String toString() {
        return new StringJoiner(", ", AirportGenerateRequest.class.getSimpleName() + "[", "]")
                .add("departAirport='" + departAirport + "'")
                .add("arriveAirport='" + arriveAirport + "'")
                .add("finalLongitude=" + finalLongitude)
                .add("finalLatitude=" + finalLatitude)
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
