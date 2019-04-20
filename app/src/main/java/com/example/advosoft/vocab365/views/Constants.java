package com.example.advosoft.vocab365.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Window;
import android.widget.Button;

import com.example.advosoft.vocab365.R;


/**
 * Created by Advosoft2 on 4/28/2017.
 */

public class Constants {
    public static final String bundleArg = "bundle";
    public static final String _id = "_id";
    public static final String title = "title";
    public static final String name = "name";
    public static final String countryCode = "countryCode";
    public static final String data = "data";
    public static final String position = "position";
    public static final String object = "object";
    public static double lat;
    public static double lng;
    public static final String link = "link";
    public static final String foodObj = "foodObj";

    public static final String address = "address";
    public static final String foodId = _id;
    public static final String foodName = "name";
    public static final String foodImg = "image";
    public static final String chefId = _id;
    public static final int imageHight = 420;
    public static final String chefImg = foodImg;
    public static final String created = "created";
    public static final String GCM_ID = "GCM_ID";
    public static final String location = "location";
    public static final int LOCATION = 101;
    public static final int LOCATION2 = 102;
    public static final int LOCATION3 = 103;
    public static final String AccessToken = "accessToken";
    public static final String UserID = "userID";
    public static final String UserName = "userName";
    public static final String UserPic = "userpic";
    public static final String UserNameDOB = "userNameDOB";
    public static final String UserNameGender = "userNameGender";
    public static final String UserEmail = "userEmail";
    public static final String UserMobile = "userMobile";
    public static final String TrickPic ="trickpic";
    public static final String TrickText ="tricktext";
    public static final String UserLocation = "userLocation";
    public static final String USER_MOBILE_NUMBER="USER_MOBILE_NUMBER";
    public static final String USER_ArchiveCount="USER_ArchiveCount";
    public static final String LangId = "lngId";
    public static final String userType = "user_type";
    public static final String USER_TdoCount="USER_TdoCount";
    public static final String Otp = "opt";
    public static final String MIN = "min";
    public static final String Moc_MIN = "min_moc";

    public static final String UserNameLooking = "userNameLooking";
    public static final String totalInviteUsers = "totalInviteUsers";
    public static final String totalDiscardUsers = "totalDiscardUsers";

    public static android.support.v7.app.AlertDialog alert;
    ////




    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
        }
        return false;
    }
    public static void showAlert(Context context, String title, String message) {
        showAlert(context, title, message, null);
    }

    public static void showAlert(Context context, String title, String message, DialogInterface.OnClickListener okListner) {
        if (alert != null && alert.isShowing())
            return;
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context, R.style.AppTheme_AlertDialog);
        builder.setMessage(message).setTitle(title)
                .setCancelable(false)
                .setNegativeButton("OK", okListner);
        alert = builder.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }
    public static Dialog dialogview(Context context, int layout, int style) {
        Dialog dialog = new Dialog(context, style);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(layout);
        return dialog;

    }
}


