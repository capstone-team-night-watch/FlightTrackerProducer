package com.capstone.producer.common.bindings.aero;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Position {
    @JsonProperty("fa_flight_id")
    private String faFlightId;

    private int altitude;

    @JsonProperty("altitude_change")
    private String altitudeChange;

    @JsonProperty("groundspeed")
    private int groundSpeed;

    private int heading;

    private float latitude;

    private float longitude;

    private String timestamp;

    @JsonProperty("update_type")
    private String updateType;
}
