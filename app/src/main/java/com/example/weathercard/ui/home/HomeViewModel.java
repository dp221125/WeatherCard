package com.example.weathercard.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weathercard.APIData.Weather;
import com.example.weathercard.BuildConfig;
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

    public Single<Weather> fetchData(String unit) {
       return Single.create( emitter ->  {

           OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_SECRET);

           OkHttpClient client = new OkHttpClient.Builder()
                   .addInterceptor(new SigningInterceptor(consumer))
                   .build();

           Retrofit retrofit = new Retrofit.Builder()
                   .baseUrl(BuildConfig.BASE_URL)
                   .addConverterFactory(GsonConverterFactory.create())
                   .client(client)
                   .build();

           WeatherAPIProvider provider = retrofit.create(WeatherAPIProvider.class);
           provider.getData("35.9084351", "128.7990138", "json", unit).enqueue(new Callback<Weather>() {
               @Override
               public void onResponse(Call<Weather> call, Response<Weather> response) {
                   if (response.isSuccessful() && response.body() != null ) {
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