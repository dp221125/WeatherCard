package com.example.weathercard.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathercard.MainDataAdapter;
import com.example.weathercard.R;

import java.util.ArrayList;


import io.reactivex.rxjava3.disposables.CompositeDisposable;

import static android.widget.Toast.LENGTH_SHORT;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView mainRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private final CompositeDisposable disposables = new CompositeDisposable();

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

        disposables.add( homeViewModel.fetchData()
                .subscribe( (weather, throwable) -> {
                    if (throwable == null) {
                        System.out.println(weather.getLocation().getCity());
                    } else {
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


}