package com.example.weathercard.APIData;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Weather {

    private UserLocation location;
    @SerializedName(value = "current_observation")
    private CurrentObservation currentObservation;
    private List<Forecast> forecasts = new ArrayList<Forecast>();

    public UserLocation getLocation() { return location; }
    public void setLocation(UserLocation value) { this.location = value; }

    public CurrentObservation getCurrentObservation() { return currentObservation; }
    public void setCurrentObservation(CurrentObservation value) { this.currentObservation = value; }

    public List<Forecast> getForecasts() { return forecasts; }
    public void setForecasts(List<Forecast> value) { this.forecasts = value; }
}