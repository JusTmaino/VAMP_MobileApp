package com.mbds.vamp.vamp_mobileapp.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mbds.vamp.vamp_mobileapp.R;
import com.mbds.vamp.vamp_mobileapp.controllers.fragments.ControlsFragment;
import com.mbds.vamp.vamp_mobileapp.controllers.fragments.HomeFragment;
import com.mbds.vamp.vamp_mobileapp.controllers.fragments.LocationFragment;
import com.mbds.vamp.vamp_mobileapp.controllers.fragments.ProfileFragment;
import com.mbds.vamp.vamp_mobileapp.models.Car;
import com.mbds.vamp.vamp_mobileapp.models.Profile;
import com.mbds.vamp.vamp_mobileapp.models.User;
import com.mbds.vamp.vamp_mobileapp.utils.Config;
import com.mbds.vamp.vamp_mobileapp.utils.SocketManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.params.ClientPNames;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.StringEntity;


public class AppActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private static final int SPEECH_REQUEST_CODE = 0;
    private TextToSpeech tts;
    private SocketManager sm;
    private HomeFragment hf;
    private ControlsFragment cf;
    private LocationFragment lf;
    private ProfileFragment pf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        sm = new SocketManager();

        hf = new HomeFragment();
        cf = new ControlsFragment();
        lf = new LocationFragment();
        pf = new ProfileFragment();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.vocal_assistant);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Starting Voice Assistant ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                displaySpeechRecognizer();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {

                case 0:
                    return hf;
                case 1:
                    return cf;
                case 2:
                    return lf;
                case 3:
                    return pf;

            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }
    }

    private void logout() {
        //TODO
    }

    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.FRENCH.toString());
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            final String spokenText = results.get(0);
            tts = new TextToSpeech(AppActivity.this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {

                    if (status == TextToSpeech.SUCCESS) {
                        tts.setLanguage(Locale.FRENCH);
                        TextToAction(spokenText);
                    }
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void TextToAction(String text) {
        if (text == null || "".equals(text)) {
            text = "Content not available";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        } else {
            if (text.toLowerCase().contains("démar".toLowerCase())) {
                tts.speak("Démarrage en cours", TextToSpeech.QUEUE_FLUSH, null);
                hf.startCar();

            }
            if (text.toLowerCase().contains("arrê".toLowerCase())) {
                tts.speak("arrêt en cours", TextToSpeech.QUEUE_FLUSH, null);
                hf.stopCar();
            }
            if (text.toLowerCase().contains("déverro".toLowerCase())
                    || text.toLowerCase().contains("ferm".toLowerCase())) {
                tts.speak("déverrouillage en cours", TextToSpeech.QUEUE_FLUSH, null);
                hf.unlockCar();
            }
            if (text.toLowerCase().contains("ferm".toLowerCase())
                    || text.toLowerCase().contains("verro".toLowerCase())) {
                tts.speak("verrouillage en cours", TextToSpeech.QUEUE_FLUSH, null);
                hf.lockCar();
            }
            if (text.toLowerCase().contains("vitr".toLowerCase())
                    && text.toLowerCase().contains("les".toLowerCase())
                    && text.toLowerCase().contains("tou".toLowerCase())
                    && (text.toLowerCase().contains("monte".toLowerCase())
                    || text.toLowerCase().contains("lév".toLowerCase()))) {
                tts.speak("monte des vitre en cours", TextToSpeech.QUEUE_FLUSH, null);
                sm.allWindowUp(1);
            }
            if (text.toLowerCase().contains("vitr".toLowerCase())
                    && text.toLowerCase().contains("les".toLowerCase())
                    && text.toLowerCase().contains("tou".toLowerCase())
                    && (text.toLowerCase().contains("desc".toLowerCase())
                    || text.toLowerCase().contains("bais".toLowerCase()))) {
                tts.speak("descente des vitre en cours", TextToSpeech.QUEUE_FLUSH, null);
                sm.allWindowDown(1);
            }
            //TODO complete the other cases
        }


    }

    @Override
    protected void onPause() {

        if (tts != null) {

            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }


}
