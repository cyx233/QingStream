package com.java.chenyuxiang;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.java.chenyuxiang.ui.FragmentData;
import com.java.chenyuxiang.ui.FragmentNews;
import com.java.chenyuxiang.ui.FragmentScholar;
import com.java.chenyuxiang.ui.MyFragmentPagerAdapter;
import com.java.tanghao.News;
import com.java.tanghao.NewsManager;

import java.util.ArrayList;
import java.util.Arrays;

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
        NewsManager manager = NewsManager.getNewsManager(getApplicationContext());
        ArrayList<News> newslist = new ArrayList<News>(Arrays.asList(manager.getPageNews("http://covid-dashboard.aminer.cn/api/events/list?type=paper%page=18&size=5")));

        list.add(new Pair<Fragment, String>(new FragmentNews(newslist),"疫情新闻"));
        list.add(new Pair<Fragment, String>(new FragmentData(),"最新数据"));
        list.add(new Pair<Fragment, String>(new FragmentScholar(),"知疫学者"));

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
}
