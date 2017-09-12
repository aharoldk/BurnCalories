package com.aharoldk.burnyourcalories.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aharoldk.burnyourcalories.InsertActivity;
import com.aharoldk.burnyourcalories.R;
import com.aharoldk.burnyourcalories.adapter.HomeAdapter;
import com.aharoldk.burnyourcalories.helper.DatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.rvHome) RecyclerView rvHome;
    @BindView(R.id.fabHome) FloatingActionButton fabHome;
    @BindView(R.id.tvTotalCalories) TextView tvTotalCalories;

    private int totalCalories = 0;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        Cursor cursor = databaseHelper.selectCalories();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                totalCalories += cursor.getInt(1);

            }
        }

        tvTotalCalories.setText(String.valueOf(totalCalories));

        rvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHome.setHasFixedSize(true);
        rvHome.setAdapter(new HomeAdapter(databaseHelper.selectFood()));

        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), InsertActivity.class));
            }
        });

        return view;
    }

}
