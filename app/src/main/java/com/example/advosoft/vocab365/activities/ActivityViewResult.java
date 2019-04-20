package com.example.advosoft.vocab365.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.advosoft.vocab365.Model.QuestionModel;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.fragments.MyApplication;
import com.example.advosoft.vocab365.views.Constants;
import com.example.advosoft.vocab365.webutility.RequestCode;
import com.example.advosoft.vocab365.webutility.WebCompleteTask;
import com.example.advosoft.vocab365.webutility.WebTask;
import com.example.advosoft.vocab365.webutility.WebUrls;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by advosoft on 9/10/2018.
 */

public class ActivityViewResult extends BaseActiivty implements WebCompleteTask {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.score_pb)
    CircleProgressBar scorePb;
    @BindView(R.id.accuracy_pb)
    CircleProgressBar accuracyPb;
    @BindView(R.id.chart)
    PieChart mChart;
    String qua_ser[];
    String qua_currect[];
    ArrayList<QuestionModel> data_feed;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_accuracy)
    TextView tvAccuracy;
    @BindView(R.id.tv_reattempt)
    TextView tvReattempt;
    @BindView(R.id.tv_view_solution)
    TextView tvViewSolution;
    int currect_ans = 0;
    int wrong_ans = 0;
    int un_ans = 0;
    float sc = 0;
    @BindView(R.id.tv_correct)
    TextView tvCorrect;
    @BindView(R.id.tv_incorrect)
    TextView tvIncorrect;
    @BindView(R.id.tv_unattempt)
    TextView tvUnattempt;
    @BindView(R.id.user_ranking__tv)
    TextView ranking_tv;
    @BindView(R.id.ranking_layout)
    LinearLayout ranking_layout;
    String method_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);
        ButterKnife.bind(this);

        qua_ser = getIntent().getStringArrayExtra("qua_ser");
        qua_currect = getIntent().getStringArrayExtra("qua_currect");
        data_feed = (ArrayList<QuestionModel>) getIntent().getSerializableExtra("data");
        ranking_tv = (TextView)findViewById(R.id.user_ranking__tv);

        tvReattempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityViewResult.this, ActivityQuestionQuiz.class).putExtra("data", data_feed));
                finish();
            }
        });
        tvViewSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityViewResult.this, ActivityQuestionQuizSolution.class).putExtra("data", data_feed).putExtra("qua_ser", qua_ser).putExtra("qua_currect", qua_currect));
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//      try {
            if (data_feed.get(0).getPractice().compareTo("practice")==0){
                ranking_layout.setVisibility(View.GONE);
                method_name = "practice_quiz_result";
            }else {
                method_name = "quiz_result";
                ranking_layout.setVisibility(View.VISIBLE);
            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        for (int i = 0; i < qua_currect.length; i++) {
            if (qua_currect[i].compareTo("1") == 0) {
                currect_ans++;
            }
            if (qua_currect[i].compareTo("2") == 0) {
                wrong_ans++;
            }
            if (qua_currect[i].compareTo("0") == 0) {
                un_ans++;
            }
        }
        Rankingmethod();
        int attempt_qua = currect_ans + wrong_ans;
        float score = ((float) currect_ans / (float) data_feed.size() * 100);
        float acuracy = ((float) currect_ans / (float) attempt_qua * 100);

        float currect_per = ((float) currect_ans / (float) data_feed.size() * 100);
        float wrong_per = ((float) wrong_ans / (float) data_feed.size() * 100);
        float un_ans_per = ((float) un_ans / (float) data_feed.size() * 100);
        scorePb.setProgress((int)score);
        accuracyPb.setProgress((int)acuracy);

        sc = (float) currect_ans - (float) (wrong_ans * 0.25);
        tvScore.setText(sc + "/" + data_feed.size());
        tvAccuracy.setText(new DecimalFormat("##.##").format(acuracy) + "%");

        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);
        //  mChart.setCenterTextTypeface(mTfLight);
        // mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setUsePercentValues(false);

//        mChart.setDrawSliceText(false);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(0f);
        mChart.setTransparentCircleRadius(20f);
        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        tvCorrect.setText(getResources().getString(R.string.correct) + "(" + currect_ans + ")");
        tvIncorrect.setText(getResources().getString(R.string.incorrect) + "(" + wrong_ans + ")");
        tvUnattempt.setText(getResources().getString(R.string.unattempted) + "(" + un_ans + ")");
        setData(currect_per, wrong_per, un_ans_per);
    }

    private void setData(float currect_per, float wrong_per, float un_ans_per) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
       /* for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
                    "",
                    getResources().getDrawable(R.drawable.back_icon)));
        }*/
        entries.add(new PieEntry(currect_per,
                "",
                getResources().getDrawable(R.drawable.back_icon)));
        entries.add(new PieEntry(wrong_per,
                "",
                getResources().getDrawable(R.drawable.back_icon)));
        entries.add(new PieEntry(un_ans_per,
                "",
                getResources().getDrawable(R.drawable.back_icon)));
        PieDataSet dataSet = new PieDataSet(entries, "Quiz Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(ColorTemplate.rgb("#37CF9C"));
        colors.add(ColorTemplate.rgb("#F36969"));
        colors.add(ColorTemplate.rgb("#EBE9EA"));
       /* for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());*/

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(0f);
       // data.setValueTextColor(Color.WHITE);
        // data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
        callapiAll();
    }

    @Override
    public void onStart() {
        Rankingmethod();
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Rankingmethod();
    }

    private void Rankingmethod(){
        HashMap objectNew = new HashMap();
        objectNew.put("user_id",MyApplication.getUserID(Constants.UserID));
        objectNew.put("quiz_date",MyApplication.current_date);
        new WebTask(ActivityViewResult.this,WebUrls.BASE_URL+"/quiz_ranking",objectNew,ActivityViewResult.this,RequestCode.CODE_Ranking,1);
    }
    private void callapiAll() {
        HashMap objectNew = new HashMap();
        //objectNew.put("date", MyApplication.current_date);
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < qua_ser.length; i++) {
            sb.append(qua_ser[i]);
            if (i != qua_ser.length - 1) {
                sb.append(",");
            }

        }
        for (int j = 0; j < qua_currect.length; j++) {
            sb1.append(qua_currect[j]);
            if (j != qua_currect.length - 1) {
                sb1.append(",");
            }
        }

        objectNew.put("user_id", MyApplication.getUserID(Constants.UserID));
        objectNew.put("quiz_date", MyApplication.current_date);
        objectNew.put("questions", sb.toString());
        objectNew.put("total_attempt", (currect_ans + wrong_ans) + "");
        objectNew.put("total_unattempt", un_ans + "");
        objectNew.put("total_right", currect_ans + "");
        objectNew.put("total_wrong", wrong_ans + "");
        objectNew.put("result", sc + "");
        objectNew.put("currect_ans", sb1.toString());
        Log.d("auth", MyApplication.getUserType(Constants.userType));

        // Log.d("datestring", MyApplication.current_date);

        new WebTask(ActivityViewResult.this, WebUrls.BASE_URL + "/"+method_name, objectNew, ActivityViewResult.this, RequestCode.CODE_HomeData, 1);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        Log.d("response", response);
        if (taskcode == RequestCode.CODE_HomeData) {

        }else if (taskcode == RequestCode.CODE_Ranking){
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.optString("status").equals("success")){
                    ranking_tv.setText(jsonObject.optString("rank")+"/"+jsonObject.optString("total_quiz"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
