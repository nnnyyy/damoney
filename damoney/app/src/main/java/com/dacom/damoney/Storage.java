package com.dacom.damoney;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by nnnyy on 2017-10-17.
 */

public class Storage {
    public static void save(Context context, String sKey, String json) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(sKey, json);
        editor.apply();
    }

    public static void saveBoolean(Context context, String sKey, boolean bData) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(sKey, bData);
        editor.apply();
    }

    public static String load(Context context, String sKey) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(sKey, "");
    }

    public static boolean loadBoolean(Context context, String sKey) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(sKey, false);
    }

    public static boolean have(Context context, String sKey) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.contains(sKey);
    }

    public static void delete(Context context, String sKey) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(sKey);
        editor.apply();
    }
}
