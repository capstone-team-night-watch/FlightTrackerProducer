package com.capstone.producer.common.bindings;

public class Airline {
    private String name;
    private String iata;
    private String icao;

    @Override
    public String toString() {
        return String.format("name: %s | icao: %s | iata: %s", name, iata, icao);
    }

    public String getName() {
        return name;
    }

    public Airline setName(String name) {
        this.name = name;
        return this;
    }

    public String getIata() {
        return iata;
    }

    public Airline setIata(String iata) {
        this.iata = iata;
        return this;
    }

    public String getIcao() {
        return icao;
    }

    public Airline setIcao(String icao) {
        this.icao = icao;
        return this;
    }
}
