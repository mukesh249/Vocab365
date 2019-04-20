package com.example.advosoft.vocab365.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.advosoft.vocab365.Adapter.VocabPlusMockTestYearPaperAdapter;
import com.example.advosoft.vocab365.Adapter.VocabPlusSubCatAdapter;
import com.example.advosoft.vocab365.Model.VocabPlus_MockTestYearPaperModel;
import com.example.advosoft.vocab365.Model.VocabPlus_SubCatModel;
import com.example.advosoft.vocab365.Model.VocabPlus_practiceModel;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.webutility.RequestCode;
import com.example.advosoft.vocab365.webutility.WebCompleteTask;
import com.example.advosoft.vocab365.webutility.WebTask;
import com.example.advosoft.vocab365.webutility.WebUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MockTestYearPaper extends AppCompatActivity implements WebCompleteTask{

    RecyclerView vocab_recyclerview;
    static ArrayList<VocabPlus_MockTestYearPaperModel> vocabPlusListall = new ArrayList();
    static VocabPlusMockTestYearPaperAdapter adapterall;
    String cat_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test_year_paper);

        cat_id = getIntent().getExtras().getString("MockTest_id", "");
        getSupportActionBar().setTitle(getIntent().getExtras().getString("MockTest_name", ""));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        vocab_recyclerview = (RecyclerView) findViewById(R.id.mocktest_recyclerview);
        vocab_recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        VocabMockTestMathod();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void VocabMockTestMathod() {
        HashMap objectNew = new HashMap();
        objectNew.put("sub_cat_id", cat_id);
        new WebTask(this, WebUrls.BASE_URL + "/vocab_sub_child_category", objectNew, MockTestYearPaper.this, RequestCode.CODE_Vocab_SubCat, 1);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (taskcode == RequestCode.CODE_Vocab_SubCat) {
            Log.d("Cat_Response: ", response);
            try {
                vocabPlusListall.clear();
                JSONObject jsonObject = new JSONObject(response);
                JSONArray dataArray = jsonObject.optJSONArray("data");

                JSONObject dataobj = null;
                if (dataArray != null) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        VocabPlus_MockTestYearPaperModel item = new VocabPlus_MockTestYearPaperModel();
                        try {
                            dataobj = dataArray.getJSONObject(i);
                            item.setMock_cat_id(dataobj.optString("cat_id"));
                            item.setMock_cat_name(dataobj.optString("cat_name"));
//                            item.setParent_id(dataobj.optString("parent_id"));

//                            if (dataobj.optJSONObject("image") != null)
//                                item.setSub_image(dataobj.optString("image"));
                            vocabPlusListall.add(item);
                            adapterall = new VocabPlusMockTestYearPaperAdapter(this, vocabPlusListall);
                            vocab_recyclerview.setAdapter(adapterall);
                        } catch (JSONException e) {
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