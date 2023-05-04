package com.capstone.producer.common.bindings.aero;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.StringJoiner;

//@JsonRootName(value = "destination")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Destination {
    @JsonProperty
    private String code;
    @JsonProperty
    private String code_icao;
    @JsonProperty
    private String code_iata;
    @JsonProperty
    private String code_lid;
    @JsonProperty
    private String timezone;
    @JsonProperty
    private String name;
    @JsonProperty
    private String city;
    @JsonProperty
    private String airport_info_url;

    @Override
    public String toString() {
        return new StringJoiner(", ", Destination.class.getSimpleName() + "[", "]")
                .add("code='" + code + "'")
                .add("code_icao='" + code_icao + "'")
                .add("code_iata='" + code_iata + "'")
                .add("code_lid='" + code_lid + "'")
                .add("timezone='" + timezone + "'")
                .add("name='" + name + "'")
                .add("city='" + city + "'")
                .add("airport_info_url='" + airport_info_url + "'")
                .toString();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirport_info_url() {
        return airport_info_url;
    }

    public void setAirport_info_url(String airport_info_url) {
        this.airport_info_url = airport_info_url;
    }
}
