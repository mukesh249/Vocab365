package com.example.advosoft.vocab365.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.advosoft.vocab365.Model.QuestionModel;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.activities.ActivityQuestionQuiz;
import com.example.advosoft.vocab365.activities.ActivityViewResult;
import com.example.advosoft.vocab365.fragments.MyApplication;

import java.util.ArrayList;


/**
 * Created by Advosoft2 on 3/1/2017.
 */

public class PagerAdapter extends android.support.v4.view.PagerAdapter {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final String DB_NAME = "QuranWords.sqlite";
    private static int oss;
    ArrayList<QuestionModel> data_feed;
    ViewPager vpager;
    Boolean check_flipper = false;
    String part;
    String typestr = "";
    String level;
    private LayoutInflater mInflater;
    private Context mContext;
    String qua_ser[];
    String qua_currect[];
    CountDownTimer timer;

    public PagerAdapter(ArrayList<QuestionModel> data, Context mContext, ViewPager viewPager, String qua_ser[], String qua_currect[], CountDownTimer timer) {
        this.data_feed = data;
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.vpager = viewPager;
        this.qua_ser = qua_ser;
        this.qua_currect = qua_currect;
        this.timer = timer;

    }


    @Override
    public int getCount() {
        return data_feed.size();
    }


    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final ViewGroup itemView = (ViewGroup) inflater.inflate(R.layout.start_test_adapter, collection, false);
        final QuestionModel datafeed = data_feed.get(position);
        TextView ques = (TextView) itemView.findViewById(R.id.txt_question);
        final TextView IDA = (TextView) itemView.findViewById(R.id.IDA);
        final TextView IDB = (TextView) itemView.findViewById(R.id.IDB);
        final TextView IDC = (TextView) itemView.findViewById(R.id.IDC);
        final TextView IDD = (TextView) itemView.findViewById(R.id.IDD);
        final TextView IDE = (TextView) itemView.findViewById(R.id.IDE);

        final TextView op_1 = (TextView) itemView.findViewById(R.id.tvOptionA);
        final TextView op_2 = (TextView) itemView.findViewById(R.id.tvOptionB);
        final TextView op_3 = (TextView) itemView.findViewById(R.id.tvOptionC);
        final TextView op_4 = (TextView) itemView.findViewById(R.id.tvOptionD);
        final TextView op_5 = (TextView) itemView.findViewById(R.id.tvOptionE);

        final LinearLayout ll_a=(LinearLayout)itemView.findViewById(R.id.QueALayout);
        final LinearLayout ll_b=(LinearLayout)itemView.findViewById(R.id.QueBLayout);
        final LinearLayout ll_c=(LinearLayout)itemView.findViewById(R.id.QueCLayout);
        final LinearLayout ll_d=(LinearLayout)itemView.findViewById(R.id.QueDLayout);
        final LinearLayout ll_e=(LinearLayout)itemView.findViewById(R.id.QueELayout);

        Button BtnSubmit = (Button) itemView.findViewById(R.id.BtnSubmit);

        collection.addView(itemView);
        Log.d("quest", datafeed.getQuestion());
        ques.setText(datafeed.getQuestion());
        op_1.setText(datafeed.getOption_1());
        op_2.setText(datafeed.getOption_2());
        op_3.setText(datafeed.getOption_3());
        op_4.setText(datafeed.getOption_4());
        op_5.setText(datafeed.getOption_5());

