package com.capstone.producer.common.bindings.aero;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.StringJoiner;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FlightInfoIdent {
    @JsonProperty
    private String ident;
    @JsonProperty
    private String ident_icao;
    @JsonProperty
    private String ident_iata;
    @JsonProperty
    private String fa_flight_id;
    @JsonProperty
    private String operator;
    @JsonProperty
    private String operator_icao;
    @JsonProperty
    private String operator_iata;
    @JsonProperty
    private String flight_number;
    @JsonProperty
    private String registration;
    @JsonProperty
    private String atc_ident;
    @JsonProperty
    private String inbound_fa_flight_id;
    @JsonProperty
    private String codeshares;
    @JsonProperty
    private String codeshares_iata;
    @JsonProperty
    private boolean blocked;
    @JsonProperty
    private boolean diverted;
    @JsonProperty
    private boolean cancelled;
    @JsonProperty
    private boolean position_only;
    @JsonUnwrapped
    @JsonProperty
    private Origin origin;
    @JsonUnwrapped
    @JsonProperty
    private Destination destination;
    @JsonProperty
    private int departure_delay;
    @JsonProperty
    private int arrival_delay;
    @JsonProperty
    private int filed_ete;
    @JsonProperty
    private int progress_percent;
    @JsonProperty
    private String status;
    @JsonProperty
    private String aircraft_type;
    @JsonProperty
    private int route_distance;
    @JsonProperty
    private int filed_airspeed;
    @JsonProperty
    private int filed_altitude;
    @JsonProperty
    private String route;
    @JsonProperty
    private String baggage_claim;
    @JsonProperty
    private int seats_cabin_business;
    @JsonProperty
    private int seats_cabin_coach;
    @JsonProperty
    private int seats_cabin_first;
    @JsonProperty
    private String gate_origin;
    @JsonProperty
    private String gate_destination;
    @JsonProperty
    private String terminal_origin;
    @JsonProperty
    private String terminal_destination;
    @JsonProperty
    private String type;
    @JsonProperty
    private String scheduled_out;
    @JsonProperty
    private String estimated_out;
    @JsonProperty
    private String actual_out;
    @JsonProperty
    private String scheduled_off;
    @JsonProperty
    private String estimated_off;
    @JsonProperty
    private String actual_off;
    @JsonProperty
    private String scheduled_on;
    @JsonProperty
    private String estimated_on;
    @JsonProperty
    private String actual_on;
    @JsonProperty
    private String scheduled_in;
    @JsonProperty
    private String estimated_in;
    @JsonProperty
    private String actual_in;
    @JsonProperty
    private boolean foresight_predictions_available;

    @JsonIgnore
    private String fullJson;

    @Override
    public String toString() {
        return new StringJoiner(", ", FlightInfoIdent.class.getSimpleName() + "[", "]")
                .add("ident='" + ident + "'")
                .add("ident_icao='" + ident_icao + "'")
                .add("ident_iata='" + ident_iata + "'")
                .add("fa_flight_id='" + fa_flight_id + "'")
                .add("operator='" + operator + "'")
                .add("operator_icao='" + operator_icao + "'")
                .add("operator_iata='" + operator_iata + "'")
                .add("flight_number='" + flight_number + "'")
                .add("registration='" + registration + "'")
                .add("atc_ident='" + atc_ident + "'")
                .add("inbound_fa_flight_id='" + inbound_fa_flight_id + "'")
                .add("codeshares='" + codeshares + "'")
                .add("codeshares_iata='" + codeshares_iata + "'")
                .add("blocked=" + blocked)
                .add("diverted=" + diverted)
                .add("cancelled=" + cancelled)
                .add("position_only=" + position_only)
                .add("origin=" + origin)
                .add("destination=" + destination)
                .add("departure_delay=" + departure_delay)
                .add("arrival_delay=" + arrival_delay)
                .add("filed_ete=" + filed_ete)
                .add("progress_percent=" + progress_percent)
                .add("status='" + status + "'")
                .add("aircraft_type='" + aircraft_type + "'")
                .add("route_distance=" + route_distance)
                .add("filed_airspeed=" + filed_airspeed)
                .add("filed_altitude=" + filed_altitude)
                .add("route='" + route + "'")
                .add("baggage_claim='" + baggage_claim + "'")
                .add("seats_cabin_business=" + seats_cabin_business)
                .add("seats_cabin_coach=" + seats_cabin_coach)
                .add("seats_cabin_first=" + seats_cabin_first)
                .add("gate_origin='" + gate_origin + "'")
                .add("gate_destination='" + gate_destination + "'")
                .add("terminal_origin='" + terminal_origin + "'")
                .add("terminal_destination='" + terminal_destination + "'")
                .add("type='" + type + "'")
                .add("scheduled_out='" + scheduled_out + "'")
                .add("estimated_out='" + estimated_out + "'")
                .add("actual_out='" + actual_out + "'")
                .add("scheduled_off='" + scheduled_off + "'")
                .add("estimated_off='" + estimated_off + "'")
                .add("actual_off='" + actual_off + "'")
                .add("scheduled_on='" + scheduled_on + "'")
                .add("estimated_on='" + estimated_on + "'")
                .add("actual_on='" + actual_on + "'")
                .add("scheduled_in='" + scheduled_in + "'")
                .add("estimated_in='" + estimated_in + "'")
                .add("actual_in='" + actual_in + "'")
                .add("foresight_predictions_available=" + foresight_predictions_available)
                .toString();
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getIdent_icao() {
        return ident_icao;
    }

    public void setIdent_icao(String ident_icao) {
        this.ident_icao = ident_icao;
    }

    public String getIdent_iata() {
        return ident_iata;
    }

    public void setIdent_iata(String ident_iata) {
        this.ident_iata = ident_iata;
    }

    public String getFa_flight_id() {
        return fa_flight_id;
    }

    public void setFa_flight_id(String fa_flight_id) {
        this.fa_flight_id = fa_flight_id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator_icao() {
        return operator_icao;
    }

    public void setOperator_icao(String operator_icao) {
        this.operator_icao = operator_icao;
    }

    public String getOperator_iata() {
        return operator_iata;
    }

    public void setOperator_iata(String operator_iata) {
        this.operator_iata = operator_iata;
    }

    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getAtc_ident() {
        return atc_ident;
    }

    public void setAtc_ident(String atc_ident) {
        this.atc_ident = atc_ident;
    }

    public String getInbound_fa_flight_id() {
        return inbound_fa_flight_id;
    }

    public void setInbound_fa_flight_id(String inbound_fa_flight_id) {
        this.inbound_fa_flight_id = inbound_fa_flight_id;
    }

    public String getCodeshares() {
        return codeshares;
    }

    public void setCodeshares(String codeshares) {
        this.codeshares = codeshares;
    }

    public String getCodeshares_iata() {
        return codeshares_iata;
    }

    public void setCodeshares_iata(String codeshares_iata) {
        this.codeshares_iata = codeshares_iata;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isDiverted() {
        return diverted;
    }

    public void setDiverted(boolean diverted) {
        this.diverted = diverted;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isPosition_only() {
        return position_only;
    }

    public void setPosition_only(boolean position_only) {
        this.position_only = position_only;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public int getDeparture_delay() {
        return departure_delay;
    }

    public void setDeparture_delay(int departure_delay) {
        this.departure_delay = departure_delay;
    }

    public int getArrival_delay() {
        return arrival_delay;
    }

    public void setArrival_delay(int arrival_delay) {
        this.arrival_delay = arrival_delay;
    }

    public int getFiled_ete() {
        return filed_ete;
    }

    public void setFiled_ete(int filed_ete) {
        this.filed_ete = filed_ete;
    }

    public int getProgress_percent() {
        return progress_percent;
    }

    public void setProgress_percent(int progress_percent) {
        this.progress_percent = progress_percent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAircraft_type() {
        return aircraft_type;
    }

    public void setAircraft_type(String aircraft_type) {
        this.aircraft_type = aircraft_type;
    }

    public int getRoute_distance() {
        return route_distance;
    }

    public void setRoute_distance(int route_distance) {
        this.route_distance = route_distance;
    }

    public int getFiled_airspeed() {
        return filed_airspeed;
    }

    public void setFiled_airspeed(int filed_airspeed) {
        this.filed_airspeed = filed_airspeed;
    }

    public int getFiled_altitude() {
        return filed_altitude;
    }

    public void setFiled_altitude(int filed_altitude) {
        this.filed_altitude = filed_altitude;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getBaggage_claim() {
        return baggage_claim;
    }

    public void setBaggage_claim(String baggage_claim) {
        this.baggage_claim = baggage_claim;
    }

    public int getSeats_cabin_business() {
        return seats_cabin_business;
    }

    public void setSeats_cabin_business(int seats_cabin_business) {
        this.seats_cabin_business = seats_cabin_business;
    }

    public int getSeats_cabin_coach() {
        return seats_cabin_coach;
    }

    public void setSeats_cabin_coach(int seats_cabin_coach) {
        this.seats_cabin_coach = seats_cabin_coach;
    }

    public int getSeats_cabin_first() {
        return seats_cabin_first;
    }

    public void setSeats_cabin_first(int seats_cabin_first) {
        this.seats_cabin_first = seats_cabin_first;
    }

    public String getGate_origin() {
        return gate_origin;
    }

    public void setGate_origin(String gate_origin) {
        this.gate_origin = gate_origin;
    }

    public String getGate_destination() {
        return gate_destination;
    }

    public void setGate_destination(String gate_destination) {
        this.gate_destination = gate_destination;
    }

    public String getTerminal_origin() {
        return terminal_origin;
    }

    public void setTerminal_origin(String terminal_origin) {
        this.terminal_origin = terminal_origin;
    }

    public String getTerminal_destination() {
        return terminal_destination;
    }

    public void setTerminal_destination(String terminal_destination) {
        this.terminal_destination = terminal_destination;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScheduled_out() {
        return scheduled_out;
    }

    public void setScheduled_out(String scheduled_out) {
        this.scheduled_out = scheduled_out;
    }

    public String getEstimated_out() {
        return estimated_out;
    }

    public void setEstimated_out(String estimated_out) {
        this.estimated_out = estimated_out;
    }

    public String getActual_out() {
        return actual_out;
    }

    public void setActual_out(String actual_out) {
        this.actual_out = actual_out;
    }

    public String getScheduled_off() {
        return scheduled_off;
    }

    public void setScheduled_off(String scheduled_off) {
        this.scheduled_off = scheduled_off;
    }

    public String getEstimated_off() {
        return estimated_off;
    }

    public void setEstimated_off(String estimated_off) {
        this.estimated_off = estimated_off;
    }

    public String getActual_off() {
        return actual_off;
    }

    public void setActual_off(String actual_off) {
        this.actual_off = actual_off;
    }

    public String getScheduled_on() {
        return scheduled_on;
    }

    public void setScheduled_on(String scheduled_on) {
        this.scheduled_on = scheduled_on;
    }

    public String getEstimated_on() {
        return estimated_on;
    }

    public void setEstimated_on(String estimated_on) {
        this.estimated_on = estimated_on;
    }

    public String getActual_on() {
        return actual_on;
    }

    public void setActual_on(String actual_on) {
        this.actual_on = actual_on;
    }

    public String getScheduled_in() {
        return scheduled_in;
    }

    public void setScheduled_in(String scheduled_in) {
        this.scheduled_in = scheduled_in;
    }

    public String getEstimated_in() {
        return estimated_in;
    }

    public void setEstimated_in(String estimated_in) {
        this.estimated_in = estimated_in;
    }

    public String getActual_in() {
        return actual_in;
    }

    public void setActual_in(String actual_in) {
        this.actual_in = actual_in;
    }

    public boolean isForesight_predictions_available() {
        return foresight_predictions_available;
    }

    public void setForesight_predictions_available(boolean foresight_predictions_available) {
        this.foresight_predictions_available = foresight_predictions_available;
    }

    public String getFullJson() {
        return fullJson;
    }

    public void setFullJson(String fullJson) {
        this.fullJson = fullJson;
    }
}
