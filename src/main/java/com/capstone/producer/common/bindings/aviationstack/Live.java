package com.capstone.producer.common.bindings.aviationstack;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Class used as bindings for the Aviation Stack API responses.
 * This class corresponds to the 'flight' JSON Object included in the response provided at the /flights endpoint
 * Also is used when building the message sent onto the Kafka broker
 */
@Data
public class Live {
    private String updated;
    private float latitude;
    private float longitude;
    private float altitude;
    private int direction;

    @JsonProperty("speed_horizontal")
    private float speedHorizontal;

    @JsonProperty("speed_vertical")
    private float speedVertical;

    @JsonProperty("is_ground")
    private boolean isGround;
}
