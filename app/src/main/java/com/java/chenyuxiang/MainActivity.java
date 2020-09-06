package com.java.chenyuxiang;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.java.chenyuxiang.channelUI.ChannelActivity;
import com.java.chenyuxiang.dataUi.FragmentData;
import com.java.chenyuxiang.dataUi.FragmentNews;
import com.java.chenyuxiang.dataUi.FragmentScholar;
import com.java.chenyuxiang.dataUi.MyFragmentPagerAdapter;
import com.java.tanghao.AppManager;
import com.java.tanghao.News;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyFragmentPagerAdapter mFragmentPagerAdapter;
    private Toolbar mToolbar;

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
        AppManager.getAppManager(this);

        AppManager.getAppManager(this);
        News[] a = AppManager.getNewsManager().getPageNews("http://covid-dashboard.aminer.cn/api/events/list?type=all%page=18&size=5");
        ArrayList<News> newsList = AppManager.getNewsManager().getTypeNews("news");

        list.add(new Pair<Fragment, String>(new FragmentNews(newsList),"疫情新闻"));
        list.add(new Pair<Fragment, String>(new FragmentData(),"最新数据"));
        list.add(new Pair<Fragment, String>(new FragmentScholar(),"知疫学者"));
        list.add(new Pair<Fragment, String>(new FragmentScholar(),"我的收藏"));


        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        mFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),list);
        mViewPager.setAdapter(mFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.item_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent=null;
        switch (id){
            case R.id.item_search:
                break;
            case R.id.item_category:
                intent = new Intent(this, ChannelActivity.class);
                startActivity(intent);
                break;
        }
        return false;
    }
}
