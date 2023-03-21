package com.capstone.producer.common.bindings;

public class Arrival {
    private String airport;
    private String timezone;
    private String iata;
    private String icao;
    private String terminal;
    private String gate;
    private String baggage;
    private String delay;
    private String scheduled;
    private String estimated;
    private String actual;
    private String estimated_runway;
    private String actual_runway;

    @Override
    public String toString() {
        return String.format("Arrival { airport: %s | timezone: %s | iata: %s | icao: %s | terminal: %s | gate: %s |" +
                " baggage: %s | delay: %s | scheduled: %s | estimated: %s | actual: %s | estimated_runway: %s |" + " actual_runway: %s",
                airport, timezone, iata, icao, terminal, gate, baggage, delay, scheduled, estimated, actual, estimated_runway, actual_runway);
    }

    public String getAirport() {
        return airport;
    }

    public Arrival setAirport(String airport) {
        this.airport = airport;
        return this;
    }

    public String getTimezone() {
        return timezone;
    }

    public Arrival setTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    public String getIata() {
        return iata;
    }

    public Arrival setIata(String iata) {
        this.iata = iata;
        return this;
    }

    public String getIcao() {
        return icao;
    }

    public Arrival setIcao(String icao) {
        this.icao = icao;
        return this;
    }

    public String getTerminal() {
        return terminal;
    }

    public Arrival setTerminal(String terminal) {
        this.terminal = terminal;
        return this;
    }

    public String getGate() {
        return gate;
    }

    public Arrival setGate(String gate) {
        this.gate = gate;
        return this;
    }

    public String getBaggage() {
        return baggage;
    }

    public Arrival setBaggage(String baggage) {
        this.baggage = baggage;
        return this;
    }

    public String getDelay() {
        return delay;
    }

    public Arrival setDelay(String delay) {
        this.delay = delay;
        return this;
    }

    public String getScheduled() {
        return scheduled;
    }

    public Arrival setScheduled(String scheduled) {
        this.scheduled = scheduled;
        return this;
    }

    public String getEstimated() {
        return estimated;
    }

    public Arrival setEstimated(String estimated) {
        this.estimated = estimated;
        return this;
    }

    public String getActual() {
        return actual;
    }

    public Arrival setActual(String actual) {
        this.actual = actual;
        return this;
    }

    public String getEstimated_runway() {
        return estimated_runway;
    }

    public Arrival setEstimated_runway(String estimated_runway) {
        this.estimated_runway = estimated_runway;
        return this;
    }

    public String getActual_runway() {
        return actual_runway;
    }

    public Arrival setActual_runway(String actual_runway) {
        this.actual_runway = actual_runway;
        return this;
    }
}