        if (position == data_feed.size() - 1) {
            BtnSubmit.setVisibility(View.VISIBLE);
        } else {
            BtnSubmit.setVisibility(View.GONE);
        }
        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                mContext.startActivity(new Intent(mContext, ActivityViewResult.class).putExtra("qua_ser", qua_ser).putExtra("qua_currect", qua_currect).putExtra("data", data_feed));
                ((ActivityQuestionQuiz) mContext).finish();
            }
        });
        ll_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_a.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                ll_b.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));
                ll_c.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));
                ll_d.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));
                ll_e.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));

                op_1.setTextColor(mContext.getResources().getColor(R.color.white));
                op_2.setTextColor(mContext.getResources().getColor(R.color.black));
                op_3.setTextColor(mContext.getResources().getColor(R.color.black));
                op_4.setTextColor(mContext.getResources().getColor(R.color.black));
                op_5.setTextColor(mContext.getResources().getColor(R.color.black));

                IDA.setTextColor(mContext.getResources().getColor(R.color.white));
                IDB.setTextColor(mContext.getResources().getColor(R.color.black));
                IDC.setTextColor(mContext.getResources().getColor(R.color.black));
                IDD.setTextColor(mContext.getResources().getColor(R.color.black));
                IDE.setTextColor(mContext.getResources().getColor(R.color.black));

                IDA.setBackground(mContext.getResources().getDrawable(R.drawable.border_white));
                IDB.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));
                IDC.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));
                IDD.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));
                IDE.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));

                if (datafeed.getAnswer().compareTo(datafeed.getOption_1()) == 0) {
                    qua_currect[position] = "1";
                } else {
                    qua_currect[position] = "2";
                }
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms

                        if (position == data_feed.size() - 1) {
                            qua_ser[position] = "1";
                        } else {
                            qua_ser[position] = "1";
                            vpager.setCurrentItem(position + 1, true);
                        }

                    }
                }, 500);
            }
        });
        ll_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_a.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));
                ll_b.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                ll_c.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));
                ll_d.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));
                ll_e.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));

                op_1.setTextColor(mContext.getResources().getColor(R.color.black));
                op_2.setTextColor(mContext.getResources().getColor(R.color.white));
                op_3.setTextColor(mContext.getResources().getColor(R.color.black));
                op_4.setTextColor(mContext.getResources().getColor(R.color.black));
                op_5.setTextColor(mContext.getResources().getColor(R.color.black));

                IDA.setTextColor(mContext.getResources().getColor(R.color.black));
                IDB.setTextColor(mContext.getResources().getColor(R.color.white));
                IDC.setTextColor(mContext.getResources().getColor(R.color.black));
                IDD.setTextColor(mContext.getResources().getColor(R.color.black));
                IDE.setTextColor(mContext.getResources().getColor(R.color.black));

                IDA.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));
                IDB.setBackground(mContext.getResources().getDrawable(R.drawable.border_white));
                IDC.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));
                IDD.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));
                IDE.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));

                if (datafeed.getAnswer().compareTo(datafeed.getOption_2()) == 0) {
                    qua_currect[position] = "1";
                } else {
                    qua_currect[position] = "2";
                }

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms

                        if (position == data_feed.size() - 1) {
                            qua_ser[position] = "2";
                        } else {
                            qua_ser[position] = "2";
                            vpager.setCurrentItem(position + 1, true);
                        }

                    }
                }, 500);
            }
        });
        ll_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_a.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));
                ll_b.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));
                ll_c.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                ll_d.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));
                ll_e.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));

                op_1.setTextColor(mContext.getResources().getColor(R.color.black));
                op_2.setTextColor(mContext.getResources().getColor(R.color.black));
                op_3.setTextColor(mContext.getResources().getColor(R.color.white));
                op_4.setTextColor(mContext.getResources().getColor(R.color.black));
                op_5.setTextColor(mContext.getResources().getColor(R.color.black));

                IDA.setTextColor(mContext.getResources().getColor(R.color.black));
                IDB.setTextColor(mContext.getResources().getColor(R.color.black));
                IDC.setTextColor(mContext.getResources().getColor(R.color.white));
                IDD.setTextColor(mContext.getResources().getColor(R.color.black));
                IDE.setTextColor(mContext.getResources().getColor(R.color.black));

                IDA.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));
                IDB.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));
                IDC.setBackground(mContext.getResources().getDrawable(R.drawable.border_white));
                IDD.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));
                IDE.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));

                if (datafeed.getAnswer().compareTo(datafeed.getOption_3()) == 0) {
                    qua_currect[position] = "1";
                } else {
                    qua_currect[position] = "2";
                }
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms

                        if (position == data_feed.size() - 1) {
                            qua_ser[position] = "3";
                        } else {
                            qua_ser[position] = "3";
                            vpager.setCurrentItem(position + 1, true);
                        }

                    }
                }, 500);
            }
        });
        ll_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_a.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));
                ll_b.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));
                ll_c.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));
                ll_d.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                ll_e.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));

                op_1.setTextColor(mContext.getResources().getColor(R.color.black));
                op_2.setTextColor(mContext.getResources().getColor(R.color.black));
                op_3.setTextColor(mContext.getResources().getColor(R.color.black));
                op_4.setTextColor(mContext.getResources().getColor(R.color.white));
                op_5.setTextColor(mContext.getResources().getColor(R.color.black));

                IDA.setTextColor(mContext.getResources().getColor(R.color.black));
                IDB.setTextColor(mContext.getResources().getColor(R.color.black));
                IDC.setTextColor(mContext.getResources().getColor(R.color.black));
                IDD.setTextColor(mContext.getResources().getColor(R.color.white));
                IDE.setTextColor(mContext.getResources().getColor(R.color.black));

                IDA.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));
                IDB.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));
                IDC.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));
                IDD.setBackground(mContext.getResources().getDrawable(R.drawable.border_white));
                IDE.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));

                if (datafeed.getAnswer().compareTo(datafeed.getOption_4()) == 0) {
                    qua_currect[position] = "1";
                } else {
                    qua_currect[position] = "2";
                }
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms

                        if (position == data_feed.size() - 1) {
                            qua_ser[position] = "4";
                        } else {
                            qua_ser[position] = "4";
                            vpager.setCurrentItem(position + 1, true);
                        }

                    }
                }, 500);
            }
        });
        ll_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_a.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));
                ll_b.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));
                ll_c.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));
                ll_d.setBackground(mContext.getResources().getDrawable(R.drawable.rect_border));
                ll_e.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));

                op_1.setTextColor(mContext.getResources().getColor(R.color.black));
                op_2.setTextColor(mContext.getResources().getColor(R.color.black));
                op_3.setTextColor(mContext.getResources().getColor(R.color.black));
                op_4.setTextColor(mContext.getResources().getColor(R.color.black));
                op_5.setTextColor(mContext.getResources().getColor(R.color.white));

                IDA.setTextColor(mContext.getResources().getColor(R.color.black));
                IDB.setTextColor(mContext.getResources().getColor(R.color.black));
                IDC.setTextColor(mContext.getResources().getColor(R.color.black));
                IDD.setTextColor(mContext.getResources().getColor(R.color.black));
                IDE.setTextColor(mContext.getResources().getColor(R.color.white));

                IDA.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));
                IDB.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));
                IDC.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));
                IDD.setBackground(mContext.getResources().getDrawable(R.drawable.opt_unchkd));
                IDE.setBackground(mContext.getResources().getDrawable(R.drawable.border_white));

                if (datafeed.getAnswer().compareTo(datafeed.getOption_5()) == 0) {
                    qua_currect[position] = "1";
                } else {
                    qua_currect[position] = "2";
                }
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms

                        if (position == data_feed.size() - 1) {
                            qua_ser[position] = "5";
                        } else {
                            qua_ser[position] = "5";
                            vpager.setCurrentItem(position + 1, true);
                        }

                    }
                }, 500);
            }
        });


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    /* @Override
     public CharSequence getPageTitle(int position) {
         ModelObject customPagerEnum = ModelObject.values()[position];
         return mContext.getString(customPagerEnum.getTitleResId());
     }*/

}
