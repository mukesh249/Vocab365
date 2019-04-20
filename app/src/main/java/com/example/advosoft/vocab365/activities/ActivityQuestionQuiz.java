package com.example.advosoft.vocab365.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.advosoft.vocab365.Adapter.PagerAdapter;
import com.example.advosoft.vocab365.Adapter.QuestionSlideAdapter;
import com.example.advosoft.vocab365.Model.QuestionModel;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.fragments.MyApplication;
import com.example.advosoft.vocab365.views.Constants;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by advosoft on 8/30/2018.
 */

public class ActivityQuestionQuiz extends BaseActiivty {
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
    ArrayList<QuestionModel> data_arr;
    QuestionSlideAdapter adapter;
    PagerAdapter pager_adapter;
    static String qua_ser[];
    static String qua_currect[];
    CountDownTimer count;
    public static ActivityQuestionQuiz mInstance =null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_quiz);
        ButterKnife.bind(this);
        data_arr = (ArrayList<QuestionModel>) getIntent().getSerializableExtra("data");
        qua_ser = new String[data_arr.size()];
        qua_currect = new String[data_arr.size()];

        mInstance = this;

        imgList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                    adapter.notifyDataSetChanged();
                    int count = 0;
                    for (int i = 0; i < qua_ser.length; i++) {
                        if (qua_ser[i].compareTo("0") != 0) {
                            count++;
                        }
                    }
                    tvTotalView.setText("Total Question Attempted : " + count);
                }
            }
        });

        for (int i = 0; i < data_arr.size(); i++) {
            qua_ser[i] = "0";
            qua_currect[i] = "0";
        }

        int m = Integer.parseInt(data_arr.get(0).getTestTime());
        Log.d("MIn_time", MyApplication.getMIN(Constants.MIN));
        count = new CountDownTimer(m*60000, 1000) {
            @SuppressLint({"DefaultLocale"})
            public void onTick(long millisUntilFinished) {
                timer.setText(String.format("%02d : %02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))}));
            }

            public void onFinish() {
                try {
                    startActivity(new Intent(ActivityQuestionQuiz.this, ActivityViewResult.class).putExtra("qua_ser", qua_ser).putExtra("qua_currect", qua_currect).putExtra("data", data_arr));
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


        pager.setOffscreenPageLimit(25);
        pager_adapter = new PagerAdapter(data_arr, ActivityQuestionQuiz.this, pager, qua_ser, qua_currect,count);
        pager.setAdapter(pager_adapter);
        adapter = new QuestionSlideAdapter(ActivityQuestionQuiz.this, data_arr, pager, drawer, qua_ser);

        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
                pb.setProgress((int) ((position * 100) / data_arr.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ImageView imgPlay = (ImageView)findViewById(R.id.img_play);
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count.cancel();
                onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        count.cancel();
        super.onBackPressed();
    }

    public static ActivityQuestionQuiz getInstance(){
       return mInstance;
    }
    public void Reattemptmethod(){
        startActivity(new Intent(ActivityQuestionQuiz.this, ActivityViewResult.class).putExtra("qua_ser", qua_ser).putExtra("qua_currect", qua_currect).putExtra("data", data_arr));
        finish();
    }

//    boolean isOutSideClicked;
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            if (drawer.isDrawerOpen(Gravity.RIGHT)) { //Your code here to check whether drawer is open or not.
//
//                View content = findViewById(R.id.drawer); //drawer view id
//                int[] contentLocation = new int[2];
//                content.getLocationOnScreen(contentLocation);
//                Rect rect = new Rect(contentLocation[0],
//                        contentLocation[1],
//                        contentLocation[0] + content.getWidth(),
//                        contentLocation[1] + content.getHeight());
//
//                if (!(rect.contains((int) event.getX(), (int) event.getY()))) {
//                    isOutSideClicked = true;
//                } else {
//                    isOutSideClicked = false;
//                }
//                drawer.closeDrawer(Gravity.RIGHT);
//
//            } else {
//                return super.dispatchTouchEvent(event);
//            }
//        } else if (event.getAction() == MotionEvent.ACTION_DOWN && isOutSideClicked) {
//            isOutSideClicked = false;
//            return super.dispatchTouchEvent(event);
//        } else if (event.getAction() == MotionEvent.ACTION_MOVE && isOutSideClicked) {
//            return super.dispatchTouchEvent(event);
//        }
//
//        if (isOutSideClicked) {
//            drawer.closeDrawer(Gravity.RIGHT);
//            return true; //restrict the touch event here
//        }else{
//            return super.dispatchTouchEvent(event);
//        }
//    }
}
