package com.spimpalkar.pcubedemo.helpers;


import org.json.JSONObject;

/**
 * Created by manish on 5/10/17.
 */

public interface WebServiceDelegate {
        void onPreFetch();
        void jSONResponseAfterRequest(JSONObject responseObject);
}
