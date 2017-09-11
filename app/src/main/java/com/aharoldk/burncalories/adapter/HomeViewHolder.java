package com.aharoldk.burncalories.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aharoldk.burncalories.R;
import com.aharoldk.burncalories.model.Home;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvDate) TextView tvDate;
    @BindView(R.id.tvCalories) TextView tvCalories;
    @BindView(R.id.tvTotalWalk) TextView tvTotalWalk;
    @BindView(R.id.tvTotalRun) TextView tvTotalRun;

    public HomeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Home home) {
        tvDate.setText(String.valueOf(home.getDate_activity()));
        tvCalories.setText(String.valueOf(" "+home.getTotal_calories()+"   "));
        tvTotalWalk.setText(String.valueOf(" "+home.getTotal_walk_off()+"   "));
        tvTotalRun.setText(String.valueOf(" "+home.getTotal_run_off()+"   "));
    }
}
