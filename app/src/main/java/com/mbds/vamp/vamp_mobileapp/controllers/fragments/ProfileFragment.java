package com.mbds.vamp.vamp_mobileapp.controllers.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mbds.vamp.vamp_mobileapp.R;
import com.mbds.vamp.vamp_mobileapp.models.Car;
import com.mbds.vamp.vamp_mobileapp.models.User;
import com.mbds.vamp.vamp_mobileapp.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class ProfileFragment extends Fragment {

    TextView firstnameTV, lastnameTV, emailTV, phoneTV, addressTV ;

    private String username;
    private String access_token;
    private User loggedUser;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lastnameTV = (TextView) getActivity().findViewById(R.id.lastname_tv);
        emailTV = (TextView) getActivity().findViewById(R.id.email_tv);
        phoneTV = (TextView) getActivity().findViewById(R.id.phone_tv);
        addressTV = (TextView) getActivity().findViewById(R.id.address_tv);

        //Fetching username and token from shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF, "Not Available");
        access_token = sharedPreferences.getString(Config.ACCESS_TOKEN_SHARED_PREF, "Not Available");
        loggedUser = new User();
        getUserDetails();

    }




    private void getUserDetails() {
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

                                loggedUser.setUsername(user.getString("username"));
                                loggedUser.setFullName(user.getString("fullName"));
                                loggedUser.setTel(user.getString("tel"));
                                loggedUser.setEmail(user.getString("mail"));
                                //loggedUser.setbDay(new Date());
                                //loggedUser.setAvatar();

                                lastnameTV.setText(loggedUser.getFullName());
                                emailTV.setText(loggedUser.getEmail());
                                phoneTV.setText(loggedUser.getTel());
                                //TODO address
                                // addressTV.setText(loggedUser.get);

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
}
