package com.example.weathercard.APIData;


public class CurrentObservation {
    private Wind wind;
    private Atmosphere atmosphere;
    private Astronomy astronomy;
    private Condition condition;
    private long pubDate;

    public Wind getWind() { return wind; }
    public void setWind(Wind value) { this.wind = value; }

    public Atmosphere getAtmosphere() { return atmosphere; }
    public void setAtmosphere(Atmosphere value) { this.atmosphere = value; }

    public Astronomy getAstronomy() { return astronomy; }
    public void setAstronomy(Astronomy value) { this.astronomy = value; }

    public Condition getCondition() { return condition; }
    public void setCondition(Condition value) { this.condition = value; }

    public long getPubDate() { return pubDate; }
    public void setPubDate(long value) { this.pubDate = value; }
}
