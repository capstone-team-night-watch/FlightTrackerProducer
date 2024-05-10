package com.capstone.producer.common.bindings.aero;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Flight information object
 */
@Data
public class FlightInfoFaid {
    private String ident;

    @JsonProperty("ident_icao")
    private String identIcao;

    @JsonProperty("ident_iata")
    private String identIata;

    @JsonProperty("fa_flight_id")
    private String faFlightId;

    @JsonProperty("actual_off")
    private String actualOff;

    @JsonProperty("actual_on")
    private String actualOn;

    @JsonProperty("predicted_out")
    private String predictedOut;

    @JsonProperty("predicted_off")
    private String predictedOff;

    @JsonProperty("predicted_on")
    private String predictedOn;

    @JsonProperty("predicted_in")
    private String predictedIn;

    @JsonProperty("foresight_predictions_available")
    private boolean foresightPredictionsAvailable;
    
    @JsonProperty("predicted_out_source")
    private String predictedOutSource;
    
    @JsonProperty("predicted_off_source")
    private String predictedOffSource;
    
    @JsonProperty("predicted_on_source")
    private String predictedOnSource;
    
    @JsonProperty("predicted_in_source")
    private String predictedInSource;
    
    private Origin origin;
    
    private Destination destination;
    
    private List<Double> waypoints;
    
    @JsonProperty("first_position_time")
    private String firstPositionTime;

    @JsonProperty("last_position")
    private Position lastPosition;

    @JsonProperty("positions")
    private Position[] positions;

    @JsonProperty("bounding_box")
    private float[] boundingBox;

    @JsonProperty("ident_prefix")
    private String identPrefix;

    @JsonProperty("aircraft_type")
    private String aircraftType;

    private String fullJson;
}
