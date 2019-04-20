package com.example.advosoft.vocab365.wrapper;

import android.Manifest;

/**
 * Created by Advosoft2 on 4/13/2017.
 */

public class Config {
    public static final String APP_PACKAGE_NAME = "com.scopp.scoppapp";
    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String RECEIVER = APP_PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = APP_PACKAGE_NAME + ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = APP_PACKAGE_NAME + ".LOCATION_DATA_EXTRA";
    public static final int PERMISSIONS_REQUEST_LOCATION = 11;
    public static String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
}
