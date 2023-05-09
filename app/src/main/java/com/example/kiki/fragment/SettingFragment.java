package com.example.kiki.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.kiki.LoginSignup.LoginActivity;
import com.example.kiki.R;
import com.google.firebase.auth.FirebaseAuth;


public class SettingFragment  extends PreferenceFragmentCompat{

    boolean nightMODE;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState , @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);

        Preference logout = getPreferenceManager().findPreference("logout");
        Preference switcher = getPreferenceManager().findPreference("switch");

        sharedPreferences = getContext().getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMODE = sharedPreferences.getBoolean("night", false);
        switcher.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                if (nightMODE){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night",false);
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", true);
                }
                editor.apply();
                return false;
            }
        });

        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                auth.signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
                return false;
            }
        });
    }

}