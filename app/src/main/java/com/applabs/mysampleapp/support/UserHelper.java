
package com.applabs.mysampleapp.support;

public class UserHelper {

    private static final String IS_LOGIN_TAG = "isLoginDone";
    private static final String PREF_USER_BIO = "userBio";
    private static final String PREF_ID = "userPrefId";
    private static final String PREF_USER_EMAIL = "userEmail";

    public static boolean isRegistered() {
        return SharedPreferencesHelper.getInstance().getBoolean(IS_LOGIN_TAG, false);
    }

    public static void setRegistered(boolean flag) {
        SharedPreferencesHelper.getInstance().setBoolean(IS_LOGIN_TAG, flag);
    }

    public static String getPrefUserBio() {
        return SharedPreferencesHelper.getInstance().getString(PREF_USER_BIO, "");
    }

    public static void setPrefUserBio(String bio) {
        SharedPreferencesHelper.getInstance().setString(PREF_USER_BIO, bio);
    }

    public static String getUserEmail() {
        return SharedPreferencesHelper.getInstance().getString(PREF_USER_EMAIL, "");
    }

    public static void setUserEmail(String emailId) {
        SharedPreferencesHelper.getInstance().setString(PREF_USER_EMAIL, emailId);
    }

    public static String getID() {
        return SharedPreferencesHelper.getInstance().getString(PREF_ID, "");
    }

    public static void setID(String ID) {
        SharedPreferencesHelper.getInstance().setString(PREF_ID, ID);
    }

}