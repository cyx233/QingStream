package com.java.chenyuxiang.searchUI;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.java.chenyuxiang.R;
import com.java.tanghao.AppManager;
import com.java.tanghao.Description;
import com.java.tanghao.NewsManager;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MySearchFragmentPagerAdapter mFragmentPagerAdapter;
    private Toolbar mToolbar;
    private Integer currentPage;
    ArrayList<Description> newsList = new ArrayList<>();

    private NewsManager mNewsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        handleIntent(getIntent());
        initView();
    }

    private void initView(){
        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        mFragmentPagerAdapter = new MySearchFragmentPagerAdapter(getSupportFragmentManager(),newsList,currentPage);
        mViewPager.setAdapter(mFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout_search);
        mTabLayout.setupWithViewPager(mViewPager);

        mToolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        if (!Intent.ACTION_SEARCH.equals(intent.getAction())) {
            return;
        }
        currentPage=1;
        String query = intent.getStringExtra(SearchManager.QUERY);
        newsList = AppManager.getNewsManager().getSearchNews(query);
    }
}