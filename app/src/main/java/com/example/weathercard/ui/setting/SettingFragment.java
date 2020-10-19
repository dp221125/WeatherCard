package com.example.weathercard.ui.setting;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.weathercard.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingFragment extends Fragment {

    private SettingViewModel mViewModel;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_setting, container, false);

        SwitchMaterial unitSwitch = root.findViewById(R.id.unitSwitch);
        unitSwitch.setChecked(getStatus());
        unitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences prefs = getActivity().getSharedPreferences("TempPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                if(isChecked) {
                    Log.d("on","F");
                    editor.putString("unitSwitch", "f");
                } else {
                    Log.d("on","C");
                    editor.putString("unitSwitch", "c");
                }
                editor.commit();

            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
    }

    private boolean getStatus() {
        SharedPreferences prefs = getActivity().getSharedPreferences("TempPref", Context.MODE_PRIVATE);
        String value = prefs.getString("unitSwitch", "c");
        Log.d("on",value);
        if (value.equals("c")) {
            return false;
        } else {
            return true;
        }
    }

}