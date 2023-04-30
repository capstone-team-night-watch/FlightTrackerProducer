package com.capstone.producer.common.bindings.aviationstack;

/**
 * Class used as bindings for the Aviation Stack API responses.
 * This class corresponds to the response provided at the /flights endpoint.
 * Includes Objects from the other /flights bindings classes
 */
public class FlightInfo {
    private String flight_date;
    private String flight_status;
    private Departure departure;
    private Arrival arrival;
    private Airline airline;
    private Flight flight;
    private Aircraft aircraft;
    private Live live;

    @Override
    public String toString() {
        return String.format("flight_date: %s | flight_status: %s | Departure: { %s } | Arrival: { %s } |" +
                        " Airline: { %s } | Flight: { %s } | Aircraft: { %s } | Live: { %s }", flight_date, flight_status,
                departure, arrival, airline, flight, aircraft, live);
    }

    public String getFlight_date() {
        return flight_date;
    }

    public void setFlight_date(String flight_date) {
        this.flight_date = flight_date;
    }

    public String getFlight_status() {
        return flight_status;
    }

    public void setFlight_status(String flight_status) {
        this.flight_status = flight_status;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public Arrival getArrival() {
        return arrival;
    }

    public void setArrival(Arrival arrival) {
        this.arrival = arrival;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Live getLive() {
        return live;
    }

    public void setLive(Live live) {
        this.live = live;
    }
}
