package com.java.chenyuxiang.listViewUi;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.java.tanghao.Description;

import java.util.ArrayList;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> tabList=new ArrayList<>();
    FragmentNews mFragmentNews;
    FragmentData mFragmentData;
    FragmentScholar mFragmentScholar;
    FragmentFavorite mFragmentFavorite;

    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Description> list, Integer currentPage, String currentCategory) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mFragmentNews = new FragmentNews(list,currentPage,currentCategory);
        mFragmentData = new FragmentData();
        mFragmentScholar = new FragmentScholar();
        mFragmentFavorite = new FragmentFavorite();
        tabList.add("疫情新闻");
        tabList.add("最新数据");
        tabList.add("知疫学者");
        tabList.add("我的收藏");
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
            case 3: default:
                return mFragmentFavorite;
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
