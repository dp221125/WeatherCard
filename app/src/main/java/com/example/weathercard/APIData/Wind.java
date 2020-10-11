package com.example.weathercard.APIData;

public class Wind {
    private long chill;
    private long direction;
    private double speed;

    public long getChill() { return chill; }
    public void setChill(long value) { this.chill = value; }

    public long getDirection() { return direction; }
    public void setDirection(long value) { this.direction = value; }

    public double getSpeed() { return speed; }
    public void setSpeed(double value) { this.speed = value; }
}
