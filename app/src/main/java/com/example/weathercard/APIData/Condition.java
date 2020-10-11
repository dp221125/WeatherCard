package com.example.weathercard.APIData;

public class Condition {
    private String text;
    private long code;
    private long temperature;

    public String getText() { return text; }
    public void setText(String value) { this.text = value; }

    public long getCode() { return code; }
    public void setCode(long value) { this.code = value; }

    public long getTemperature() { return temperature; }
    public void setTemperature(long value) { this.temperature = value; }
}

