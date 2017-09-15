package com.sudigital.burnyourcalories;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.sudigital.burnyourcalories.fragment.BurnFragment;
import com.sudigital.burnyourcalories.fragment.HomeFragment;
import com.sudigital.burnyourcalories.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case com.sudigital.burnyourcalories.R.id.navigation_home:
                    fragment = new HomeFragment();
                    break;
                case com.sudigital.burnyourcalories.R.id.navigation_burn:
                    fragment = new BurnFragment();
                    break;
                case com.sudigital.burnyourcalories.R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    break;


            }

            if(fragment!=null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(com.sudigital.burnyourcalories.R.id.content, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }

            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.sudigital.burnyourcalories.R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(com.sudigital.burnyourcalories.R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().
                    add(com.sudigital.burnyourcalories.R.id.content,
                            new HomeFragment()).commit();
        }

    }

}
