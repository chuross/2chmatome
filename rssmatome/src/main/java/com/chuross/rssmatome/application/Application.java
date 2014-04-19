package com.chuross.rssmatome.application;

import com.activeandroid.ActiveAndroid;
import com.androidquery.callback.BitmapAjaxCallback;
import com.chuross.rssmatome.infrastructure.android.preference.SharedPreferences;

public class Application extends android.app.Application {

    private Config config;

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        sharedPreferences = new SharedPreferences(getApplicationContext());
        config = new Config(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        BitmapAjaxCallback.clearCache();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public Config getConfig() {
        return config;
    }

}
