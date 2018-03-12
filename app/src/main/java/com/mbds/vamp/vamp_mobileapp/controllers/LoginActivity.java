package com.mbds.vamp.vamp_mobileapp.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mbds.vamp.vamp_mobileapp.R;
import com.mbds.vamp.vamp_mobileapp.models.User;
import com.mbds.vamp.vamp_mobileapp.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.params.ClientPNames;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.StringEntity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button connexion;
    EditText usernameEditText;
    EditText passwordEditText;

    public static boolean loggedIn = false;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = (EditText) findViewById(R.id.login_username);
        passwordEditText = (EditText) findViewById(R.id.login_password);
        connexion = (Button) findViewById(R.id.login_button);

        connexion.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
        if (loggedIn) {
            String username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF, "");
            String password = sharedPreferences.getString(Config.PASSWORD_SHARED_PREF, "");
            try {
                login(username, password);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        try {
            login(usernameEditText.getText().toString(), passwordEditText.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* Intent intent = new Intent(LoginActivity.this, AppActivity.class);
        startActivity(intent);*/
    }

    private void login(final String username, final String password) throws JSONException, IOException {
        final AsyncHttpClient client = new AsyncHttpClient();
        //RequestParams params = new RequestParams();
        //params.add("username", username);
        //params.add("password", password);

        JSONObject jsonParams = new JSONObject();
        jsonParams.put("username", username);
        jsonParams.put("password", password);

        StringEntity entity = new StringEntity(jsonParams.toString());
        Log.d("entity", entity.getContent().toString());
        Log.d("jsonParams", jsonParams.toString());


        client.setEnableRedirects(true);
        client.getHttpClient().getParams().setParameter(ClientPNames.MAX_REDIRECTS, 3);
        client.post(getApplicationContext(), Config.LOGIN_URL, entity, ContentType.APPLICATION_JSON.getMimeType(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("response", response.toString());
                Log.d("statu code : ", String.valueOf(statusCode));
                try {
                    final String username = response.get("username").toString();
                    final String access_token = response.get("access_token").toString();

                    //Creating a shared preference
                    SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //Adding values to editor
                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                    editor.putString(Config.USERNAME_SHARED_PREF, username);
                    //editor.putString(Config.PASSWORD_SHARED_PREF, password);
                    editor.putString(Config.ACCESS_TOKEN_SHARED_PREF, access_token);
                    //Saving values to editor
                    editor.commit();

                    startActivity(new Intent(LoginActivity.this, AppActivity.class));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("onFailure : ", String.valueOf(statusCode));
                //TODO message en foction de status code
                Toast.makeText(getApplicationContext(), "Nom d'utilisateur ou mot de passe invalide", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
