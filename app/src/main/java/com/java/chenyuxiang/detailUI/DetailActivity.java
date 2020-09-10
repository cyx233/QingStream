package com.java.chenyuxiang.detailUI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.java.chenyuxiang.R;
import com.java.tanghao.AppManager;
import com.java.tanghao.News;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private TextView titleView;
    private TextView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        String newsId = intent.getStringExtra("id");
        titleView = findViewById(R.id.view_detail_title);
        contentView = findViewById(R.id.view_detail_content);
        ArrayList<News> detail = AppManager.getNewsManager().getNewsContent(newsId);
        titleView.setText(detail.get(0).getTitle());
        contentView.setText(detail.get(0).getContent());
    }
}
