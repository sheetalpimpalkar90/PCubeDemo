package com.spimpalkar.pcubedemo.helpers;

import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.spimpalkar.pcubedemo.R;
import com.spimpalkar.pcubedemo.models.DealModel;
import com.spimpalkar.pcubedemo.models.FBUserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sheetal.pimpalkar on 6/30/2017.
 */

public class CustomParser {
    /**
     * Private constructor to prevent instantiation
     */
    private CustomParser() {
    }

    /*Parse facebook user info from Json*/
    public static FBUserInfo parseFBUserInfo(JSONObject object) {

        FBUserInfo fbUserInfo = new FBUserInfo();
        try {
            fbUserInfo.setUsername(object.getString("name"));
            fbUserInfo.setProfilePicture(object.getJSONObject("picture").getJSONObject("data").getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fbUserInfo;
    }

    /*Parsing of top and popular deal data from json using Gson*/
    public static DealModel parseDeals(JSONObject responseObject){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(String.valueOf(responseObject), DealModel.class);

    }
}
