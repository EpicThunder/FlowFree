package com.example.FlowFree;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by Kristján on 22.9.2014.
 */
public class SettingsActivity extends PreferenceActivity {
    CheckBoxPreference pref;
    private Global mGlobal = Global.getInstance();
    private SharedPreferences prefs;
    private Context context = this;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        /*pref = (CheckBoxPreference) findPreference("sound_on");

        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference) {
                boolean sound_on = prefs.getBoolean("sound_on", false);
                if(sound_on) mGlobal.playMusic((AudioManager) getSystemService(AUDIO_SERVICE), context);
                else mGlobal.stopMusic();
                return true;
            }
        });*/
    }
}