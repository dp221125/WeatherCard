package com.example.weathercard.APIData;

import com.fasterxml.jackson.annotation.*;

public class Atmosphere {
    private long humidity;
    private long visibility;
    private double pressure;
    private long rising;

    @JsonProperty("humidity")
    public long getHumidity() { return humidity; }
    @JsonProperty("humidity")
    public void setHumidity(long value) { this.humidity = value; }

    @JsonProperty("visibility")
    public long getVisibility() { return visibility; }
    @JsonProperty("visibility")
    public void setVisibility(long value) { this.visibility = value; }

    @JsonProperty("pressure")
    public double getPressure() { return pressure; }
    @JsonProperty("pressure")
    public void setPressure(double value) { this.pressure = value; }

    @JsonProperty("rising")
    public long getRising() { return rising; }
    @JsonProperty("rising")
    public void setRising(long value) { this.rising = value; }
}
