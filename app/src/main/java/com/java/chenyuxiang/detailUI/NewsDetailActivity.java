package com.java.chenyuxiang.detailUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.java.chenyuxiang.R;
import com.java.chenyuxiang.Utils.WXShareUtils;
import com.java.tanghao.AppManager;
import com.java.tanghao.News;

import java.util.ArrayList;
import java.util.Calendar;

public class NewsDetailActivity extends AppCompatActivity {
    private TextView titleView;
    private TextView contentView;
    String title;
    String content;
    String appid = "fa70344612e204bdf7d77fbef3914fa8";
    public static final int MIN_CLICK_DELAY_TIME = 900;
    private long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
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
        String newsId = intent.getStringExtra("id");
        titleView = findViewById(R.id.view_detail_title);
        contentView = findViewById(R.id.view_detail_content);
        ArrayList<News> detail = AppManager.getNewsManager().getNewsContent(newsId);
        title = detail.get(0).getTitle();
        titleView.setText(title);

        if(detail.get(0).getContent()!=null){
            content = "    "+detail.get(0).getContent().replace("<br>","\n    ");
            contentView.setText(content);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime <= MIN_CLICK_DELAY_TIME)
            return false;
        lastClickTime = currentTime;
        WXShareUtils.share(this,appid,"标题"+title+"   正文"+content);
        return false;
    }

}
