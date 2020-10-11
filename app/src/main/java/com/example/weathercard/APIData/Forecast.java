package com.example.weathercard.APIData;



public class Forecast {
    private String day;
    private long date;
    private long low;
    private long high;
    private String text;
    private long code;

    public String getDay() { return day; }
    public void setDay(String value) { this.day = value; }

    public long getDate() { return date; }
    public void setDate(long value) { this.date = value; }

    public long getLow() { return low; }
    public void setLow(long value) { this.low = value; }

    public long getHigh() { return high; }
    public void setHigh(long value) { this.high = value; }

    public String getText() { return text; }
    public void setText(String value) { this.text = value; }

    public long getCode() { return code; }
    public void setCode(long value) { this.code = value; }
}
