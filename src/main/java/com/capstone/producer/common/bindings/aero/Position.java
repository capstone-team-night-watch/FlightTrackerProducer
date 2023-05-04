package com.capstone.producer.common.bindings.aero;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.StringJoiner;

@JsonRootName("last_position")
public class Position {
    @JsonProperty
    private String fa_flight_id;
    @JsonProperty
    private int altitude;
    @JsonProperty
    private String altitude_change;
    @JsonProperty
    private int groundspeed;
    @JsonProperty
    private int heading;
    @JsonProperty
    private float latitude;
    @JsonProperty
    private float longitude;
    @JsonProperty
    private String timestamp;
    @JsonProperty
    private String update_type;


    @Override
    public String toString() {
        return new StringJoiner(", ", Position.class.getSimpleName() + "[", "]")
                .add("fa_flight_id='" + fa_flight_id + "'")
                .add("altitude=" + altitude)
                .add("altitude_change='" + altitude_change + "'")
                .add("groundspeed=" + groundspeed)
                .add("heading=" + heading)
                .add("latitude=" + latitude)
                .add("longitude=" + longitude)
                .add("timestamp='" + timestamp + "'")
                .add("update_type='" + update_type + "'")
                .toString();
    }

    public String getFa_flight_id() {
        return fa_flight_id;
    }

    public void setFa_flight_id(String fa_flight_id) {
        this.fa_flight_id = fa_flight_id;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public String getAltitude_change() {
        return altitude_change;
    }

    public void setAltitude_change(String altitude_change) {
        this.altitude_change = altitude_change;
    }

    public int getGroundspeed() {
        return groundspeed;
    }

    public void setGroundspeed(int groundspeed) {
        this.groundspeed = groundspeed;
    }

    public int getHeading() {
        return heading;
    }

    public void setHeading(int heading) {
        this.heading = heading;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUpdate_type() {
        return update_type;
    }

    public void setUpdate_type(String update_type) {
        this.update_type = update_type;
    }
}
