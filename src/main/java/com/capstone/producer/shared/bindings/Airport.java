package com.capstone.producer.shared.bindings;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

/**
 * Object class for transmitting Airport information
 */
@Data
public class Airport {
    @NotNull
    private String name;

    @NotNull
    private String icaoCode;

    @NotNull
    private GeographicCoordinates2D coordinates;
}
