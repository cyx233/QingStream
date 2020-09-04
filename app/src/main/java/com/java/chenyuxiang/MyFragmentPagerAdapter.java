package com.java.chenyuxiang;


import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Pair<Fragment,String>> mFragmentsList;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Pair<Fragment,String>> list) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mFragmentsList = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentsList.get(position).first;
    }

    @Override
    public int getCount() {
        return mFragmentsList.size();
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentsList.get(position).second;
    }
}
