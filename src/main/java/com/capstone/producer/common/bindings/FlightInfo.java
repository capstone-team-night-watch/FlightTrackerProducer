package com.capstone.producer.common.bindings;

public class FlightInfo {
    private String flight_date;
    private String flight_status;
    private Departure departure;
    private Arrival arrival;
    private Airline airline;
    private Flight flight;
    private Object aircraft;
    private Object live;

    @Override
    public String toString() {
        return String.format("flight_date: %s | flight_status: %s | departure: { %s } | arrival: { %s } |" +
                        " airline: { %s } | flight: { %s } | aircraft: { %s } | live: { %s }", flight_date, flight_status,
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

    public Object getAircraft() {
        return aircraft;
    }

    public void setAircraft(Object aircraft) {
        this.aircraft = aircraft;
    }

    public Object getLive() {
        return live;
    }

    public void setLive(Object live) {
        this.live = live;
    }
}
