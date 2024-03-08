package com.capstone.producer.shared.enums;


import com.capstone.producer.shared.bindings.CircularNoFlyZone;
import com.capstone.producer.shared.bindings.PolygonNoFlyZone;

/**
 * Describes the type of no-fly-zone supported by the system
 */
public enum NoFlyZoneType {
    /**
     * Describes a circular no-fly-zone. Circular no-fly-zones are defined by a center point, a radius and an altitude
     * @see CircularNoFlyZone
     */
    CIRCLE,

    /**
     * Describes a polygon no-fly-zone. Polygon no-fly-zones are defined by a list of points (vertices) and an altitude
     * @see PolygonNoFlyZone
     */
    POLYGON
}
