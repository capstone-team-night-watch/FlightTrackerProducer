package com.capstone.producer.shared.bindings;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class FlightInformation {
    /**
     * Unique identifier for the flight
     */
    @NotNull
    private String flightId;

    /**
     * Current location of the plane
     */
    @NotNull
    private GeographicCoordinates3D location;


    /**
     * Describes the group speed of the plan in miles per second
     */
    private float groundSpeed;


    /**
     * Describes the general direction of the plane
     */
    private float heading;


    /**
     * Source location where the flight has departed. Only specified on initial flight creation or on update
     */
    private Airport source;

    /**
     * Destination airport of the flight. Only specified on initial flight creation or on update
     */
    private Airport destination;

    /**
     * List of checkpoints that the flight must pass through. Only specified on initial flight creation or on update
     */
    private List<GeographicCoordinates2D> checkPoints;
}