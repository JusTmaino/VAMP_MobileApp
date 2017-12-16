package com.mbds.vamp.vamp_mobileapp.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mbds.vamp.vamp_mobileapp.R;
import com.mbds.vamp.vamp_mobileapp.controllers.fragments.ControlsFragment;
import com.mbds.vamp.vamp_mobileapp.controllers.fragments.HomeFragment;
import com.mbds.vamp.vamp_mobileapp.controllers.fragments.LocationFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationFragment home = new LocationFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, home).commit();
    }


}
