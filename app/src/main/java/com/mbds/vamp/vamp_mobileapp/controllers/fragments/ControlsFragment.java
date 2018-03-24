package com.mbds.vamp.vamp_mobileapp.controllers.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.mbds.vamp.vamp_mobileapp.R;
import com.mbds.vamp.vamp_mobileapp.utils.SocketManager;

public class ControlsFragment extends Fragment {

    private FloatingActionButton bt_fl_win_up;
    private FloatingActionButton bt_fr_win_up;
    private FloatingActionButton bt_bl_win_up;
    private FloatingActionButton bt_br_win_up;

    private FloatingActionButton bt_fl_win_down;
    private FloatingActionButton bt_fr_win_down;
    private FloatingActionButton bt_bl_win_down;
    private FloatingActionButton bt_br_win_down;

    private SocketManager sm;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_controls, container, false);
        return rootView;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sm = new SocketManager();

        FloatingActionButton bt_fl_win_up = (FloatingActionButton) getActivity().findViewById(R.id.bt_fl_win_up);
        FloatingActionButton bt_fr_win_up = (FloatingActionButton) getActivity().findViewById(R.id.bt_fr_win_up);
        FloatingActionButton bt_bl_win_up = (FloatingActionButton) getActivity().findViewById(R.id.bt_bl_win_up);
        FloatingActionButton bt_br_win_up = (FloatingActionButton) getActivity().findViewById(R.id.bt_br_win_up);

        FloatingActionButton bt_fl_win_down = (FloatingActionButton) getActivity().findViewById(R.id.bt_fl_win_down);
        FloatingActionButton bt_fr_win_down = (FloatingActionButton) getActivity().findViewById(R.id.bt_fr_win_down);
        FloatingActionButton bt_bl_win_down = (FloatingActionButton) getActivity().findViewById(R.id.bt_bl_win_down);
        FloatingActionButton bt_br_win_down = (FloatingActionButton) getActivity().findViewById(R.id.bt_br_win_down);


        bt_fl_win_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    sm.frontLeftWindowUp(0);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    sm.frontLeftWindowUp(1);
                }
                return false;
            }
        });

        bt_fr_win_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    sm.frontRightWindowUp(0);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    sm.frontRightWindowUp(1);
                }
                return false;
            }
        });

        bt_bl_win_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    sm.backLeftWindowUp(0);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    sm.backLeftWindowUp(1);
                }
                return false;
            }
        });

        bt_br_win_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    sm.backRightWindowUp(0);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    sm.backRightWindowUp(1);
                }
                return false;
            }
        });

        bt_fl_win_down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    sm.frontLeftWindowDown(0);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    sm.frontLeftWindowDown(1);
                }
                return false;
            }
        });

        bt_fr_win_down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    sm.frontRightWindowDown(0);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    sm.frontRightWindowDown(1);
                }
                return false;
            }
        });

        bt_bl_win_down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    sm.backLeftWindowDown(0);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    sm.backLeftWindowDown(1);
                }
                return false;
            }
        });

        bt_br_win_down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    sm.backRightWindowDown(0);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    sm.backRightWindowDown(1);
                }
                return false;
            }
        });

    }

    public void allWindowsUp(){
        sm.allWindowUp(1);
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                R.string.home_stop, Snackbar.LENGTH_LONG)
                .setAction(R.string.home_stop, null).show();
    }

    public void allWindowsDown(){
        sm.allWindowDown(1);
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                R.string.home_stop, Snackbar.LENGTH_LONG)
                .setAction(R.string.home_stop, null).show();
    }




}
