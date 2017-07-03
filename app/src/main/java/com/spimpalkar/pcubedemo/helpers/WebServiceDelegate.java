package com.spimpalkar.pcubedemo.helpers;


import org.json.JSONObject;

/*Volley webservice callbacks*/
public interface WebServiceDelegate {
        void onPreFetch();
        void jSONResponseAfterRequest(JSONObject responseObject);
}
