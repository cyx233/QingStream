package com.java.chenyuxiang.dataUI;


import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ChartFragmentAdapter extends FragmentPagerAdapter {

    Context context;
    List<Fragment> fragments;

    public ChartFragmentAdapter(FragmentManager fm, Context context, List<Fragment> fragments) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

}
