package com.example.advosoft.vocab365.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.advosoft.vocab365.Adapter.VocabPlusLessonAdapter;
import com.example.advosoft.vocab365.Adapter.VocabPlusSubCatAdapter;
import com.example.advosoft.vocab365.Model.VocabPlus_SubCatModel;
import com.example.advosoft.vocab365.Model.VocabPlus_lessonModel;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.activities.Grammar;
import com.example.advosoft.vocab365.activities.Lession_and_practice;
import com.example.advosoft.vocab365.webutility.RequestCode;
import com.example.advosoft.vocab365.webutility.WebCompleteTask;
import com.example.advosoft.vocab365.webutility.WebTask;
import com.example.advosoft.vocab365.webutility.WebUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Lession extends Fragment implements WebCompleteTask {

    RecyclerView vocab_recyclerview;
    static ArrayList<VocabPlus_lessonModel> vocabPlusListall = new ArrayList();
    static VocabPlusLessonAdapter adapterall;
    String sub_cat_id;

    public Lession() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lession, container, false);
        sub_cat_id = getActivity().getIntent().getExtras().getString("subcat_id","");
        ((Lession_and_practice)getActivity()).getSupportActionBar().setTitle(getActivity().getIntent().getExtras().getString("subcat_name",""));
        ((Lession_and_practice)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        vocab_recyclerview = (RecyclerView)view.findViewById(R.id.lesson_recyclerview);
        vocab_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        VocablessonMathod();

        return view;
    }

    private void VocablessonMathod(){
        HashMap objectNew = new HashMap();
        objectNew.put("sub_cat_id",sub_cat_id);
        new WebTask(getActivity(), WebUrls.BASE_URL+"/vocab_lession",objectNew,Lession.this, RequestCode.CODE_Vocab_Lesson,1);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (taskcode == RequestCode.CODE_Vocab_Lesson){
            Log.d("lesson_Response: ",response);
            try {
                vocabPlusListall.clear();
                JSONObject jsonObject = new JSONObject(response);
                JSONArray dataArray = jsonObject.optJSONArray("data");

                JSONObject dataobj=null;
                if (dataArray!=null){
                    for (int i=0;i<dataArray.length();i++){
                        VocabPlus_lessonModel item = new VocabPlus_lessonModel();
                        try {
                            dataobj=dataArray.getJSONObject(i);
                            item.setLesson_id(dataobj.optString("id"));
                            item.setLesson_name(dataobj.optString("title"));
//                            item.setParent_id(dataobj.optString("parent_id"));
//
//                            if (dataobj.optJSONObject("image")!=null)
                                item.setLesson_pdf(dataobj.optString("image"));
//                            if (dataobj.optJSONObject("video")!=null)
                                item.setLesson_video(dataobj.optString("video"));
                                item.setLesson_video_link(dataobj.optString("video_link"));
                            vocabPlusListall.add(item);
                            adapterall = new VocabPlusLessonAdapter(getContext(),vocabPlusListall);
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
