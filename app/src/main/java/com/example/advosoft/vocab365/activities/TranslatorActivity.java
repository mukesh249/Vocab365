

package com.example.advosoft.vocab365.activities;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.webutility.RequestCode;
import com.example.advosoft.vocab365.webutility.WebCompleteTask;
import com.example.advosoft.vocab365.webutility.WebTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class TranslatorActivity extends AppCompatActivity implements WebCompleteTask {

    ImageView translator_back_btn;
//    String url="http://inter.youdao.com/intersearch?";
    String apiKey="trnsl.1.1.20180928T063157Z.59bf2395e79894fd.aad7da424e7fd6e23c2d2a6b77de5c599f49dcf6";
    EditText enter_text;
    TextView to_Translate_btn,tranlated_tv,copy_tv,paste_tv,clear_tv;

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private ImageView mSpeakBtn;
    String s1_lag_code,s2_lag_code;
    String[] ss1= {"en","hi","bn","gu","kn","ml","mr","pa","ta","te","ur"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);

        to_Translate_btn = (TextView)findViewById(R.id.to_translate);
        copy_tv =(TextView)findViewById(R.id.copy_tv);
        paste_tv =(TextView)findViewById(R.id.paste_tv);
        clear_tv =(TextView)findViewById(R.id.clear_tv);
        tranlated_tv = (TextView)findViewById(R.id.translated_tv);
        enter_text = (EditText)findViewById(R.id.write_et);
        mSpeakBtn = (ImageView)findViewById(R.id.speek_btn);

        mSpeakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });

        translator_back_btn = (ImageView)findViewById(R.id.translator_back_icon);
        translator_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // Get clipboard manager object.
        Object clipboardService = getSystemService(CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager)clipboardService;

        to_Translate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Translatormethod();
            }
        });

        copy_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String srcText = enter_text.getText().toString();

                if (TextUtils.isEmpty(enter_text.getText().toString())){
                    Snackbar snackbar = Snackbar.make(v, "Write something on Edit Text", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else {
                    // Create a new ClipData.
                    ClipData clipData = ClipData.newPlainText("Source Text", enter_text.getText().toString());
                    // Set it as primary clip data to copy text to system clipboard.
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(getApplicationContext(), "Copy.", Toast.LENGTH_LONG).show();
                }

            }
        });

        paste_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (TextUtils.isEmpty(enter_text.getText().toString())){
//                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
//                }else {
                    ClipData abc = clipboardManager.getPrimaryClip();
                    ClipData.Item item = abc.getItemAt(0);

                    String text = item.getText().toString();
                    enter_text.setText(text);

                    Toast.makeText(getApplicationContext(), "Text Pasted",Toast.LENGTH_SHORT).show();
//                }
            }
        });
        clear_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(enter_text.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Already clear text", Toast.LENGTH_LONG).show();
                }else {
                    enter_text.setText("");
                    ClipData clipData = ClipData.newPlainText("", "");
                    clipboardManager.setPrimaryClip(clipData);

                    Toast.makeText(getApplicationContext(), "text has been cleared.", Toast.LENGTH_LONG).show();
                }
            }
        });

//        enter_text.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                Translatormethod();
//            }
//        });




        // Spinner element
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner_1);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner_2);

        // Spinner click listener
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s1_lag_code = ss1[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_item, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner1.setAdapter(adapter);

        // Spinner click listener
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s2_lag_code = ss1[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.spinner_item, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner2.setAdapter(adapter);

    }


    private void Translatormethod(){

        if (TextUtils.isEmpty(enter_text.getText().toString())){
            Toast.makeText(this,"Write something on input text.", Toast.LENGTH_LONG).show();
        }
        else {
            HashMap objectNew = new HashMap();
            URL url= null;
            try {
                url = new URL("https://translate.yandex.net/api/v1.5/tr.json/translate?" + ("key=" + URLEncoder.encode(apiKey, "UTF-8") + "&lang=" + URLEncoder.encode(s1_lag_code, "UTF-8") + URLEncoder.encode("-", "UTF-8") + URLEncoder.encode(s2_lag_code, "UTF-8") + "&text=" + URLEncoder.encode(enter_text.getText().toString(), "UTF-8")));
                new WebTask(TranslatorActivity.this,url+"",objectNew,TranslatorActivity.this, RequestCode.CODE_Translator,0);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    public void onComplete(String response, int taskcode) {
        if (taskcode == RequestCode.CODE_Translator){
            Log.d("Translated: ",response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("Translated_text: ",jsonObject.getJSONArray("text").getString(0));
                tranlated_tv.setText(jsonObject.getJSONArray("text").getString(0));

//                JSONObject dataObj = jsonObject.optJSONObject("data");
//                Iterator<String> iter = dataObj.keys();
//                String text="";
//                JSONArray trs=null;
//                JSONObject ehObj = dataObj.optJSONObject("eh");
//                if (dataObj.optJSONObject("eh")!=null)
//                {
//                    trs = ehObj.getJSONArray("trs");
//                }
//
//                if (trs!= null && trs.length()>0){
//                    for (int i=0;i<trs.length();i++){
//                        text +=trs.optJSONObject(i).optString("i");
//                    }
//                    tranlated_tv.setText(text);
//                }else {
//                    JSONObject lagObj = dataObj.optJSONObject("he");
//                    while (iter.hasNext()) {
//                        if (iter.next().equals("he")){
//                            JSONArray trans =lagObj.getJSONArray("trans");
//                            for (int i=0;i<trans.length();i++){
//                                text +=trans.optJSONObject(i).optString("w")+",";
//                            }
//                            tranlated_tv.setText(text);
//                        }
//                    }
//                }

            }catch (JSONException e){

            }
        }

    }


    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    enter_text.setText(result.get(0));
                }
                break;
            }

        }
    }
}
