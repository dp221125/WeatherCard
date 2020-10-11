package com.example.weathercard.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weathercard.APIData.Weather;
import com.example.weathercard.network.WeatherAPIProvider;

import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    final String CONSUMER_KEY = "dj0yJmk9bTYzMDA5WU1GREEzJmQ9WVdrOU5YQkpWMlZqYUdzbWNHbzlNQT09JnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PWI3";
    final String CONSUMER_SECRET = "d9be33bf0bf6fd581a87f645adf1c55f98c00821";
    final String baseUrl = "https://weather-ydn-yql.media.yahoo.com/forecastrss/";

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public Single<Weather> fetchData() {
       return Single.create( emitter ->  {

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
                       emitter.onSuccess(weather);
                   } else {
                       Log.e("onResponse at FetchData", response.headers().toString());
                   }
               }

               @Override
               public void onFailure(Call<Weather> call, Throwable t) {
                   Log.e("Fetch Error", t.toString());
                   emitter.onError(t);
               }
           });
        });
    }
}