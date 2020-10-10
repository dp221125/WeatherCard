package com.example.weathercard.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.weathercard.APIData.Weather;
import com.example.weathercard.MainDataAdapter;
import com.example.weathercard.R;
import com.example.weathercard.network.APIRequest;
import com.example.weathercard.network.RequestManager;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView mainRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

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
        RequestManager requestManager = RequestManager.getInstance(this.getActivity());
        APIRequest request = new APIRequest(Request.Method.GET, null, null, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                // Add success logic here
//                Log.e(response.toString());
                Weather weather = (Weather) response;
                System.out.println("성공");
                System.out.println(weather.getLocation().getRegion());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Add error handling here
                Log.e("실패 ", ":" + error);
                System.out.println("실패");
            }
        });
        requestManager.addToRequestQueue(request);
    }
}