package com.spimpalkar.pcubedemo.helpers;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by QMCPL on 08/05/17.
 */

public class ConstantsCustom
{
//    public static final  String baseServer                      = "http://192.168.1.193:3000/api/";  /*Atish Local Url*/
    public static final  String baseServer                      = "http://yogastudioapp.herokuapp.com/api/";
    public static final  String baseServerImage                 = "http://yogastudioapp.herokuapp.com";
    public static final  String serviceNameLogin                = "login";
    public static final  String serviceNameLogout               = "logout";
    public static final  String serviceNameProductsCategory     = "product_categories";
    public static final  String serviceNameProducts             = "products";
    public static final  String serviceProductsByCategory       = "productsbycategory";
    public static final  String serviceNamePackages             = "packages";
    public static final  String serviceMemberProductHistory     = "orders/member/";
    public static final  String serviceTrainerProductHistory    = "orders/trainer/";
    public static final  String serviceOrderProduct             = "orders/";
    public static final  String serviceNameFitnessTips          = "fitness_tips";
    public static final  String serviceNameBatchTaskDetails     = "tasks/";
    public static final  String serviceNameUpdateUserProfile    = "user/";
    public static final  String serviceNameUpdateTask           = "tasks/";
    public static final  String serviceNameAttendence           = "attendances/";
    public static final String serviceNameGuest                 = "guests/";
    public static final String serviceNameDisclaimer            = "guests/disclaimer/";

    public static final  String deviceType                      = "android";

    // Shared Preference Keys
    public static final  String isAutoLoginSP                   = "isAutoLogin";
    public static final  String isMemberSP                      = "isMember";
    public static final  String isGuestUserSP                   = "isGuest";
    public static final  String accessTokenSP                   = "accessToken";
    public static final  String idSP                            = "id";
    public static final  String emailSP                         = "emailID";
    public static final  String passwordSP                      = "password";
    public static final  String nameSP                          = "name";
    public static final  String addressSP                       = "address";
    public static final  String contactSP                       = "contact";
    public static final  String weightSP                        = "weight";
    public static final  String heightSP                        = "height";
    public static final  String profilePicSP                    = "profilePic";
    public static final  String citySP                            = "city";
    public static final  String qualificationSP                 = "qualification";
    public static final  String salarySP                        = "salary";
    public static final  String uniqueIdSP                      = "uniqueId";
    public static final  String fcmIdSP                         = "fcmId";

    // Access Token
    public  static String accessToken                          = "na";

    // Auto Login Check
    public static String isAutoLogin = "na";
    public static String isMember = "na";
    public static String isGuestUser = "na";

    // Variable to know that the fitness menu is clicked from menu
//    public static boolean isFitnessClickedFromMenu = false;
    public static boolean isProductOrdered = false;
    public static String productCategoryID = "";

    // Device
    public  static  float getDeviceWidthInPx(Context mContext)
    {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / displayMetrics.density;
    }
}
