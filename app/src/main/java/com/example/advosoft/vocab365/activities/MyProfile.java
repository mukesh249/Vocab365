package com.example.advosoft.vocab365.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.fragments.MyApplication;
import com.example.advosoft.vocab365.fragments.ProfileFragment;
import com.example.advosoft.vocab365.views.Constants;
import com.example.advosoft.vocab365.webutility.DataPart;
import com.example.advosoft.vocab365.webutility.RequestCode;
import com.example.advosoft.vocab365.webutility.VolleyMultipartRequest;
import com.example.advosoft.vocab365.webutility.WebCompleteTask;
import com.example.advosoft.vocab365.webutility.WebTask;
import com.example.advosoft.vocab365.webutility.WebUrls;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfile extends AppCompatActivity implements WebCompleteTask,GoogleApiClient.OnConnectionFailedListener {
    Button my_profile_back;
    TextView change_mobile_tv,my_profile_cancel,my_profile_save,email_tv,header_name_tv,mobile_tv;
    LinearLayout change_pass_tv,logout_li,logout_gplus;
    ImageView my_profile_edit_icon;
    CircleImageView my_profile_user_image;
    RelativeLayout my_profile_rel4,my_profile_rel;
    EditText pass_et,confirm_pas_et,my_profile_user_name_et;
    AlertDialog pass_ad;
    private KeyListener listener;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    CircleProgressBar circleProgress;

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

//checking the permission
        //if the permission is not given we will open setting to add permission
        //else app will not open
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }
        mAuth = FirebaseAuth.getInstance();

//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if (mAuth.getCurrentUser()==null){
//                    updateUI();
//                }
//            }
//        };

        circleProgress = (CircleProgressBar) findViewById(R.id.circle_progress);
        //Back Button
        my_profile_back = (Button)findViewById(R.id.my_profile_back);
        change_mobile_tv=(TextView)findViewById(R.id.my_profile_change_mobile_tv);
        change_pass_tv = (LinearLayout)findViewById(R.id.my_profile_le2);
        my_profile_edit_icon = (ImageView)findViewById(R.id.my_profile_edit_icon);
        my_profile_save =(TextView)findViewById(R.id.my_profile_save);
        my_profile_cancel =(TextView)findViewById(R.id.my_profile_cancel);
        my_profile_rel4 = (RelativeLayout)findViewById(R.id.my_profile_rel4);
        my_profile_rel = (RelativeLayout)findViewById(R.id.my_profile_rel);
        mobile_tv = (TextView)findViewById(R.id.my_profile_usser_mobile);
        logout_li = (LinearLayout)findViewById(R.id.my_profile_logout);
        my_profile_user_image = (CircleImageView)findViewById(R.id.my_profile_user_image);

        try {
            my_profile_user_name_et.setText(MyApplication.getUserName(Constants.UserName));
            header_name_tv.setText(MyApplication.getUserName(Constants.UserName));
            email_tv.setText(MyApplication.getUserEmail(Constants.UserEmail));
            mobile_tv.setText(MyApplication.getUserMobile(Constants.UserMobile));
        }catch (Exception e){
            e.printStackTrace();
        }


        my_profile_user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if everything is ok we will open image chooser
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 100);
//                startActivityForResult(i, 100);
            }
        });

        logout_gplus = (LinearLayout)findViewById(R.id.my_profile_logout_Gplus);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        logout_gplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                signOut();
