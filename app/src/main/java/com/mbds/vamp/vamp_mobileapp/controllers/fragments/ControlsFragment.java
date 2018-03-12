package com.mbds.vamp.vamp_mobileapp.controllers.fragments;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbds.vamp.vamp_mobileapp.R;

public class ControlsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_controls, container, false);
        Log.d("hiiiiiiiiii","hiiiiiiiii");

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("hellllllllllllllloooooo","helllo");

    }
}
