package com.example.advosoft.vocab365.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.WrapperListAdapter;

import com.bumptech.glide.Glide;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.activities.ActivityBookmarks;
import com.example.advosoft.vocab365.activities.Dictionary;
import com.example.advosoft.vocab365.activities.FeedbackActivity;
import com.example.advosoft.vocab365.activities.LoginActivity;
import com.example.advosoft.vocab365.activities.MyProfile;
import com.example.advosoft.vocab365.activities.TranslatorActivity;
import com.example.advosoft.vocab365.views.Constants;
import com.example.advosoft.vocab365.webutility.RequestCode;
import com.example.advosoft.vocab365.webutility.WebCompleteTask;
import com.example.advosoft.vocab365.webutility.WebTask;
import com.example.advosoft.vocab365.webutility.WebUrls;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements WebCompleteTask{

    LinearLayout dic_pro_btn,feedback,share,rate,translator,bookmark;
    RelativeLayout pro_frg_rel;
    TextView name_tv,email_tv,mobile_tv;
    Button pro_frg_next;
    String pop_email_string,pop_mobile_string,pop_write_string;
    RatingBar bar;
    EditText feedback_pop_write;
    AlertDialog rating_ad,feedback_ad;
    CircleImageView profile_image_circle;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Profile_Feg_Data();
//    }
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        dic_pro_btn = (LinearLayout)view.findViewById(R.id.profile_dictionary_li);
        feedback = (LinearLayout)view.findViewById(R.id.pro_frg_feedback_lin);
        share = (LinearLayout)view.findViewById(R.id.pro_frg_share_lin);
        rate = (LinearLayout)view.findViewById(R.id.pro_frg_rate_lin);
        translator = (LinearLayout)view.findViewById(R.id.pro_frg_translator);
        bookmark = (LinearLayout)view.findViewById(R.id.pro_frg_bookmark_lin);
        profile_image_circle=(CircleImageView)view.findViewById(R.id.profile_image_circle);

        pro_frg_rel = (RelativeLayout)view.findViewById(R.id.pro_frg_rel);
        name_tv = (TextView)view.findViewById(R.id.profile_freg_name);
        email_tv = (TextView)view.findViewById(R.id.profile_freg_email);
        mobile_tv =(TextView)view.findViewById(R.id.profile_freg_mobile);
        pro_frg_next =(Button)view.findViewById(R.id.pro_frg_next);

        try {
            name_tv.setText(MyApplication.getUserName(Constants.UserName));
            email_tv.setText(MyApplication.getUserEmail(Constants.UserEmail));
            mobile_tv.setText(MyApplication.getUserMobile(Constants.UserMobile));
        }catch (Exception e){
            e.printStackTrace();
        }


        Profile_Feg_Data();
        pro_frg_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProfile.class);
                startActivity(intent);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
// Get the layout inflater
                LayoutInflater inflater = getLayoutInflater();
// Inflate and set the layout for the dialog
// Pass null as the parent view because its going in the dialog
// layout
                View view =inflater.inflate(R.layout.activity_feedback, null);
                ImageView cancel;
                EditText feedback_pop_email,feedback_pop_mobile;
                Button feed_send_btn;
                feedback_pop_write = (EditText)view.findViewById(R.id.feedback_write_here_et);
                feedback_pop_mobile = (EditText)view.findViewById(R.id.feedback_mobile_et);
                feedback_pop_email = (EditText)view.findViewById(R.id.feedback_email_et);
                feed_send_btn = (Button)view.findViewById(R.id.feedback_send_btn);
                cancel = (ImageView)view.findViewById(R.id.feedback_close);

                if (MyApplication.getUserMobile(Constants.UserEmail).compareTo("null")!=0){
                    feedback_pop_email.setVisibility(View.VISIBLE);
                    feedback_pop_mobile.setVisibility(View.GONE);
                    feedback_pop_email.setText(MyApplication.getUserID(Constants.UserEmail));
                }else{
                    feedback_pop_email.setVisibility(View.GONE);
                    feedback_pop_mobile.setVisibility(View.VISIBLE);
                    feedback_pop_mobile.setText(MyApplication.getUserMobile(Constants.UserMobile));
                }

                feed_send_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Profile_Feg_Feedback();
                    }
                });
                builder.setView(view);
                feedback_ad= builder.create();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        feedback_ad.dismiss();
                    }
                });
                feedback_ad.setCanceledOnTouchOutside(true);
                feedback_ad.show();
            }
        });
        dic_pro_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Dictionary.class);
                startActivity(intent);
            }
        });
        pro_frg_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProfile.class);
                startActivity(intent);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=bababazrii.com.bababazri");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
// Get the layout inflater
                LayoutInflater inflater = getLayoutInflater();
// Inflate and set the layout for the dialog
// Pass null as the parent view because its going in the dialog
// layout
                View view =inflater.inflate(R.layout.rating_activity, null);
                ImageView cancel;
                Button feed_send_btn;
                cancel = (ImageView)view.findViewById(R.id.ratenow_close);
                bar = (RatingBar)view.findViewById(R.id.ratingbar);

                feed_send_btn = (Button)view.findViewById(R.id.rate_now_btn);
                feed_send_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Profile_Feg_Rating();
                    }
                });
                builder.setView(view);
                rating_ad = builder.create();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rating_ad.dismiss();
                    }
                });
                rating_ad.setCanceledOnTouchOutside(true);
                rating_ad.show();
            }
        });
        translator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TranslatorActivity.class);
                startActivity(intent);
            }
        });
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityBookmarks.class);
                startActivity(intent);
            }
        });

