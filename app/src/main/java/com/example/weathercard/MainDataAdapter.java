package com.example.weathercard;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathercard.APIData.Weather;

public class MainDataAdapter extends RecyclerView.Adapter {

    private Weather weatherData = null;
    private Context context;

    private static int TYPE_Today = 1;
    private static int TYPE_Forecast = 2;

    public MainDataAdapter(Weather weather) {
        this.weatherData = weather;
    }

    public void setWeather(Weather weather) {
        this.weatherData = weather;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ForecastsViewHolder extends RecyclerView.ViewHolder {
        TextView cityNameTextView;
        TextView weatherStringTextView;

        ForecastsViewHolder(View itemView) {
            super(itemView);
            cityNameTextView = itemView.findViewById(R.id.cityNameTextView);
            weatherStringTextView = itemView.findViewById(R.id.weatherStringTextView);
        }
    }

    public class ToDayViewHolder extends RecyclerView.ViewHolder {
        TextView cityNameTextView;
        TextView regionTextView;
        TextView weatherStringTextView;
        TextView currentTempTextView;
        ImageView weatherImageView;

        ToDayViewHolder(View itemView) {
            super(itemView);
            cityNameTextView = itemView.findViewById(R.id.cityNameTextView);
            regionTextView = itemView.findViewById(R.id.regionNameTextView);
            weatherStringTextView = itemView.findViewById(R.id.weatherStringTextView);
            currentTempTextView = itemView.findViewById(R.id.currentTempTextView);
            weatherImageView = itemView.findViewById(R.id.weatherImageView);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        if (viewType == TYPE_Today) {
            view = LayoutInflater.from(context).inflate(R.layout.mainrecyclerview_today, parent, false);
            return new ToDayViewHolder(view);

        } else {
            view = LayoutInflater.from(context).inflate(R.layout.mainrecyclerview_forecasts, parent, false);
            return new ForecastsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if ((getItemViewType(position) == TYPE_Today) && weatherData.getLocation() != null && weatherData.getCurrentObservation() != null) {
            ((ToDayViewHolder)holder).cityNameTextView.setText(weatherData.getLocation().getCity());
            ((ToDayViewHolder)holder).regionTextView.setText(weatherData.getLocation().getRegion());
            ((ToDayViewHolder)holder).weatherStringTextView.setText(weatherData.getCurrentObservation().getCondition().getText());

            String imageURL = String.format("http://l.yimg.com/a/i/us/we/52/%s.gif", weatherData.getCurrentObservation().getCondition().getCode());
            Log.d("imageURL",imageURL);

            ImageAdapter imageAdapter = new ImageAdapter();
            imageAdapter.loadImage(((ToDayViewHolder) holder).weatherImageView, imageURL);

            if (getUnit().equals("c")) {
                ((ToDayViewHolder)holder).currentTempTextView.setText(String.valueOf(weatherData.getCurrentObservation().getCondition().getTemperature()) + "°C");
            } else {
                ((ToDayViewHolder)holder).currentTempTextView.setText(String.valueOf(weatherData.getCurrentObservation().getCondition().getTemperature()) + "°F");
            }

        } else {

        }
    }

    @Override
    public int getItemCount() {
        if (weatherData.getForecasts() != null) {
            return weatherData.getForecasts().size() + 1;
        } else {
            return 0;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_Today;
        } else {
            return TYPE_Forecast;
        }
    }

    private String getUnit() {
        SharedPreferences prefs = context.getSharedPreferences("TempPref", Context.MODE_PRIVATE);
        String value = prefs.getString("unitSwitch", "c");

        return value;
    }






}

