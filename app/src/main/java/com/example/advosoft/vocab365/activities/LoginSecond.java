package com.example.advosoft.vocab365.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.fragments.MyApplication;
import com.example.advosoft.vocab365.views.Constants;
import com.example.advosoft.vocab365.webutility.RequestCode;
import com.example.advosoft.vocab365.webutility.WebCompleteTask;
import com.example.advosoft.vocab365.webutility.WebTask;
import com.example.advosoft.vocab365.webutility.WebUrls;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by advosoft on 8/9/2018.
 */

public class LoginSecond extends BaseActiivty implements WebCompleteTask {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.edit_pass)
    EditText editPass;
    @BindView(R.id.forgot_password)
    TextView forgotPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_2);
        ButterKnife.bind(this);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(LoginSecond.this, "");
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editPass.getText().toString().isEmpty()){
                    editPass.setError(getResources().getString(R.string.notempty));
                    editPass.setFocusable(true);
                }else if (editPass.getText().toString().length()<6){
                    Toast.makeText(LoginSecond.this,"password should not less than 6",Toast.LENGTH_SHORT).show();
                }else {
                    callapiLogin();
                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void showDialog(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.forgot_dialog);

        //  TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        //text.setText(msg);

        ImageView dialogButton = (ImageView) dialog.findViewById(R.id.cancel);
        Button btn_submit=(Button)dialog.findViewById(R.id.btn_submit);
        EditText edittext=(EditText)dialog.findViewById(R.id.edittext);
        TextView fr_tv = (TextView)dialog.findViewById(R.id.forgot_tv);
        if (getIntent().getStringExtra("type").compareTo("email") == 0) {
            edittext.setText(getIntent().getStringExtra("email"));
            edittext.setCompoundDrawablesWithIntrinsicBounds(R.drawable.email, 0, 0, 0);
            fr_tv.setText(getResources().getString(R.string.forgot_email_tv));

        } else {
            edittext.setText(getIntent().getStringExtra("mobile"));
            edittext.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mobile, 0, 0, 0);
            fr_tv.setText(getResources().getString(R.string.forgot_mobile_tv));
        }
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (getIntent().getStringExtra("type").compareTo("email") == 0) {
                    callapiForgot();
                }else {
                    callapiMobileForgot();
                }

            }
        });


        dialog.show();

    }

    private void callapiLogin() {
        HashMap object = new HashMap();
        if (getIntent().getStringExtra("type").compareTo("email") == 0) {
            object.put("email", getIntent().getStringExtra("email"));
            object.put("mobile", "");
        } else {
            object.put("mobile", getIntent().getStringExtra("mobile"));
            object.put("email", "");
        }

        object.put("password", editPass.getText().toString());
        object.put("device_type", "android");
        object.put("device_token", MyApplication.getUserData(Constants.GCM_ID));
        object.put("device_id", Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        new WebTask(LoginSecond.this, WebUrls.BASE_URL + "/login", object, LoginSecond.this, RequestCode.CODE_Login, 1);
    }
    private void callapiForgot() {
        HashMap object = new HashMap();
        if (getIntent().getStringExtra("type").compareTo("email") == 0) {
            object.put("email", getIntent().getStringExtra("email"));
            object.put("mobile", "");
        } else {
            object.put("mobile", getIntent().getStringExtra("mobile"));
            object.put("email", "");
        }

        new WebTask(LoginSecond.this, WebUrls.BASE_URL + "/forgot_password", object, LoginSecond.this, RequestCode.CODE_Forgot, 1);
    }
    private void callapiMobileForgot() {
        HashMap object = new HashMap();
        if (getIntent().getStringExtra("type").compareTo("email") == 0) {
            object.put("email", getIntent().getStringExtra("email"));
            object.put("mobile", "");
        } else {
            object.put("mobile", getIntent().getStringExtra("mobile"));
            object.put("email", "");
        }

        new WebTask(LoginSecond.this, WebUrls.BASE_URL + "/forgot_password_mobile", object, LoginSecond.this, RequestCode.CODE_Mobile_forgot, 1);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        Log.d("Login_response", response);
        if (taskcode==RequestCode.CODE_Login){
            try{
                JSONObject object=new JSONObject(response);
                Toast.makeText(LoginSecond.this,object.getString("message"),Toast.LENGTH_SHORT).show();
                if (object.optString("status").compareTo("success")==0){
                    MyApplication.setUserEmail(Constants.UserEmail,object.optJSONObject("data").optString("email"));
                    MyApplication.setUserID(Constants.UserID,object.optJSONObject("data").optInt("id")+"");
                    MyApplication.setUserMobile(Constants.UserMobile,object.optJSONObject("data").optString("phone"));
                    MyApplication.setUserName(Constants.UserName,object.optJSONObject("data").optString("first_name"));
                    MyApplication.setUserLoginStatus(LoginSecond.this,true);
                    startActivity(new Intent(LoginSecond.this,DemoHomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }
            }catch (Exception e){

            }
        }else if (taskcode==RequestCode.CODE_Forgot){
            try{

                JSONObject object=new JSONObject(response);
                Toast.makeText(LoginSecond.this,object.getString("message"),Toast.LENGTH_SHORT).show();

            }catch (Exception e){

            }
        }else if (taskcode==RequestCode.CODE_Mobile_forgot){
            try{
                JSONObject object=new JSONObject(response);
                Toast.makeText(LoginSecond.this,object.getString("message"),Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(LoginSecond.this,Otp.class);
//                startActivity(intent);
            }catch (Exception e){

            }
        }

    }
}
