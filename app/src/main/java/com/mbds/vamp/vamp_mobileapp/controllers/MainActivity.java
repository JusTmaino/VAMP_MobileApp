package com.mbds.vamp.vamp_mobileapp.controllers;

import android.content.Intent;
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

        if(LoginActivity.loggedIn) {
            Intent intent = new Intent(MainActivity.this, AppActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }


}
