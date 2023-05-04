package com.capstone.producer.common.bindings;

import java.util.StringJoiner;

/**
 * Request Object that is received by the /airport/generate endpoint in this application.
 * Helps facilitate the generation of a Mock flight when only given airport names
 * Airport information is the additional functionality added on top of the GenerateRequest class
 */
public class AirportGenerateRequest extends GenerateRequest {
    private String departAirport;
    private String arriveAirport;

    private float finalLongitude;
    private float finalLatitude;

    public String getDepartAirport() {
        return departAirport;
    }

    public AirportGenerateRequest setDepartAirport(String departAirport) {
        this.departAirport = departAirport;
        return this;
    }

    public String getArriveAirport() {
        return arriveAirport;
    }

    public AirportGenerateRequest setArriveAirport(String arriveAirport) {
        this.arriveAirport = arriveAirport;
        return this;
    }

    public float getFinalLongitude() {
        return finalLongitude;
    }

    public AirportGenerateRequest setFinalLongitude(float finalLongitude) {
        this.finalLongitude = finalLongitude;
        return this;
    }

    public float getFinalLatitude() {
        return finalLatitude;
    }

    public AirportGenerateRequest setFinalLatitude(float finalLatitude) {
        this.finalLatitude = finalLatitude;
        return this;
    }

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
