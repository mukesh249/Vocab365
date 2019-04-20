package com.example.advosoft.vocab365.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.advosoft.vocab365.Model.QuestionModel;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.activities.ActivityQuestionQuiz;
import com.example.advosoft.vocab365.activities.ActivityViewResult;
import com.example.advosoft.vocab365.activities.ActivityViewResult_ViewBinding;
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

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Advosoft2 on 11/1/2017.
 */

public class QuizFragment extends BaseFragment implements WebCompleteTask {
    static TextView startQuiz,data_not_found,min_daily_quiz;
    static TextView num_ques;
    static ArrayList<QuestionModel> data_arr = new ArrayList<>();
    static Context ctx;
    static Boolean Flag_Datatype = false;
    static String qua_ser[];
    static String qua_currect[];
    static LinearLayout viewlayou_daily;
    static String min;
    Unbinder unbinder;


    public QuizFragment() {
    }

    public static QuizFragment getInstance(Bundle bundle) {
        QuizFragment chef = new QuizFragment();
        chef.setArguments(bundle);
        return chef;
    }

    Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        mActivity = activity;
        super.onAttach(activity);
    }

    @Override
    protected void initUi(View view) {
        startQuiz = (TextView) view.findViewById(R.id.start_quiz);
        num_ques = (TextView) view.findViewById(R.id.num_ques);
        data_not_found = (TextView)view.findViewById(R.id.data_not_found);
        min_daily_quiz = (TextView)view.findViewById(R.id.min_daily_quiz);
        viewlayou_daily = (LinearLayout)view.findViewById(R.id.dally_quiz);
        callapiAll();
        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Flag_Datatype == true) {
                    startActivity(new Intent(getActivity(), ActivityViewResult.class).putExtra("qua_ser", qua_ser).putExtra("qua_currect", qua_currect).putExtra("data", data_arr));
                } else {
                    startActivity(new Intent(getActivity(), ActivityQuestionQuiz.class).putExtra("data", data_arr));
                }
            }
        });
    }


    @Override
    protected void setValueOnUi() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public boolean onBackPressedListener() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.quiz_main_layout, container, false);
        ButterKnife.bind(this, view);
        disableBackButton();
        ctx = getActivity();
        initUi(view);
        DailyQuizMethod();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Log.d("width of screen:-", "" + width);
        Log.d("height of screen:-", "" + height);
        unbinder = ButterKnife.bind(this, view);
        // setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        callapiAll();
        DailyQuizMethod();
    }

    private void DailyQuizMethod(){
        HashMap objectNew = new HashMap();
        new WebTask(ctx, WebUrls.BASE_URL + "/daily_quiz_time", objectNew, QuizFragment.this, RequestCode.CODE_Daily_Quiz, 1);
    }

    private void callapiAll() {
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", MyApplication.getUserID(Constants.UserID));
        objectNew.put("date", MyApplication.current_date);
        Log.d("auth", MyApplication.getUserType(Constants.userType));
        // Log.d("datestring", MyApplication.current_date);

        new WebTask(ctx, WebUrls.BASE_URL + "/questions", objectNew, QuizFragment.this, RequestCode.CODE_HomeData, 1);
    }

    @Override
    public void onComplete(String response, int taskcode) {

        try {
            Log.e("response", response);
            if (taskcode == RequestCode.CODE_HomeData) {
                data_arr.clear();
                JSONObject object = null;
                try {
                    object = new JSONObject(response);

                    if (object.optString("status").compareTo("success")==0){
                        data_not_found.setVisibility(View.GONE);
                        viewlayou_daily.setVisibility(View.VISIBLE);
                        JSONArray data = null;
                        data = object.getJSONArray("data");
                        JSONObject jobj = null;

                        for (int i = 0; i < data.length(); i++) {
                            jobj = data.getJSONObject(i);
                            QuestionModel model = new QuestionModel();
                            int num = i + 1;
                            model.setNumber(num + "");
                            model.setPractice("DailyQuiz");
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

                        num_ques.setText(data_arr.size() + "");
                        JSONArray pq_array = object.getJSONArray("quizResult");
                        if (pq_array.length() == 0) {
                            Flag_Datatype = false;
                            startQuiz.setText("Start Quiz");
                        } else {
                            Flag_Datatype = true;
                            startQuiz.setText("View Result");
                            JSONObject object_qus = pq_array.getJSONObject(0);
                            qua_ser = object_qus.optString("questions").split(",");
                            qua_currect = object_qus.optString("currect_ans").split(",");
                        }
                    }else {
                       data_not_found.setVisibility(View.VISIBLE);
                       viewlayou_daily.setVisibility(View.GONE);
                    }

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }else if (taskcode==RequestCode.CODE_Daily_Quiz){
                JSONObject object = new JSONObject(response);
                if (object.optString("status").compareTo("success")==0){
//                    object.optString("message");
                    min = object.optString("data");
                    MyApplication.setMIN(Constants.MIN,min);
                    min_daily_quiz.setText(min);
//                    num_ques.setText(object.optString("data1"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Reload() {
        callapiAll();
    }
}
