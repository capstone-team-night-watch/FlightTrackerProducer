package com.capstone.producer.common.bindings;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TfrNotam {
    @NotEmpty
    private String notamNumber;
    @NotEmpty
    private String notamType;

    List<String> latlong;

    private String radius;

    private String startTime;

    private String endTime;

    public TfrNotam(String notamNumber, String notamType, List<String> latlong, String radius, String startTime, String endTime){
        this.notamNumber = notamNumber;
        this.notamType = notamType;
        this.latlong = latlong;
        this.radius = radius;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
