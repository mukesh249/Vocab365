package com.example.advosoft.vocab365.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.fragments.MyApplication;
import com.example.advosoft.vocab365.fragments.QuizFragment;
import com.example.advosoft.vocab365.views.Constants;
import com.example.advosoft.vocab365.views.Utils;
import com.example.advosoft.vocab365.webutility.RequestCode;
import com.example.advosoft.vocab365.webutility.WebCompleteTask;
import com.example.advosoft.vocab365.webutility.WebTask;
import com.example.advosoft.vocab365.webutility.WebUrls;
import com.example.advosoft.vocab365.wrapper.HomeDataWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.advosoft.vocab365.fragments.MyApplication.tts;

/**
 * Created by advosoft on 8/28/2018.
 */

public class HomeDetailActivity extends BaseActiivty implements WebCompleteTask ,TextToSpeech.OnInitListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_home_detail)
    TextView title_home_detail;
    @BindView(R.id.news_boook_image)
    ImageView image;
    @BindView(R.id.discription)
    TextView discription;
    @BindView(R.id.watch_now)
    TextView watchNow;
    @BindView(R.id.text_tranlator)
    TextView text_tranlator;
    @BindView(R.id.img_bookmark)
    ImageView imgBookmark;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.speek_btn)
    Button speek_btn;
    @BindView(R.id.likes_news) ImageView likes_news;
    @BindView(R.id.home_frg_upvotes_count) TextView home_frg_upvotes_count;
    @BindView(R.id.dally_quiz) TextView daily_quiz;
    HomeDataWrapper feed;
    private int mOffset;
    String apiKey="trnsl.1.1.20180928T063157Z.59bf2395e79894fd.aad7da424e7fd6e23c2d2a6b77de5c599f49dcf6";
    private String text,b_id,is_like;
    private TextToSpeech tts;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_detail_activity);

        tts = new TextToSpeech(this, this);

        ButterKnife.bind(this);
        feed = (HomeDataWrapper) getIntent().getSerializableExtra("data");

        if (getIntent().getExtras()!=null){
            home_frg_upvotes_count.setText(getIntent().getExtras().getString("Like_count")+" Likes");
            is_like = getIntent().getExtras().getString("is_like");
        }



        if (is_like.compareTo("1")==0){
            likes_news.setBackground(getResources().getDrawable(R.drawable.upvotes_click));
        }else {
            likes_news.setBackground(getResources().getDrawable(R.drawable.upvotes));
        }

        daily_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                DemoHomeActivity.getInstance().DailyMeh();
            }
        });

        likes_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikeMethod();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // button on click event
        speek_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                speakOut();
            }

        });
        callapiAll();

        imgBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callapiAddBookmark();
            }
        });

        watchNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (feed.getVideo_link()!=null){
                    Uri uri = Uri.parse(feed.getVideo_link());
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else {
                    Uri uri = Uri.parse(feed.getVideo());
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            }
        });
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap vsdv = takeScreenShot();
                Uri uri = getImageUri(HomeDetailActivity.this, vsdv);
                sendMail(uri);
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT,
//                        "Hey check out my app at: https://play.google.com/store/apps/details?id=bababazrii.com.bababazri");
//                sendIntent.setType("text/plain");
//                startActivity(sendIntent);
            }
        });

        discription.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mOffset = discription.getOffsetForPosition( motionEvent.getX(), motionEvent.getY());

                    //  mTxtOffset.setText("" + mOffset);
                    //Toast.makeText(HomeDetailActivity.this, findWordForRightHanded(discription.getText().toString(), mOffset), Toast.LENGTH_SHORT).show();
                    if (findWordForRightHanded(discription.getText().toString(),mOffset).compareTo("")==0){
//                        Toast.makeText(HomeDetailActivity.this, "", Toast.LENGTH_SHORT).show();
                    }else {
                        text=findWordForRightHanded(discription.getText().toString(), mOffset);
                        speakOut();
                        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                        HashMap objectNew = new HashMap();
                        URL url= null;
                        try {
                            url = new URL("https://translate.yandex.net/api/v1.5/tr.json/translate?" + ("key=" + URLEncoder.encode(apiKey, "UTF-8") + "&lang=en" + URLEncoder.encode("-", "UTF-8") + "hi" + "&text=" + URLEncoder.encode(findWordForRightHanded(discription.getText().toString(), mOffset), "UTF-8")));
                            new WebTask(HomeDetailActivity.this,url+"",objectNew,HomeDetailActivity.this, RequestCode.CODE_Translator,3);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }

                }
                return false;
            }
        });
    }

    private void callapiAll() {
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", MyApplication.getUserID(Constants.UserID));
        objectNew.put("news_id", getIntent().getStringExtra("id"));
        Log.d("auth", MyApplication.getUserType(Constants.userType));

        new WebTask(HomeDetailActivity.this, WebUrls.BASE_URL + "/news_detail", objectNew, HomeDetailActivity.this, RequestCode.CODE_HomeData, 1);
    }

    private void callapiAddBookmark() {
        HashMap objectNew = new HashMap();
        objectNew.put("b_id", getIntent().getStringExtra("id"));
        objectNew.put("user_id", MyApplication.getUserID(Constants.UserID));
        objectNew.put("type", "news");

        Log.d("auth", MyApplication.getUserType(Constants.userType));

        new WebTask(HomeDetailActivity.this, WebUrls.BASE_URL + "/bookmarks", objectNew, HomeDetailActivity.this, RequestCode.CODE_AddBookmarks, 1);
    }
    public void LikeMethod(){
//        poss = getAdapterPosition();
//        HomeDataWrapper homeDataWrapper = feedsListall.get(poss);
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", MyApplication.getUserID(Constants.UserID));
        objectNew.put("type","news");
        objectNew.put("b_id",b_id);
        new WebTask(HomeDetailActivity.this, WebUrls.BASE_URL + "/like", objectNew, this, RequestCode.CODE_Upvotes, 1);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        Log.d("response", response);
        if (taskcode == RequestCode.CODE_HomeData) {
            try {
                JSONObject object = new JSONObject(response);
                JSONObject data_obj = object.getJSONObject("data");
                b_id = data_obj.optString("id");
                Utils.loadImage(HomeDetailActivity.this, image, WebUrls.BASE_URL_image + data_obj.optString("image"));
                discription.setText(Html.fromHtml(data_obj.optString("description")));
                title.setText(data_obj.optString("courtesy"));
                title_home_detail.setText(data_obj.optString("title"));
                if (data_obj.optString("bookmark").compareTo("1")==0) {
                    imgBookmark.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_off));
                } else {
                    imgBookmark.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_on));
                }
                if (data_obj.optString("type").compareTo("Video") == 0) {
                    watchNow.setVisibility(View.VISIBLE);
                } else {
                    watchNow.setVisibility(View.GONE);
                }
            } catch (Exception e) {

            }
        } else if (taskcode == RequestCode.CODE_AddBookmarks) {
            try {
                JSONObject object = new JSONObject(response);
                if (object.optString("status").compareTo("success") == 0) {
                    if (object.optString("data").compareTo("True") == 0) {
                        imgBookmark.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_off));
                    } else {
                        imgBookmark.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_on));
                    }
                }
            } catch (Exception e) {

            }
        } else if (taskcode == RequestCode.CODE_Translator){
            Log.d("Translated: ",response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("Translated_text: ",jsonObject.getJSONArray("text").getString(0));
                text_tranlator.setText(text +" = " + jsonObject.getJSONArray("text").getString(0) );
               // Toast.makeText(HomeDetailActivity.this,jsonObject.getJSONArray("text").getString(0),Toast.LENGTH_LONG).show();
            }catch (JSONException e){

            }
        }else if (taskcode==RequestCode.CODE_Upvotes){

            try {
                JSONObject object = new JSONObject(response);

                if (object.optString("status").equals("success")){
                    home_frg_upvotes_count.setText(object.optString("data")+" Likes");
//                    Toast.makeText(HomeDetailActivity.this,object.optString("message"),Toast.LENGTH_SHORT).show();

                    if (object.optString("message").equalsIgnoreCase("Like")){
                        likes_news.setBackground(getResources().getDrawable(R.drawable.upvotes_click));
                    }else {
                        likes_news.setBackground(getResources().getDrawable(R.drawable.upvotes));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String findWordForRightHanded(String str, int offset) { // when you touch ' ', this method returns left word.
        if (str.length() == offset) {
            offset--; // without this code, you will get exception when touching end of the text
        }

        if (str.charAt(offset) == ' ') {
            offset--;
        }
        int startIndex = offset;
        int endIndex = offset;

        try {
            while (str.charAt(startIndex) != ' ' && str.charAt(startIndex) != '\n') {
                startIndex--;
            }
        } catch (StringIndexOutOfBoundsException e) {
            startIndex = 0;
        }

        try {
            while (str.charAt(endIndex) != ' ' && str.charAt(endIndex) != '\n') {
                endIndex++;
            }
        } catch (StringIndexOutOfBoundsException e) {
            endIndex = str.length();
        }

        // without this code, you will get 'here!' instead of 'here'
        // if you use only english, just check whether this is alphabet,
        // but 'I' use korean, so i use below algorithm to get clean word.
        char last = str.charAt(endIndex - 1);
        if (last == ',' || last == '.' ||
                last == '!' || last == '?' ||
                last == ':' || last == ';') {
            endIndex--;
        }
        //create your spannable
        final SpannableString spannable = new SpannableString(str);
        final ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(final View view) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.darkred));
            }
        };

        spannable.setSpan(clickableSpan, startIndex, endIndex+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        discription.setMovementMethod(LinkMovementMethod.getInstance());
        discription.setText(spannable);

        return str.substring(startIndex, endIndex+1);
    }


    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speek_btn.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {

//        String text = txtText.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public Bitmap takeScreenShot() {
        View view = getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        //Find the screen dimensions to create bitmap in the same size.
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        int a = (int) (getWindowManager().getDefaultDisplay().getHeight() * 0.1);

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - a);
        view.destroyDrawingCache();
        return b;
    }
    public void sendMail(Uri path) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"demo@website.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Truiton Test Mail");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "download this Vocab365 App \n" + "https://play.google.com/store/apps/details?id=bababazrii.com.bababazri");
        emailIntent.setType("image/png");
        //Uri myUri = Uri.parse("file://" + path);
        emailIntent.putExtra(Intent.EXTRA_STREAM, path);
        startActivity(Intent.createChooser(emailIntent, "Share Pic..."));
    }
}
