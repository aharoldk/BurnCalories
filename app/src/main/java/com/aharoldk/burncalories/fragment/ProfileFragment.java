package com.aharoldk.burncalories.fragment;


import android.database.Cursor;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aharoldk.burncalories.R;
import com.aharoldk.burncalories.helper.DatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvAvgSteps) TextView tvAvgSteps;
    @BindView(R.id.tvAvgDistance) TextView tvAvgDistance;
    @BindView(R.id.tvAvgCal) TextView tvAvgCal;

    double totalDistance;
    double totalSteps;
    double totalCalories;

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

        while(cursorActivity.moveToNext()){
            totalDistance += cursorActivity.getDouble(1);
            totalCalories += cursorActivity.getDouble(2);
            totalSteps += cursorActivity.getDouble(3);

        }

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        tvAvgSteps.setText(String.valueOf(decimalFormat.format(totalSteps / cursorActivity.getCount())));
        tvAvgDistance.setText(String.valueOf(decimalFormat.format(totalDistance / cursorActivity.getCount())));
        tvAvgCal.setText(String.valueOf(decimalFormat.format(totalCalories / cursorActivity.getCount())));

        return view;
    }

}
