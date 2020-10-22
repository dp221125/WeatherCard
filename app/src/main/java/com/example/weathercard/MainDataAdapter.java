package com.example.weathercard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathercard.APIData.Weather;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Locale;

public class MainDataAdapter extends RecyclerView.Adapter {

    private Weather weatherData;
    private Context context;

    private static final int TYPE_Today = 1;
    private static final int TYPE_Footer = 3;

    public MainDataAdapter(Weather weather) {
        this.weatherData = weather;
    }

    public void setWeather(Weather weather) {
        this.weatherData = weather;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public static class ForecastsViewHolder extends RecyclerView.ViewHolder {
        TextView weekTextView;
        TextView dateTextView;
        TextView tempTextView;
        ImageView weatherImageView;

        ForecastsViewHolder(View itemView) {
            super(itemView);
            weekTextView = itemView.findViewById(R.id.weekdayTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            tempTextView = itemView.findViewById(R.id.minMaxTempTextView);
            weatherImageView = itemView.findViewById(R.id.weatherImageView);
        }
    }

    public static class ToDayViewHolder extends RecyclerView.ViewHolder {
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

    public static class FooterHolder extends RecyclerView.ViewHolder {
        public FooterHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;

        if (viewType == TYPE_Footer) {
            view = LayoutInflater.from(context).inflate(R.layout.mainrecyclerview_footer, parent, false);
            return new FooterHolder(view);
        } else if (viewType == TYPE_Today) {
            view = LayoutInflater.from(context).inflate(R.layout.mainrecyclerview_today, parent, false);
            return new ToDayViewHolder(view);

        } else {
            view = LayoutInflater.from(context).inflate(R.layout.mainrecyclerview_forecasts, parent, false);
            return new ForecastsViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
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
                ((ToDayViewHolder)holder).currentTempTextView.setText(weatherData.getCurrentObservation().getCondition().getTemperature() + "°F");
            }

        } else if (position != weatherData.getForecasts().size() + 1)  {
            Log.d("position", String.valueOf(position));
             if (weatherData.getForecasts() != null && position > 0) {

                 if (position == 1) {
                     ((ForecastsViewHolder) holder).weekTextView.setText("Today");
                 } else {
                     ((ForecastsViewHolder) holder).weekTextView.setText(weatherData.getForecasts().get(position - 1).getDay());
                 }

                 if (getUnit().equals("c")) {
                     ((ForecastsViewHolder) holder).tempTextView.setText(String.valueOf(weatherData.getForecasts().get(position - 1).getHigh()) + "-" + weatherData.getForecasts().get(position - 1).getLow() + "°C");
                 } else {
                     ((ForecastsViewHolder) holder).tempTextView.setText(String.valueOf(weatherData.getForecasts().get(position - 1).getHigh()) + "-" + weatherData.getForecasts().get(position - 1).getLow() + "°F");
                 }

                 String imageURL = String.format("http://l.yimg.com/a/i/us/we/52/%s.gif", weatherData.getForecasts().get(position - 1).getCode());
                 Log.d("imageURL", imageURL);

                 ImageAdapter imageAdapter = new ImageAdapter();
                 imageAdapter.loadImage(((ForecastsViewHolder) holder).weatherImageView, imageURL);

                 long dv = weatherData.getForecasts().get(position - 1).getDate() * 1000;
                 Date df = new java.util.Date(dv);
                 String vv = new SimpleDateFormat("MMM dd일", Locale.KOREA).format(df);

                 ((ForecastsViewHolder)holder).dateTextView.setText(vv);
             }
        }
    }

    @Override
    public int getItemCount() {
        if (weatherData.getForecasts() != null) {
            return weatherData.getForecasts().size() + 2;
        } else {
            return 0;
        }

    }

    @Override
    public int getItemViewType(int position) {

        if (weatherData.getForecasts().size() + 1 == position) {
            return  TYPE_Footer;
        } else if (position == 0) {
            return TYPE_Today;
        } else {
            return 2;
        }
    }

    private String getUnit() {
        SharedPreferences prefs = context.getSharedPreferences("TempPref", Context.MODE_PRIVATE);

        return prefs.getString("unitSwitch", "c");
    }

}

