package com.example.advosoft.vocab365.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.advosoft.vocab365.Model.QuestionModel;
import com.example.advosoft.vocab365.R;

import java.util.ArrayList;


/**
 * Created by Advosoft2 on 3/1/2017.
 */

public class PagerAdapterSolution extends android.support.v4.view.PagerAdapter {

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

    public PagerAdapterSolution(ArrayList<QuestionModel> data, Context mContext, ViewPager viewPager, String qua_ser[], String qua_currect[]) {
        this.data_feed = data;
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.vpager = viewPager;
        this.qua_ser = qua_ser;
        this.qua_currect = qua_currect;

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

        final LinearLayout li_1 = (LinearLayout) itemView.findViewById(R.id.QueALayout);
        final LinearLayout li_2 = (LinearLayout) itemView.findViewById(R.id.QueBLayout);
        final LinearLayout li_3 = (LinearLayout) itemView.findViewById(R.id.QueCLayout);
        final LinearLayout li_4 = (LinearLayout) itemView.findViewById(R.id.QueDLayout);
        final LinearLayout li_5 = (LinearLayout) itemView.findViewById(R.id.QueELayout);

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

        final TextView des = (TextView) itemView.findViewById(R.id.Explation);
        Button BtnSubmit = (Button) itemView.findViewById(R.id.BtnSubmit);

        collection.addView(itemView);
        Log.d("quest", datafeed.getQuestion());
        ques.setText(datafeed.getQuestion());
        op_1.setText(datafeed.getOption_1());
        op_2.setText(datafeed.getOption_2());
        op_3.setText(datafeed.getOption_3());
        op_4.setText(datafeed.getOption_4());
        op_5.setText(datafeed.getOption_5());
        des.setText(Html.fromHtml(datafeed.getDescription()));



        if (qua_ser[position].compareTo("1") == 0) {
            li_1.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            op_1.setTextColor(mContext.getResources().getColor(R.color.white));
            IDA.setBackground(mContext.getResources().getDrawable(R.drawable.border_white));
            IDA.setTextColor(mContext.getResources().getColor(R.color.white));
        } else if (qua_ser[position].compareTo("2") == 0) {
            li_2.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            op_2.setTextColor(mContext.getResources().getColor(R.color.white));
            IDB.setBackground(mContext.getResources().getDrawable(R.drawable.border_white));
            IDB.setTextColor(mContext.getResources().getColor(R.color.white));
        } else if (qua_ser[position].compareTo("3") == 0) {
            li_3.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            op_3.setTextColor(mContext.getResources().getColor(R.color.white));
            IDC.setBackground(mContext.getResources().getDrawable(R.drawable.border_white));
            IDC.setTextColor(mContext.getResources().getColor(R.color.white));
        } else if (qua_ser[position].compareTo("4") == 0) {
            li_4.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            op_4.setTextColor(mContext.getResources().getColor(R.color.white));
            IDD.setBackground(mContext.getResources().getDrawable(R.drawable.border_white));
            IDD.setTextColor(mContext.getResources().getColor(R.color.white));
        } else if (qua_ser[position].compareTo("5") == 0) {
            li_5.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            op_5.setTextColor(mContext.getResources().getColor(R.color.white));
            IDE.setBackground(mContext.getResources().getDrawable(R.drawable.border_white));
            IDE.setTextColor(mContext.getResources().getColor(R.color.white));
        }

        if (datafeed.getAnswer().compareTo(datafeed.getOption_1()) == 0) {
            li_1.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            op_1.setTextColor(mContext.getResources().getColor(R.color.white));
            IDA.setBackground(mContext.getResources().getDrawable(R.drawable.border_white));
            IDA.setTextColor(mContext.getResources().getColor(R.color.white));
        } else if (datafeed.getAnswer().compareTo(datafeed.getOption_2()) == 0) {
            li_2.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            op_2.setTextColor(mContext.getResources().getColor(R.color.white));
            IDB.setBackground(mContext.getResources().getDrawable(R.drawable.border_white));
            IDB.setTextColor(mContext.getResources().getColor(R.color.white));
        } else if (datafeed.getAnswer().compareTo(datafeed.getOption_3()) == 0) {
            li_3.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            op_3.setTextColor(mContext.getResources().getColor(R.color.white));
            IDC.setBackground(mContext.getResources().getDrawable(R.drawable.border_white));
            IDC.setTextColor(mContext.getResources().getColor(R.color.white));
        } else if (datafeed.getAnswer().compareTo(datafeed.getOption_4()) == 0) {
            li_4.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            op_4.setTextColor(mContext.getResources().getColor(R.color.white));
            IDD.setBackground(mContext.getResources().getDrawable(R.drawable.border_white));
            IDD.setTextColor(mContext.getResources().getColor(R.color.white));
        } else if (datafeed.getAnswer().compareTo(datafeed.getOption_5()) == 0) {
            li_5.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            op_5.setTextColor(mContext.getResources().getColor(R.color.white));
            IDE.setBackground(mContext.getResources().getDrawable(R.drawable.border_white));
            IDE.setTextColor(mContext.getResources().getColor(R.color.white));
        }


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
