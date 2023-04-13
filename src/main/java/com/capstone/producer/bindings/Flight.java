package com.capstone.producer.bindings;

public class Flight {
    private int number;
    private String iata;
    private String icao;
    private Codeshared codeshared;

    @Override
    public String toString() {
        return String.format("number: %d | icao: %s | iata: %s | codeshared { %s }", number, icao, iata, codeshared);
    }

    public int getNumber() {
        return number;
    }

    public Flight setNumber(int number) {
        this.number = number;
        return this;
    }

    public String getIata() {
        return iata;
    }

    public Flight setIata(String iata) {
        this.iata = iata;
        return this;
    }

    public String getIcao() {
        return icao;
    }

    public Flight setIcao(String icao) {
        this.icao = icao;
        return this;
    }

    public Codeshared getCodeshared() {
        return codeshared;
    }

    public Flight setCodeshared(Codeshared codeshared) {
        this.codeshared = codeshared;
        return this;
    }
}
