package com.example.weathercard.APIData;

import com.fasterxml.jackson.annotation.*;

public class Astronomy {
    private String sunrise;
    private String sunset;

    @JsonProperty("sunrise")
    public String getSunrise() { return sunrise; }
    @JsonProperty("sunrise")
    public void setSunrise(String value) { this.sunrise = value; }

    @JsonProperty("sunset")
    public String getSunset() { return sunset; }
    @JsonProperty("sunset")
    public void setSunset(String value) { this.sunset = value; }
}

