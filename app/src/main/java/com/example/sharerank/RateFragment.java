package com.example.sharerank;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class RateFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}