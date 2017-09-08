package com.aharoldk.burncalories.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aharoldk.burncalories.KillerActivity;
import com.aharoldk.burncalories.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BurnFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.tvBurnCalories) TextView tvBurnCalories;
    @BindView(R.id.btnStart) Button btnStart;

    public BurnFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_burn, container, false);

        ButterKnife.bind(this, view);

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
