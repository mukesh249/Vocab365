package com.example.advosoft.vocab365.Adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.advosoft.vocab365.Model.DateItem;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.activities.DemoHomeActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DateAdapterMain extends PagerAdapter {
    Activity activity;
    ViewPager pagerDate;
    ArrayList<DateItem> testitem;



    public DateAdapterMain(Activity activity, ArrayList<DateItem> testitem, ViewPager pagerDate) {
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
        View itemView = inflater.inflate(R.layout.date_pager, (ViewGroup) collection, false);
        RelativeLayout mainLayout = (RelativeLayout) itemView.findViewById(R.id.mainLayout);
        TextView date = (TextView) itemView.findViewById(R.id.date);


        mainLayout.setRotationY(180.0f);
        //date.setText(((DateItem) this.testitem.get(position)).getDate().replace(",", " "));
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DateAdapterMain.this.getParentActivity().getcalendar();
            }
        });

        String id = ((DateItem) this.testitem.get(position)).getId();
        String webDate = ((DateItem) this.testitem.get(position)).getDate();
        String year = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
        date.setText(webDate);


        return itemView;
    }

    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    public DemoHomeActivity getParentActivity() {
        if (this.activity instanceof DemoHomeActivity) {
            return (DemoHomeActivity) this.activity;
        }
        return null;
    }
}
