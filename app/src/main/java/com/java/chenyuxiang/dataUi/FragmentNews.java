package com.java.chenyuxiang.dataUi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import com.java.chenyuxiang.R;
import com.java.chenyuxiang.detailUI.DetailActivity;
import com.java.tanghao.Description;

import java.util.ArrayList;


public class FragmentNews extends ListFragment {
    ArrayList<Description> newsList;
    NewsListAdapter adapter;//new出适配器的实例
    public FragmentNews(ArrayList<Description> list){
        newsList = list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsListAdapter(newsList);//new出适配器的实例
        setListAdapter(adapter);//和List绑定
    }

    public void update(ArrayList<Description>list){
        newsList.clear();
        newsList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Description detail = newsList.get(position);
        Intent intent = new Intent(this.getActivity(), DetailActivity.class);
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
            assert c != null;
            titleTextView.setText(c.getTitle());
            return convertView;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}