//               mAuth.signOut();
//               updateUI();
            }
        });

        my_profile_user_name_et = (EditText)findViewById(R.id.my_profile_user_name_et);
        listener = my_profile_user_name_et.getKeyListener(); // Save the default KeyListener!!!
        my_profile_user_name_et.setKeyListener(null); // Disable input

        email_tv =(TextView)findViewById(R.id.my_profile_user_email);
        header_name_tv =(TextView)findViewById(R.id.my_profile_user_header_name);

        my_profile_edit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(my_profile_user_name_et, InputMethodManager.SHOW_IMPLICIT);
                my_profile_user_name_et.requestFocus();
                my_profile_user_name_et.setKeyListener(listener);
                my_profile_user_name_et.setCursorVisible(true);
                my_profile_edit_icon.setVisibility(View.GONE);
                my_profile_save.setVisibility(View.VISIBLE);
                my_profile_cancel.setVisibility(View.VISIBLE);
            }
        });
        my_profile_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyProfileUserName();
                listener = my_profile_user_name_et.getKeyListener(); // Save the default KeyListener!!!
                my_profile_user_name_et.setKeyListener(null); // Disable input
                my_profile_edit_icon.setVisibility(View.VISIBLE);
                my_profile_save.setVisibility(View.GONE);
                my_profile_cancel.setVisibility(View.GONE);
            }
        });
        my_profile_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                View view = getCurrentFocus();
                Log.e("hide kb", "1");
                if (view != null) {
                    Log.e("hide kb", "2");
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

//                my_profile_user_name_et.setText(MyApplication.getUserName(Constants.UserName));
                MyProfileData();
                listener = my_profile_user_name_et.getKeyListener(); // Save the default KeyListener!!!
                my_profile_user_name_et.setKeyListener(null); // Disable input
                my_profile_user_name_et.setCursorVisible(false);

                my_profile_edit_icon.setVisibility(View.VISIBLE);
                my_profile_save.setVisibility(View.GONE);
                my_profile_cancel.setVisibility(View.GONE);
            }
        });
        logout_li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginActivity.googlelogin){
                    signOut();
                }else {
                    MyProfileLogout();
                }

            }
        });
        change_pass_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
// Get the layout inflater
                LayoutInflater inflater = getLayoutInflater();
// Inflate and set the layout for the dialog
// Pass null as the parent view because its going in the dialog
// layout
                View view =inflater.inflate(R.layout.activity_change__password, null);
                ImageView cancel;
                Button btn_submit;
                cancel = (ImageView)view.findViewById(R.id.change_pass_close);
                pass_et = (EditText)view.findViewById(R.id.change_pass_et);
                confirm_pas_et = (EditText)view.findViewById(R.id.change_con_pass_et);
                btn_submit = (Button)view.findViewById(R.id.change_pass_submit);
                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyProfileChangePass();
                    }
                });

                builder.setView(view);
                pass_ad = builder.create();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pass_ad.dismiss();
                    }
                });
                pass_ad.setCanceledOnTouchOutside(true);
                pass_ad.show();
            }
        });
        change_mobile_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
// Get the layout inflater
                LayoutInflater inflater = getLayoutInflater();
