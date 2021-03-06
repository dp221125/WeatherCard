package com.example.weathercard.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.weathercard.R;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_setting, container, false);

        Button btn = root.findViewById(R.id.showLicenseBtn);

        btn.setOnClickListener(v -> startActivity(new Intent(getActivity(), OssLicensesMenuActivity.class)));


        SwitchMaterial unitSwitch = root.findViewById(R.id.unitSwitch);
        unitSwitch.setChecked(getStatus());
        unitSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            SharedPreferences prefs = getActivity().getSharedPreferences("TempPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            if(isChecked) {
                Log.d("on","F");
                editor.putString("unitSwitch", "f");
            } else {
                Log.d("on","C");
                editor.putString("unitSwitch", "c");
            }
            editor.apply();

        });

        hiddenFloatingBtn();

        return root;
    }

    private boolean getStatus() {
        SharedPreferences prefs = getActivity().getSharedPreferences("TempPref", Context.MODE_PRIVATE);
        String value = prefs.getString("unitSwitch", "c");
        Log.d("on",value);
        return !value.equals("c");
    }

    private void hiddenFloatingBtn() {
        FloatingActionButton floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.floatingBtn);
        floatingActionButton.setVisibility(View.INVISIBLE);
    }

}