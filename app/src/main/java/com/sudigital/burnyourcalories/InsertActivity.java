package com.sudigital.burnyourcalories;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.sudigital.burnyourcalories.helper.DatabaseHelper;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InsertActivity extends AppCompatActivity {
    @BindView(com.sudigital.burnyourcalories.R.id.chkSoda) CheckBox chkSoda;
    @BindView(com.sudigital.burnyourcalories.R.id.chkPizza) CheckBox chkPizza;
    @BindView(com.sudigital.burnyourcalories.R.id.chkSandwich) CheckBox chkSandwich;
    @BindView(com.sudigital.burnyourcalories.R.id.chkCereal) CheckBox chkCereal;
    @BindView(com.sudigital.burnyourcalories.R.id.chkMuffin) CheckBox chkMuffin;

    @BindView(com.sudigital.burnyourcalories.R.id.chkChoco) CheckBox chkChoco;
    @BindView(com.sudigital.burnyourcalories.R.id.chkMocha) CheckBox chkMocha;
    @BindView(com.sudigital.burnyourcalories.R.id.chkChips) CheckBox chkChips;
    @BindView(com.sudigital.burnyourcalories.R.id.chkPeanuts) CheckBox chkPeanuts;
    @BindView(com.sudigital.burnyourcalories.R.id.chkCinnamon) CheckBox chkCinnamon;

    double total_calories = 0;
    int total_walk = 0;
    int total_run = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.sudigital.burnyourcalories.R.layout.activity_insert);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(com.sudigital.burnyourcalories.R.menu.menu_insert, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case com.sudigital.burnyourcalories.R.id.action_send:
                DatabaseHelper databaseHelper = new DatabaseHelper(this);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                Calendar calendar = Calendar.getInstance();
                String dateNow = dateFormat.format(calendar.getTime());
                if(databaseHelper.insertFood(total_calories, total_walk, total_run, dateNow)){
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }
                break;
        }

        return true;
    }

    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case com.sudigital.burnyourcalories.R.id.chkSoda:
                if (checked) {
                    total_calories += 138;
                    total_walk += 26;
                    total_run += 13;
                } else {
                    total_calories -= 138;
                    total_walk -= 26;
                    total_run -= 13;
                }
                 break;

            case com.sudigital.burnyourcalories.R.id.chkChoco:
                if (checked) {
                    total_calories += 229;
                    total_walk += 42;
                    total_run += 22;
                } else {
                    total_calories -= 229;
                    total_walk -= 42;
                    total_run -= 22;
                }
                break;

            case com.sudigital.burnyourcalories.R.id.chkSandwich:
                if (checked) {
                    total_calories += 445;
                    total_walk += 82;
                    total_run += 42;
                } else {
                    total_calories -= 445;
                    total_walk -= 82;
                    total_run -= 42;
                }
                break;

            case com.sudigital.burnyourcalories.R.id.chkPizza:
                if (checked) {
                    total_calories += 449;
                    total_walk += 83;
                    total_run += 43;
                } else {
                    total_calories -= 449;
                    total_walk -= 83;
                    total_run -= 43;
                }

                break;



            case com.sudigital.burnyourcalories.R.id.chkMocha:
                if (checked) {
                    total_calories += 290;
                    total_walk += 53;
                    total_run += 28;
                } else {
                    total_calories -= 290;
                    total_walk -= 53;
                    total_run -= 28;
                }
                break;


            case com.sudigital.burnyourcalories.R.id.chkChips:
                if (checked) {
                    total_calories += 171;
                    total_walk += 31;
                    total_run += 16;
                } else {
                    total_calories -= 171;
                    total_walk -= 31;
                    total_run -= 16;
                }
                break;


            case com.sudigital.burnyourcalories.R.id.chkPeanuts:
                if (checked) {
                    total_calories += 296;
                    total_walk += 54;
                    total_run += 28;
                } else {
                    total_calories += 296;
                    total_walk += 54;
                    total_run += 28;
                }
                break;

            case com.sudigital.burnyourcalories.R.id.chkCinnamon:
                if (checked) {
                    total_calories += 420;
                    total_walk += 117;
                    total_run += 40;
                } else {
                    total_calories -= 420;
                    total_walk -= 117;
                    total_run -= 40;
                }
                break;

            case com.sudigital.burnyourcalories.R.id.chkCereal:
                if (checked) {
                    total_calories += 172;
                    total_walk += 31;
                    total_run += 16;
                } else {
                    total_calories -= 172;
                    total_walk -= 31;
                    total_run -= 16;
                }
                break;

            case com.sudigital.burnyourcalories.R.id.chkMuffin:
                if (checked) {
                    total_calories += 265;
                    total_walk += 48;
                    total_run += 25;
                } else {
                    total_calories -= 265;
                    total_walk -= 48;
                    total_run -= 25;
                }
                break;
        }

    }
}
