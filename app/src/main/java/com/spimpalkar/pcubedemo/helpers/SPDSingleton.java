package com.spimpalkar.pcubedemo.helpers;

/**
 * Created by QMCPL on 08/05/17.
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


public class SPDSingleton
{
    public static final String MY_PREFS_NAME = "login_preferences";
    ProgressDialog progressDialog;
    private static SPDSingleton spdSingleton;

    public static SPDSingleton getInstance() {
        if(spdSingleton == null){
            spdSingleton = new SPDSingleton();
        }
        return spdSingleton;
    }

    public void setStringToSp(String value, String key, Context context)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void setIntegerToSp(int value, String key, Context context)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void setDoubleToSp(double value, String key, Context context)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putFloat(key, (float) value);
        editor.apply();
    }

    public String getStringFromSp(String key, Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(key, "N/A");
    }

    public int getIntegerFromSp(String key, Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }

    public Double getDoubleFromSp(String key, Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return Double.valueOf(prefs.getFloat(key, 0));
    }

    public void clearDataFromSp(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().commit();
    }

    /* Short toast message */
    public void showShortToast(String txtString, Context contextPassed)
    {
        Toast.makeText(contextPassed, txtString, Toast.LENGTH_SHORT).show();
    }

    /* Long toast message*/
    public void showLongToast(String txtString, Context contextPassed)
    {
        Toast.makeText(contextPassed, txtString, Toast.LENGTH_LONG).show();
    }

    // Indicators
    public void  showProgressDialog(Context passedContext, String displayString, Boolean blockUI)
    {
        progressDialog = new ProgressDialog(passedContext);
        progressDialog.setMessage(displayString);
        progressDialog.setCancelable(blockUI);
        progressDialog.show();
    }

    public  void hideProgressDialog()
    {
        progressDialog.hide();
    }
}