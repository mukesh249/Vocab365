package com.example.advosoft.vocab365.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.fragments.MyApplication;
import com.example.advosoft.vocab365.views.Constants;
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
 * Created by advosoft on 8/29/2018.
 */

public class VocabDetailActivity extends BaseActiivty implements WebCompleteTask {
    @BindView(R.id.currentPage)
    TextView currentPage;
    @BindView(R.id.totalPage)
    TextView totalPage;
    @BindView(R.id.mainWord)
    TextView mainWord;
    @BindView(R.id.partofspeech)
    TextView partofspeech;
    @BindView(R.id.bookmark_vocab)
    ImageView bookmarkVocab;
    @BindView(R.id.wordsearch)
    ImageView wordsearch;
    @BindView(R.id.googleVoice)
    ImageView googleVoice;
    @BindView(R.id.bookMarkdialog)
    ImageView bookMarkdialog;
    @BindView(R.id.topLayout)
    RelativeLayout topLayout;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.meaniniTv)
    TextView meaniniTv;
    @BindView(R.id.related_forms)
    TextView relatedForms;
    @BindView(R.id.relatedfromLayout)
    LinearLayout relatedfromLayout;
    @BindView(R.id.example)
    TextView example;
    @BindView(R.id.exampleLayout)
    LinearLayout exampleLayout;
    @BindView(R.id.Antonyms)
    TextView Antonyms;
    @BindView(R.id.AntonymsLayout)
    LinearLayout AntonymsLayout;
    @BindView(R.id.Synonyms)
    TextView Synonyms;
    @BindView(R.id.SynonymsLayout)
    LinearLayout SynonymsLayout;
    @BindView(R.id.txtPrime)
    TextView txtPrime;
    @BindView(R.id.txtPrimedetail)
    TextView txtPrimedetail;
    @BindView(R.id.PrimeLayout)
    LinearLayout PrimeLayout;
    @BindView(R.id.tv_Trick)
    TextView tvTrick;
    @BindView(R.id.TrickLayout)
    LinearLayout TrickLayout;
    @BindView(R.id.content_layout)
    CardView contentLayout;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;
    @BindView(R.id.back_icon)
    ImageView backIcon;
    private BottomSheetDialog mBottomSheetDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vertical_word_pager);
        ButterKnife.bind(this);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bookmarkVocab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callapiAddBookmark();
            }
        });
        googleVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 21) {
                    MyApplication.tts.speak(getIntent().getStringExtra("word"), 0, null, hashCode() + "");
                    return;
                }
                MyApplication.tts.speak(getIntent().getStringExtra("word"), 0, null);
            }
        });

        TrickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View bottomSheetLayout = getLayoutInflater().inflate(R.layout.activity_bottom_pop, null);
                ImageView imageView = (ImageView) bottomSheetLayout.findViewById(R.id.trick_image_pop);
                TextView textView = (TextView) bottomSheetLayout.findViewById(R.id.trick_text_pop);

                Utils.loadImage(VocabDetailActivity.this, imageView, MyApplication.getTrickPic(Constants.TrickPic));
                textView.setText(MyApplication.getTrickName(Constants.TrickText));
                mBottomSheetDialog = new BottomSheetDialog(VocabDetailActivity.this);
                mBottomSheetDialog.setContentView(bottomSheetLayout);
                mBottomSheetDialog.show();
            }
        });

        callapiAll();
    }

    private void callapiAll() {
        HashMap objectNew = new HashMap();
        objectNew.put("news_id", getIntent().getStringExtra("id"));
        objectNew.put("user_id", MyApplication.getUserID(Constants.UserID));
        Log.d("auth", MyApplication.getUserType(Constants.userType));

        new WebTask(VocabDetailActivity.this, WebUrls.BASE_URL + "/news_detail", objectNew, VocabDetailActivity.this, RequestCode.CODE_HomeData, 1);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        Log.d("response", response);
        if (taskcode == RequestCode.CODE_HomeData) {
            try {
                JSONObject object = new JSONObject(response);
                JSONObject data_obj = object.getJSONObject("data");
                Utils.loadImage(VocabDetailActivity.this, image, WebUrls.BASE_URL_image + data_obj.optString("image"));
                mainWord.setText(data_obj.optString("title"));
                partofspeech.setText("(" + data_obj.optString("part_of_speech") + ")");
                meaniniTv.setText(data_obj.optString("meaning"));
                if (data_obj.optString("related_forms").isEmpty()) {
                    relatedfromLayout.setVisibility(View.GONE);
                } else {
                    relatedfromLayout.setVisibility(View.VISIBLE);
                }
                if (data_obj.optString("bookmark").compareTo("1")==0) {
                    bookmarkVocab.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_off));
                } else {
                    bookmarkVocab.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_on));
                }

                relatedForms.setText(data_obj.optString("related_forms"));
                example.setText(data_obj.optString("example"));
                Antonyms.setText(data_obj.optString("antonyms"));
                Synonyms.setText(data_obj.optString("synonyms"));



                if (data_obj.optString("trick_text").isEmpty()) {
                    TrickLayout.setVisibility(View.GONE);
                } else {
                    TrickLayout.setVisibility(View.VISIBLE);
                    MyApplication.setTrickPic(Constants.TrickPic,data_obj.optString("trick_image"));
                    MyApplication.setTrickName(Constants.TrickText,data_obj.optString("trick_text"));
                }
            } catch (Exception e) {
                Log.d("exception...", e.toString());

            }
        }else if (taskcode == RequestCode.CODE_AddBookmarks) {
            try {
                JSONObject object = new JSONObject(response);
                if (object.optString("status").compareTo("success") == 0) {
                    if (object.optString("data").compareTo("True") == 0) {
                        bookmarkVocab.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_off));
                    } else {
                        bookmarkVocab.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_on));
                    }
                }
            } catch (Exception e) {

            }

        }
    }
    private void callapiAddBookmark() {
        HashMap objectNew = new HashMap();
        objectNew.put("b_id", getIntent().getStringExtra("id"));
        objectNew.put("user_id", MyApplication.getUserID(Constants.UserID));
        objectNew.put("type", "news");

        Log.d("auth", MyApplication.getUserType(Constants.userType));

        new WebTask(VocabDetailActivity.this, WebUrls.BASE_URL + "/bookmarks", objectNew, VocabDetailActivity.this, RequestCode.CODE_AddBookmarks, 1);
    }
}
