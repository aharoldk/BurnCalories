package com.aharoldk.burncalories.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.icu.text.DecimalFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aharoldk.burncalories.KillerActivity;
import com.aharoldk.burncalories.R;
import com.aharoldk.burncalories.helper.DatabaseHelper;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BurnFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.tvBurnCalories) TextView tvBurnCalories;
    @BindView(R.id.btnStart) Button btnStart;

    String dateNow;
    double totalCalories;

    DecimalFormat format;

    public BurnFragment() {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy");

        Calendar calendar = Calendar.getInstance();
        dateNow = dateTimeFormat.format(calendar.getTime());

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

        Cursor cursor = databaseHelper.selectActivity();

        while(cursor.moveToNext()){
            if(dateNow.equals(cursor.getString(4))){
                totalCalories += cursor.getDouble(2);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_burn, container, false);

        ButterKnife.bind(this, view);

        format = new DecimalFormat("#.##");
        tvBurnCalories.setText(String.valueOf(format.format(totalCalories)));

        btnStart.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.equals(btnStart)){
            getActivity().startActivity(new Intent(getContext(), KillerActivity.class));
        }
    }
}
