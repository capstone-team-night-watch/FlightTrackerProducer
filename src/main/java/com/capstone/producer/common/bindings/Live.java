package com.capstone.producer.common.bindings;

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

        return String.format("{\"updated\":\"%s\",\"latitude\":%.2f,\"longitude\":%.2f,\"altitude\":%.2f," +
                        "\"direction\":%d,\"speed_horizontal\":%.2f,\"speed_vertical\":%.2f,\"is_ground\":%b}",
                updated, latitude, longitude, altitude, direction, speed_horizontal, speed_vertical, is_ground);
    }

    public String getUpdated() {
        return updated;
    }

    public float getAltitude() {
        return altitude;
    }

    public Live setAltitude(float altitude) {
        this.altitude = altitude;
        return this;
    }

    public Live setUpdated(String updated) {
        this.updated = updated;
        return this;
    }

    public float getLatitude() {
        return latitude;
    }

    public Live setLatitude(float latitude) {
        this.latitude = latitude;
        return this;
    }

    public float getLongitude() {
        return longitude;
    }

    public Live setLongitude(float longitude) {
        this.longitude = longitude;
        return this;
    }

    public int getDirection() {
        return direction;
    }

    public Live setDirection(int direction) {
        this.direction = direction;
        return this;
    }

    public float getSpeed_horizontal() {
        return speed_horizontal;
    }

    public Live setSpeed_horizontal(float speed_horizontal) {
        this.speed_horizontal = speed_horizontal;
        return this;
    }

    public float getSpeed_vertical() {
        return speed_vertical;
    }

    public Live setSpeed_vertical(float speed_vertical) {
        this.speed_vertical = speed_vertical;
        return this;
    }

    public boolean isIs_ground() {
        return is_ground;
    }

    public Live setIs_ground(boolean is_ground) {
        this.is_ground = is_ground;
        return this;
    }
}
