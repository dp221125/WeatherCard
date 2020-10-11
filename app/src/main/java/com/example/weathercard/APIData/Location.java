package com.example.weathercard.APIData;

public class Location {
    private long woeid;
    private String city;
    private String region;
    private String country;
    private double lat;
    private double locationLong;
    private String timezoneID;

    public long getWoeid() { return woeid; }
    public void setWoeid(long value) { this.woeid = value; }

    public String getCity() { return city; }
    public void setCity(String value) { this.city = value; }

    public String getRegion() { return region; }
    public void setRegion(String value) { this.region = value; }

    public String getCountry() { return country; }
    public void setCountry(String value) { this.country = value; }

    public double getLat() { return lat; }
    public void setLat(double value) { this.lat = value; }

    public double getLocationLong() { return locationLong; }
    public void setLocationLong(double value) { this.locationLong = value; }

    public String getTimezoneID() { return timezoneID; }
    public void setTimezoneID(String value) { this.timezoneID = value; }
}
