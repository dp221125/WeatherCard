package com.example.weathercard.network;

import com.example.weathercard.APIData.Weather;
import com.example.weathercard.BuildConfig;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface WeatherAPIProvider {

    @Headers({
            "Content-Type: application/json",
            "X-Yahoo-App-Id: " + BuildConfig.APP_ID
    })
    @GET("/forecastrss")
    Call<Weather> getData(@Query("lat") String lat,
                          @Query("lon") String lon,
                          @Query("format") String format,
                          @Query("u") String unit);
}