package com.example.weathercard.APIData;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class Weather {
    private Location location;
    private CurrentObservation currentObservation;
    private List<Forecast> forecasts;

    @JsonProperty("location")
    public Location getLocation() { return location; }
    @JsonProperty("location")
    public void setLocation(Location value) { this.location = value; }

    @JsonProperty("current_observation")
    public CurrentObservation getCurrentObservation() { return currentObservation; }
    @JsonProperty("current_observation")
    public void setCurrentObservation(CurrentObservation value) { this.currentObservation = value; }

    @JsonProperty("forecasts")
    public List<Forecast> getForecasts() { return forecasts; }
    @JsonProperty("forecasts")
    public void setForecasts(List<Forecast> value) { this.forecasts = value; }
}
