package com.spimpalkar.pcubedemo.helpers;


/**
 * Created by sheetal.pimpalkar on 6/30/2017.
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.spimpalkar.pcubedemo.activities.LoginActivity;
import com.spimpalkar.pcubedemo.activities.NavigationDrawerActivity;
import com.spimpalkar.pcubedemo.activities.SplashScreenActivity;

import java.util.ArrayList;


public class SPDSingleton {
    public static final String MY_PREFS_NAME = "login_preferences";
    ProgressDialog progressDialog;
    private static SPDSingleton spdSingleton;

    public static SPDSingleton getInstance() {
        if (spdSingleton == null) {
            spdSingleton = new SPDSingleton();
        }
        return spdSingleton;
    }

    /*Setting string value to shared preferences*/
    public void setStringToSp(String value, String key, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    /*Setting int value to shared preferences*/
    public void setIntegerToSp(int value, String key, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /*Getting String value from preferences*/
    public String getStringFromSp(String key, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(key, "N/A");
    }

    /*Getting int value from preferences*/
    public int getIntegerFromSp(String key, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }

    /*Clear preferences*/
    public void clearDataFromSp(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().commit();
    }

    /* Short toast message */
    public void showShortToast(String txtString, Context contextPassed) {
        Toast.makeText(contextPassed, txtString, Toast.LENGTH_SHORT).show();
    }

    /* Long toast message*/
    public void showLongToast(String txtString, Context contextPassed) {
        Toast.makeText(contextPassed, txtString, Toast.LENGTH_LONG).show();
    }

    // Indicators
    public void showProgressDialog(Context passedContext, String displayString, Boolean blockUI) {
        progressDialog = new ProgressDialog(passedContext);
        progressDialog.setMessage(displayString);
        progressDialog.setCancelable(blockUI);
        progressDialog.show();
    }

    public void hideProgressDialog() {
        progressDialog.hide();
    }


    /*Presenting Login screen using intent*/
    public void presentLoginPage(Context passedContext) {
        Intent activityChangeIntent = new Intent(passedContext, getLoginClass());
        passedContext.startActivity(activityChangeIntent);
    }

    private Class<LoginActivity> getLoginClass() {
        return LoginActivity.class;
    }

    /*Presenting navigation drawer screen using intent*/
    public void presentNavigationDrawerActivity(Context passedContext) {
        Intent activityChangeIntent = new Intent(passedContext, getNavigationDrawerClass());
        passedContext.startActivity(activityChangeIntent);
    }

    private Class<NavigationDrawerActivity> getNavigationDrawerClass() {
        return NavigationDrawerActivity.class;
    }
}