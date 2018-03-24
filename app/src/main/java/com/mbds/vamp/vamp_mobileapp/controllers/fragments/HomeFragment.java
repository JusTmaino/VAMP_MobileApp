package com.mbds.vamp.vamp_mobileapp.controllers.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mbds.vamp.vamp_mobileapp.R;
import com.mbds.vamp.vamp_mobileapp.models.Car;
import com.mbds.vamp.vamp_mobileapp.models.User;
import com.mbds.vamp.vamp_mobileapp.utils.Config;
import com.mbds.vamp.vamp_mobileapp.utils.SocketManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    TextView charge;
    TextView registerNumber;
    TextView brand;
    TextView model;

    ImageButton lock;
    boolean state_lock = true;

    ImageButton start;
    boolean state_start = false;

    ImageButton hornButton;
    ImageButton allUpButton;
    ImageButton allDownButton;

    private Spinner spinner;

    private SocketManager sm;

    private String username;
    private String access_token;
    private User loggedUser;
    private List<Car> userCars;
    private ArrayAdapter carArrayAdapter;


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
        hornButton = (ImageButton) getActivity().findViewById(R.id.home_control_horn);
        allUpButton = (ImageButton) getActivity().findViewById(R.id.home_control_all_up);
        allDownButton = (ImageButton) getActivity().findViewById(R.id.home_control_all_down);
        spinner = (Spinner) getActivity().findViewById(R.id.spinner);


        sm = new SocketManager();


        //Fetching username and token from shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF, "Not Available");
        access_token = sharedPreferences.getString(Config.ACCESS_TOKEN_SHARED_PREF, "Not Available");
        loggedUser = new User();
        userCars = new ArrayList<Car>();
        spinner.setOnItemSelectedListener(this);

        getUserCars();

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Gestion de commandes de contrôle
        ////////////////////////////////////////////////////////////////////////////////////////////

        lock.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (state_lock) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                       unlockCar(0);
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        unlockCar(1);
                        state_lock = false;
                    }
                    return false;
                } else {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        lockCar(0);
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        lockCar(1);
                        state_lock = true;
                    }
                    return false;
                }
            }
        });

        start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (state_start) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        stopCar(0);
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        stopCar(1);
                        state_start = false;
                    }
                    return false;
                } else {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        startCar(0);
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        startCar(1);
                        state_start = true;
                    }
                    return false;
                }
            }
        });

        hornButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    sm.horn(0);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    sm.horn(1);
                }
                return false;
            }
        });

        allUpButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    sm.allWindowUp(0);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    sm.allWindowUp(1);
                }
                return false;
            }
        });

        allDownButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    sm.allWindowDown(0);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    sm.allWindowDown(1);
                }
                return false;
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

    private void getUserCars() {
        AsyncHttpClient client2 = new AsyncHttpClient();
        client2.addHeader("X-Auth-Token", access_token);
        client2.get(Config.USERS_URL, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray users) {
                super.onSuccess(statusCode, headers, users);
                Log.d("users", users.toString());
                Log.d("statu code : ", String.valueOf(statusCode));

                for (int n = 0; n < users.length(); n++) {
                    try {
                        final JSONObject user = users.getJSONObject(n);
                        if (user != null) {
                            if (user.get("username").toString().equals(username)) {
                                Log.d("loggedUser", user.toString());

                                JSONArray jsonArray = user.getJSONArray("cars");

                                final ArrayList<String> carsIds = new ArrayList<String>();
                                if (jsonArray != null) {
                                    int len = jsonArray.length();
                                    for (int i = 0; i < len; i++) {
                                        carsIds.add(jsonArray.getJSONObject(i).get("id").toString());
                                    }
                                }

                                AsyncHttpClient client3 = new AsyncHttpClient();
                                client3.addHeader("X-Auth-Token", access_token);

                                for (String id : carsIds) {

                                    client3.get(Config.CARS_URL + "/" + id + ".json", null, new JsonHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, JSONObject car) {
                                            super.onSuccess(statusCode, headers, car);
                                            Log.d("car", car.toString());
                                            Log.d("statu code : ", String.valueOf(statusCode));

                                            Car userCar = new Car();
                                            try {
                                                userCar.setModel(car.get("model").toString());
                                                userCar.setBrand(car.get("brand").toString());
                                                userCar.setRegisterNumber(car.get("matricule").toString());
                                                userCar.setCharge(car.getInt("charge"));
                                                userCar.setSeatCount(car.getInt("nb_place"));
                                                userCar.setLocked(car.getBoolean("locked"));
                                                //userCar.setAvatar(String.valueOf(car.getJSONObject("images").getInt("id")));
                                                userCars.add(userCar);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            carArrayAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, userCars);
                                            spinner.setAdapter(carArrayAdapter);
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                            super.onFailure(statusCode, headers, responseString, throwable);
                                            Log.d("onFailure : ", String.valueOf(statusCode));
                                        }
                                    });
                                }

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("onFailure : ", String.valueOf(statusCode));
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (userCars.size() > 0) {

            model.setText(userCars.get(i).getModel());
            brand.setText(userCars.get(i).getBrand());
            registerNumber.setText(userCars.get(i).getRegisterNumber());
            charge.setText(String.valueOf(userCars.get(i).getCharge()));
            state_lock = userCars.get(i).isLocked();
            //TODO seatCount
        }
    }

    public void lockCar(int isClicked) {
        sm.lockCar(isClicked);
        lock.setImageResource(R.drawable.ic_lock);
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                R.string.home_lock, Snackbar.LENGTH_LONG)
                .setAction(R.string.home_lock, null).show();
    }

    public void unlockCar(int isClicked) {
        sm.unlockCar(isClicked);
        lock.setImageResource(R.drawable.ic_unlock);
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                R.string.home_unlock, Snackbar.LENGTH_LONG)
                .setAction(R.string.home_unlock, null).show();
    }

    public void startCar(int isClicked) {
        sm.startCar(isClicked);
        start.setImageResource(R.drawable.ic_stop);
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                R.string.home_start, Snackbar.LENGTH_LONG)
                .setAction(R.string.home_start, null).show();
    }

    public void stopCar(int isClicked) {
        sm.stopCar(isClicked);
        start.setImageResource(R.drawable.ic_start);
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                R.string.home_stop, Snackbar.LENGTH_LONG)
                .setAction(R.string.home_stop, null).show();
    }

    public void allWindowsUp(){
        sm.allWindowUp(0);
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                "Montée des vitres", Snackbar.LENGTH_LONG).show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        sm.allWindowUp(1);

                    }
                },
                9000);
    }

    public void allWindowsDown(){
        sm.allWindowDown(0);
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                "Descente des vitres", Snackbar.LENGTH_LONG).show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        sm.allWindowDown(1);

                    }
                },
                9000);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sm.disconnect();
    }
}
