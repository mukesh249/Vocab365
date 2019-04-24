package com.example.advosoft.vocab365.activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.example.advosoft.vocab365.Adapter.CustomPagerAdapter;
import com.example.advosoft.vocab365.Adapter.HomePagerAdapter;
import com.example.advosoft.vocab365.Model.DateItem;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.fragments.MyApplication;
import com.example.advosoft.vocab365.fragments.PastListFragment;
import com.example.advosoft.vocab365.fragments.ProfileFragment;
import com.example.advosoft.vocab365.fragments.QuizFragment;
import com.example.advosoft.vocab365.fragments.VocabListFragment;
import com.example.advosoft.vocab365.fragments.VocabPlusFragment;
import com.example.advosoft.vocab365.views.Constants;
import com.example.advosoft.vocab365.views.CustomViewPager;
import com.example.advosoft.vocab365.views.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

/**
 * Created by advosoft on 8/20/2018.
 */

public class DemoHomeActivity extends BaseActiivty implements OnDateSelectedListener {
    @BindView(R.id.pagerdate)
    ViewPager pagerdate;
    @BindView(R.id.arrow)
    ImageView arrow;
    @BindView(R.id.rl_pager)
    CardView rlPager;
    @BindView(R.id.pager_left)
    ImageView pagerLeft;
    @BindView(R.id.pager_right)
    ImageView pagerRight;
    @BindView(R.id.SearchWordDB)
    SearchView SearchWordDB;
    @BindView(R.id.topLayout)
    RelativeLayout topLayout;
    @BindView(R.id.pager)
    CustomViewPager pager;
    @BindView(R.id.frame_progress_bar)
    ProgressBar frameProgressBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.round_img)
    ImageView roundImg;
    @BindView(R.id.tab_outer)
    RelativeLayout tabOuter;
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.datePicker)
    MaterialCalendarView datePicker;
    @BindView(R.id.calendar)
    RelativeLayout calendar;
    @BindView(R.id.progress_view)
    ProgressBar progressView;
    @BindView(R.id.MainLayout)
    RelativeLayout MainLayout;
    CustomPagerAdapter adapter;
//    private static DemoHomeActivity sInstance = null;

    public static DemoHomeActivity mInstance;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_home);
        ButterKnife.bind(this);

        mInstance = this;

//        sInstance = this;
        rlPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar();
            }
        });
        pagerLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagerdate.setCurrentItem(pagerdate.getCurrentItem() + 1, true);
            }
        });

