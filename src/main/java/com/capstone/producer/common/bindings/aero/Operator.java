package com.capstone.producer.common.bindings.aero;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Arrays;
import java.util.StringJoiner;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonRootName(value = "operator")
public class Operator {
    @JsonProperty
    private String icao;
    @JsonProperty
    private String iata;
    @JsonProperty
    private String callsign;
    @JsonProperty
    private String name;
    @JsonProperty
    private String country;
    @JsonProperty
    private String location;
    @JsonProperty
    private String phone;
    @JsonProperty
    private String shortname;
    @JsonProperty
    private String url;
    @JsonProperty
    private String wiki_url;
    @JsonProperty
    private Operator[] alternatives;

    @JsonIgnore
    private String fullJson;

    @Override
    public String toString() {
        return new StringJoiner(", ", Operator.class.getSimpleName() + "[", "]")
                .add("icao='" + icao + "'")
                .add("iata='" + iata + "'")
                .add("callsign='" + callsign + "'")
                .add("name='" + name + "'")
                .add("country='" + country + "'")
                .add("location='" + location + "'")
                .add("phone='" + phone + "'")
                .add("shortname='" + shortname + "'")
                .add("url='" + url + "'")
                .add("wiki_url='" + wiki_url + "'")
                .add("alternatives=" + Arrays.toString(alternatives))
                .toString();
    }

    public String getFullJson() {
        return fullJson;
    }

    public void setFullJson(String fullJson) {
        this.fullJson = fullJson;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWiki_url() {
        return wiki_url;
    }

    public void setWiki_url(String wiki_url) {
        this.wiki_url = wiki_url;
    }

    public Operator[] getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(Operator[] alternatives) {
        this.alternatives = alternatives;
    }


}
