package com.capstone.producer.common.bindings.aero;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.StringJoiner;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FlightInfoFa_Id {
    @JsonProperty
    private String ident;
    @JsonProperty
    private String ident_icao;
    @JsonProperty
    private String ident_iata;
    @JsonProperty
    private String fa_flight_id;
    @JsonProperty
    private String actual_off;
    @JsonProperty
    private String actual_on;
    @JsonProperty
    private boolean foresight_predictions_available;
    @JsonProperty
    private String predicted_out;
    @JsonProperty
    private String predicted_off;
    @JsonProperty
    private String predicted_on;
    @JsonProperty
    private String predicted_in;
    @JsonProperty
    private String predicted_out_source;
    @JsonProperty
    private String predicted_off_source;
    @JsonProperty
    private String predicted_on_source;
    @JsonProperty
    private String predicted_in_source;
    @JsonProperty
    private Origin origin;
    @JsonProperty
    private Destination destination;
    @JsonProperty
    private float[] waypoints;
    @JsonProperty
    private String first_position_time;
    @JsonProperty
    private Position last_position;
    @JsonProperty
    private float[] bounding_box;
    @JsonProperty
    private String ident_prefix;
    @JsonProperty
    private String aircraft_type;

    @JsonIgnore
    private String fullJson;

    @Override
    public String toString() {
        return new StringJoiner(", ", FlightInfoFa_Id.class.getSimpleName() + "[", "]")
                .add("ident='" + ident + "'")
                .add("ident_icao='" + ident_icao + "'")
                .add("ident_iata='" + ident_iata + "'")
                .add("fa_flight_id='" + fa_flight_id + "'")
                .add("actual_off='" + actual_off + "'")
                .add("actual_on='" + actual_on + "'")
                .add("foresight_predictions_available=" + foresight_predictions_available)
                .add("predicted_out='" + predicted_out + "'")
                .add("predicted_off='" + predicted_off + "'")
                .add("predicted_on='" + predicted_on + "'")
                .add("predicted_in='" + predicted_in + "'")
                .add("predicted_out_source='" + predicted_out_source + "'")
                .add("predicted_off_source='" + predicted_off_source + "'")
                .add("predicted_on_source='" + predicted_on_source + "'")
                .add("predicted_in_source='" + predicted_in_source + "'")
                .add("origin=" + origin)
                .add("destination=" + destination)
                .add("waypoints=" + Arrays.toString(waypoints))
                .add("first_position_time='" + first_position_time + "'")
                .add("last_position=" + last_position)
                .add("bounding_box=" + Arrays.toString(bounding_box))
                .add("ident_prefix='" + ident_prefix + "'")
                .add("aircraft_type='" + aircraft_type + "'")
                .add("fullJson='" + fullJson + "'")
                .toString();
    }

    public String getFullJson() {
        return fullJson;
    }

    public void setFullJson(String fullJson) {
        this.fullJson = fullJson;
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

    public String getActual_off() {
        return actual_off;
    }

    public void setActual_off(String actual_off) {
        this.actual_off = actual_off;
    }

    public String getActual_on() {
        return actual_on;
    }

    public void setActual_on(String actual_on) {
        this.actual_on = actual_on;
    }

    public boolean getForesight_predictions_available() {
        return foresight_predictions_available;
    }

    public void setForesight_predictions_available(boolean foresight_predictions_available) {
        this.foresight_predictions_available = foresight_predictions_available;
    }

    public String getPredicted_out() {
        return predicted_out;
    }

    public void setPredicted_out(String predicted_out) {
        this.predicted_out = predicted_out;
    }

    public String getPredicted_off() {
        return predicted_off;
    }

    public void setPredicted_off(String predicted_off) {
        this.predicted_off = predicted_off;
    }

    public String getPredicted_on() {
        return predicted_on;
    }

    public void setPredicted_on(String predicted_on) {
        this.predicted_on = predicted_on;
    }

    public String getPredicted_in() {
        return predicted_in;
    }

    public void setPredicted_in(String predicted_in) {
        this.predicted_in = predicted_in;
    }

    public String getPredicted_out_source() {
        return predicted_out_source;
    }

    public void setPredicted_out_source(String predicted_out_source) {
        this.predicted_out_source = predicted_out_source;
    }

    public String getPredicted_off_source() {
        return predicted_off_source;
    }

    public void setPredicted_off_source(String predicted_off_source) {
        this.predicted_off_source = predicted_off_source;
    }

    public String getPredicted_on_source() {
        return predicted_on_source;
    }

    public void setPredicted_on_source(String predicted_on_source) {
        this.predicted_on_source = predicted_on_source;
    }

    public String getPredicted_in_source() {
        return predicted_in_source;
    }

    public void setPredicted_in_source(String predicted_in_source) {
        this.predicted_in_source = predicted_in_source;
    }

    public Origin getOrigin() {
        return origin;
    }

    public String getFa_flight_id() {
        return fa_flight_id;
    }

    public void setFa_flight_id(String fa_flight_id) {
        this.fa_flight_id = fa_flight_id;
    }

    public boolean isForesight_predictions_available() {
        return foresight_predictions_available;
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

    public float[] getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(float[] waypoints) {
        this.waypoints = waypoints;
    }

    public String getFirst_position_time() {
        return first_position_time;
    }

    public void setFirst_position_time(String first_position_time) {
        this.first_position_time = first_position_time;
    }

    public Position getLast_position() {
        return last_position;
    }

    public void setLast_position(Position last_position) {
        this.last_position = last_position;
    }

    public float[] getBounding_box() {
        return bounding_box;
    }

    public void setBounding_box(float[] bounding_box) {
        this.bounding_box = bounding_box;
    }

    public String getIdent_prefix() {
        return ident_prefix;
    }

    public void setIdent_prefix(String ident_prefix) {
        this.ident_prefix = ident_prefix;
    }

    public String getAircraft_type() {
        return aircraft_type;
    }

    public void setAircraft_type(String aircraft_type) {
        this.aircraft_type = aircraft_type;
    }
}
