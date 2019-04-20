package com.example.advosoft.vocab365.activities;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.views.Utils;
import com.example.advosoft.vocab365.webutility.RequestCode;
import com.example.advosoft.vocab365.webutility.WebCompleteTask;
import com.example.advosoft.vocab365.webutility.WebTask;
import com.example.advosoft.vocab365.webutility.WebUrls;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by advosoft on 8/9/2018.
 */

public class ActivityContinueMobile extends BaseActiivty implements WebCompleteTask {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.btn_continue)
    Button btnContinue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_with_mobile);
        ButterKnife.bind(this);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mobile.getText().toString().isEmpty()) {
                    mobile.setError(getResources().getString(R.string.notempty));
                } else if (mobile.getText().toString().length() < 10) {
                    Toast.makeText(ActivityContinueMobile.this, "mobile is not valid", Toast.LENGTH_SHORT).show();
                } else {
                    callapiLoginCheck();
                }
                // startActivity(new Intent(ActivityContinueEmail.this,LoginSecond.class));
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void callapiLoginCheck() {
        HashMap object = new HashMap();
        object.put("mobile", mobile.getText().toString());
        new WebTask(ActivityContinueMobile.this, WebUrls.BASE_URL + "/check_mobile", object, ActivityContinueMobile.this, RequestCode.CODE_Login, 1);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        Log.d("response", response);
        if (taskcode==RequestCode.CODE_Login){
            try{
                JSONObject obj=new JSONObject(response);
                if (obj.optString("data").compareTo("0")==0){
                    startActivity(new Intent(ActivityContinueMobile.this,RegistrationActivity.class).putExtra("mobile",mobile.getText().toString()).putExtra("type","mobile"));
//                    finish();
                } else if (obj.optString("data").compareTo("2")==0) {
                    startActivity(new Intent(ActivityContinueMobile.this,RegistrationActivity.class).putExtra("mobile",mobile.getText().toString()).putExtra("type","mobile"));
//                    finish();
                } else {
                    startActivity(new Intent(ActivityContinueMobile.this,LoginSecond.class).putExtra("mobile",mobile.getText().toString()).putExtra("type","mobile"));
//                    finish();
                }

            }catch (Exception e){

            }
        }

    }
}
