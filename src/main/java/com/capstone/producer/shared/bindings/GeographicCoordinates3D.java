package com.capstone.producer.shared.bindings;

import lombok.Data;


/*
 * Describes geographic coordinates of a point
 */
@Data
public class GeographicCoordinates3D {
    private double latitude;

    private double longitude;

    private double altitude;

    public GeographicCoordinates3D() {
    }
}