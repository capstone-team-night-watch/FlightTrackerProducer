package com.capstone.producer.shared.bindings;

import com.capstone.producer.shared.enums.NoFlyZoneType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Bindings class used for representing Polygon no-fly zone data
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CircularNoFlyZone extends BaseNoFlyZone {
    private double radius;

    private GeographicCoordinates2D center;

    public CircularNoFlyZone() {
        this.type = NoFlyZoneType.CIRCLE;
    }
}
