package com.aharoldk.burncalories;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.aharoldk.burncalories.fragment.BurnFragment;
import com.aharoldk.burncalories.fragment.HomeFragment;
import com.aharoldk.burncalories.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.navigation_burn:
                    fragment = new BurnFragment();
                    break;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    break;


            }

            if(fragment!=null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, fragment)
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
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.content,
                            new HomeFragment()).commit();
        }

    }

}
