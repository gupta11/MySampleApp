package com.applabs.mysampleapp.support;

import android.app.Application;
import android.content.Context;

public class MainApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static final String STORAGE_PATH_UPLOADS = "users";
    public static Context getmContext() {
        return mContext;
    }

    public static void setmContext(Context mContext) {
        MainApplicationClass.mContext = mContext;
    }

    public static Context mContext;
}
