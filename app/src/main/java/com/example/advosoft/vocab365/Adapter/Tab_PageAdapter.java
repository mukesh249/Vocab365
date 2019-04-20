package com.example.advosoft.vocab365.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.advosoft.vocab365.fragments.Lession;
import com.example.advosoft.vocab365.fragments.Practice;

/**
 * Created by mukku on 1/29/2018.
 */

public class Tab_PageAdapter extends FragmentPagerAdapter{

    int mNumOfTabs;

    public Tab_PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Lession tab1 = new Lession();
                return tab1;
            case 1:
                Practice tab2 = new Practice();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
