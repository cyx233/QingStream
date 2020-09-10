package com.java.chenyuxiang;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.tabs.TabLayout;
import com.java.chenyuxiang.channelUI.ChannelActivity;
import com.java.chenyuxiang.listViewUi.MyFragmentPagerAdapter;
import com.java.tanghao.AppManager;
import com.java.tanghao.Category;
import com.java.tanghao.CategoryManager;
import com.java.tanghao.Description;
import com.java.tanghao.NewsManager;
import com.java.tanghao.YiqingScholarDescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyFragmentPagerAdapter mFragmentPagerAdapter;
    private Toolbar mToolbar;
    private String currentCategory;
    ArrayList<Description> newsList = new ArrayList<>();
    ArrayList<YiqingScholarDescription> scholarList = new ArrayList<>();
    ArrayList<YiqingScholarDescription> pastScholarList = new ArrayList<>();
    private NewsManager mNewsManager;
    private CategoryManager mCategoryManager;
    private HashMap<String,Integer> loadPage= new HashMap<>();
    public static final int MIN_CLICK_DELAY_TIME = 900;
    private long lastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_main);
        initData();
        //初始化视图
        initViews();
    }
    private void initData(){
        currentCategory = "全部";
        AppManager.getAppManager(this);
        AppManager.getYiqingScholarManager().getPageScholar("https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2");
        mCategoryManager = AppManager.getCategoryManager();
        mNewsManager = AppManager.getNewsManager();
        ArrayList<Category> categoryList = mCategoryManager.getAllCategories();
        if(categoryList==null || categoryList.size()==0){
            mCategoryManager.insertCategory(new Category("全部",true));
            mCategoryManager.insertCategory(new Category("论文",true));
            mCategoryManager.insertCategory(new Category("新闻",true));
            mCategoryManager.insertCategory(new Category("国内",false));
            mCategoryManager.insertCategory(new Category("国外",false));
        }
        loadPage.put("news",2);
        loadPage.put("paper",2);
        loadPage.put("all",2);

//        start test cluster
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

//        end test cluster

        Description[] news = mNewsManager.getPageNews(generateUrl("all"));
        List<Description> list = Arrays.asList(news).subList(0,20);
        newsList = new ArrayList<>(list);
        scholarList = AppManager.getYiqingScholarManager().getScholar(false);
        if(scholarList.size()>20){
            scholarList = new ArrayList<>(scholarList.subList(0,20));
        }
        pastScholarList = AppManager.getYiqingScholarManager().getScholar(true);
        if(pastScholarList.size()>20){
            pastScholarList = new ArrayList<>(pastScholarList.subList(0,20));
        }
    }
    private String generateUrl(String type){
        Integer page = loadPage.get(type);
        String url= "http://covid-dashboard.aminer.cn/api/events/list?type="+type+"&page="+page+"&size="+20;
        page+=1;
        loadPage.put(type, page);
        return url;
    }

    private void initViews() {
        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        mFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),newsList,scholarList,
                pastScholarList,2,currentCategory);
        mViewPager.setAdapter(mFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        mToolbar = findViewById(R.id.toolbar);
        MenuItem item=  mToolbar.findViewById(R.id.item_search);
        setSupportActionBar(mToolbar);
        initSearchView(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime <= MIN_CLICK_DELAY_TIME)
            return false;
        lastClickTime = currentTime;
        int id = item.getItemId();
        Intent intent=null;
        switch (id){
            case R.id.item_search:
                break;
            case R.id.item_category:
                intent = new Intent(this, ChannelActivity.class);
                mCategoryManager.updateInCategory(new Category(currentCategory,true));
                intent.putExtra("currentCategory",currentCategory);
                startActivityForResult(intent,1);
                break;
        }
        return false;
    }

    private void initSearchView(final MenuItem item){
        //通过 item 获取 actionview
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("搜索知识库");

        //改变默认的搜索图标
        //((ImageView)searchView.findViewById(R.id.search_button)).setImageResource(R.drawable.ic_search);

        //搜索监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //在输入法按下搜索或者回车时，会调用次方法，在这里可以作保存历史记录的操作，我这里用了 sharepreference保存
                SPUtils spUtils = new SPUtils("knowledgeHistory");
                spUtils.put(query, query);
                presenter.searchKnowledge(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
            //输入字符则回调此方法


            //当输入字符为空时，重新设置 item

                if(newText==null||newText.length()==0){

                    //由于实现了历史数据的功能，在此重新设置此 item才能实时生效
                    initSearchView(item); }

                return false;
            }
        });
        //根据id-search_src_text获取TextView
        searchViewOfKnowledge = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        //改变输入文字的颜色
        searchViewOfKnowledge.setTextColor(ContextCompat.getColor(HomeTabActivity.this, R.color.colorAccent));
        try {
            //取出历史数据，你可以利用其他方式
            final List<String> arr = new ArrayList<>();
            SPUtils spUtils = new SPUtils("knowledgeHistory");
            Map<String, ?> map = spUtils.getAll();

            for (String key : map.keySet()) {
                arr.add(map.get(key).toString());
            }
            //显示历史数据列表
            searchViewOfKnowledge.setThreshold(0);

            //历史数据列表的 adapter,必须继承 ArrayAdater 或实现 filterable接口
            HistoryAdapter adapter = new HistoryAdapter(HomeTabActivity.this, R.layout.item_history, arr,searchView);
            //设置 adapter
            searchViewOfKnowledge.setAdapter(adapter);
            //如果重写了 Adapter 的 getView 方法，可以不用实现 item 监听（实现了也没用），否则必须实现监听，不然会报错
            searchViewOfKnowledge.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    searchView.setQuery(arr.get(position), true);
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        //searchview 的关闭监听
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {        @Override        public boolean onClose() {
            return false;
        }
        });}



    @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        ArrayList<Description>newsList;
        currentCategory = Objects.requireNonNull(data.getExtras()).getString("result");//得到新Activity 关闭后返回的数据
        assert currentCategory != null;
        switch (currentCategory){
            case "论文":
                newsList = mNewsManager.getTypeNews("paper");
                if(newsList.size()==0){
                    Description[]temp = mNewsManager.getPageNews(generateUrl("paper"));
                    newsList = new ArrayList<Description>(Arrays.asList(temp));
                }
                break;
            case "新闻":
                newsList = mNewsManager.getTypeNews("news");
                if(newsList.size()==0){
                    Description[]temp = mNewsManager.getPageNews(generateUrl("news"));
                    newsList = new ArrayList<Description>(Arrays.asList(temp));
                }
                break;
            default:
                newsList = mNewsManager.getAllNews();
                break;
        }
        if(newsList.size()>20)
            mFragmentPagerAdapter.updateNews(new ArrayList<Description>(newsList.subList(0,20)));
        else
            mFragmentPagerAdapter.updateNews(newsList);
        mFragmentPagerAdapter.updateNewsCategory(currentCategory);
        Toast.makeText(this,currentCategory,Toast.LENGTH_SHORT).show();
    }
}
