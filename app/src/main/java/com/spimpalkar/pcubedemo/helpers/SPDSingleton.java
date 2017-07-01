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


public class SPDSingleton
{
    public static final String MY_PREFS_NAME = "mySharedPreference";
    public static final String FCM_PREFS = "fcmPreference";
    ProgressDialog activityIndicator;
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

    public void setFcmStringToSp(String value, String key, Context context)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(FCM_PREFS, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getFcmStringFromSp(String key, Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(FCM_PREFS, Context.MODE_PRIVATE);
        return prefs.getString(key, "N/A");
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

    public  Boolean isBothStringSame(String strI, String strII)
    {
        if (strI.equals(strII))
        {
            return true;
        }else
        {
            return false;
        }

    }

    /* Toast message */
    public void showToastWithText(String txtString, Context contextPassed)
    {
        //TopMessageManager.showWarning(txtString);
    }

    public void showSuccessToastWithText(String txtString, Context contextPassed)
    {
        //TopMessageManager.showSuccess(txtString);
    }

    public void showToastWithTitleText(String txtString, String title)
    {
    }

    // Indicators
    public void  showIndicator(Context passedContext, String displayString, Boolean blockUI)
    {
        activityIndicator = new ProgressDialog(passedContext);
        activityIndicator.setMessage(displayString);
        activityIndicator.setCancelable(blockUI);
        activityIndicator.show();
    }

    public  void hideIndicator()
    {
        activityIndicator.hide();
    }
}