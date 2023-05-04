package com.capstone.producer.common.bindings.aviationstack;

/**
 * Class used as bindings for the Aviation Stack API Responses.
 * This class corresponds to the 'aircraft' JSON Object included in the response provided at the /flights endpoint
 */
public class Aircraft {
    private String registration;
    private String iata;
    private String icao;
    private String icao24;

    @Override
    public String toString() {
        return String.format("{\"registration\":\"%s\",\"iata\":\"%s\",\"icao\":\"%s\",\"icao24\":\"%s\"}",
                registration, iata, icao, icao24);
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
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

    public String getIcao24() {
        return icao24;
    }

    public void setIcao24(String icao24) {
        this.icao24 = icao24;
    }
}
