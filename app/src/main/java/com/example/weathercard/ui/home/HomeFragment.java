package com.example.weathercard.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathercard.APIData.Weather;
import com.example.weathercard.MainDataAdapter;
import com.example.weathercard.R;
import com.example.weathercard.network.WeatherAPIProvider;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView mainRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    final String CONSUMER_KEY = "dj0yJmk9bTYzMDA5WU1GREEzJmQ9WVdrOU5YQkpWMlZqYUdzbWNHbzlNQT09JnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PWI3";
    final String CONSUMER_SECRET = "d9be33bf0bf6fd581a87f645adf1c55f98c00821";
    final String baseUrl = "https://weather-ydn-yql.media.yahoo.com/forecastrss/";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(String.format("TEXT %d", i)) ;
        }

        mainRecyclerView = (RecyclerView) root.findViewById(R.id.mainRecyclerView);
        mainRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        mainRecyclerView.setLayoutManager(layoutManager);

        MainDataAdapter mainDataAdapter = new MainDataAdapter(list);
        mainRecyclerView.setAdapter(mainDataAdapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        System.out.println("CALL");

        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        WeatherAPIProvider provider = retrofit.create(WeatherAPIProvider.class);
        provider.getData("37.372", "-122.038", "json").enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.isSuccessful()) {
                    Weather weather = response.body();

                    System.out.println(weather.getLocation().getCity());
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e("fail", t.toString());
            }
        });
//        lat=37.372&lon=-122.038
    }
}