package com.example.weathercard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathercard.APIData.Weather;

public class MainDataAdapter extends RecyclerView.Adapter<MainDataAdapter.ViewHolder> {

    private Weather weatherData = null;

    public MainDataAdapter(Weather weather) {
        this.weatherData = weather;
    }

    public void setWeather(Weather weather) {
        this.weatherData = weather;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityNameTextView;
        TextView weatherStringTextView;

        ViewHolder(View itemView) {
            super(itemView);
            cityNameTextView = itemView.findViewById(R.id.cityNameTextView);
            weatherStringTextView = itemView.findViewById(R.id.weatherStringTextView);
        }
    }

    @Override
    public MainDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.mainrecyclerview_item, parent, false);
        MainDataAdapter.ViewHolder viewHolder = new MainDataAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainDataAdapter.ViewHolder holder, int position) {
        if (weatherData.getLocation() != null) {
            holder.cityNameTextView.setText(weatherData.getLocation().getCity());
            if (position == 0 ) {
                if (weatherData.getCurrentObservation() != null) {
                    holder.weatherStringTextView.setText(weatherData.getCurrentObservation().getCondition().getText());
                }
            } else {
                holder.weatherStringTextView.setText(weatherData.getForecasts().get(position - 1).getText());
            }

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
}
