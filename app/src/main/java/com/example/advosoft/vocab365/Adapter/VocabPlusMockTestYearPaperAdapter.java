package com.example.advosoft.vocab365.Adapter;

/**
 * Created by Mukesh on 01-02-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.advosoft.vocab365.Model.QuestionModel;
import com.example.advosoft.vocab365.Model.VocabPlus_MockTestYearPaperModel;
import com.example.advosoft.vocab365.Model.VocabPlus_SubCatModel;
import com.example.advosoft.vocab365.Model.VocabPlus_practiceModel;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.activities.ActivityQuestionQuiz;
import com.example.advosoft.vocab365.activities.Lession_and_practice;
import com.example.advosoft.vocab365.activities.MockTestYearPaper;
import com.example.advosoft.vocab365.fragments.MyApplication;
import com.example.advosoft.vocab365.fragments.Practice;
import com.example.advosoft.vocab365.fragments.QuizFragment;
import com.example.advosoft.vocab365.views.Constants;
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
import java.util.Random;

public class VocabPlusMockTestYearPaperAdapter extends RecyclerView.Adapter<VocabPlusMockTestYearPaperAdapter.ViewHolder> {

    private ArrayList<VocabPlus_MockTestYearPaperModel> vocabPlus_mockTestYearPaperModelArrayList;
    VocabPlus_MockTestYearPaperModel vocabPlusMockTestYearPaperModel;
    Context context;
    static ArrayList<QuestionModel> data_arr = new ArrayList<>();
    int i;
    static String min;

    public VocabPlusMockTestYearPaperAdapter(Context context, ArrayList<VocabPlus_MockTestYearPaperModel> vocabPlus_mockTestYearPaperModelArrayList) {
        this.vocabPlus_mockTestYearPaperModelArrayList=vocabPlus_mockTestYearPaperModelArrayList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (vocabPlus_mockTestYearPaperModelArrayList == null)
            return 0;
        return vocabPlus_mockTestYearPaperModelArrayList.size();
    }

    @Override
    public VocabPlusMockTestYearPaperAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vocab_plus_subcat_raw, parent, false);
        return new ViewHolder(v,vocabPlus_mockTestYearPaperModelArrayList,context);
    }

    int j=0;
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        vocabPlusMockTestYearPaperModel = this.vocabPlus_mockTestYearPaperModelArrayList.get(position);

        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        GradientDrawable drawable = (GradientDrawable) holder.sub_cat_no.getBackground();
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        if (i % 2 == 0) {
            holder.cat_image.setColorFilter(randomAndroidColor);
//            drawable.setColor(randomAndroidColor);
            holder.sub_cat_no.setTextColor(randomAndroidColor);
            drawable.setStroke(3,randomAndroidColor);
        } else {
            drawable.setColor(Color.BLUE);
        }

        holder.sub_cat_no.setText(++position+"");
        holder.sub_cat_name.setText(vocabPlusMockTestYearPaperModel.getMock_cat_name());
//        Glide.with(context)
//                .load(vocabPlus_subcatModel.getSub_image()).placeholder(R.drawable.logo1)
//                .into(holder.cat_image);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements WebCompleteTask {
        TextView sub_cat_name,sub_cat_no;
        ImageView cat_image;
        ArrayList<VocabPlus_MockTestYearPaperModel> vocabPlus_mockTestYearPaperModelArrayList;
        int pos;
        Context context;
        ViewHolder(View itemView,ArrayList<VocabPlus_MockTestYearPaperModel> vocabPlus_mockTestYearPaperModelArrayList,Context context) {
            super(itemView);
            this.context = context;
            this.vocabPlus_mockTestYearPaperModelArrayList = vocabPlus_mockTestYearPaperModelArrayList;
            sub_cat_name = (TextView)itemView.findViewById(R.id.sub_cat_vocab_plus_color_name);
            sub_cat_no = (TextView)itemView.findViewById(R.id.sub_cat_vocab_plus_color_tv);
            cat_image =(ImageView)itemView.findViewById(R.id.sub_cat_vocab_plus_color_arrow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    VocabPlus_MockTestYearPaperModel vocabPlusMockTestYearPaperModel = vocabPlus_mockTestYearPaperModelArrayList.get(pos);
                   // Toast.makeText(context,vocabPlusMockTestYearPaperModel.getMock_cat_name(),Toast.LENGTH_SHORT).show();
                    VocabpracticeMathod(pos);
                }
            });
        }
        private void DailyQuizMethod(){
            HashMap objectNew = new HashMap();
            new WebTask(context, WebUrls.BASE_URL + "/mock_test_time", objectNew, this, RequestCode.CODE_Daily_Quiz, 1);
        }

        private void VocabpracticeMathod(int poss){
            poss = getAdapterPosition();
            VocabPlus_MockTestYearPaperModel vocabPlusMockTestYearPaperModel = vocabPlus_mockTestYearPaperModelArrayList.get(poss);
            HashMap objectNew = new HashMap();
            objectNew.put("category",vocabPlusMockTestYearPaperModel.getMock_cat_id());
            objectNew.put("user_id",MyApplication.getUserID(Constants.UserID));
            new WebTask(context, WebUrls.BASE_URL+"/vocab_questions_quiz",objectNew,this, RequestCode.CODE_Vocab_MockTest,1);
        }

        @Override
        public void onComplete(String response, int taskcode) {
            try {
                Log.e("response", response);
                if (taskcode == RequestCode.CODE_Vocab_MockTest) {
                    data_arr.clear();
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response);
                        if (object.optString("status").compareTo("fail")==0){
                            Toast.makeText(context,object.optString("message"),Toast.LENGTH_SHORT).show();
                        }else {
                            JSONArray data = null;
                            data = object.getJSONArray("data");
                            JSONObject jobj = null;

                            for (int i = 0; i < data.length(); i++) {
                                jobj = data.getJSONObject(i);
                                QuestionModel model = new QuestionModel();
                                int num = i + 1;
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
                                model.setTestTime(object.optString("data1"));
                                data_arr.add(model);
                            }
                            context.startActivity(new Intent(context, ActivityQuestionQuiz.class).putExtra("data", data_arr));
//                            DailyQuizMethod();
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }else if (taskcode==RequestCode.CODE_Daily_Quiz){
                JSONObject object = new JSONObject(response);
                if (object.optString("status").compareTo("success")==0){
                    min = object.optString("data");
                    MyApplication.setMIN(Constants.MIN,min);

                }
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}