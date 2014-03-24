package com.jimboulter.menextandroid;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Jim Boulter on 3/23/14.
 * Settings for MeNext
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.xml.preferences);
    }
}
