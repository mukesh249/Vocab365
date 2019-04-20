package com.example.advosoft.vocab365.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.advosoft.vocab365.Adapter.Tab_PageAdapter;
import com.example.advosoft.vocab365.R;

import java.util.Objects;

public class Lession_and_practice extends AppCompatActivity {
    Tab_PageAdapter tab_pageAdapter;
    ViewPager viewPagerTab;
    TabLayout tabLayout;
    String sub_cat_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lession_and_practice);

        sub_cat_id = getIntent().getExtras().getString("subcat_id","");
        Objects.requireNonNull(getSupportActionBar()).setTitle(getIntent().getExtras().getString("subcat_name",""));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //---------------------------------Add Title on TabLayout----------------------------------
        tabLayout.addTab(tabLayout.newTab().setText("Lesson"));
        tabLayout.addTab(tabLayout.newTab().setText("Practice"));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        //-------------------------Fragments set on ViewPager----------------------
        viewPager.setAdapter(new Tab_PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
