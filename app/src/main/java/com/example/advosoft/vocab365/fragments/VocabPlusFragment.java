package com.example.advosoft.vocab365.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.advosoft.vocab365.Adapter.UserHomeAdapter;
import com.example.advosoft.vocab365.Adapter.VocabPlusCatAdapter;
import com.example.advosoft.vocab365.Model.VocabPlus_CatModel;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.webutility.RequestCode;
import com.example.advosoft.vocab365.webutility.WebCompleteTask;
import com.example.advosoft.vocab365.webutility.WebTask;
import com.example.advosoft.vocab365.webutility.WebUrls;
import com.example.advosoft.vocab365.wrapper.HomeDataWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class VocabPlusFragment extends Fragment implements WebCompleteTask {


    public VocabPlusFragment() {
        // Required empty public constructor
    }
    RecyclerView vocab_recyclerview;
    static ArrayList<VocabPlus_CatModel> vocabPlusListall = new ArrayList();
    static VocabPlusCatAdapter adapterall;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vocab_plus, container, false);

        vocab_recyclerview = (RecyclerView)view.findViewById(R.id.vocab_plus_cat_recyclerview);
        vocab_recyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        VocabCatMathod();
        return view;
    }

    private void VocabCatMathod(){
        HashMap objectNew = new HashMap();
        new WebTask(getActivity(), WebUrls.BASE_URL+"/vocab_parent_category",objectNew,VocabPlusFragment.this, RequestCode.CODE_Vocab_Cat,1);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (taskcode == RequestCode.CODE_Vocab_Cat){
            Log.d("Cat_Response: ",response);
            try {
                vocabPlusListall.clear();
                JSONObject jsonObject = new JSONObject(response);
                JSONArray dataArray = jsonObject.optJSONArray("data");

                JSONObject dataobj=null;
                if (dataArray!=null){
                    for (int i=0;i<dataArray.length();i++){
                        VocabPlus_CatModel item = new VocabPlus_CatModel();
                        try {
                            dataobj=dataArray.getJSONObject(i);
                            item.setCat_id(dataobj.optString("cat_id"));
                            item.setCat_name(dataobj.optString("cat_name"));

//                            if (dataobj.optJSONObject("image")!=null)
                            item.setImage(dataobj.optString("image"));

                            vocabPlusListall.add(item);
                            adapterall = new VocabPlusCatAdapter(getActivity(),vocabPlusListall);
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
