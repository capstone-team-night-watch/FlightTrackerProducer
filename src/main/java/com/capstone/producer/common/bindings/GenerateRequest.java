package com.capstone.producer.common.bindings;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GenerateRequest {
    private String airlineName;
    private String flightIcao;

    private float longitude;
    private float latitude;
    private float altitude;

    private float longitudeChange;
    private float latitudeChange;
    private float altitudeChange;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("airlineName", airlineName)
                .append("flightIcao", flightIcao)
                .append("longitude", longitude)
                .append("latitude", latitude)
                .append("altitude", altitude)
                .append("longitudeChange", longitudeChange)
                .append("latitudeChange", latitudeChange)
                .append("altitudeChange", altitudeChange)
                .toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        GenerateRequest that = (GenerateRequest) o;

        return new EqualsBuilder().append(getLongitude(), that.getLongitude()).append(getLatitude(), that.getLatitude()).append(getAltitude(), that.getAltitude()).append(getLongitudeChange(), that.getLongitudeChange()).append(getLatitudeChange(), that.getLatitudeChange()).append(getAltitudeChange(), that.getAltitudeChange()).append(getAirlineName(), that.getAirlineName()).append(getFlightIcao(), that.getFlightIcao()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getAirlineName()).append(getFlightIcao()).append(getLongitude()).append(getLatitude()).append(getAltitude()).append(getLongitudeChange()).append(getLatitudeChange()).append(getAltitudeChange()).toHashCode();
    }
}
