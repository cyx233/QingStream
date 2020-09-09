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
import com.java.chenyuxiang.detailUI.MarqueeTextView;
import com.java.chenyuxiang.detailUI.NewsDetailActivity;
import com.java.tanghao.AppManager;
import com.java.tanghao.YiqingScholarDescription;

import java.util.ArrayList;

public class FragmentScholar extends ListFragment {
    ArrayList<YiqingScholarDescription> scholarList;
    ScholarListAdapter adapter;//new出适配器的实例
    private SwipeRefreshView mSwipeRefreshView;
    private Integer currentPage;

    public FragmentScholar(ArrayList<YiqingScholarDescription> list, Integer currentPage){
        scholarList = list;
        this.currentPage = currentPage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentPage = 1;
        adapter = new ScholarListAdapter(scholarList);//new出适配器的实例
        setListAdapter(adapter);//和List绑定
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mSwipeRefreshView = view.findViewById(R.id.view_news_swipe);
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
                Toast.makeText(getContext(), "load", Toast.LENGTH_SHORT).show();
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
        ArrayList<YiqingScholarDescription> list;
        list = AppManager.getYiqingScholarManager().getScholar(false);
        currentPage += 1;
        scholarList.addAll(list.subList((currentPage-1)*20,currentPage*20));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        YiqingScholarDescription detail = scholarList.get(position);
        Intent intent = new Intent(this.getActivity(), NewsDetailActivity.class);
        intent.putExtra("id",detail.getId());
        startActivity(intent);
    }

    class ScholarListAdapter extends ArrayAdapter<YiqingScholarDescription> {
        private ArrayList<YiqingScholarDescription> mList;
        public ScholarListAdapter(ArrayList<YiqingScholarDescription> list) {
            super(requireActivity(), android.R.layout.simple_list_item_1, list);
            mList=list;
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (null == convertView) {
                convertView = requireActivity().getLayoutInflater().inflate(R.layout.list_item_scholar, null);
            }
            YiqingScholarDescription c = getItem(position);
            TextView nameTextView,companyTextView,profileTextView;
            MarqueeTextView dataTextView;
            nameTextView = (TextView)convertView.findViewById(R.id.view_list_scholar_name);
            dataTextView = (MarqueeTextView) convertView.findViewById(R.id.view_list_scholar_data);
            companyTextView = (TextView)convertView.findViewById(R.id.view_list_scholar_company);
            profileTextView = (TextView)convertView.findViewById(R.id.view_list_scholar_profile);
            assert c != null;
            if(c.getName_zh().equals(" "))
                nameTextView.setText(c.getName());
            else
                nameTextView.setText(c.getName_zh());
            Integer hIndex = c.getIndice().getHindex();
            Double activity = c.getIndice().getActivity();
            Double sociability = c.getIndice().getSociability();
            Integer citation = c.getIndice().getCitations();
            Integer paper = c.getIndice().getPubs();
            String data = "H指数:"+hIndex+" 活跃度:"+activity+" 学术合作:"+sociability+" 引用数:"+citation+" 论文数:"+paper;

            dataTextView.setText(data);
            profileTextView.setText(c.getPosition());
            companyTextView.setText(c.getAff());
            return convertView;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

    }
}


