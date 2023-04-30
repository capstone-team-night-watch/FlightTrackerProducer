package com.capstone.producer.common.bindings.aviationstack;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Class used as bindings for the Aviation Stack API responses.
 * This class corresponds to the response provided at the /airports endpoint
 */
public class Airport {
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
        return new ToStringBuilder(this)
                .append("id", id)
                .append("gmt", gmt)
                .append("airport_id", airport_id)
                .append("iata_code", iata_code)
                .append("city_iata_code", city_iata_code)
                .append("icao_code", icao_code)
                .append("country_iso2", country_iso2)
                .append("geoname_id", geoname_id)
                .append("latitude", latitude)
                .append("longitude", longitude)
                .append("airport_name", airport_name)
                .append("country_name", country_name)
                .append("phone_number", phone_number)
                .append("timezone", timezone)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Airport airport = (Airport) o;

        return new EqualsBuilder().append(getLatitude(), airport.getLatitude()).append(getLongitude(), airport.getLongitude()).append(getId(), airport.getId()).append(getGmt(), airport.getGmt()).append(getAirport_id(), airport.getAirport_id()).append(getIata_code(), airport.getIata_code()).append(getCity_iata_code(), airport.getCity_iata_code()).append(getIcao_code(), airport.getIcao_code()).append(getCountry_iso2(), airport.getCountry_iso2()).append(getGeoname_id(), airport.getGeoname_id()).append(getAirport_name(), airport.getAirport_name()).append(getCountry_name(), airport.getCountry_name()).append(getPhone_number(), airport.getPhone_number()).append(getTimezone(), airport.getTimezone()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getGmt()).append(getAirport_id()).append(getIata_code()).append(getCity_iata_code()).append(getIcao_code()).append(getCountry_iso2()).append(getGeoname_id()).append(getLatitude()).append(getLongitude()).append(getAirport_name()).append(getCountry_name()).append(getPhone_number()).append(getTimezone()).toHashCode();
    }

    public String getId() {
        return id;
    }

    public Airport setId(String id) {
        this.id = id;
        return this;
    }

    public String getGmt() {
        return gmt;
    }

    public Airport setGmt(String gmt) {
        this.gmt = gmt;
        return this;
    }

    public String getAirport_id() {
        return airport_id;
    }

    public Airport setAirport_id(String airport_id) {
        this.airport_id = airport_id;
        return this;
    }

    public String getIata_code() {
        return iata_code;
    }

    public Airport setIata_code(String iata_code) {
        this.iata_code = iata_code;
        return this;
    }

    public String getCity_iata_code() {
        return city_iata_code;
    }

    public Airport setCity_iata_code(String city_iata_code) {
        this.city_iata_code = city_iata_code;
        return this;
    }

    public String getIcao_code() {
        return icao_code;
    }

    public Airport setIcao_code(String icao_code) {
        this.icao_code = icao_code;
        return this;
    }

    public String getCountry_iso2() {
        return country_iso2;
    }

    public Airport setCountry_iso2(String country_iso2) {
        this.country_iso2 = country_iso2;
        return this;
    }

    public String getGeoname_id() {
        return geoname_id;
    }

    public Airport setGeoname_id(String geoname_id) {
        this.geoname_id = geoname_id;
        return this;
    }

    public float getLatitude() {
        return latitude;
    }

    public Airport setLatitude(float latitude) {
        this.latitude = latitude;
        return this;
    }

    public float getLongitude() {
        return longitude;
    }

    public Airport setLongitude(float longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getAirport_name() {
        return airport_name;
    }

    public Airport setAirport_name(String airport_name) {
        this.airport_name = airport_name;
        return this;
    }

    public String getCountry_name() {
        return country_name;
    }

    public Airport setCountry_name(String country_name) {
        this.country_name = country_name;
        return this;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public Airport setPhone_number(String phone_number) {
        this.phone_number = phone_number;
        return this;
    }

    public String getTimezone() {
        return timezone;
    }

    public Airport setTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }
}
