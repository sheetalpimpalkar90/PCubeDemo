package com.spimpalkar.pcubedemo.helpers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sheetal on 5/10/17.
 */

public class Webservices
{
    private WebServiceDelegate  webserviceObject; // Instance of interface
    private Activity    passedActivity;
    private FragmentActivity fragmentActivity;

    public Webservices(Activity getActivity, FragmentActivity getFragmentActivity, WebServiceDelegate getWeservice)
    {
        webserviceObject = getWeservice;
        passedActivity   = getActivity;
        fragmentActivity = getFragmentActivity;

    }

    // Get Request With Authorization Header
    public void startGetRequestWithAuthorization(String serviceName, JSONObject params)
    {
        webserviceObject.onPreFetch();

        String finalURL = Constants.BASE_SERVER + serviceName;

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, finalURL, params, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        // the response is already constructed as a JSONObject!
                        System.out.print("Response is");
                        System.out.print(response);
                        webserviceObject.jSONResponseAfterRequest(response);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("status", "fail");
                            obj.put("errors", "Request cannot initiate");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        webserviceObject.jSONResponseAfterRequest(obj);
                    }
                }){@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("accept", "text/javascript");
            headers.put("Content-Type", "application/json");
            headers.put("X-Desidime-Client", Constants.accessToken); // passing access token

            return headers;
        }};

        if (passedActivity != null) // This is to handle if the passed activity is a fragment activity
        {
            Volley.newRequestQueue(passedActivity).add(jsonRequest);
        }else
        {
            Volley.newRequestQueue(fragmentActivity).add(jsonRequest);
        }
    }

}
