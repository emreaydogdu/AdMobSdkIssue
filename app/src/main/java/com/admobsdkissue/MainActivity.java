package com.admobsdkissue;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // THIS IS FOR SWITCHING DayNight-Mode
        if (!getMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshFragment();

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        findViewById(R.id.switchDarkMode).setOnClickListener(v -> {
            changePreference();
            recreate();
        });

        findViewById(R.id.switchFragment).setOnClickListener(v -> {
            Toast.makeText(this, "Fragment refreshed.", Toast.LENGTH_SHORT).show();
            refreshFragment();
        });

    }

    private void changePreference(){
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("mode", !getMode()).apply();
    }

    private boolean getMode(){
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("mode", false);
    }

    private void refreshFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, new FragmentA());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}