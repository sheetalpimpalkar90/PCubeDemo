package com.spimpalkar.pcubedemo.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.spimpalkar.pcubedemo.R;
import com.spimpalkar.pcubedemo.helpers.Constants;
import com.spimpalkar.pcubedemo.helpers.SPDSingleton;

/**
 * Created by intel on 11/05/2017.
 */

public class SplashScreenActivity extends BaseActivity{

    // Splash screen timer
    private static int SPLASH_SCREEN_TIME_OUT = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*Getting the login status and token from preferences to check user is auto logged in or not*/
        getAutoLoginStatus();
        /*Splash screen visibility timer*/
        initScreenTimer();
    }

    private void getAutoLoginStatus() {
        Constants.isAutoLogin = SPDSingleton.getInstance().getStringFromSp(Constants.isAutoLoginSP,
                SplashScreenActivity.this);
    }

    private void initScreenTimer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*If auto logged in then redirect to Landing screen else login screen*/
                if(Constants.isAutoLogin.equalsIgnoreCase("true")){
                    SPDSingleton.getInstance().presentNavigationDrawerActivity(SplashScreenActivity.this);
                    finish();
                }else{
                    SPDSingleton.getInstance().presentLoginPage(SplashScreenActivity.this);
                    finish();
                }
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}
