package com.capstone.producer.common.bindings.aero;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.StringJoiner;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Airport {
    @JsonProperty
    private String airport_code;
    @JsonProperty
    private String alternate_ident;
    @JsonProperty
    private String code_icao;
    @JsonProperty
    private String code_iata;
    @JsonProperty
    private String code_lid;
    @JsonProperty
    private String name;
    @JsonProperty
    private String type;
    @JsonProperty
    private float elevation;
    @JsonProperty
    private String city;
    @JsonProperty
    private String state;
    @JsonProperty
    private float longitude;
    @JsonProperty
    private float latitude;
    @JsonProperty
    private String timezone;
    @JsonProperty
    private String country_code;
    @JsonProperty
    private String wiki_url;
    @JsonProperty
    private String airport_flights_url;
    @JsonProperty
    private String alternatives;

    @Override
    public String toString() {
        return new StringJoiner(", ", Airport.class.getSimpleName() + "[", "]")
                .add("airport_code='" + airport_code + "'")
                .add("alternate_ident='" + alternate_ident + "'")
                .add("code_icao='" + code_icao + "'")
                .add("code_iata='" + code_iata + "'")
                .add("code_lid='" + code_lid + "'")
                .add("name='" + name + "'")
                .add("type='" + type + "'")
                .add("elevation=" + elevation)
                .add("city='" + city + "'")
                .add("state='" + state + "'")
                .add("longitude=" + longitude)
                .add("latitude=" + latitude)
                .add("timezone='" + timezone + "'")
                .add("country_code='" + country_code + "'")
                .add("wiki_url='" + wiki_url + "'")
                .add("airport_flights_url='" + airport_flights_url + "'")
                .add("alternatives='" + alternatives + "'")
                .toString();
    }

    public String getAirport_code() {
        return airport_code;
    }

    public void setAirport_code(String airport_code) {
        this.airport_code = airport_code;
    }

    public String getAlternate_ident() {
        return alternate_ident;
    }

    public void setAlternate_ident(String alternate_ident) {
        this.alternate_ident = alternate_ident;
    }

    public String getCode_icao() {
        return code_icao;
    }

    public void setCode_icao(String code_icao) {
        this.code_icao = code_icao;
    }

    public String getCode_iata() {
        return code_iata;
    }

    public void setCode_iata(String code_iata) {
        this.code_iata = code_iata;
    }

    public String getCode_lid() {
        return code_lid;
    }

    public void setCode_lid(String code_lid) {
        this.code_lid = code_lid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getElevation() {
        return elevation;
    }

    public void setElevation(float elevation) {
        this.elevation = elevation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getWiki_url() {
        return wiki_url;
    }

    public void setWiki_url(String wiki_url) {
        this.wiki_url = wiki_url;
    }

    public String getAirport_flights_url() {
        return airport_flights_url;
    }

    public void setAirport_flights_url(String airport_flights_url) {
        this.airport_flights_url = airport_flights_url;
    }

    public String getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(String alternatives) {
        this.alternatives = alternatives;
    }
}
