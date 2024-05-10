package com.capstone.producer.shared.bindings;

import lombok.Data;

@Data
public class GeographicCoordinates2D {
    private double latitude;

    private double longitude;

    /**
     * Default constructor
     */
    public GeographicCoordinates2D() {
    }

    /**
     * Constructor for setting lat long
     * 
     * @param latitude latitude for the object
     * @param longitude longitude for the object
     */
    public GeographicCoordinates2D(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
