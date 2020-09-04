package com.java.chenyuxiang.ui;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import com.java.chenyuxiang.R;
import com.java.tanghao.News;

import java.util.ArrayList;


public class FragmentNews extends ListFragment {
    ArrayList<News> newsList;
    public FragmentNews(ArrayList<News> list){
        newsList=list;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NewsListAdapter adapter = new NewsListAdapter(newsList);//new出适配器的实例
        setListAdapter(adapter);//和List绑定
    }
    class NewsListAdapter extends ArrayAdapter<News> {
        private ArrayList<News> mList;
        public NewsListAdapter(ArrayList<News> list) {
            super(requireActivity(), android.R.layout.simple_list_item_1, list);
            mList=list;
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (null == convertView) {
                convertView = requireActivity().getLayoutInflater().inflate(R.layout.list_item_news, null);
            }
//            News c = getItem(position);
            TextView titleTextView = (TextView)convertView.findViewById(R.id.news_list_item_titleTextView);
            titleTextView.setText("Hello"+position);
//            titleTextView.setText(c.getTitle());
            return convertView;
        }

        @Override
        public int getCount() {
            return mList.size()+100;
        }
    }
}


