package com.capstone.producer.common.bindings;

public class AirportGenerateRequest extends GenerateRequest{
    private String departAirport;
    private String arriveAirport;

    private float finalLongitude;
    private float finalLatitude;

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
}
