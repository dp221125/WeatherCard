package com.example.weathercard.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathercard.APIData.UserLocation;
import com.example.weathercard.APIData.Weather;
import com.example.weathercard.MainDataAdapter;
import com.example.weathercard.R;
import com.example.weathercard.ui.utility.GpsTracker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static android.content.Context.LOCATION_SERVICE;
import static android.widget.Toast.LENGTH_SHORT;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView mainRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Weather weather;
    private MainDataAdapter mainDataAdapter;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    GpsTracker gpsTracker;

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

        showFloatingBtn();
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

        Log.d("onStart", "onStart");
        if (!checkLocationServicesStatus()) {
            showDialogForLocationService();
        }else {
            checkPermission();
        }

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

    private boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||  locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showDialogForLocationService() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("위치 서비스");
        builder.setMessage("앱의 정상적인 서비스를 위해서 위치서비스가 필요합니다. \n" + "위치 서비스를 켜주세요");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent callGPSIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivityForResult(callGPSIntent, GPS_ENABLE_REQUEST_CODE);
            }

        });

        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }

        });

        builder.create().show();

    }


    private void checkPermission() {
        Log.d("checkPermission", "CALL");

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);

        if (!(hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED)) {
            String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])) {
                Toast.makeText(getActivity(), "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            }

            requestPermissions(REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
        } else {
            Log.d("checkPermission", "Success");

            gpsTracker  = new GpsTracker(getActivity());
            disposables.add(gpsTracker.locationMessage
                    .subscribe( (location) -> {
                        callFetchData(location);
                    }));

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("CALL onRequestPermissionsResult", "CALL ");
        String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if ( requestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length) {

            boolean check_result = true;

            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if ( check_result ) {
                Log.d("check_result", "TRUE");

                gpsTracker  = new GpsTracker(getActivity());

                disposables.add(gpsTracker.locationMessage
                        .subscribe( (location) -> {
                            callFetchData(location);
                        }));
            }


            else {

                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(getActivity(), "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    callFetchData(null);
                }else {
                    Toast.makeText(getActivity(), "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                    callFetchData(null);
                }
            }

        }
    }


    private void callFetchData(@Nullable Location location) {

        Location baseLocation = new Location("A");

        if (location == null) {
            baseLocation.setLatitude(35.9084351);
            baseLocation.setLongitude(128.7990138);
        } else {
            baseLocation = location;
        }

        disposables.add( homeViewModel.fetchData(baseLocation, getUnit())
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
    public void onPause() {
        super.onPause();
        gpsTracker.stopUsingGPS();
    }

    public void reloadData() {
        if (!checkLocationServicesStatus()) {
            showDialogForLocationService();
        }else {
            checkPermission();
        }
    }

    private void showFloatingBtn() {
        FloatingActionButton floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.floatingBtn);
        floatingActionButton.setVisibility(View.VISIBLE);
    }


}