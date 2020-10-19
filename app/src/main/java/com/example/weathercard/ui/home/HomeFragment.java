package com.example.weathercard.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathercard.APIData.Weather;
import com.example.weathercard.MainDataAdapter;
import com.example.weathercard.R;
import com.example.weathercard.ui.setting.SettingViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static android.widget.Toast.LENGTH_SHORT;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView mainRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Weather weather;
    private MainDataAdapter mainDataAdapter;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        weather = new Weather();

        mainRecyclerView = (RecyclerView) root.findViewById(R.id.mainRecyclerView);
        mainRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        mainRecyclerView.setLayoutManager(layoutManager);

        mainDataAdapter = new MainDataAdapter(weather);
        mainRecyclerView.setAdapter(mainDataAdapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();

        System.out.println("CALL");

        disposables.add( homeViewModel.fetchData(getUnit())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( (weather, throwable) -> {
                    if (throwable == null) {
                        Log.d("Unit",getUnit());
                        Log.d("Success", String.valueOf(weather.getCurrentObservation().getCondition().getTemperature()));
                        mainDataAdapter.setWeather(weather);
                        mainDataAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("fragment fetchData Error", throwable.toString());
                        Toast toast = Toast.makeText(getActivity(), throwable.toString(), LENGTH_SHORT);
                        toast.show();
                    }
                }));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!disposables.isDisposed()){
            disposables.dispose();
        }
    }

    private String getUnit() {
        SharedPreferences prefs = getActivity().getSharedPreferences("TempPref", Context.MODE_PRIVATE);
        String value = prefs.getString("unitSwitch", "c");

        return value;
    }


}