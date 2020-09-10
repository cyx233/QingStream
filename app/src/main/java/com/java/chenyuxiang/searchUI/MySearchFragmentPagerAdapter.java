package com.java.chenyuxiang.searchUI;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.java.tanghao.Description;

import java.util.ArrayList;

public class MySearchFragmentPagerAdapter extends FragmentPagerAdapter{

    private ArrayList<String> tabList=new ArrayList<>();
    FragmentNewsResult mFragmentNews;
    FragmentNewsResult mFragmentEntity;

    public MySearchFragmentPagerAdapter(FragmentManager fm, ArrayList<Description> newsList, Integer currentPage) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mFragmentNews = new FragmentNewsResult(newsList,currentPage);
        mFragmentEntity = new FragmentNewsResult(newsList,currentPage);
        tabList.add("疫情新闻");
        tabList.add("疫情图谱");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) { switch (position){
            case 0:
                return mFragmentNews;
            case 1: default:
                return mFragmentEntity;
        }
    }

    @Override
    public int getCount() {
        return tabList.size();
    }


    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position);
    }
}
