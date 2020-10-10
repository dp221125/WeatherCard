package com.example.weathercard.APIData;

import com.fasterxml.jackson.annotation.*;

public class Condition {
    private String text;
    private long code;
    private long temperature;

    @JsonProperty("text")
    public String getText() { return text; }
    @JsonProperty("text")
    public void setText(String value) { this.text = value; }

    @JsonProperty("code")
    public long getCode() { return code; }
    @JsonProperty("code")
    public void setCode(long value) { this.code = value; }

    @JsonProperty("temperature")
    public long getTemperature() { return temperature; }
    @JsonProperty("temperature")
    public void setTemperature(long value) { this.temperature = value; }
}

