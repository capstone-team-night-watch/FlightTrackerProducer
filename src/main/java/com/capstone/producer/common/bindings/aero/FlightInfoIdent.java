package com.capstone.producer.common.bindings.aero;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Flight information object
 */
@Data
public class FlightInfoIdent {
    private String ident;
    private String operator;
    @JsonProperty("ident_icao")
    private String identIcao;

    @JsonProperty("ident_iata")
    private String identIata;

    @JsonProperty("fa_flight_id")
    private String faFlightId;

    @JsonProperty("operator_icao")
    private String operatorIcao;

    @JsonProperty("operator_iata")
    private String operatorIata;

    @JsonProperty("flight_number")
    private String flightNumber;

    @JsonProperty("atc_ident")
    private String atcIdent;

    @JsonProperty("inbound_fa_flight_id")
    private String inboundFaFlightId;

    @JsonProperty("codeshares")
    private String codeShares;

    @JsonProperty("codeshares_iata")
    private String codeSharesIata;

    private boolean blocked;
    private boolean diverted;
    private boolean cancelled;
    private String registration;

    private Origin origin;
    private String status;
    private Destination destination;

    @JsonProperty("position_only")
    private boolean positionOnly;

    @JsonProperty("departure_delay")
    private int departureDelay;

    @JsonProperty("arrival_delay")
    private int arrivalDelay;

    @JsonProperty("filed_ete")
    private int filedEte;

    @JsonProperty("progress_percent")
    private int progressPercent;

    @JsonProperty("aircraft_type")
    private String aircraftType;

    @JsonProperty("route_distance")
    private int routeDistance;

    @JsonProperty("filed_airspeed")
    private int filedAirspeed;

    @JsonProperty("filed_altitude")
    private int filedAltitude;

    @JsonProperty("baggage_claim")
    private String baggageClaim;

    @JsonProperty("seats_cabin_business")
    private int seatsCabinBusiness;

    @JsonProperty("seats_cabin_coach")
    private int seatsCabinCoach;

    @JsonProperty("seats_cabin_first")
    private int seatsCabinFirst;

    @JsonProperty("gate_origin")
    private String gateOrigin;

    @JsonProperty("gate_destination")
    private String gateDestination;

    @JsonProperty("terminal_origin")
    private String terminalOrigin;

    @JsonProperty("terminal_destination")
    private String terminalDestination;

    @JsonProperty("scheduled_out")
    private String scheduledOut;

    @JsonProperty("estimated_out")
    private String estimatedOut;

    @JsonProperty("actual_out")
    private String actualOut;

    @JsonProperty("scheduled_off")
    private String scheduledOff;

    @JsonProperty("estimated_off")
    private String estimatedOff;

    @JsonProperty("actual_off")
    private String actualOff;

    @JsonProperty("scheduled_on")
    private String scheduledOn;

    @JsonProperty("estimated_on")
    private String estimatedOn;

    @JsonProperty("actual_on")
    private String actualOn;

    @JsonProperty("scheduled_in")
    private String scheduledIn;

    @JsonProperty("estimated_in")
    private String estimatedIn;

    @JsonProperty("actual_in")
    private String actualIn;

    @JsonProperty("foresight_predictions_available")
    private boolean foresightPredictionsAvailable;

    private String type;
    private String fullJson;
    private String route;
}
