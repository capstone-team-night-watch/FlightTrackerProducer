package com.capstone.producer.common.bindings.aviationstack;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.StringJoiner;

/**
 * Class used as bindings for the Aviation Stack API responses.
 * This class corresponds to the response provided at the /airports endpoint
 */
public class Airport_Aviation {
    private String id;
    private String gmt;
    private String airport_id;
    private String iata_code;
    private String city_iata_code;
    private String icao_code;
    private String country_iso2;
    private String geoname_id;
    private float latitude;
    private float longitude;
    private String airport_name;
    private String country_name;
    private String phone_number;
    private String timezone;

    @Override
    public String toString() {
        return new StringJoiner(", ", Airport_Aviation.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("gmt='" + gmt + "'")
                .add("airport_id='" + airport_id + "'")
                .add("iata_code='" + iata_code + "'")
                .add("city_iata_code='" + city_iata_code + "'")
                .add("icao_code='" + icao_code + "'")
                .add("country_iso2='" + country_iso2 + "'")
                .add("geoname_id='" + geoname_id + "'")
                .add("latitude=" + latitude)
                .add("longitude=" + longitude)
                .add("airport_name='" + airport_name + "'")
                .add("country_name='" + country_name + "'")
                .add("phone_number='" + phone_number + "'")
                .add("timezone='" + timezone + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Airport_Aviation airportAviation = (Airport_Aviation) o;

        return new EqualsBuilder().append(getLatitude(), airportAviation.getLatitude()).append(getLongitude(), airportAviation.getLongitude()).append(getId(), airportAviation.getId()).append(getGmt(), airportAviation.getGmt()).append(getAirport_id(), airportAviation.getAirport_id()).append(getIata_code(), airportAviation.getIata_code()).append(getCity_iata_code(), airportAviation.getCity_iata_code()).append(getIcao_code(), airportAviation.getIcao_code()).append(getCountry_iso2(), airportAviation.getCountry_iso2()).append(getGeoname_id(), airportAviation.getGeoname_id()).append(getAirport_name(), airportAviation.getAirport_name()).append(getCountry_name(), airportAviation.getCountry_name()).append(getPhone_number(), airportAviation.getPhone_number()).append(getTimezone(), airportAviation.getTimezone()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getGmt()).append(getAirport_id()).append(getIata_code()).append(getCity_iata_code()).append(getIcao_code()).append(getCountry_iso2()).append(getGeoname_id()).append(getLatitude()).append(getLongitude()).append(getAirport_name()).append(getCountry_name()).append(getPhone_number()).append(getTimezone()).toHashCode();
    }

    public String getId() {
        return id;
    }

    public Airport_Aviation setId(String id) {
        this.id = id;
        return this;
    }

    public String getGmt() {
        return gmt;
    }

    public Airport_Aviation setGmt(String gmt) {
        this.gmt = gmt;
        return this;
    }

    public String getAirport_id() {
        return airport_id;
    }

    public Airport_Aviation setAirport_id(String airport_id) {
        this.airport_id = airport_id;
        return this;
    }

    public String getIata_code() {
        return iata_code;
    }

    public Airport_Aviation setIata_code(String iata_code) {
        this.iata_code = iata_code;
        return this;
    }

    public String getCity_iata_code() {
        return city_iata_code;
    }

    public Airport_Aviation setCity_iata_code(String city_iata_code) {
        this.city_iata_code = city_iata_code;
        return this;
    }

    public String getIcao_code() {
        return icao_code;
    }

    public Airport_Aviation setIcao_code(String icao_code) {
        this.icao_code = icao_code;
        return this;
    }

    public String getCountry_iso2() {
        return country_iso2;
    }

    public Airport_Aviation setCountry_iso2(String country_iso2) {
        this.country_iso2 = country_iso2;
        return this;
    }

    public String getGeoname_id() {
        return geoname_id;
    }

    public Airport_Aviation setGeoname_id(String geoname_id) {
        this.geoname_id = geoname_id;
        return this;
    }

    public float getLatitude() {
        return latitude;
    }

    public Airport_Aviation setLatitude(float latitude) {
        this.latitude = latitude;
        return this;
    }

    public float getLongitude() {
        return longitude;
    }

    public Airport_Aviation setLongitude(float longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getAirport_name() {
        return airport_name;
    }

    public Airport_Aviation setAirport_name(String airport_name) {
        this.airport_name = airport_name;
        return this;
    }

    public String getCountry_name() {
        return country_name;
    }

    public Airport_Aviation setCountry_name(String country_name) {
        this.country_name = country_name;
        return this;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public Airport_Aviation setPhone_number(String phone_number) {
        this.phone_number = phone_number;
        return this;
    }

    public String getTimezone() {
        return timezone;
    }

    public Airport_Aviation setTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }
}
