package com.spimpalkar.pcubedemo.helpers;

import android.content.Context;
import android.util.DisplayMetrics;

// Constant class
public class Constants
{
    public static final  String BASE_SERVER                   = "https://stagingapi.desidime.com/v3/deals.json?type=";
    public static final  String BASE_SERVER_IMAGE             = "https://stagingapi.desidime.com/";
    public static final  String GET_TOP_DEALS                 = "top&deal_view=true";
    public static final  String GET_POPULAR_DEALS             = "top&deal_view=true";

    // Shared Preference Keys
    public static final  String isAutoLoginSP                 = "isAutoLogin";
    // Auto Login Check
    public static String isAutoLogin = "na";
}