//        pagerdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calendar();
//            }
//        });


        MyApplication.hideKeyBoard(DemoHomeActivity.this);
        pagerRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pagerdate.setCurrentItem(pagerdate.getCurrentItem() - 1, true);
            }
        });

        SearchWordDB.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchWordDB.setBackground(getResources().getDrawable(R.drawable.round_btn_white));
                View searchViewView = (View) SearchWordDB;
                searchViewView.getLayoutParams().width= SearchView.LayoutParams.MATCH_PARENT;
                rlPager.setVisibility(View.GONE);
                pagerLeft.setVisibility(View.GONE);
                pagerRight.setVisibility(View.GONE);

            }
        });
        SearchWordDB.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                SearchWordDB.setBackground(null);
                View searchViewView = (View) SearchWordDB;
                searchViewView.getLayoutParams().width= SearchView.LayoutParams.WRAP_CONTENT;
                rlPager.setVisibility(View.VISIBLE);
                pagerLeft.setVisibility(View.VISIBLE);
                pagerRight.setVisibility(View.VISIBLE);

                return false;
            }
        });
        SearchWordDB.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (MyApplication.current_fragment.compareTo("PastFragment")==0){
                    PastListFragment.getInstance(getBundle()).searchsubmit(newText);
                }

                return true;
            }
        });



        pagerdate.setRotationY(180.0f);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = sdf.format(cal.getTime());
        MyApplication.current_date = strDate;
        MyApplication.current_fragment="PastFragment";


        if (MyApplication.arrDateItemwithBlank.size() == 0 || MyApplication.arrDateItem.size() == 0) {
            List<String> dates = getDates("2016-10-01", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
            Set<DateItem> set = new HashSet();
            for (String date : dates) {
                DateItem ad = new DateItem();
                ad.setDate(date);
                ad.setId(date);
                ad.setVocabCount(10);
                set.add(ad);
                MyApplication.arrDateItem.add(ad);
                MyApplication.arrDateItemwithBlank.add(ad);
            }
            Collections.reverse(MyApplication.arrDateItem);
            Collections.reverse(MyApplication.arrDateItemwithBlank);
        }

        Log.d("date_arr", MyApplication.arrDateItem.size() + "");
        Log.d("userId",MyApplication.getUserID(Constants.UserID));
        Daily();

        HomePagerAdapter Pageradapter = new HomePagerAdapter(getSupportFragmentManager());
        Pageradapter.addFragment(new PastListFragment(), getResources().getString(R.string.home));
        Pageradapter.addFragment(new QuizFragment(), getResources().getString(R.string.quiz));
        Pageradapter.addFragment(new VocabPlusFragment(), getResources().getString(R.string.prime));
        Pageradapter.addFragment(new ProfileFragment(), getResources().getString(R.string.profile));
        pager.setAdapter(Pageradapter);
//        pager.setOffscreenPageLimit(3);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(pager);
        final Drawable draw = Utils.DrawableChange(this, R.drawable.home, getResources().getColor(R.color.colorPrimary));
        final Drawable draw5 = Utils.DrawableChange(this, R.drawable.home, getResources().getColor(R.color.gray_4));
        // final Drawable draw6 = Utils.DrawableChange(this, R.drawable.vocab, getResources().getColor(R.color.gray_4));
        final Drawable draw7 = Utils.DrawableChange(this, R.drawable.quiz, getResources().getColor(R.color.gray_4));
        final Drawable draw8 = Utils.DrawableChange(this, R.drawable.prime, getResources().getColor(R.color.gray_4));
        final Drawable draw9 = Utils.DrawableChange(this, R.drawable.setting, getResources().getColor(R.color.gray_4));

        try {
            View ll_one = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tab_custom, null);
            ((ImageView) ll_one.findViewById(R.id.img)).setImageDrawable(draw);
            MyApplication.current_fragment="PastFragment";
            tabLayout.getTabAt(0).setCustomView(ll_one);
            tabLayout.getTabAt(0).setTag(getResources().getString(R.string.home));

            View ll_three = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tab_custom, null);
            ((ImageView) ll_three.findViewById(R.id.img)).setImageDrawable(draw7);

            tabLayout.getTabAt(1).setCustomView(ll_three);
            tabLayout.getTabAt(1).setTag(getResources().getString(R.string.quiz));
            View ll_four = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tab_custom, null);
            ((ImageView) ll_four.findViewById(R.id.img)).setImageDrawable(draw8);

            tabLayout.getTabAt(2).setCustomView(ll_four);
            tabLayout.getTabAt(2).setTag(getResources().getString(R.string.prime));
            View ll_five = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tab_custom, null);
            ((ImageView) ll_five.findViewById(R.id.img)).setImageDrawable(draw9);

            tabLayout.getTabAt(3).setCustomView(ll_five);
            tabLayout.getTabAt(3).setTag(getResources().getString(R.string.profile));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager) {
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                ImageView img = (ImageView) ((RelativeLayout) tab.getCustomView()).getChildAt(0);
                // ((TextView) ((RelativeLayout) tab.getCustomView()).getChildAt(1)).setTextColor(ContextCompat.getColor(DemoHomeActivity.this, R.color.colorPrimary));
                switch (tab.getPosition()) {
                    case 0:
                        TabBack(0, 8, 0);
                        img.setImageDrawable(draw);
                       // SearchWordDB.setVisibility(View.VISIBLE);
                        MyApplication.current_fragment="PastFragment";
                        if (MyApplication.current_fragment .compareTo("PastFragment")==0) {
                            PastListFragment pastListFragment = new PastListFragment();
                            pastListFragment.Reload();
                        } else if (MyApplication.current_fragment .compareTo("QuizFragment")==0) {
                            QuizFragment quizFragment = new QuizFragment();
                            quizFragment.Reload();
                        }
                        break;

                    case 1:
                        MyApplication.current_fragment="QuizFragment";

                        //SearchWordDB.setVisibility(View.GONE);
                        topLayout.setVisibility(View.VISIBLE);
                        calendar.setVisibility(View.INVISIBLE);
                        SearchWordDB.setVisibility(View.INVISIBLE);
                        //TabBack(0, 8, 8);
                        img.setImageDrawable(Utils.DrawableChange(DemoHomeActivity.this, R.drawable.quiz, getResources().getColor(R.color.colorPrimary)));
                        if (MyApplication.current_fragment .compareTo("PastFragment")==0) {
                            PastListFragment pastListFragment = new PastListFragment();
                            pastListFragment.Reload();
                        } else if (MyApplication.current_fragment .compareTo("QuizFragment")==0) {
                           // SearchWordDB.setVisibility(View.GONE);
                            QuizFragment quizFragment = new QuizFragment();
                            quizFragment.Reload();
                        }

                        break;
                    case 2:
                        MyApplication.current_fragment="VocabPlusFragment";
                        TabBack(8, 8, 8);
                        img.setImageDrawable(Utils.DrawableChange(DemoHomeActivity.this, R.drawable.prime, getResources().getColor(R.color.colorPrimary)));
                        break;
                    case 3:
                        MyApplication.current_fragment="ProfileFragment";
                        TabBack(8, 8, 8);
                        img.setImageDrawable(Utils.DrawableChange(DemoHomeActivity.this, R.drawable.setting, getResources().getColor(R.color.colorPrimary)));
                        break;
                }
                img.startAnimation(AnimationUtils.loadAnimation(DemoHomeActivity.this, R.anim.hyperspace_zoom));
            }

            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                ImageView img = (ImageView) ((RelativeLayout) tab.getCustomView()).getChildAt(0);
                //  ((TextView) ((RelativeLayout) tab.getCustomView()).getChildAt(1)).setTextColor(ContextCompat.getColor(DemoHomeActivity.this, R.color.gray_4));
                switch (tab.getPosition()) {
                    case 0:
                        img.setImageDrawable(draw5);
                        return;
                    case 1:
                        img.setImageDrawable(draw7);
                        return;
                    case 2:
                        img.setImageDrawable(draw8);
                        return;
                    case 3:
                        img.setImageDrawable(draw9);
                        return;
                    default:
                        return;
                }
            }

            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }
        });
    }
    void TabBack(int visible, int gone, int visi) {
        topLayout.setVisibility(visible);
        calendar.setVisibility(gone);
        SearchWordDB.setVisibility(visi);
    }
    public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
        String selected_date = date.toString().trim();
        selected_date = selected_date.substring(selected_date.indexOf("{") + 1);
        String[] datee = selected_date.substring(0, selected_date.indexOf("}")).split("-");
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.MONTH, Integer.parseInt(datee[1]));
        String monthName = new SimpleDateFormat("MMM").format(calendar1.getTime());
        String day = String.valueOf(datee[2]);
        if (String.valueOf(datee[2]).length() == 1) {
            day = "0" + day;
        }
        for (int i = 0; i < MyApplication.arrDateItem.size(); i++) {
            if (((DateItem) MyApplication.arrDateItem.get(i)).getDate().equals("" + day + " " + monthName + "," + datee[0])) {
                pagerdate.setCurrentItem(i, true);
            }
        }
        MyApplication.current_date = day + "-" + monthName + "-" + datee[0];
        calendar.setVisibility(View.GONE);
        try {
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressLint({"SimpleDateFormat"})
    void calendar() {
        if (calendar.getVisibility() == View.VISIBLE) {
            calendar.setVisibility(View.GONE);
            arrow.setImageDrawable(Utils.DrawableChange(this, R.drawable.small_arrow, getResources().getColor(R.color.colorPrimary)));
            return;
        }
        datePicker.clearSelection();
        datePicker.state().edit().setMinimumDate(CalendarDay.from(new GregorianCalendar(2016, 9, 1))).setMaximumDate(CalendarDay.from(new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE)))).commit();

         CalendarDay calendarDay = CalendarDay.from(new GregorianCalendar(Integer.parseInt(Utils.year(pagerdate.getCurrentItem())), Integer.parseInt(Utils.month(pagerdate.getCurrentItem())) - 1, Integer.parseInt(Utils.day(pagerdate.getCurrentItem()))).getTime());
         datePicker.setCurrentDate(calendarDay);
         datePicker.setDateSelected(calendarDay, true);

        calendar.setVisibility(View.VISIBLE);
        Animation animSequential = AnimationUtils.loadAnimation(this, R.anim.bottom);
        datePicker.startAnimation(animSequential);
        pager.startAnimation(animSequential);
        arrow.setImageResource(R.drawable.small_arrow_top);
        datePicker.setOnDateChangedListener(this);
    }
    public static List<String> getDates(String dateString1, String dateString2) {
        ArrayList<String> dates = new ArrayList();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = df1.parse(dateString1);
            Date date2 = df1.parse(dateString2);
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            while (!cal1.after(cal2)) {
                String[] s = format1.format(cal1.getTime()).split("-");
                dates.add(s[2] + " " + String.valueOf(Utils.getMonth(Integer.parseInt(s[1]) - 1).substring(0, 3)) + "," + s[0]);
                cal1.add(Calendar.DATE, 1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dates;
    }
    public void getcalendar() {
        calendar();
    }
    public void Daily() {

        try {
            adapter = new CustomPagerAdapter(this, MyApplication.arrDateItemwithBlank, pagerdate);
            pagerdate.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            pagerdate.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    if (position == 0) {
                        pagerRight.setVisibility(View.GONE);
                    } else {
                        pagerRight.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onPageSelected(int position) {
                    try {
                        DateItem dateModel = (DateItem) MyApplication.arrDateItem.get(position);
                        String date = dateModel.getDate();
                        String last_select = date;
                        String last_date1;
                        Date startDate;
                        DateFormat df = new SimpleDateFormat("dd MMM,yyyy", Locale.ENGLISH);
                        DateFormat target_date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                        startDate = df.parse(last_select);
                        last_date1 = target_date.format(startDate);
                        MyApplication.current_date = last_date1;
                        Log.d("position",position+"");
                        Log.d("current_fragment",MyApplication.current_fragment);
//                        SearchWordDB.setVisibility(View.VISIBLE);
                        if (MyApplication.current_fragment .compareTo("PastFragment")==0) {
                            PastListFragment pastListFragment = new PastListFragment();
                            pastListFragment.Reload();
                        } else if (MyApplication.current_fragment .compareTo("QuizFragment")==0) {
                            QuizFragment quizFragment = new QuizFragment();
                            quizFragment.Reload();
                        }

                    } catch (Exception e) {

                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.hideKeyBoard(DemoHomeActivity.this);
    }

    public void DailyMeh(){
        MyApplication.current_fragment="QuizFragment";

        //SearchWordDB.setVisibility(View.GONE);

        tabLayout.setScrollPosition(1,0f,true);
        pager.setCurrentItem(1);

        /*topLayout.setVisibility(View.VISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        SearchWordDB.setVisibility(View.INVISIBLE);
        //TabBack(0, 8, 8);
       // img.setImageDrawable(Utils.DrawableChange(DemoHomeActivity.this, R.drawable.quiz, getResources().getColor(R.color.colorPrimary)));
        if (MyApplication.current_fragment .compareTo("PastFragment")==0) {
            PastListFragment pastListFragment = new PastListFragment();
            pastListFragment.Reload();
        } else if (MyApplication.current_fragment .compareTo("QuizFragment")==0) {
            // SearchWordDB.setVisibility(View.GONE);
            QuizFragment quizFragment = new QuizFragment();
            quizFragment.Reload();
        }*/
    }


    public static DemoHomeActivity getInstance(){
        return mInstance;
    }
//
//    public void PostMehtod(){
//        Intent intent = new Intent(DemoHomeActivity.this,DemoHomeActivity.class);
//        startActivity(intent);
////        finish();
//        //getSupportFragmentManager().beginTransaction().replace(R.id.container,new PastListFragment()).commit();
//    }
}
