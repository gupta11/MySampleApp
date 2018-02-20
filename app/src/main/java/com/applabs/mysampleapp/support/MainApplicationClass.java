package com.applabs.mysampleapp.support;

import android.app.Application;
import android.content.Context;

public class MainApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        MainApplicationClass.mContext = mContext;
    }

    private static Context mContext;
}
