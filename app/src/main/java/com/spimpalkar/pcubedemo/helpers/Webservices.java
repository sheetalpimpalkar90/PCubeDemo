package com.spimpalkar.pcubedemo.helpers;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by manish on 5/10/17.
 */

public class Webservices
{
    private WebServiceDelegate  webserviceObject; // Instance of interface
    private Activity    passedActivity;

    public Webservices(Activity getActivity, WebServiceDelegate getWeservice)
    {
        webserviceObject = getWeservice;
        passedActivity   = getActivity;
    }

    public  void startPostRequest(String serviceName, JSONObject params)
    {
        webserviceObject.onPreFetch();
        String finalURL = ConstantsCustom.baseServer+serviceName;
        System.out.print(finalURL);

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.POST, finalURL, params, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        // the response is already constructed as a JSONObject!
                        Log.i("Response >> ", ""+response);
                        webserviceObject.jSONResponseAfterRequest(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print(error);

                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("status", "fail");
                            obj.put("errors", "Request cannot initiate");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        webserviceObject.jSONResponseAfterRequest(obj);
                    }
                });

        Volley.newRequestQueue(passedActivity).add(jsonRequest);

    }

    public  void startPatchRequest(String serviceName, JSONObject params)
    {
        webserviceObject.onPreFetch();
        String finalURL = ConstantsCustom.baseServer+serviceName;
        System.out.print(finalURL);

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.PATCH, finalURL, params, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        // the response is already constructed as a JSONObject!
                        Log.i("Response >> ", ""+response);
                        webserviceObject.jSONResponseAfterRequest(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print(error);

                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("status", "fail");
                            obj.put("errors", "Request cannot initiate");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        webserviceObject.jSONResponseAfterRequest(obj);
                    }
                });

        Volley.newRequestQueue(passedActivity).add(jsonRequest);

    }

    public  void startPostRequestWithAuthorization(String serviceName, JSONObject params)
    {
        webserviceObject.onPreFetch();
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.POST, ConstantsCustom.baseServer+serviceName, params, new Response.Listener<JSONObject>()
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
                    headers.put("Authorization", "Bearer " + ConstantsCustom.baseServer);
                    return headers;
        }};

        Volley.newRequestQueue(passedActivity).add(jsonRequest);

    }
/*  @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + ConstantsCustom.baseServer);
                return headers;
            };*/
    public void startGetRequest(String serviceName)
    {
        webserviceObject.onPreFetch();
        Log.i("URL >>", ConstantsCustom.baseServer+serviceName);
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, ConstantsCustom.baseServer+serviceName, null, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        // the response is already constructed as a JSONObject!
                        Log.i("RESPONSE ====>>>> ", ""+response);
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
                });

        Volley.newRequestQueue(passedActivity).add(jsonRequest);

    }

    public void startGetRequestWithAuthorization(String serviceName)
    {
        webserviceObject.onPreFetch();
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, ConstantsCustom.baseServer+serviceName, null, new Response.Listener<JSONObject>()
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
                        headers.put("Authorization", "Bearer " + ConstantsCustom.accessToken);
                        return headers;
        }};

        Volley.newRequestQueue(passedActivity).add(jsonRequest);

    }

    public void startGetArrayRequest(String serviceName)
    {
        webserviceObject.onPreFetch();
        String finalURL = ConstantsCustom.baseServer+serviceName;
        Log.i("GET",finalURL);
        JsonArrayRequest jsonRequest = new JsonArrayRequest
                (Request.Method.GET, ConstantsCustom.baseServer+serviceName, null, new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        // the response is already constructed as a JSONObject!
                        System.out.print("Response is");
                        System.out.print(response);
                        webserviceObject.jSONArrayResponseAfterRequest(response);
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        JSONArray obj = new JSONArray();
                        webserviceObject.jSONArrayResponseAfterRequest(obj);
                    }
                });

        Volley.newRequestQueue(passedActivity).add(jsonRequest);

    }


    public void startDeleteRequestWithAuthorization(String serviceName, JSONObject params)
    {
        webserviceObject.onPreFetch();
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.DELETE, ConstantsCustom.baseServer+serviceName, params, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        // the response is already constructed as a JSONObject!
                        Log.i("Response >> ", ""+response);
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
//            headers.put("Authorization", "Bearer " + ConstantsCustom.accessToken);
            headers.put("token", ConstantsCustom.accessToken);
            return headers;
        }};

        Volley.newRequestQueue(passedActivity).add(jsonRequest);

    }
}
