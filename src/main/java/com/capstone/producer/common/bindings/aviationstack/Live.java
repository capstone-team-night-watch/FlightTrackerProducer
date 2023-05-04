package com.capstone.producer.common.bindings.aviationstack;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Class used as bindings for the Aviation Stack API responses.
 * This class corresponds to the 'flight' JSON Object included in the response provided at the /flights endpoint
 * Also is used when building the message sent onto the Kafka broker
 */
public class Live {
    private String updated;
    private float latitude;
    private float longitude;
    private float altitude;
    private int direction;
    private float speed_horizontal;
    private float speed_vertical;
    private boolean is_ground;

    @Override
    public String toString() {
        return getJSON();
    }

    public String getJSON() {
        return String.format("{\"updated\":\"%s\",\"latitude\":%.2f,\"longitude\":%.2f,\"altitude\":%.2f," +
                        "\"direction\":%d,\"speed_horizontal\":%.2f,\"speed_vertical\":%.2f,\"is_ground\":%b}",
                updated, latitude, longitude, altitude, direction, speed_horizontal, speed_vertical, is_ground);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Live live = (Live) o;

        return new EqualsBuilder().append(getLatitude(), live.getLatitude()).append(getLongitude(), live.getLongitude()).append(getAltitude(), live.getAltitude()).append(getDirection(), live.getDirection()).append(getSpeed_horizontal(), live.getSpeed_horizontal()).append(getSpeed_vertical(), live.getSpeed_vertical()).append(isIs_ground(), live.isIs_ground()).append(getUpdated(), live.getUpdated()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getUpdated()).append(getLatitude()).append(getLongitude()).append(getAltitude()).append(getDirection()).append(getSpeed_horizontal()).append(getSpeed_vertical()).append(isIs_ground()).toHashCode();
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public float getSpeed_horizontal() {
        return speed_horizontal;
    }

    public void setSpeed_horizontal(float speed_horizontal) {
        this.speed_horizontal = speed_horizontal;
    }

    public float getSpeed_vertical() {
        return speed_vertical;
    }

    public void setSpeed_vertical(float speed_vertical) {
        this.speed_vertical = speed_vertical;
    }

    public boolean isIs_ground() {
        return is_ground;
    }

    public void setIs_ground(boolean is_ground) {
        this.is_ground = is_ground;
    }
}
