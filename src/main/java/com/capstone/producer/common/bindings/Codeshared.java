package com.capstone.producer.common.bindings;

public class Codeshared {
    private String airline_name;
    private String airline_iata;
    private String airline_icao;
    private int flight_number;
    private String flight_iata;
    private String flight_icao;

    @Override
    public String toString() {
        return String.format("Codeshared { airline_name: %s | airline_iata: %s | airline_icao: %s | flight_number: %s |" +
                        " flight_iata: %s | flight_icao: %s", airline_name, airline_iata, airline_icao, flight_number,
                flight_iata, flight_icao);
    }

    public String getAirlineName() {
        return airline_name;
    }

    public Codeshared setAirlineName(String airlineName) {
        this.airline_name = airlineName;
        return this;
    }

    public String getAirlineIata() {
        return airline_iata;
    }

    public Codeshared setAirlineIata(String airlineIata) {
        this.airline_iata = airlineIata;
        return this;
    }

    public String getAirlineIcao() {
        return airline_icao;
    }

    public Codeshared setAirlineIcao(String airlineIcao) {
        this.airline_icao = airlineIcao;
        return this;
    }

    public int getFlightNumber() {
        return flight_number;
    }

    public Codeshared setFlightNumber(int flightNumber) {
        this.flight_number = flightNumber;
        return this;
    }

    public String getFlightIata() {
        return flight_iata;
    }

    public Codeshared setFlightIata(String flightIata) {
        this.flight_iata = flightIata;
        return this;
    }

    public String getFlightIcao() {
        return flight_icao;
    }

    public Codeshared setFlightIcao(String flightIcao) {
        this.flight_icao = flightIcao;
        return this;
    }
}
