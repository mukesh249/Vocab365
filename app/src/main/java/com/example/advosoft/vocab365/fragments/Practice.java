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
import com.example.advosoft.vocab365.Adapter.VocabPlusPracticeAdapter;
import com.example.advosoft.vocab365.Model.QuestionModel;
import com.example.advosoft.vocab365.Model.VocabPlus_lessonModel;
import com.example.advosoft.vocab365.Model.VocabPlus_practiceModel;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.activities.Lession_and_practice;
import com.example.advosoft.vocab365.views.Constants;
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
public class Practice extends Fragment implements WebCompleteTask{

    RecyclerView vocab_recyclerview;
    static ArrayList<VocabPlus_practiceModel> vocabPlusListall = new ArrayList();
    static VocabPlusPracticeAdapter adapterall;
    String sub_cat_id;
//    static ArrayList<QuestionModel> data_arr = new ArrayList<>();
    static ArrayList<ArrayList<QuestionModel>> dsdfsa = new ArrayList<>();

    public Practice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_practice, container, false);

        sub_cat_id = getActivity().getIntent().getExtras().getString("subcat_id","");
        ((Lession_and_practice)getActivity()).getSupportActionBar().setTitle(getActivity().getIntent().getExtras().getString("subcat_name",""));
        ((Lession_and_practice)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        vocab_recyclerview = (RecyclerView)view.findViewById(R.id.practice_recyclerview);
        vocab_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        VocabpracticeMathod();

        return view;
    }
    private void VocabpracticeMathod(){
        HashMap objectNew = new HashMap();
        objectNew.put("category",sub_cat_id);
        objectNew.put("user_id",MyApplication.getUserID(Constants.UserID));
        new WebTask(getActivity(), WebUrls.BASE_URL+"/vocab_questions_quiz",objectNew,Practice.this, RequestCode.CODE_Vocab_Practice,1);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (taskcode == RequestCode.CODE_Vocab_Practice){
            Log.d("practice_Response: ",response);
            try {
                vocabPlusListall.clear();
                JSONObject jsonObject = new JSONObject(response);
                JSONArray dataArray = jsonObject.optJSONArray("data");
                if (dataArray!=null){
                    dsdfsa.clear();
                    for (int i=0;i<dataArray.length();i++){

                        VocabPlus_practiceModel item = new VocabPlus_practiceModel();
                        JSONObject jobj = null;
                        try {
                            item.setPractice_id((i+1)+"");
                            vocabPlusListall.add(item);
//                            data_arr.clear();
                            ArrayList<QuestionModel> data_arr = new ArrayList<>();
                            for (int j = 0; j<dataArray.getJSONArray(i).length();j++){
                                jobj = dataArray.getJSONArray(i).getJSONObject(j);
                                QuestionModel model = new QuestionModel();
                                int num = j + 1;
                                model.setNumber(num + "");
                                model.setPractice("practice");
                                model.setId(jobj.optString("id"));
                                model.setUser_type(jobj.optString("user_type"));
                                model.setCategory(jobj.optString("category"));
                                model.setSub_category(jobj.optString("sub_category"));
                                model.setSub_sub_category(jobj.optString("sub_sub_category"));
                                model.setQuestion(jobj.optString("question"));
                                model.setAnswer(jobj.optString("answer"));
                                model.setPublish_date(jobj.optString("publish_date"));
                                model.setOption_1(jobj.optJSONArray("options").optJSONObject(0).optString("options"));
                                model.setOption_2(jobj.optJSONArray("options").optJSONObject(1).optString("options"));
                                model.setOption_3(jobj.optJSONArray("options").optJSONObject(2).optString("options"));
                                model.setOption_4(jobj.optJSONArray("options").optJSONObject(3).optString("options"));
                                model.setOption_5(jobj.optJSONArray("options").optJSONObject(4).optString("options"));
                                model.setSlug(jobj.optString("slug"));
                                model.setDescription(jobj.optString("description"));
                                model.setStatus(jobj.optString("status"));
                                model.setAdd_date(jobj.optString("add_date"));
                                model.setTestTime(jsonObject.optString("data1"));
                                data_arr.add(model);
                            }
                            dsdfsa.add(data_arr);

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapterall = new VocabPlusPracticeAdapter(getContext(),vocabPlusListall,dsdfsa);
                    vocab_recyclerview.setAdapter(adapterall);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
