package com.java.chenyuxiang.listViewUi;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.java.tanghao.Description;
import com.java.tanghao.YiqingScholarDescription;

import java.util.ArrayList;
import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> tabList=new ArrayList<>();
    FragmentNews mFragmentNews;
    FragmentData mFragmentData;
    FragmentScholar mFragmentScholar;
    FragmentScholar mFragmentPastScholar;
    ArrayList<String> provinceList;
    ArrayList<String> countryList;

    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Description> newsList,
                                  List<String> chinaList, List<String> worldList,
                                  ArrayList<YiqingScholarDescription> scholarList,
                                  ArrayList<YiqingScholarDescription> pastScholarList , Integer currentPage, String currentCategory) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        provinceList = new ArrayList<>(chinaList);
        countryList = new ArrayList<>(worldList);


        mFragmentNews = new FragmentNews(newsList,currentPage,currentCategory);
        mFragmentData = new FragmentData(provinceList,countryList);
        mFragmentScholar = new FragmentScholar(scholarList,currentPage);
        mFragmentPastScholar = new FragmentPastScholar(pastScholarList,currentPage);
        tabList.add("疫情新闻");
        tabList.add("最新数据");
        tabList.add("高关注学者");
        tabList.add("追忆学者");
    }
    public void updateNews(ArrayList<Description>list){
        mFragmentNews.updateNews(list);
    }

    public void updateNewsCategory(String category){mFragmentNews.updateCategory(category);}

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return mFragmentNews;
            case 1:
                return mFragmentData;
            case 2:
                return mFragmentScholar;
            case 4: default:
                return mFragmentPastScholar;
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
