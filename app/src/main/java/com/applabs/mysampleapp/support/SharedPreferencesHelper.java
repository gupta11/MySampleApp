package com.applabs.mysampleapp.support;

/* this class is for save or retrieving data in/from shared preference */

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesHelper {

    private static final String APPNAME = "DemoApp";
    private static SharedPreferencesHelper instance;

    private SharedPreferences settings;

    private SharedPreferences.Editor editor;

    private SharedPreferencesHelper() {

        settings = MainApplicationClass.getmContext().getSharedPreferences(APPNAME, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    public static SharedPreferencesHelper getInstance() {

        if (instance == null)
            instance = new SharedPreferencesHelper();
        return instance;
    }

    public boolean getBoolean(String key, boolean defValue) {

        return settings.getBoolean(key, defValue);
    }

    public SharedPreferencesHelper setBoolean(String key, boolean value) {

        editor.putBoolean(key, value);
        editor.commit();

        return this;
    }

    public String getString(String key, String defValue) {

        return settings.getString(key, defValue);
    }

    public SharedPreferencesHelper setString(String key, String value) {

        editor.putString(key, value);
        editor.commit();

        return this;
    }
}
