package com.aharoldk.burncalories.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aharoldk.burncalories.R;
import com.aharoldk.burncalories.SettingActivity;
import com.aharoldk.burncalories.helper.DatabaseHelper;

import java.io.File;
import java.io.FilenameFilter;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    @BindView(R.id.ivSetting) ImageView ivSetting;
    @BindView(R.id.profile_image) CircleImageView profile_image;

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

        showImages();

        if(cursorActivity.getCount() > 0){
            while(cursorActivity.moveToNext()){
                totalDistance += cursorActivity.getDouble(1);
                totalCalories += cursorActivity.getDouble(2);
                totalSteps += cursorActivity.getDouble(3);

                tvAvgSteps.setText(String.valueOf(totalSteps / cursorActivity.getCount()));
                tvAvgDistance.setText(String.valueOf(String.format("%.2f",(totalDistance / cursorActivity.getCount()))));
                tvAvgCal.setText(String.valueOf(String.format("%.2f", (totalCalories / cursorActivity.getCount()))));

                tvTotalSteps.setText(String.valueOf(totalSteps));
                tvTotalDistance.setText(String.valueOf(String.format("%.2f",totalDistance)));
                tvTotalCalories.setText(String.valueOf(String.format("%.2f",totalCalories)));
            }
        } else {
            tvAvgSteps.setText(String.valueOf(totalSteps));
            tvAvgDistance.setText(String.valueOf(String.format("%.2f",totalDistance)));
            tvAvgCal.setText(String.valueOf(String.format("%.2f",totalCalories)));

            tvTotalSteps.setText(String.valueOf(totalSteps));
            tvTotalDistance.setText(String.valueOf(String.format("%.2f",totalDistance)));
            tvTotalCalories.setText(String.valueOf(String.format("%.2f",totalCalories)));
        }

        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SettingActivity.class));
            }
        });

        return view;
    }

    static final String[] EXTENSIONS = new String[]{
            "jpg" // and other formats you need
    };
    // filter to identify images based on their extensions
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };

    private void showImages() {
        File dir = new File(String.valueOf(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)));

        File[] filelist = dir.listFiles(IMAGE_FILTER );
        for (File f : filelist) {

            String mCurrentPhotoPath = f.getAbsolutePath();

            Log.i("current", mCurrentPhotoPath);

            Bitmap photoReducedSizeBitmp = BitmapFactory.decodeFile(mCurrentPhotoPath);

            profile_image.setImageBitmap(photoReducedSizeBitmp);
        }
    }

}
