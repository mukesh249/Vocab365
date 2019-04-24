package com.example.advosoft.vocab365.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.advosoft.vocab365.MainActivity;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.fragments.MyApplication;
import com.example.advosoft.vocab365.webutility.RequestCode;


/**
 * Created by Advosoft2 on 12/2/2017.
 */

public class SplashActivity extends Activity {
    private static String TAG = "Splash";
    private Boolean mLocationPermissionsGranted = false;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (getPermission()){
            initApp();
        }
    }

    private boolean getPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
        };

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {

                Log.d(TAG, "getLocationPermission: mLocationPermissionsGranted = false");
                ActivityCompat.requestPermissions(this, permissions, RequestCode.LOCATION_PERMISSION_REQUEST_CODE);
                // Permission Denied
                Toast.makeText(SplashActivity.this, "Some Permission is Denied, please allow permission for that the app can work.", Toast.LENGTH_SHORT)
                        .show();
            }
            else
            {
                ActivityCompat.requestPermissions(this, permissions, RequestCode.LOCATION_PERMISSION_REQUEST_CODE);
                Log.d(TAG, "getLocationPermission: mLocationPermissionsGranted = false");
            }
        }
        else
        {
            mLocationPermissionsGranted = true;
            Log.d(TAG, "getLocationPermission: mLocationPermissionsGranted = true");
        }
        return mLocationPermissionsGranted;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case RequestCode.LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            finish();
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    initApp();
                    //initialize our map
                }
            }
        }
    }

    private void initApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (MyApplication.getUserLoginStatus(getApplicationContext())) {

                    intent = new Intent(SplashActivity.this, DemoHomeActivity.class);
                    // intent = new Intent(SplashActivity.this, DriverHome.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1500);
    }

}
