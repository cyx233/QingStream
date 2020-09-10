package com.java.chenyuxiang.listViewUi;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class FragmentNews extends ListFragment {
    ArrayList<Description> newsList;
    NewsListAdapter adapter;//new出适配器的实例
    private SwipeRefreshView mSwipeRefreshView;
    private Integer currentPage;
    private String currentCategory;
    public static final int MIN_CLICK_DELAY_TIME = 900;
    private long lastClickTime = 0;
    public FragmentNews(ArrayList<Description> list,Integer currentPage,String category){
        newsList = list;
        this.currentPage = currentPage;
        updateCategory(category);
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
                Toast.makeText(getContext(), "更新新闻", Toast.LENGTH_SHORT).show();
                mSwipeRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myUpdateOperation();
                        adapter.notifyDataSetChanged();
                        // 更新完后调用该方法结束刷新
                        mSwipeRefreshView.setRefreshing(false);
                    }
                }, 1000);
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
    private void myUpdateOperation(){
        currentPage = 1;
        Description[] temp;
        ArrayList<Description> list;
        switch (currentCategory){
            case "news": case"paper": case "all":
                temp = AppManager.getNewsManager().getPageNews(generateUrl(currentCategory,currentPage));
                list = new ArrayList<>(Arrays.asList(temp));
                break;
            default:
                list = AppManager.getNewsManager().getTypeNews(currentCategory);
                break;
        }
        updateNews(list);
    }

    private void myLoadOperation(){
        Description[] temp;
        ArrayList<Description> list;
        switch (currentCategory){
            case "news": case"paper": case "all":
                temp = AppManager.getNewsManager().getPageNews(generateUrl(currentCategory,currentPage+1));
                newsList.addAll(Arrays.asList(temp));
                currentPage+=1;
                break;
            default:
                list = AppManager.getNewsManager().getTypeNews(currentCategory);
                if(list.size()<currentPage*20){
                    Toast.makeText(getContext(),"没有更多了",Toast.LENGTH_SHORT).show();
                }else{
                    currentPage+=1;
                    if(list.size()<currentPage*20){
                        newsList.addAll(list.subList((currentPage-1)*20,list.size()));
                    }else{
                        newsList.addAll(list.subList((currentPage-1)*20,currentPage*20));
                    }
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    private String generateUrl(String type,Integer page){
        return "http://covid-dashboard.aminer.cn/api/events/list?type="+type+"&page="+page+"&size="+20;
    }

    public void updateNews(ArrayList<Description>list){
        newsList.clear();
        newsList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void updateCategory(String category){
        switch (category){
            case "全部":
                currentCategory="all";
                break;
            case "新闻":
                currentCategory="news";
                break;
            case "论文":
                currentCategory="paper";
                break;
            default:
                currentCategory="";
                break;
        }
    }


    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime <= MIN_CLICK_DELAY_TIME) {
            return;
        }
        lastClickTime = currentTime;
        Description detail = newsList.get(position);
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

            if(c.getIsRead()){
                String temp = "[已读]"+c.getTitle();
                titleTextView.setText(temp);
                titleTextView.setTextColor(0x969696);
                dateTextView.setTextColor(0x969696);
                sourceTextView.setTextColor(0x969696);
            }

            return convertView;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

    }
}


