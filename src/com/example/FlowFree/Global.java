package com.example.FlowFree;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.preference.Preference;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristján on 22.9.2014.
 */
public class Global {

    public List<Puzzle> puzzles = new ArrayList<Puzzle>();
    public Puzzle puzzle;
    private SharedPreferences prefs;
    SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    private int soundID;
    private boolean loaded = false, running = false;

    ///
    private static Global mInstance = new Global();

    public static Global getInstance() {
        return mInstance;
    }

    private Global() {}

    public void loadMusic(final AudioManager audioManager, Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                boolean sound_on = prefs.getBoolean("sound_on", false);
                if(sound_on) {
                    float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    float volume = actualVolume / maxVolume;
                    soundPool.play(soundID, volume, volume, 1, -1, 1f);
                    running = true;
                }
                loaded = true;
            }
        });
        soundID = soundPool.load(context, R.raw.synth_dream_bells_sync, 1);
    }

    public void playMusic(final AudioManager audioManager, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if(prefs.getBoolean("sound_on", false) && loaded && !running) {
            float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float volume = actualVolume / maxVolume;
            soundPool.play(soundID, volume, volume, 1, -1, 1f);
            running = true;
        }
    }

    public void stopMusic() { if(running) { soundPool.stop(soundID);  running = false; } }

}
