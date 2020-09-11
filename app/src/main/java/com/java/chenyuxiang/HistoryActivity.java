package com.java.chenyuxiang;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.java.chenyuxiang.searchUI.SearchResultActivity;
import com.java.tanghao.AppManager;
import com.java.tanghao.History;
import com.java.tanghao.HistoryManager;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity{
    private ListView mListView;
    private HistoryManager mHistoryManager;
    private HistoryAdapter adapter;
    private ArrayList<History> historyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_history);
        mListView = findViewById(R.id.view_history);
        mHistoryManager = AppManager.getHistoryManager();
        historyList = mHistoryManager.getAllHistories();
        adapter = new HistoryAdapter(this,R.layout.item_history,historyList);//new出适配器的实例
        mListView.setAdapter(adapter);//和List绑定
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String temp = historyList.get(position).getContent();
                Intent intent = new Intent(parent.getContext(), SearchResultActivity.class);
                intent.setAction(Intent.ACTION_SEARCH);
                intent.putExtra(SearchManager.QUERY,temp);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        historyList = mHistoryManager.getAllHistories();
        adapter.notifyDataSetChanged();
    }

    class HistoryAdapter extends ArrayAdapter<History> {
        ArrayList<History> mList;
        public HistoryAdapter(@NonNull Context context, int resource, @NonNull List<History> list) {
            super(context, resource, list);
            mList = new ArrayList<>(list);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (null == convertView) {
                convertView = getLayoutInflater().inflate(R.layout.item_history, null);
            }
            String temp = mList.get(position).getContent();
            TextView text = (TextView) convertView.findViewById(R.id.titleTv);
            text.setText(temp);
            return convertView;
        }

        @Nullable
        @Override
        public History getItem(int position) {
           return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}