// Inflate and set the layout for the dialog
// Pass null as the parent view because its going in the dialog
// layout
                View view =inflater.inflate(R.layout.activity_change__mobile, null);
                ImageView cancel;
                cancel = (ImageView)view.findViewById(R.id.change_mobile_close);

                builder.setView(view);
                AlertDialog ad = builder.create();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ad.dismiss();
                    }
                });
                ad.setCanceledOnTouchOutside(true);
                ad.show();
            }
        });
        my_profile_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        if (LoginActivity.googlelogin){
//            email_tv.setVisibility(View.VISIBLE);
//            email_tv.setText(MyApplication.getUserEmail(Constants.UserEmail));
//            my_profile_user_name_et.setText(MyApplication.getUserName(Constants.UserName));
//            header_name_tv.setText(MyApplication.getUserName(Constants.UserName));
//            Glide.with(this)
//                    .load(MyApplication.getUserPic(Constants.UserPic))
//                    .into(my_profile_user_image);
//        }else {
            MyProfileData();
//        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                //displaying selected image to imageview
                my_profile_user_image.setImageBitmap(bitmap);

                //calling the method uploadBitmap to upload

                uploadBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void MyProfileData(){

        HashMap objectNew = new HashMap();
        objectNew.put("user_id", MyApplication.getUserID(Constants.UserID));
        new WebTask(this, WebUrls.BASE_URL+ "/user_profile",objectNew,MyProfile.this, RequestCode.CODE_MyProfile,1);
    }
    private void MyProfileLogout(){
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", MyApplication.getUserID(Constants.UserID));
        new WebTask(this, WebUrls.BASE_URL+ "/logout",objectNew,MyProfile.this, RequestCode.CODE_MyProfileLogout,1);
    }
    private void MyProfileUserName(){
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", MyApplication.getUserID(Constants.UserID));
        objectNew.put("name",my_profile_user_name_et.getText()+"");
        new WebTask(this, WebUrls.BASE_URL+ "/update_profile",objectNew,MyProfile.this, RequestCode.CODE_updateUserProfile,1);
    }
    private void MyProfileUserImage(){
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", MyApplication.getUserID(Constants.UserID));
        objectNew.put("image","");
       // new WebTask(this, WebUrls.BASE_URL+ "/profile",objectNew,MyProfile.this, RequestCode.CODE_updateUserProfile,1);
    }
    private void MyProfileChangePass(){

        if (TextUtils.isEmpty(pass_et.getText().toString())){
            pass_et.setError("Please enter the Password");
            pass_et.requestFocus();
        } if (pass_et.getText().length()<6){
            pass_et.setError("Can't be Empty");
            pass_et.requestFocus();
        } else if (TextUtils.isEmpty(confirm_pas_et.getText().toString())){
            confirm_pas_et.setError("Please enter the confirm Password");
            confirm_pas_et.requestFocus();
        }else if (!pass_et.getText().toString().equals(confirm_pas_et.getText().toString())){
            confirm_pas_et.setError("Password and confirm Password do not match");
            confirm_pas_et.requestFocus();
        }else {
            HashMap objectNew = new HashMap();
            objectNew.put("user_id", MyApplication.getUserID(Constants.UserID));
            objectNew.put("password", pass_et.getText()+"");
            new WebTask(this, WebUrls.BASE_URL+ "/change_password",objectNew,MyProfile.this, RequestCode.CODE_ChangePass,1);
        }
    }
    @Override
    public void onComplete(String response, int taskcode) {
        Log.d("response", response);
        if (taskcode== RequestCode.CODE_MyProfile){
            try {
                JSONObject object = new JSONObject(response);
                JSONObject data_obj = object.getJSONObject("data");

                my_profile_user_name_et.setText(data_obj.optString("first_name"));
                header_name_tv.setText(data_obj.optString("first_name"));

                if (data_obj.optString("mobile").compareTo("null")!=0){
                    my_profile_rel.setVisibility(View.VISIBLE);
                    my_profile_rel4.setVisibility(View.GONE);
                    mobile_tv.setText(data_obj.optString("mobile"));
                    email_tv.setText(data_obj.optString("email"));

//                    if (data_obj.optString("image").compareTo("null")!=0){
                        Glide.with(this)
                                .load(data_obj.optString("image"))
                                .error(getResources().getDrawable(R.drawable.user))
                                .into(my_profile_user_image);
//                    }
                }else {
                    my_profile_rel.setVisibility(View.GONE);
                    my_profile_rel4.setVisibility(View.VISIBLE);
                    email_tv.setText(data_obj.optString("email"));
//                    if (data_obj.optString("image").compareTo("null")!=0){
                        Glide.with(this)
                                .load(data_obj.optString("image"))
                                .error(getResources().getDrawable(R.drawable.user))
                                .into(my_profile_user_image);
//                    }
                }

            } catch (Exception e) {

            }
        }
        if (taskcode== RequestCode.CODE_MyProfileLogout){
            try {
                JSONObject object = new JSONObject(response);
                String message = object.optString("message");
                String status = object.optString("status");
                if (status.equals("success"))
                {
                    Intent intent = new Intent(MyProfile.this,LoginActivity.class);
                    Toast.makeText(this,message, Toast.LENGTH_LONG).show();
                    MyApplication.setUserLoginStatus(MyProfile.this,false);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {

            }
        }
        if (taskcode== RequestCode.CODE_ChangePass){
            try {
                JSONObject object = new JSONObject(response);
                String message = object.optString("message");
                String status = object.optString("status");
                if (status.equals("success"))
                {
                    Toast.makeText(this,message, Toast.LENGTH_LONG).show();
                    pass_ad.dismiss();
                }
            } catch (Exception e) {

            }
        }
        if (taskcode== RequestCode.CODE_updateUserProfile){
            try {
                JSONObject object = new JSONObject(response);
                String message = object.optString("message");
                String status = object.optString("status");
                JSONObject dataObj = object.getJSONObject("data");

                if (status.equals("success"))
                {
                    MyApplication.setUserName(Constants.UserName,dataObj.optString("first_name"));
                    header_name_tv.setText(dataObj.optString("first_name"));
                    Toast.makeText(this,message, Toast.LENGTH_LONG).show();
                    pass_ad.dismiss();
                }
            } catch (Exception e) {

            }
        }
    }

    private void signOut() {
//        mGoogleApiClient.connect();
        mAuth.signOut();
        if (mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Intent intent = new Intent(MyProfile.this,LoginActivity.class);
                            Toast.makeText(MyProfile.this,"Logout Successfully", Toast.LENGTH_LONG).show();
                            MyApplication.setUserLoginStatus(MyProfile.this,false);
                            LoginActivity.googlelogin = false;
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });
        }
    }
//    private void revokeAccess() {
//        mGoogleSignInClient.revokeAccess()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                    }
//                });
//    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("LoginActivity", "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
    private void updateUI() {
            Toast.makeText(MyProfile.this, " Logout Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MyProfile.this,LoginActivity.class);
            startActivity(intent);
            finish();
    }
    private void uploadBitmap(final Bitmap bitmap) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        //getting the tag from the edittext
//        final String tags = editTextTags.getText().toString().trim();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, WebUrls.BASE_URL+ "/profile",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Glide.with(MyProfile.this)
                                    .load(obj.optString("data"))
                                    .into(my_profile_user_image);
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        NetworkResponse networkResponse = error.networkResponse;
                        String errorMessage = "Unknown error";
                        if (networkResponse == null) {
                            if (error.getClass().equals(TimeoutError.class)) {
                                errorMessage = "Request timeout";
                            } else if (error.getClass().equals(NoConnectionError.class)) {
                                errorMessage = "Failed to connect server";
                            }
//                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            String result = new String(networkResponse.data);
                            try {
                                JSONObject response = new JSONObject(result);
                                String status = response.getString("status");
                                String message = response.getString("message");
                                Log.e("Error Status", status);
                                Log.e("Error Message", message);
                                if (networkResponse.statusCode == 404) {
                                    errorMessage = "Resource not found";
                                } else if (networkResponse.statusCode == 401) {
                                    errorMessage = message + " Please login again";
                                } else if (networkResponse.statusCode == 400) {
                                    errorMessage = message + " Check your inputs";
                                } else if (networkResponse.statusCode == 500) {
                                    errorMessage = message + " Something is getting wrong";
                                }
//                                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        Log.i("Error", errorMessage);
                        error.printStackTrace();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("tags", tags);
                params.put("user_id", MyApplication.getUserID(Constants.UserID));
//                params.put("image","");
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    /*
     * The method is taking Bitmap as an argument
     * then it will return the byte[] array for the given bitmap
     * and we will send this array to the server
     * here we are using PNG Compression with 80% quality
     * you can give quality between 0 to 100
     * 0 means worse quality
     * 100 means best quality
     * */

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
