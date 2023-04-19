package com.example.kiki.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kiki.LoginSignup.LoginActivity;
import com.example.kiki.R;
import com.google.firebase.auth.FirebaseAuth;


public class SettingFragment  extends PreferenceFragmentCompat{

    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState , @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);

        Preference logout = getPreferenceManager().findPreference("logout");
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