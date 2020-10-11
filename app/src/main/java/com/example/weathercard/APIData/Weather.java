package com.example.weathercard.APIData;

import java.util.ArrayList;
import java.util.List;

public class Weather {

    private Location location;
    private CurrentObservation currentObservation;
    private List<Forecast> forecasts = new ArrayList<Forecast>();

    public Location getLocation() { return location; }
    public void setLocation(Location value) { this.location = value; }

    public CurrentObservation getCurrentObservation() { return currentObservation; }
    public void setCurrentObservation(CurrentObservation value) { this.currentObservation = value; }

    public List<Forecast> getForecasts() { return forecasts; }
    public void setForecasts(List<Forecast> value) { this.forecasts = value; }
}