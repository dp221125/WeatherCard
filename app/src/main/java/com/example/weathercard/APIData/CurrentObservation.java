package com.example.weathercard.APIData;

import com.fasterxml.jackson.annotation.*;

public class CurrentObservation {
    private Wind wind;
    private Atmosphere atmosphere;
    private Astronomy astronomy;
    private Condition condition;
    private long pubDate;

    @JsonProperty("wind")
    public Wind getWind() { return wind; }
    @JsonProperty("wind")
    public void setWind(Wind value) { this.wind = value; }

    @JsonProperty("atmosphere")
    public Atmosphere getAtmosphere() { return atmosphere; }
    @JsonProperty("atmosphere")
    public void setAtmosphere(Atmosphere value) { this.atmosphere = value; }

    @JsonProperty("astronomy")
    public Astronomy getAstronomy() { return astronomy; }
    @JsonProperty("astronomy")
    public void setAstronomy(Astronomy value) { this.astronomy = value; }

    @JsonProperty("condition")
    public Condition getCondition() { return condition; }
    @JsonProperty("condition")
    public void setCondition(Condition value) { this.condition = value; }

    @JsonProperty("pubDate")
    public long getPubDate() { return pubDate; }
    @JsonProperty("pubDate")
    public void setPubDate(long value) { this.pubDate = value; }
}
