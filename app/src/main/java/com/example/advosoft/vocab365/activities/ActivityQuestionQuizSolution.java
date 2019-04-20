package com.example.advosoft.vocab365.activities;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.advosoft.vocab365.Adapter.PagerAdapterSolution;
import com.example.advosoft.vocab365.Adapter.QuestionSlideAdapterSolution;
import com.example.advosoft.vocab365.Model.QuestionModel;
import com.example.advosoft.vocab365.R;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by advosoft on 8/30/2018.
 */

public class ActivityQuestionQuizSolution extends BaseActiivty {
    @BindView(R.id.noDataAvailable)
    RelativeLayout noDataAvailable;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.backimage)
    TextView backimage;
    @BindView(R.id.timer)
    TextView timer;
    @BindView(R.id.ic_bookmark)
    ImageView icBookmark;
    @BindView(R.id.ic_share_quiz)
    ImageView icShareQuiz;
    @BindView(R.id.ic_report_quiz)
    ImageView icReportQuiz;
    @BindView(R.id.img_list)
    ImageView imgList;
    @BindView(R.id.tvQuestionNo)
    TextView tvQuestionNo;
    @BindView(R.id.pb)
    CircleProgressBar pb;
    @BindView(R.id.Lineartop)
    RelativeLayout Lineartop;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.testName)
    TextView testName;
    @BindView(R.id.list)
    ImageView list;
    @BindView(R.id.grid)
    ImageView grid;
    @BindView(R.id.views)
    LinearLayout views;
    @BindView(R.id.top_info)
    RelativeLayout topInfo;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.Correct)
    TextView Correct;
    @BindView(R.id.InCorrect)
    TextView InCorrect;
    @BindView(R.id.unans)
    TextView unans;
    @BindView(R.id.tv_totalView)
    TextView tvTotalView;
    @BindView(R.id.bottom_info)
    LinearLayout bottomInfo;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.outside)
    LinearLayout outside;
//    @BindView(R.id.mainLayout)
//    RelativeLayout mainLayout;

    ArrayList<QuestionModel> data_arr;
    QuestionSlideAdapterSolution adapter;
    PagerAdapterSolution pager_adapter;
    static String qua_ser[];
    static String qua_currect[];
    @BindView(R.id.ans)
    TextView ans;
    @BindView(R.id.img_play)
    ImageView imgPlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_quiz);
        ButterKnife.bind(this);
        data_arr = (ArrayList<QuestionModel>) getIntent().getSerializableExtra("data");
        qua_ser = getIntent().getStringArrayExtra("qua_ser");
        qua_currect = getIntent().getStringArrayExtra("qua_currect");
        unans.setVisibility(View.GONE);
        ans.setVisibility(View.GONE);
        Correct.setVisibility(View.VISIBLE);
        InCorrect.setVisibility(View.VISIBLE);
//        imgPlay.setImageResource(R.drawable.ic_play_arrow);

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                    adapter.notifyDataSetChanged();

                }
            }
        });
//        mainLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
//                    drawer.closeDrawer(Gravity.RIGHT);
//                } else {
//                    drawer.openDrawer(Gravity.RIGHT);
//                    adapter.notifyDataSetChanged();
//
//                }
//            }
//        });
       /* for (int i = 0; i < data_arr.size(); i++) {
            qua_ser[i] = "0";
            qua_currect[i] = "0";
        }*/
        int count = 0;
        for (int i = 0; i < qua_ser.length; i++) {
            if (qua_ser[i].compareTo("0") != 0) {
                count++;
            }
        }
        tvTotalView.setText("Total Question Attempted : " + count);
        pager.setOffscreenPageLimit(25);
        pager_adapter = new PagerAdapterSolution(data_arr, ActivityQuestionQuizSolution.this, pager, qua_ser, qua_currect);
        pager.setAdapter(pager_adapter);
        adapter = new QuestionSlideAdapterSolution(ActivityQuestionQuizSolution.this, data_arr, pager, drawer, qua_currect);

        recycler.setLayoutManager(new GridLayoutManager(this, 4));
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int oss = position + 1;
                tvQuestionNo.setText(oss + "/" + data_arr.size());

            }

            @Override
            public void onPageSelected(int position) {
                // pb.setProgress((float) ((this.attemptCount * 100) / data_arr.size()));
                pb.setProgress((int)(float) ((position * 100) / data_arr.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        timer.setVisibility(View.GONE);

       /* CountDownTimer count = new CountDownTimer(600000, 1000) {
            @SuppressLint({"DefaultLocale"})
            public void onTick(long millisUntilFinished) {
                timer.setText(String.format("%02d : %02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))}));

            }

            public void onFinish() {
                try {
                    startActivity(new Intent(ActivityQuestionQuizSolution.this, ActivityViewResult.class).putExtra("qua_ser", qua_ser).putExtra("qua_currect", qua_currect).putExtra("data", data_arr));
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();*/

    }
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Rect viewRect = new Rect();
//        outside.getGlobalVisibleRect(viewRect);
//
//        if (!viewRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
//
//            if (drawer.isDrawerOpen(Gravity.RIGHT)) {
//                drawer.closeDrawer(Gravity.RIGHT);
//            } else {
//                drawer.openDrawer(Gravity.RIGHT);
//                adapter.notifyDataSetChanged();
//
//            }
//
//        }
//        return super.dispatchTouchEvent(ev);
//    }
}
