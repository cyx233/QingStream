package com.java.chenyuxiang.searchUI;

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
import com.java.tanghao.Description;

import java.util.ArrayList;

public class FragmentEntityResult extends ListFragment {
    ArrayList<Description> newsList;
    EntityListAdapter adapter;//new出适配器的实例
    private SwipeRefreshView mSwipeRefreshView;
    private Integer currentPage;
    public FragmentEntityResult(ArrayList<Description> list,Integer currentPage){
        newsList = list;
        this.currentPage = currentPage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new EntityListAdapter(newsList);//new出适配器的实例
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

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Description detail = newsList.get(position);
        Intent intent = new Intent(this.getActivity(), NewsDetailActivity.class);
        intent.putExtra("id",detail.getId());
        startActivity(intent);
    }

    class EntityListAdapter extends ArrayAdapter<Description> {
        private ArrayList<Description> mList;
        public EntityListAdapter(ArrayList<Description> list) {
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
            assert c != null;

            titleTextView.setText(c.getTitle());
            dateTextView.setText(c.getDate());
            return convertView;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
