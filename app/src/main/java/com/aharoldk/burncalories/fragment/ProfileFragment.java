package com.aharoldk.burncalories.fragment;


import android.database.Cursor;
import android.os.Bundle;
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

    private DatabaseHelper databaseHelper;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        databaseHelper = new DatabaseHelper(getActivity());

        Cursor cursor = databaseHelper.selectUser();
        cursor.moveToFirst();
        tvName.setText(String.valueOf(cursor.getString(1)));

        return view;
    }

}
