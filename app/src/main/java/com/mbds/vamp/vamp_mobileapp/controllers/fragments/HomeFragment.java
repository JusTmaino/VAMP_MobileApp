package com.mbds.vamp.vamp_mobileapp.controllers.fragments;

import android.media.Image;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mbds.vamp.vamp_mobileapp.R;


public class HomeFragment extends Fragment {

    TextView charge;
    TextView registerNumber;
    TextView brand;
    TextView model;

    ImageButton lock;
    boolean state_lock = true;

    ImageButton start;
    boolean state_start = false;

    ImageButton undefined1;
    ImageButton undefined2;
    ImageButton undefined3;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        charge = (TextView) getActivity().findViewById(R.id.home_charge);
        registerNumber = (TextView) getActivity().findViewById(R.id.home_registerNumber);
        brand = (TextView) getActivity().findViewById(R.id.home_brand);
        model = (TextView) getActivity().findViewById(R.id.home_model);

        lock = (ImageButton) getActivity().findViewById(R.id.home_lock);
        start = (ImageButton) getActivity().findViewById(R.id.home_start);
        undefined1 = (ImageButton) getActivity().findViewById(R.id.home_control_undefined_3);
        undefined2 = (ImageButton) getActivity().findViewById(R.id.home_control_undefined_4);
        undefined3 = (ImageButton) getActivity().findViewById(R.id.home_control_undefined_5);


        ////////////////////////////////////////////////////////////////////////////////////////////
        // Gestion de commandes de contrôle
        ////////////////////////////////////////////////////////////////////////////////////////////

        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state_lock) {
                    state_lock = false;
                    lock.setImageResource(R.drawable.ic_unlock);
                    Snackbar.make(v, R.string.home_unlock, Snackbar.LENGTH_LONG)
                            .setAction(R.string.home_unlock, null).show();
                } else {
                    state_lock = true;
                    lock.setImageResource(R.drawable.ic_lock);
                    Snackbar.make(v, R.string.home_lock, Snackbar.LENGTH_LONG)
                            .setAction(R.string.home_lock, null).show();
                }
            }

        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state_start) {
                    state_start = false;
                    start.setImageResource(R.drawable.ic_start);
                    Snackbar.make(v, R.string.home_stop, Snackbar.LENGTH_LONG)
                            .setAction(R.string.home_stop, null).show();
                } else {
                    state_start = true;
                    start.setImageResource(R.drawable.ic_stop);
                    Snackbar.make(v, R.string.home_start, Snackbar.LENGTH_LONG)
                            .setAction(R.string.home_start, null).show();

                }
            }
        });

        undefined1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, R.string.home_undefined, Snackbar.LENGTH_LONG)
                        .setAction(R.string.home_undefined, null).show();
            }
        });

        undefined2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, R.string.home_undefined, Snackbar.LENGTH_LONG)
                        .setAction(R.string.home_undefined, null).show();
            }
        });

        undefined3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, R.string.home_undefined, Snackbar.LENGTH_LONG)
                        .setAction(R.string.home_undefined, null).show();
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Initialisation des données du véhicule
        ////////////////////////////////////////////////////////////////////////////////////////////

        charge.setText("99%");
        registerNumber.setText("AA-280-TC");
        brand.setText("PEUGEOT");
        model.setText("Ion");

        ////////////////////////////////////////////////////////////////////////////////////////////
    }
}
