package com.example.advosoft.vocab365.activities;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by advosoft on 8/9/2018.
 */

public class ActivityContinueEmail extends BaseActiivty implements WebCompleteTask {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.btn_continue)
    Button btnContinue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_with_email);
        ButterKnife.bind(this);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty()) {
                    email.setError(getResources().getString(R.string.notempty));
                } else if (!Utils.isEmailValid(email.getText().toString())) {
                    Toast.makeText(ActivityContinueEmail.this, "Email is not valid", Toast.LENGTH_SHORT).show();
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
        object.put("email", email.getText().toString());
        new WebTask(ActivityContinueEmail.this, WebUrls.BASE_URL + "/check_email", object, ActivityContinueEmail.this, RequestCode.CODE_Login, 1);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        Log.d("register_response", response);
        if (taskcode==RequestCode.CODE_Login){
            try{
                JSONObject obj=new JSONObject(response);
                if (obj.optString("data").compareTo("False")==0){
                    startActivity(new Intent(ActivityContinueEmail.this,RegistrationActivity.class).putExtra("email",email.getText().toString()).putExtra("type","email"));
                    finish();
                }else {
                    startActivity(new Intent(ActivityContinueEmail.this,LoginSecond.class).putExtra("email",email.getText().toString()).putExtra("type","email"));
                    finish();
                }

            }catch (Exception e){

            }
        }

    }
}
