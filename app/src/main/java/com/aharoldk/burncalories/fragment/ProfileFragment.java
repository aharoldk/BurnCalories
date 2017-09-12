package com.aharoldk.burncalories.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aharoldk.burncalories.R;
import com.aharoldk.burncalories.SettingActivity;
import com.aharoldk.burncalories.helper.DatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {
    @BindView(R.id.ivSetting) ImageView ivSetting;

    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvAvgSteps) TextView tvAvgSteps;
    @BindView(R.id.tvAvgDistance) TextView tvAvgDistance;
    @BindView(R.id.tvAvgCal) TextView tvAvgCal;

    @BindView(R.id.tvTotalSteps) TextView tvTotalSteps;
    @BindView(R.id.tvTotalDistance) TextView tvTotalDistance;
    @BindView(R.id.tvTotalCalories) TextView tvTotalCalories;

    int totalSteps = 0;
    double totalDistance = 0;
    double totalCalories = 0;

    public ProfileFragment() {
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        Cursor cursor = databaseHelper.selectUser();
        cursor.moveToFirst();
        tvName.setText(String.valueOf(cursor.getString(1)));

        Cursor cursorActivity = databaseHelper.selectActivity();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        if(cursorActivity.getCount() > 0){
            while(cursorActivity.moveToNext()){
                totalDistance += cursorActivity.getDouble(1);
                totalCalories += cursorActivity.getDouble(2);
                totalSteps += cursorActivity.getDouble(3);

                tvAvgSteps.setText(String.valueOf(totalSteps / cursorActivity.getCount()));
                tvAvgDistance.setText(String.valueOf(decimalFormat.format(totalDistance / cursorActivity.getCount())));
                tvAvgCal.setText(String.valueOf(decimalFormat.format(totalCalories / cursorActivity.getCount())));

                tvTotalSteps.setText(String.valueOf(totalSteps));
                tvTotalDistance.setText(String.valueOf(decimalFormat.format(totalDistance)));
                tvTotalCalories.setText(String.valueOf(decimalFormat.format(totalCalories)));
            }
        } else {
            tvAvgSteps.setText(String.valueOf(totalSteps));
            tvAvgDistance.setText(String.valueOf(decimalFormat.format(totalDistance)));
            tvAvgCal.setText(String.valueOf(decimalFormat.format(totalCalories)));

            tvTotalSteps.setText(String.valueOf(totalSteps));
            tvTotalDistance.setText(String.valueOf(decimalFormat.format(totalDistance)));
            tvTotalCalories.setText(String.valueOf(decimalFormat.format(totalCalories)));
        }

        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SettingActivity.class));
            }
        });

        return view;
    }

}
