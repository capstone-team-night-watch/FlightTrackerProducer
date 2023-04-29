package com.capstone.producer.common.bindings;

import org.apache.commons.lang.builder.ToStringBuilder;

public class GenerateRequest {
    protected String airlineName;
    protected String flightIcao;

    protected float longitude;
    protected float latitude;
    protected float altitude;

    protected float longitudeChange;
    protected float latitudeChange;
    protected float altitudeChange;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("airlineName", airlineName)
                .append("flightIcao", flightIcao)
                .toString();
    }

    public String getAirlineName() {
        return airlineName;
    }

    public GenerateRequest setAirlineName(String airlineName) {
        this.airlineName = airlineName;
        return this;
    }

    public String getFlightIcao() {
        return flightIcao;
    }

    public GenerateRequest setFlightIcao(String flightIcao) {
        this.flightIcao = flightIcao;
        return this;
    }

    public GenerateRequest setAltitude(float altitude) {
        this.altitude = altitude;
        return this;
    }

    public float getLongitude() {
        return longitude;
    }

    public GenerateRequest setLongitude(float longitude) {
        this.longitude = longitude;
        return this;
    }

    public float getLatitude() {
        return latitude;
    }

    public GenerateRequest setLatitude(float latitude) {
        this.latitude = latitude;
        return this;
    }

    public float getAltitude() {
        return altitude;
    }

    public float getLongitudeChange() {
        return longitudeChange;
    }

    public GenerateRequest setLongitudeChange(float longitudeChange) {
        this.longitudeChange = longitudeChange;
        return this;
    }

    public float getLatitudeChange() {
        return latitudeChange;
    }

    public GenerateRequest setLatitudeChange(float latitudeChange) {
        this.latitudeChange = latitudeChange;
        return this;
    }

    public float getAltitudeChange() {
        return altitudeChange;
    }

    public GenerateRequest setAltitudeChange(float altitudeChange) {
        this.altitudeChange = altitudeChange;
        return this;
    }
}
