package com.example.advosoft.vocab365.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
 * Created by advosoft on 8/24/2018.
 */

public class RegistrationActivity extends BaseActiivty implements WebCompleteTask {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.name_reg)
    EditText name_reg;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_continue)
    Button btnContinue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(name_reg.getText())){
                    name_reg.setError("Please enter your name");
                    name_reg.requestFocus();
                }else if (password.getText().toString().isEmpty()) {
                    password.setError(getResources().getString(R.string.notempty));
                    password.setFocusable(true);
                } else if (password.getText().toString().length() < 6) {
                    Toast.makeText(RegistrationActivity.this, "password should not less than 6", Toast.LENGTH_SHORT).show();
                } else {
                    callapiRegistration();
                }
            }
        });
    }

    private void callapiRegistration() {
        HashMap object = new HashMap();
        if (getIntent().getStringExtra("type").compareTo("email") == 0) {
            object.put("email", getIntent().getStringExtra("email"));
            object.put("name",name_reg.getText().toString());
        } else {
            object.put("mobile", getIntent().getStringExtra("mobile"));
            object.put("name",name_reg.getText().toString());
        }

        object.put("password", password.getText().toString());
        new WebTask(RegistrationActivity.this, WebUrls.BASE_URL + "/register", object, RegistrationActivity.this, RequestCode.CODE_Register, 1);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        Log.d("Reg_response", response);
        if (taskcode == RequestCode.CODE_Register) {
            try {
                JSONObject object = new JSONObject(response);
                Toast.makeText(RegistrationActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                if (object.optString("status").compareTo("success") == 0) {
                    MyApplication.setUserEmail(Constants.UserEmail, object.optJSONObject("data").optString("email"));
                    MyApplication.setUserID(Constants.UserID, object.optJSONObject("data").optInt("id")+ "");
                    MyApplication.setUserName(Constants.UserName, object.optJSONObject("data").optString("name") + "");
                    MyApplication.setUserMobile(Constants.UserMobile,object.optJSONObject("data").optString("phone"));
                    //Toast.makeText(RegistrationActivity.this,object.optJSONObject("data").optString("mobile"),Toast.LENGTH_SH
                    // ORT).show();
                    if (object.optJSONObject("data").optString("status").compareTo("Inactive")==0){
                        startActivity(new Intent(RegistrationActivity.this, Otp.class));
                        //finish();
                    }
                    else {
                        MyApplication.setUserLoginStatus(RegistrationActivity.this,true);
                        startActivity(new Intent(RegistrationActivity.this, DemoHomeActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        finish();
                    }

                }
            } catch (Exception e) {

            }
        }
    }
}
