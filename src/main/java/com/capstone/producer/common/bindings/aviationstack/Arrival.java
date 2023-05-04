package com.capstone.producer.common.bindings.aviationstack;

/**
 * Class used as bindings for the Aviation Stack API responses.
 * This class corresponds to the 'arrival' JSON Object included in the response provided at the /flights endpoint
 */
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
        return String.format("{\"airport\":\"%s\",\"timezone\":\"%s\",\"iata\":\"%s\",\"icao\":\"%s\",\"terminal\":\"%s\",\"gate\":\"%s\",\"baggage\":\"%s\"," +
                        "\"delay\":\"%s\",\"scheduled\":\"%s\",\"estimated\":\"%s\",\"actual\":\"%s\",\"estimated_runway\":\"%s\",\"actual_runway\":\"%s\"}",
                airport, timezone, iata, icao, terminal, gate, baggage, delay, scheduled, estimated, actual, estimated_runway, actual_runway);
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getBaggage() {
        return baggage;
    }

    public void setBaggage(String baggage) {
        this.baggage = baggage;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    public String getEstimated() {
        return estimated;
    }

    public void setEstimated(String estimated) {
        this.estimated = estimated;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getEstimated_runway() {
        return estimated_runway;
    }

    public void setEstimated_runway(String estimated_runway) {
        this.estimated_runway = estimated_runway;
    }

    public String getActual_runway() {
        return actual_runway;
    }

    public void setActual_runway(String actual_runway) {
        this.actual_runway = actual_runway;
    }
}
