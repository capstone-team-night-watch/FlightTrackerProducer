package com.capstone.producer.common.bindings;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * Temporary Flight restriciton notam, used for storing and transmitting information about received temporary flight restrictions
 */
@Setter
@Getter
public class TfrNotam {
    @NotEmpty
    private String notamNumber;
    @NotEmpty
    private String notamType;

    // stores standard degrees longitude first then latitude after.
    List<Double> latlong;

    // Radius is in meters
    private double radius;

    private List<Integer> altitude;

    private String startTime;

    private String endTime;

    // Required to deserialize a serialized object
    public TfrNotam() {
        super();
    }

    public TfrNotam(String notamNumber, String notamType, List<Double> latlong, double radius, List<Integer> altitude, String startTime, String endTime){
        this.notamNumber = notamNumber;
        this.notamType = notamType;
        this.latlong = latlong;
        this.radius = radius;
        this.altitude = altitude;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
