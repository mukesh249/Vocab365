package com.example.advosoft.vocab365.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.advosoft.vocab365.Model.DateItem;
import com.example.advosoft.vocab365.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Advosoft2 on 6/27/2017.
 */

public class MyApplication extends MultiDexApplication {
    public static MyApplication myApplication = null;
    private static Context ctx;
    private String CANCER = "Vocab";
    public static SharedPreferences sp;
    private double currentLatitude;
    private double currentLongitude;
    private int hight;
    private static final String isLogin = "loginVocab";
    public static ImageLoader loader;
    private static final String profileImage = "profileImageVocab";

    public static final String filter_friends="filterfrindsVocab";
    public static final String filter_partner="filterpartnerVocab";
    public static final String filter_categor="filtercategoryVocab";
    public static final String filter_subcategory="filtersubcategoryVocab";
    public static final String filter_location="filterlocationVocab";
    public static final String filter_lat="filterlatVocab";
    public static final String filter_long="filterlongVocab";
    public static final String filter_range="filterrangeVocab";
    public static final String filter_startdate="startdateVocab";
    public static final String filter_enddate="enddateVocab";
    public static final String contrycode="contrycodeVocab";
    public static final String server_updatelist="serverupdatelistVocab";
    public static final String practice="practice";
    public static final String test="test";
    public static ArrayList<DateItem> arrDateItemwithBlank;
    public static ArrayList<DateItem> arrDateItem;
    public static String current_date="";
    public static String current_fragment="";
    public static TextToSpeech tts;


    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = new MyApplication();
        MultiDex.install(this);
//        Crittercism.initialize(getApplicationContext(), "9e06313bc11a42fbb3b9e92051213e9900555300");
        ctx = getApplicationContext();
        sp = ctx.getSharedPreferences(CANCER, 0);
        loader = ImageLoader.getInstance();
        arrDateItemwithBlank=new ArrayList<>();
        arrDateItem = new ArrayList();
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != -1) {
                    try {
                        MyApplication.tts.setLanguage(Locale.getDefault());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        loader.init(ImageLoaderConfiguration.createDefault(this));


    }




    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(0).cacheInMemory(true)
            .showImageOnFail(0).cacheOnDisc(true).considerExifParams(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;

    }

    public static void showMessage(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }
    public static void showMessageOtp(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }
    public void hideSoftKeyBoard(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            // check if no view has focus:
            View v = activity.getCurrentFocus();
            if (v == null)
                return;

            inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MyApplication getInstance() {
        if (myApplication == null) {
            myApplication = new MyApplication();
        }


        return myApplication;
    }
    public double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }



    public static void setUserLocation(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getUserLocation(String type) {
        String e = sp.getString(type, "");
        return e;
    }



    public double getCurrentLatitude() {
        return currentLatitude;
    }
    public int getImageHight() {
        return hight;
    }

    public void getImageHight(int hight) {
        this.hight = hight;
    }
    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }
    public static String getUserData(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static void saveUserData(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static void setSid(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getSid(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static void setUserID(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getUserID(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static String getlangID(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static void setotp(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getotp(String type) {
        String e = sp.getString(type, "");
        return e;
    }

    public static void setUserType(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getUserType(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static void setMIN(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getMIN(String type) {
        String e = sp.getString(type, "");
        return e;
    }


    public static void setLangId(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getUserName(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static void setUserName(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static void setTrickName(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getTrickName(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static void setUserPic(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static void setTrickPic(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getTrickPic(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static String getUserPic(String type) {
        String e = sp.getString(type, "");
        return e;
    }


    public static String getUserDOB(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static void setUserDOB(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }

    public static String getUserGender(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static void setUserGender(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static void setUserEmail(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getUserEmail(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static void setUserMobile(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getUserMobile(String type) {
        String e = sp.getString(type, "");
        return e;
    }


    public static void setUserArchiveCount(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getUserArchiveCount(String type) {
        String e = sp.getString(type, "");
        return e;
    }

    public static String getProfileImage(Context context,String s) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(profileImage, "");
    }

    public static void setProfileImage(Context context, String profile) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(profileImage, profile);
        editor.commit();
    }


    public static Boolean getUserLoginStatus(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(isLogin, false);
    }

    public static void setUserLoginStatus(Context context, Boolean isUserLogin) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(isLogin, isUserLogin);
        editor.commit();
    }
    /*add filter*/
    public static Boolean getFilter_friends(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(filter_friends, true);
    }

    public static void setFilter_friends(Context context, Boolean friends) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(filter_friends, friends);
        editor.commit();
    }

    public static Boolean getFilter_partner(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(filter_partner, true);
    }

    public static void setFilter_partner(Context context, Boolean friends) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(filter_partner, friends);
        editor.commit();
    }

    public static String getFilter_categor(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(filter_categor, "");
    }

    public static void setFilter_categor(Context context, String friends) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(filter_categor, friends);
        editor.commit();
    }

    public static String getFilter_location(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(filter_location, "");
    }

    public static void setFilter_location(Context context, String friends) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(filter_location, friends);
        editor.commit();
    }

    public static String getFilter_lat(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(filter_lat, "");
    }

    public static void setFilter_lat(Context context, String friends) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(filter_lat, friends);
        editor.commit();
    }

    public static String getFilter_long(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(filter_long, "");
    }

    public static void setFilter_long(Context context, String friends) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(filter_long, friends);
        editor.commit();
    }

    public static String getFilter_range(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(filter_range, "");
    }

    public static void setFilter_range(Context context, String friends) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(filter_range, friends);
        editor.commit();
    }

    public static String getFilter_startdate(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(filter_startdate, "");
    }

    public static void setFilter_startdate(Context context, String friends) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(filter_startdate, friends);
        editor.commit();
    }

    public static String getFilter_enddate(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(filter_enddate, "");
    }

    public static void setFilter_enddate(Context context, String friends) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(filter_enddate, friends);
        editor.commit();
    }

    public static String getFilter_subcategory(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(filter_subcategory, "");
    }

    public static void setFilter_subcategory(Context context, String friends) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(filter_subcategory, friends);
        editor.commit();
    }

    public static void setContrycode(Context context, String data){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(contrycode, data);
        editor.commit();
    }
    public static String getContrycode(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(contrycode, "");
    }
    public static void setServer_updatelist(Context context, String data){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(server_updatelist, data);
        editor.commit();
    }
    public static String getServer_updatelist(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(server_updatelist, "");
    }


    public static String getScanCode(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static void setScanCode(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }

    public static String getlat(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static void setlat(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }


    public static String getlng(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static void setlng(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static void setUserTdoCount(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getUserTdoCount(String type) {
        String e = sp.getString(type, "");
        return e;
    }

    public static void settotalInviteUsers(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String gettotalInviteUsers(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static void settotalAttendUsers(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String gettotalAttendUsers(String type) {
        String e = sp.getString(type, "");
        return e;
    }

    public static void settotalDiscardUsers(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String gettotalDiscardUsers(String type) {
        String e = sp.getString(type, "");
        return e;
    }

    public static void setPractice(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getPractice(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static void setTest(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getTest(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static void setAutoTrans(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getAutoTrans(String type) {
        String e = sp.getString(type, "");
        return e;
    }

    public static void setReverseTrans(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getReverseTrans(String type) {
        String e = sp.getString(type, "");
        return e;
    }

    public static void setLoginstatus(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getLoginstatus(String type) {
        String e = sp.getString(type, "");
        return e;
    }

    public static void setContryCodenew(String type, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type, value);
        editor.commit();
    }
    public static String getContrycodenew(String type) {
        String e = sp.getString(type, "");
        return e;
    }
    public static void setquaCurrent(String type, String qua_currect[]){
        SharedPreferences.Editor editor = sp.edit();
//        editor.putString(type,qua_currect);
    }

    public static void showFullImage(Context context, String imagePath) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.full_image);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();
        ImageView image = (ImageView) dialog.findViewById(R.id.image);

//        Glide.with(context)
//                .load(imagePath)
//                .apply(new RequestOptions().placeholder(android.R.drawable.ic_media_play).error(android.R.drawable.ic_media_play))
//                .into(image);

        Glide.with(context)
                .load(imagePath).placeholder(android.R.drawable.ic_media_play)
                .into(image);
        image.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
    public static void hideKeyBoard(Activity activity){
        InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        Log.e("hide kb", "1");
        if (view != null) {
            Log.e("hide kb", "2");
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
