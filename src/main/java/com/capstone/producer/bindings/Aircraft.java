package com.capstone.producer.bindings;

public class Aircraft {
    private String registration;
    private String iata;
    private String icao;
    private String icao24;

    @Override
    public String toString() {
        return String.format("registration: %s | iata: %s | icao: %s | icao24: %s",
                registration, iata, icao, icao24);
    }

    public String getRegistration() {
        return registration;
    }

    public Aircraft setRegistration(String registration) {
        this.registration = registration;
        return this;
    }

    public String getIata() {
        return iata;
    }

    public Aircraft setIata(String iata) {
        this.iata = iata;
        return this;
    }

    public String getIcao() {
        return icao;
    }

    public Aircraft setIcao(String icao) {
        this.icao = icao;
        return this;
    }

    public String getIcao24() {
        return icao24;
    }

    public Aircraft setIcao24(String icao24) {
        this.icao24 = icao24;
        return this;
    }
}
