package com.example.weathercard.APIData;

public class Atmosphere {
    private double humidity;
    private double visibility;
    private double pressure;
    private double rising;

    public double getHumidity() { return humidity; }
    public void setHumidity(long value) { this.humidity = value; }

    public double getVisibility() { return visibility; }
    public void setVisibility(long value) { this.visibility = value; }

    public double getPressure() { return pressure; }
    public void setPressure(double value) { this.pressure = value; }

    public double getRising() { return rising; }
    public void setRising(long value) { this.rising = value; }
}
