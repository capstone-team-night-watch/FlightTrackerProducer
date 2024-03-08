package com.capstone.producer.shared.bindings;

import lombok.Data;

@Data
public class FlightInformation {
    /**
     * Unique identifier for the flight
     */
    private String flightId;

    /**
     * Current location of the plane
     */
    private GeographicCoordinates3D location;


    /**
     * Describes the group speed of the plan in miles per second
     */
    private float groundSpeed;


    /**
     * Describes the general direction of the plane
     */
    private float heading;
}