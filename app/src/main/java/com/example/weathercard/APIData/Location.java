package com.example.weathercard.APIData;

import com.fasterxml.jackson.annotation.*;

public class Location {
    private long woeid;
    private String city;
    private String region;
    private String country;
    private double lat;
    private double locationLong;
    private String timezoneID;

    @JsonProperty("woeid")
    public long getWoeid() { return woeid; }
    @JsonProperty("woeid")
    public void setWoeid(long value) { this.woeid = value; }

    @JsonProperty("city")
    public String getCity() { return city; }
    @JsonProperty("city")
    public void setCity(String value) { this.city = value; }

    @JsonProperty("region")
    public String getRegion() { return region; }
    @JsonProperty("region")
    public void setRegion(String value) { this.region = value; }

    @JsonProperty("country")
    public String getCountry() { return country; }
    @JsonProperty("country")
    public void setCountry(String value) { this.country = value; }

    @JsonProperty("lat")
    public double getLat() { return lat; }
    @JsonProperty("lat")
    public void setLat(double value) { this.lat = value; }

    @JsonProperty("long")
    public double getLocationLong() { return locationLong; }
    @JsonProperty("long")
    public void setLocationLong(double value) { this.locationLong = value; }

    @JsonProperty("timezone_id")
    public String getTimezoneID() { return timezoneID; }
    @JsonProperty("timezone_id")
    public void setTimezoneID(String value) { this.timezoneID = value; }
}
