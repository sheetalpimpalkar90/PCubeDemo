package com.spimpalkar.pcubedemo.helpers;

import android.content.Context;
import android.util.DisplayMetrics;

// Constant class
public class Constants
{
    public static final  String BASE_SERVER                   = "https://stagingapi.desidime.com/v3/deals.json?type=";
    public static final  String GET_TOP_DEALS                 = "top&deal_view=true";
    public static final  String GET_POPULAR_DEALS             = "top&deal_view=true";

    // Shared Preference Keys
    public static final  String isAutoLoginSP                 = "isAutoLogin";
    public static final  String userNameSP                    = "name";
    public static final  String profilePicSP                  = "picture";
    // Auto Login Check
    public static String isAutoLogin = "na";

    public static String accessToken = "0c50c23d1ac0ec18eedee20ea0cdce91ea68a20e9503b2ad77f44dab982034b0";
}
