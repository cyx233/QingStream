package com.java.chenyuxiang.searchUI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.java.chenyuxiang.R;
import com.java.chenyuxiang.detailUI.NewsDetailActivity;
import com.java.chenyuxiang.view.SwipeRefreshView;
import com.java.tanghao.AppManager;
import com.java.tanghao.Description;
import com.java.tanghao.NewsManager;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentNewsResult extends ListFragment {
    ArrayList<Description> allNewsList;
    ArrayList<Description> newsList;
    NewsListAdapter adapter;//new出适配器的实例
    private SwipeRefreshView mSwipeRefreshView;
    private Integer currentPage;
    public static final int MIN_CLICK_DELAY_TIME = 900;
    private long lastClickTime = 0;

    public FragmentNewsResult(ArrayList<Description> list,Integer currentPage){
        allNewsList = list;
        if(allNewsList.size()<20){
            newsList = allNewsList;
        }
        else{
            newsList = new ArrayList<>(allNewsList.subList(0,20));
        }
        this.currentPage = currentPage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsListAdapter(newsList);//new出适配器的实例
        setListAdapter(adapter);//和List绑定
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        mSwipeRefreshView = view.findViewById(R.id.view_news_swipe);

        // 设置下拉刷新监听器
        mSwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshView.setRefreshing(false);
            }
        });

        // 加载监听器
        mSwipeRefreshView.setOnLoadListener(new SwipeRefreshView.OnLoadListener() {
            @Override
            public void onLoad() {
                Toast.makeText(getContext(), "加载新闻", Toast.LENGTH_SHORT).show();
                mSwipeRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myLoadOperation();
                        adapter.notifyDataSetChanged();
                        // 加载完后调用该方法
                        mSwipeRefreshView.setLoading(false);
                    }
                }, 1500);
            }
        });
        return view;
    }

    private void myLoadOperation(){
        if(allNewsList.size()<currentPage*20){
            Toast.makeText(getContext(),"没有更多了",Toast.LENGTH_SHORT).show();
        }else{
            currentPage+=1;
            if(allNewsList.size()<currentPage*20){
                newsList.addAll(allNewsList.subList((currentPage-1)*20,allNewsList.size()));
            }else{
                newsList.addAll(allNewsList.subList((currentPage-1)*20,currentPage*20));
            }
        }
        adapter.notifyDataSetChanged();
    }
    public void updateNews(ArrayList<Description> list){
        currentPage=1;
        newsList.clear();
        allNewsList = list;
        if(allNewsList.size()>20){
            newsList.addAll(allNewsList.subList(0,20));
        } else{
            newsList.addAll(allNewsList);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime <= MIN_CLICK_DELAY_TIME) {
            return;
        }
        lastClickTime = currentTime;
        Description detail = newsList.get(position);
        NewsManager mNewsManager = AppManager.getNewsManager();


        Intent intent = new Intent(this.getActivity(), NewsDetailActivity.class);
        intent.putExtra("id",detail.getId());
        startActivity(intent);
    }

    class NewsListAdapter extends ArrayAdapter<Description> {
        private ArrayList<Description> mList;
        public NewsListAdapter(ArrayList<Description> list) {
            super(requireActivity(), android.R.layout.simple_list_item_1, list);
            mList=list;
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (null == convertView) {
                convertView = requireActivity().getLayoutInflater().inflate(R.layout.list_item_news, null);
            }
            Description c = getItem(position);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.news_list_item_titleTextView);
            TextView dateTextView = (TextView) convertView.findViewById(R.id.news_list_item_dateTextView);
            TextView sourceTextView = (TextView) convertView.findViewById(R.id.news_list_item_sourceTextView);
            assert c != null;

            titleTextView.setText(c.getTitle());
            dateTextView.setText(c.getDate());
            sourceTextView.setText(c.getSource());

            if(c.getIsRead()!=null && c.getIsRead()) {
                String temp = "[已读]" + c.getTitle();
                titleTextView.setText(temp);
                titleTextView.setTextColor(Color.parseColor("#C0C0C0"));
                dateTextView.setTextColor(Color.parseColor("#C0C0C0"));
                sourceTextView.setTextColor(Color.parseColor("#C0C0C0"));
            }else{
                titleTextView.setTextColor(Color.parseColor("#000000"));
                dateTextView.setTextColor(Color.parseColor("#000000"));
                sourceTextView.setTextColor(Color.parseColor("#000000"));
            }
            return convertView;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
