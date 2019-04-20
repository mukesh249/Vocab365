package com.example.advosoft.vocab365.activities;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.advosoft.vocab365.Adapter.VocabPlusCatAdapter;
import com.example.advosoft.vocab365.Adapter.VocabPlusSubCatAdapter;
import com.example.advosoft.vocab365.Model.VocabPlus_CatModel;
import com.example.advosoft.vocab365.Model.VocabPlus_SubCatModel;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.fragments.VocabPlusFragment;
import com.example.advosoft.vocab365.webutility.RequestCode;
import com.example.advosoft.vocab365.webutility.WebCompleteTask;
import com.example.advosoft.vocab365.webutility.WebTask;
import com.example.advosoft.vocab365.webutility.WebUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Grammar extends AppCompatActivity implements WebCompleteTask{


    RecyclerView vocab_recyclerview;
    static ArrayList<VocabPlus_SubCatModel> vocabPlusListall = new ArrayList();
    static VocabPlusSubCatAdapter adapterall;
    String cat_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar);



        cat_id = getIntent().getExtras().getString("cat_id","");
        getSupportActionBar().setTitle(getIntent().getExtras().getString("cat_name",""));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        vocab_recyclerview = (RecyclerView)findViewById(R.id.grammar_recyclerview);
        vocab_recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        VocabSubCatMathod();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void VocabSubCatMathod(){
        HashMap objectNew = new HashMap();
        objectNew.put("cat_id",cat_id);
        new WebTask(this, WebUrls.BASE_URL+"/vocab_child_category",objectNew,Grammar.this, RequestCode.CODE_Vocab_SubCat,1);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (taskcode == RequestCode.CODE_Vocab_SubCat){
            Log.d("Cat_Response: ",response);
            try {
                vocabPlusListall.clear();
                JSONObject jsonObject = new JSONObject(response);
                JSONArray dataArray = jsonObject.optJSONArray("data");

                JSONObject dataobj=null;
                if (dataArray!=null){
                    for (int i=0;i<dataArray.length();i++){
                        VocabPlus_SubCatModel item = new VocabPlus_SubCatModel();
                        try {
                            dataobj=dataArray.getJSONObject(i);
                            item.setSub_cat_id(dataobj.optString("cat_id"));
                            item.setSub_cat_name(dataobj.optString("cat_name"));
                            item.setParent_id(dataobj.optString("parent_id"));

                            if (dataobj.optJSONObject("image")!=null)
                                item.setSub_image(dataobj.optString("image"));
                            vocabPlusListall.add(item);
                            adapterall = new VocabPlusSubCatAdapter(this,vocabPlusListall);
                            vocab_recyclerview.setAdapter(adapterall);
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
