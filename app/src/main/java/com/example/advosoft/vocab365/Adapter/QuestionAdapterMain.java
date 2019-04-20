package com.example.advosoft.vocab365.Adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.advosoft.vocab365.Model.DateItem;
import com.example.advosoft.vocab365.Model.QuestionModel;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.activities.DemoHomeActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class QuestionAdapterMain extends PagerAdapter {
    Activity activity;
    ViewPager pagerDate;
    ArrayList<QuestionModel> testitem;



    public QuestionAdapterMain(Activity activity, ArrayList<QuestionModel> testitem, ViewPager pagerDate) {
        this.activity = activity;
        this.testitem = testitem;
        this.pagerDate = pagerDate;
    }

    public int getCount() {
        return this.testitem.size();
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public int getItemPosition(Object object) {
        return -2;
    }

    public Object instantiateItem(View collection, int position) {
        String value;
        LayoutInflater inflater = LayoutInflater.from(activity);
        View itemView = inflater.inflate(R.layout.start_test_adapter, (ViewGroup) collection, false);
        TextView ques=(TextView)itemView.findViewById(R.id.txt_question);
        TextView op_1=(TextView)itemView.findViewById(R.id.tvOptionA);
        TextView op_2=(TextView)itemView.findViewById(R.id.tvOptionB);
        TextView op_3=(TextView)itemView.findViewById(R.id.tvOptionC);
        TextView op_4=(TextView)itemView.findViewById(R.id.tvOptionD);
        Log.d("quest",testitem.get(position).getQuestion());
        ques.setText(testitem.get(position).getQuestion());
        op_1.setText(testitem.get(position).getOption_1());
        op_2.setText(testitem.get(position).getOption_2());
        op_3.setText(testitem.get(position).getOption_3());
        op_4.setText(testitem.get(position).getOption_4());
        return itemView;
    }

    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }


}
