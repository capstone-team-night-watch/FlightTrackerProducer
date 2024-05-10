package com.capstone.producer.shared.bindings;

import com.capstone.producer.shared.enums.NoFlyZoneType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Bindings class used for representing Polygon no-fly zone data
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PolygonNoFlyZone extends BaseNoFlyZone {
    /**
     * List of vertices that make up the polygon
     */
    private List<GeographicCoordinates2D> vertices;

    /**
     * constructor for the class
     */
    public PolygonNoFlyZone() {
        this.type = NoFlyZoneType.POLYGON;
    }
}
