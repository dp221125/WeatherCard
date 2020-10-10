package com.example.weathercard.APIData;

import com.fasterxml.jackson.annotation.*;

public class Wind {
    private long chill;
    private long direction;
    private double speed;

    @JsonProperty("chill")
    public long getChill() { return chill; }
    @JsonProperty("chill")
    public void setChill(long value) { this.chill = value; }

    @JsonProperty("direction")
    public long getDirection() { return direction; }
    @JsonProperty("direction")
    public void setDirection(long value) { this.direction = value; }

    @JsonProperty("speed")
    public double getSpeed() { return speed; }
    @JsonProperty("speed")
    public void setSpeed(double value) { this.speed = value; }
}
