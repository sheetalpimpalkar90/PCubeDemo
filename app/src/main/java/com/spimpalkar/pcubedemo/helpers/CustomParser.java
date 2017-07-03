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
 * Created by manish on 5/14/17.
 */

public class CustomParser {
    /**
     * Private constructor to prevent instantiation
     */
    private CustomParser() {
    }

    public static Boolean checkIfSuccess(JSONObject object, Resources resource) {
        if (object.has("meta")) {
            JSONObject objResp = (JSONObject) object.opt("meta");

            if (objResp.opt("status").equals(resource.getString(R.string.server_response_success)) && (boolean) objResp.opt("isData")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static Boolean checkIfLogOutSuccess(JSONObject object, Resources resource) {
        if (object.has("meta")) {
            JSONObject objResp = (JSONObject) object.opt("meta");

            if (objResp.opt("status").equals(resource.getString(R.string.server_response_success))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String getErrorMessage(JSONObject object, Resources resource) {
        if (object.has("meta")) {
            JSONObject objResp = (JSONObject) object.opt("meta");
            if (objResp.has(resource.getString(R.string.server_response_error))) {
                return (String) objResp.opt(resource.getString(R.string.server_response_error));
            } else if (objResp.has(resource.getString(R.string.server_response_message))) {
                return (String) objResp.opt(resource.getString(R.string.server_response_message));
            } else {
                return "Failed";
            }
        } else {
            return "Failed";
        }
    }

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

    public static DealModel parseDeals(JSONObject responseObject){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(String.valueOf(responseObject), DealModel.class);

    }
}
