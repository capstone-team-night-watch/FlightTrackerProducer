package com.capstone.producer.common.bindings.aviationstack;

/**
 * Class used as bindings for the Aviation Stack API responses.
 * This class corresponds to the 'arrival' JSON Object included in the response provided at the /flights endpoint
 * Information in here isn't really used but is necessary to build FlightInfo Objects
 */
public class Codeshared {
    private String airline_name;
    private String airline_iata;
    private String airline_icao;
    private int flight_number;
    private String flight_iata;
    private String flight_icao;

    @Override
    public String toString() {
        return String.format("{\"airline_name\":\"%s\",\"airline_iata\":\"%s\",\"airline_icao\":\"%s\",\"flight_number\":\"%s\"," +
                        "\"flight_iata\":\"%s\",\"flight_icao\":\"%s\"}", airline_name, airline_iata, airline_icao, flight_number,
                flight_iata, flight_icao);
    }

    public String getAirline_name() {
        return airline_name;
    }

    public Codeshared setAirline_name(String airline_name) {
        this.airline_name = airline_name;
        return this;
    }

    public String getAirline_iata() {
        return airline_iata;
    }

    public Codeshared setAirline_iata(String airline_iata) {
        this.airline_iata = airline_iata;
        return this;
    }

    public String getAirline_icao() {
        return airline_icao;
    }

    public Codeshared setAirline_icao(String airline_icao) {
        this.airline_icao = airline_icao;
        return this;
    }

    public int getFlight_number() {
        return flight_number;
    }

    public Codeshared setFlight_number(int flight_number) {
        this.flight_number = flight_number;
        return this;
    }

    public String getFlight_iata() {
        return flight_iata;
    }

    public Codeshared setFlight_iata(String flight_iata) {
        this.flight_iata = flight_iata;
        return this;
    }

    public String getFlight_icao() {
        return flight_icao;
    }

    public Codeshared setFlight_icao(String flight_icao) {
        this.flight_icao = flight_icao;
        return this;
    }
}
