package com.java.chenyuxiang;

import android.os.Bundle;
import android.util.Pair;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyFragmentPagerAdapter mFragmentPagerAdapter;
    private SearchView mSearchView;

    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化视图
        initViews();
    }

    private void initViews() {
        ArrayList<Pair<Fragment,String>> list = new ArrayList<>();
        list.add(new Pair<Fragment, String>(new FragmentNews(),"疫情新闻"));
        list.add(new Pair<Fragment, String>(new FragmentData(),"最新数据"));
        list.add(new Pair<Fragment, String>(new FragmentScholar(),"知疫学者"));

        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        mFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),list);
        mViewPager.setAdapter(mFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        mSearchView = findViewById(R.id.searchView);


        mSearchView.setOnQueryTextListener(new OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
//                if (!TextUtils.isEmpty(s)){
//                    mListView.setFilterText(s);
//                }else{
//                    mListView.clearTextFilter();
//                }
                return false;
            }
        });

    }
}
