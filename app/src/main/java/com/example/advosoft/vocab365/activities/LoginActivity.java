package com.example.advosoft.vocab365.activities;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.fragments.MyApplication;
import com.example.advosoft.vocab365.fragments.PastListFragment;
import com.example.advosoft.vocab365.views.Constants;
import com.example.advosoft.vocab365.webutility.RequestCode;
import com.example.advosoft.vocab365.webutility.WebCompleteTask;
import com.example.advosoft.vocab365.webutility.WebTask;
import com.example.advosoft.vocab365.webutility.WebUrls;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by advosoft on 6/14/2018.
 */

public class LoginActivity extends BaseActiivty implements GoogleApiClient.OnConnectionFailedListener,WebCompleteTask{
    @BindView(R.id.with_email)
    TextView withEmail;
    @BindView(R.id.with_mobile)
    TextView withMobile;
    @BindView(R.id.gplus)
    ImageView gplus;

    private FirebaseAuth mAuth;
    private String TAG ="LoginActivity";
    private final static int RC_SIGN_IN = 105;
    GoogleApiClient mGoogleApiClient;
    FirebaseAuth.AuthStateListener mAuthListener;
    public static Boolean googlelogin=false;

    private GoogleSignInClient mGoogleSignInClient;
//    GoogleSignInOptions gso;

    //    @Override
    public void onStart() {
        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        gplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null){
//                    updateUI(firebaseAuth.getCurrentUser());
                    Intent intent = new Intent(LoginActivity.this,DemoHomeActivity.class);
                    googlelogin = true;
                    startActivity(intent);
                    finish();
                }
            }
        };

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        withEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ActivityContinueEmail.class));
            }
        });
        withMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ActivityContinueMobile.class));
            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
//                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show();
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user!=null){
//            Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
            MyApplication.setUserEmail(Constants.UserEmail,user.getEmail());
            MyApplication.setUserName(Constants.UserName,user.getDisplayName());
            MyApplication.setUserPic(Constants.UserPic,user.getPhotoUrl()+"");
            Log.d("Email:",user.getEmail()+ "\n User Name: " + user.getDisplayName()+" \n Photo Url: "+user.getPhotoUrl());
            LoginGoogleApi();
        }
    }
    private void LoginGoogleApi(){
        try {
            HashMap objectNew = new HashMap();
            objectNew.put("email",MyApplication.getUserEmail(Constants.UserEmail));
            objectNew.put("device_type", "android");
            objectNew.put("device_token", MyApplication.getUserData(Constants.GCM_ID));
            objectNew.put("device_id", Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
            objectNew.put("name",MyApplication.getUserName(Constants.UserName));
            objectNew.put("image",MyApplication.getUserPic(Constants.UserPic));
            new WebTask(LoginActivity.this, WebUrls.BASE_URL+"/google_login",objectNew,LoginActivity.this, RequestCode.CODE_GoogleLoginApi,1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("LoginActivity", "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (taskcode == RequestCode.CODE_GoogleLoginApi){
            Log.d("Google_Login_Response: ", response);
            try{
                JSONObject object=new JSONObject(response);
                Toast.makeText(LoginActivity.this,object.getString("message"),Toast.LENGTH_SHORT).show();
                if (object.optString("status").compareTo("success")==0){
                    MyApplication.setUserEmail(Constants.UserEmail,object.optJSONObject("data").optString("email"));
                    MyApplication.setUserID(Constants.UserID,object.optJSONObject("data").optInt("id")+"");
                    MyApplication.setUserMobile(Constants.UserMobile,object.optJSONObject("data").optString("phone"));
                    MyApplication.setUserName(Constants.UserName,object.optJSONObject("data").optString("first_name"));
                    MyApplication.setUserPic(Constants.UserPic,object.optJSONObject("data").optString("image"));
                    Intent intent = new Intent(LoginActivity.this,DemoHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    googlelogin = true;
                    startActivity(intent);
                    finish();
                }
            }catch (Exception e){

            }
        }
    }
}
