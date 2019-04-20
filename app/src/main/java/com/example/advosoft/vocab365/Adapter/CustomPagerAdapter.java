package com.example.advosoft.vocab365.Adapter;

/**
 * Created by advosoft on 8/24/2018.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.advosoft.vocab365.Model.DateItem;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.activities.DemoHomeActivity;

import java.util.ArrayList;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;

    ViewPager pagerDate;
    ArrayList<DateItem> testitem;

    public CustomPagerAdapter(Context context, ArrayList<DateItem> testitem, ViewPager pagerDate) {
        this.mContext = context;
        this.testitem = testitem;
        this.pagerDate = pagerDate;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        DateItem modelObject = testitem.get(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.date_pager, collection, false);
        TextView date = (TextView) layout.findViewById(R.id.date);
        RelativeLayout mainLayout = (RelativeLayout) layout.findViewById(R.id.mainLayout);
        mainLayout.setRotationY(180.0f);
        date.setText(modelObject.getDate().replace(",", " "));

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DemoHomeActivity.getInstance().getcalendar();
//                Toast.makeText(mContext, "Slide 1 Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        ViewPager vp = (ViewPager) collection;
        vp.addView(layout, 0);

//        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        ViewPager vp = (ViewPager) collection;
        View viw = (View) view;
        vp.removeView(viw);
    }

    @Override
    public int getCount() {
        return testitem.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

   /* @Override
    public CharSequence getPageTitle(int position) {
        DateItem customPagerEnum = testitem.get(position);
        return mContext.getString(customPagerEnum.getTitleResId());
    }*/
//   public DemoHomeActivity getParentActivity() {
//       if (mContext instanceof DemoHomeActivity) {
//           return (DemoHomeActivity) mContext;
//       }
//       return null;
//   }
}