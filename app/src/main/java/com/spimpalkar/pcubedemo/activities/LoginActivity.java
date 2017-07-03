package com.spimpalkar.pcubedemo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.spimpalkar.pcubedemo.R;
import com.spimpalkar.pcubedemo.helpers.Constants;
import com.spimpalkar.pcubedemo.helpers.CustomParser;
import com.spimpalkar.pcubedemo.helpers.SPDSingleton;
import com.spimpalkar.pcubedemo.models.FBUserInfo;

import org.json.JSONObject;

import java.io.Serializable;

public class LoginActivity extends BaseActivity {

    CallbackManager callbackManager;
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*Initialize toolbar from Base Activity*/
        initToolBar();
        toolBarTitle.setText(getResources().getString(R.string.login));

        /*Initialise views and methods*/
        initData();

        /*register facebook login callback*/
        fbLoginCallback();

    }

    private void initData() {
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.fb_login_buttonID);
    }
    
    private void fbLoginCallback() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {

                                /*Save the login data to preferences*/
                                saveDataToPreferences(object);
                                /*Traverse to Navigation drawer activity*/
                                SPDSingleton.getInstance().presentNavigationDrawerActivity(LoginActivity.this);
                                finish();
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void saveDataToPreferences(JSONObject object) {
        FBUserInfo fbUserInfo = CustomParser.parseFBUserInfo(object);
        SPDSingleton.getInstance().setStringToSp("true", Constants.isAutoLoginSP, LoginActivity.this);
        /*Static variable to get the auto logged in status, so that user don't have to everytime get
        * the data from preferences*/
        Constants.isAutoLogin = "true";
        SPDSingleton.getInstance().setStringToSp(fbUserInfo.getUsername(), Constants.userNameSP, LoginActivity.this);
        SPDSingleton.getInstance().setStringToSp(fbUserInfo.getProfilePicture(), Constants.profilePicSP, LoginActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        loginButton.setVisibility(View.GONE);
    }
}
