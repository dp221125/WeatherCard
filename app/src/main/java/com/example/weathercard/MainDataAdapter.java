package com.example.weathercard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainDataAdapter extends RecyclerView.Adapter<MainDataAdapter.ViewHolder> {

    private ArrayList<String> weatherData = null;

    public MainDataAdapter(ArrayList<String> list) {
        this.weatherData = list;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;

        ViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.cityNameTextView) ;
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
        String text = weatherData.get(position);
        holder.textView1.setText(text);
    }

    @Override
    public int getItemCount() {
        return weatherData.size();
    }
}
