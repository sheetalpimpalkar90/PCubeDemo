package com.spimpalkar.pcubedemo.helpers;

import android.content.res.Resources;

import com.spimpalkar.pcubedemo.R;

import org.json.JSONObject;

/**
 * Created by manish on 5/14/17.
 */

public class CustomParser
{
    /**
     * Private constructor to prevent instantiation
     */
    private CustomParser() {}

    public static Boolean checkIfSuccess(JSONObject object, Resources resource)
    {
        if(object.has("meta"))
        {
            JSONObject objResp = (JSONObject) object.opt("meta");

            if(objResp.opt("status").equals(resource.getString(R.string.server_response_success)) && (boolean)objResp.opt("isData"))
            {
                return true;
            }else
            {
                return false;
            }
        }else
        {
            return false;
        }
    }

    public static Boolean checkIfLogOutSuccess(JSONObject object, Resources resource)
    {
        if(object.has("meta"))
        {
            JSONObject objResp = (JSONObject) object.opt("meta");

            if(objResp.opt("status").equals(resource.getString(R.string.server_response_success)))
            {
                return true;
            }else
            {
                return false;
            }
        }else
        {
            return false;
        }
    }

    public static String getErrorMessage(JSONObject object, Resources resource)
    {
        if(object.has("meta"))
        {
            JSONObject objResp = (JSONObject) object.opt("meta");
            if(objResp.has(resource.getString(R.string.server_response_error)))
            {
                return (String) objResp.opt(resource.getString(R.string.server_response_error));
            }else if (objResp.has(resource.getString(R.string.server_response_message))){
                return (String) objResp.opt(resource.getString(R.string.server_response_message));
            }else{
                return "Failed";
            }
        }else
        {
            return "Failed";
        }
    }
}
