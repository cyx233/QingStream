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
import com.java.tanghao.History;
import com.java.tanghao.YiqingEntity;
import com.java.tanghao.YiqingEntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MySearchFragmentPagerAdapter mFragmentPagerAdapter;
    private Toolbar mToolbar;
    private Integer currentPage;
    ArrayList<Description> newsList = new ArrayList<>();
    ArrayList<YiqingEntity> entityList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_search_result);
        getNewsList(getIntent());
        getEntityList(getIntent());
        initView();
    }

    private void initView(){
        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager= (ViewPager) findViewById(R.id.viewPager_search);
        mFragmentPagerAdapter = new MySearchFragmentPagerAdapter(getSupportFragmentManager(),newsList,entityList,currentPage);
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
        getNewsList(getIntent());
        getEntityList(getIntent());
        mFragmentPagerAdapter.updateNews(newsList);
    }

    private void getNewsList(Intent intent) {
        if (!Intent.ACTION_SEARCH.equals(intent.getAction())) {
            return;
        }
        currentPage=1;
        String query = intent.getStringExtra(SearchManager.QUERY);
        newsList = AppManager.getNewsManager().getSearchNews(query);
    }
    private void getEntityList(Intent intent){
        if (!Intent.ACTION_SEARCH.equals(intent.getAction())) {
            return;
        }
        currentPage=1;
        String query = intent.getStringExtra(SearchManager.QUERY);
        AppManager.getHistoryManager().insertHistory(new History(query));
        List<YiqingEntity> temp = Arrays.asList(YiqingEntityManager.getYiqingEntity(query));
        entityList = new ArrayList<>(temp);
    }
}