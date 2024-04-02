package com.capstone.producer.shared.bindings;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class Airport {
    @NotNull
    private String name;

    @NotNull
    private String icaoCode;

    @NotNull
    private GeographicCoordinates2D coordinates;
}
