package com.spimpalkar.pcubedemo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.fb_login_buttonID);
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

                                FBUserInfo fbUserInfo = CustomParser.parseFBUserInfo(object);
                                SPDSingleton.getInstance().setStringToSp("true", Constants.isAutoLoginSP, LoginActivity.this);
                                Constants.isAutoLogin = "true";
                                SPDSingleton.getInstance().setStringToSp(fbUserInfo.getUsername(), Constants.userNameSP, LoginActivity.this);
                                SPDSingleton.getInstance().setStringToSp(fbUserInfo.getProfilePicture(), Constants.profilePicSP, LoginActivity.this);
                                Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                                startActivity(intent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
