package com.example.weathercard.APIData;

public class Atmosphere {
    private long humidity;
    private long visibility;
    private double pressure;
    private long rising;

    public long getHumidity() { return humidity; }
    public void setHumidity(long value) { this.humidity = value; }

    public long getVisibility() { return visibility; }
    public void setVisibility(long value) { this.visibility = value; }

    public double getPressure() { return pressure; }
    public void setPressure(double value) { this.pressure = value; }

    public long getRising() { return rising; }
    public void setRising(long value) { this.rising = value; }
}
