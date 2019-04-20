package com.example.advosoft.vocab365.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.fragments.MyApplication;
import com.example.advosoft.vocab365.views.Constants;
import com.example.advosoft.vocab365.webutility.RequestCode;
import com.example.advosoft.vocab365.webutility.WebCompleteTask;
import com.example.advosoft.vocab365.webutility.WebTask;
import com.example.advosoft.vocab365.webutility.WebUrls;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

public class Otp extends AppCompatActivity implements WebCompleteTask {
    PinView pinView;
    Button next_btn;
    SmsVerifyCatcher smsVerifyCatcher;
    TextView mobile_tv,resend_otp_tv;
    ImageView img_back_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        pinView = (PinView)findViewById(R.id.pinView);
        img_back_otp = (ImageView)findViewById(R.id.img_back_otp);
        next_btn = (Button)findViewById(R.id.next_btn_security);
        mobile_tv = (TextView)findViewById(R.id.recevied_tv);
        resend_otp_tv = (TextView)findViewById(R.id.resend_otp_tv);
        mobile_tv.setText("Recevied on "+MyApplication.getUserMobile(Constants.UserMobile));

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(pinView.getText())){
                    pinView.setError("Please Enter OTP");
                    pinView.requestFocus();
                }else {
                    CheckOtp();
                }
            }
        });
        resend_otp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResendMethod();
            }
        });
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
//                String code = parseCode(message);//Parse verification code
                Log.d("message:",message );
                Pattern pattern = Pattern.compile("(\\d{4})");
                Matcher matcher = pattern.matcher(message);
                String val = "";
                if (matcher.find()) {
                    System.out.println(matcher.group(1));
                    val = matcher.group(1);
                }
                pinView.setText(val);//set code in edit text
                //then you can send verification code to server
            }
        });

        img_back_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    private void CheckOtp() {
        HashMap object = new HashMap();
        Log.d("Mobile_number",MyApplication.getUserMobile(Constants.UserMobile));
        object.put("mobile", MyApplication.getUserMobile(Constants.UserMobile)+"");
        object.put("otp",pinView.getText()+"");
        new WebTask(Otp.this, WebUrls.BASE_URL + "/check_otp", object, Otp.this, RequestCode.CODE_Otp_check, 1);
    }
    private void ResendMethod() {
        HashMap object = new HashMap();
        object.put("mobile", MyApplication.getUserMobile(Constants.UserMobile)+"");
        new WebTask(Otp.this, WebUrls.BASE_URL + "/resend_otp", object, Otp.this, RequestCode.CODE_Otp_Resend, 1);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (taskcode == RequestCode.CODE_Otp_check){
            try {
                JSONObject obj=new JSONObject(response);
                Log.d("Otp_response",response);
                if (obj.optString("status").compareTo("success")==0){
                    Log.d("data1",obj.optString("data1"));
                    MyApplication.setUserLoginStatus(Otp.this,true);
                    Intent intent = new Intent(Otp.this,DemoHomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(Otp.this,obj.optString("message"),Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (taskcode == RequestCode.CODE_Otp_Resend){
            try {
                JSONObject obj=new JSONObject(response);
                Toast.makeText(Otp.this,obj.optString("message"),Toast.LENGTH_SHORT).show();
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}
