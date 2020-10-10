package com.example.weathercard.APIData;

import com.fasterxml.jackson.annotation.*;

public class Forecast {
    private String day;
    private long date;
    private long low;
    private long high;
    private String text;
    private long code;

    @JsonProperty("day")
    public String getDay() { return day; }
    @JsonProperty("day")
    public void setDay(String value) { this.day = value; }

    @JsonProperty("date")
    public long getDate() { return date; }
    @JsonProperty("date")
    public void setDate(long value) { this.date = value; }

    @JsonProperty("low")
    public long getLow() { return low; }
    @JsonProperty("low")
    public void setLow(long value) { this.low = value; }

    @JsonProperty("high")
    public long getHigh() { return high; }
    @JsonProperty("high")
    public void setHigh(long value) { this.high = value; }

    @JsonProperty("text")
    public String getText() { return text; }
    @JsonProperty("text")
    public void setText(String value) { this.text = value; }

    @JsonProperty("code")
    public long getCode() { return code; }
    @JsonProperty("code")
    public void setCode(long value) { this.code = value; }
}