//        if (LoginActivity.googlelogin){
//            email_tv.setVisibility(View.VISIBLE);
//            email_tv.setText(MyApplication.getUserEmail(Constants.UserEmail));
//            name_tv.setText(MyApplication.getUserName(Constants.UserName));
//            Glide.with(this)
//                    .load(MyApplication.getUserPic(Constants.UserPic))
//                    .into(profile_image_circle);
//        }else {
//        }
        return  view;
    }

    private void Profile_Feg_Data(){
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", MyApplication.getUserID(Constants.UserID));
        new WebTask(getActivity(), WebUrls.BASE_URL+ "/user_profile",objectNew,ProfileFragment.this, RequestCode.CODE_MyProfile,1);
    }

    @Override
    public void onStart() {
//        if (LoginActivity.googlelogin){
//            email_tv.setVisibility(View.VISIBLE);
//            email_tv.setText(MyApplication.getUserEmail(Constants.UserEmail));
//            name_tv.setText(MyApplication.getUserName(Constants.UserName));
//            Glide.with(this)
//                    .load(MyApplication.getUserPic(Constants.UserPic))
//                    .into(profile_image_circle);
//        }else {
        Profile_Feg_Data();
        MyApplication.hideKeyBoard(Objects.requireNonNull(this.getActivity()));
        super.onStart();
    }

    private void Profile_Feg_Rating(){

        HashMap objectNew = new HashMap();
        objectNew.put("user_id", MyApplication.getUserID(Constants.UserID));
        objectNew.put("rating", bar.getRating()+"");
        new WebTask(getActivity(), WebUrls.BASE_URL+ "/rating",objectNew,ProfileFragment.this, RequestCode.CODE_Rating,1);
    }
    private void Profile_Feg_Feedback(){

        if (TextUtils.isEmpty(feedback_pop_write.getText().toString())){
            feedback_pop_write.setError("Write something");
            feedback_pop_write.requestFocus();
        }else {
            HashMap objectNew = new HashMap();
            objectNew.put("user_id", MyApplication.getUserID(Constants.UserID));
            objectNew.put("feedback", feedback_pop_write.getText()+"");
            new WebTask(getActivity(), WebUrls.BASE_URL+ "/feedback",objectNew,ProfileFragment.this, RequestCode.CODE_Feedback,1);
        }
    }

    @Override
    public void onComplete(String response, int taskcode) {
        Log.d("response", response);
        if (taskcode==RequestCode.CODE_MyProfile){
            try {
                JSONObject object = new JSONObject(response);
                if (object.optString("message").equals("User not found")){
                    MyApplication.setUserLoginStatus(getActivity(),false);
                    startActivity(new Intent(getActivity(), LoginActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    getActivity().finish();
                }else {
                    JSONObject data_obj = object.getJSONObject("data");
                    name_tv.setText(data_obj.optString("first_name"));

                    if (data_obj.optString("mobile").compareTo("null")==0){
                        mobile_tv.setVisibility(View.GONE);
                        email_tv.setVisibility(View.VISIBLE);
                        email_tv.setText(data_obj.optString("email"));
//                    if (data_obj.optString("image")!=null){
                        Glide.with(this)
                                .load(data_obj.optString("image"))
                                .error(getResources().getDrawable(R.drawable.user))
                                .into(profile_image_circle);
//                    }

                    }else {
                        mobile_tv.setVisibility(View.VISIBLE);
                        email_tv.setVisibility(View.GONE);
                        mobile_tv.setText(data_obj.optString("mobile"));
                        email_tv.setText(data_obj.optString("email"));
//                    if (data_obj.optString("image")!=null){
                        Glide.with(this)
                                .load(data_obj.optString("image"))
                                .error(getResources().getDrawable(R.drawable.user))
                                .into(profile_image_circle);
//                    }
                    }
                }
            } catch (Exception e) {

            }
        }
        if (taskcode==RequestCode.CODE_Rating){
            try {
                JSONObject object = new JSONObject(response);
                String message = object.optString("message");
                String status = object.optString("status");


                if (status.equals("success"))
                {
                    Toast.makeText(getActivity(),message, Toast.LENGTH_LONG).show();
                    rating_ad.dismiss();
                }else {
                    Toast.makeText(getActivity(),message, Toast.LENGTH_LONG).show();
                    rating_ad.dismiss();
                }
            } catch (Exception e) {

            }
        }
        if (taskcode==RequestCode.CODE_Feedback){
            try {
                JSONObject object = new JSONObject(response);
                String message = object.optString("message");
                String status = object.optString("status");


                if (status.equals("success"))
                {
                    Toast.makeText(getActivity(),message, Toast.LENGTH_LONG).show();
                    feedback_ad.dismiss();
                }else {
                    Toast.makeText(getActivity(),message, Toast.LENGTH_LONG).show();
                    feedback_ad.dismiss();
                }
            } catch (Exception e) {

            }
        }
    }
}
