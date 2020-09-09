package com.java.chenyuxiang.detailUI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.java.chenyuxiang.R;
import com.java.tanghao.AppManager;
import com.java.tanghao.YiqingScholar;

public class ScholarDetailActivity extends AppCompatActivity {
    private TextView bioView;
    private TextView eduView;
    private TextView homepageView;
    private TextView workView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        String scholarId = intent.getStringExtra("id");
        bioView = findViewById(R.id.view_scholar_detail_bio);
        eduView = findViewById(R.id.view_scholar_detail_edu);
        homepageView = findViewById(R.id.view_scholar_detail_homepage);
        workView = findViewById(R.id.view_scholar_detail_work);
        YiqingScholar detail = AppManager.getYiqingScholarManager().getIdScholar(scholarId).get(0);
    }
}
