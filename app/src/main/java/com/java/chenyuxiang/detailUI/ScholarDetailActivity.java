package com.java.chenyuxiang.detailUI;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.java.chenyuxiang.R;
import com.java.chenyuxiang.view.UrlImageView;
import com.java.tanghao.AppManager;
import com.java.tanghao.Profile;
import com.java.tanghao.YiqingScholar;

public class ScholarDetailActivity extends AppCompatActivity {
    protected TextView bioView;
    protected TextView eduView;
    protected TextView homepageView;
    protected TextView workView;
    protected ColorMatrixColorFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setColor();
        setContentView(R.layout.activity_scholar_detail);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setColor();
        setIntent(intent);
        handleIntent(getIntent());
    }

    protected void setColor(){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(1);//饱和度 0灰色 100过度彩色，50正常
        filter = new ColorMatrixColorFilter(matrix);
    }

    protected void handleIntent(Intent intent) {
        String scholarId = intent.getStringExtra("id");
        bioView = findViewById(R.id.view_scholar_detail_bio);
        eduView = findViewById(R.id.view_scholar_detail_edu);
        homepageView = findViewById(R.id.view_scholar_detail_homepage);
        workView = findViewById(R.id.view_scholar_detail_work);
        YiqingScholar scholar = AppManager.getYiqingScholarManager().getIdScholar(scholarId).get(0);
        Profile detail = scholar.getProfile();
        if(detail.getBio()!=null){
            String content = "    "+detail.getBio().replace("<br>","\n    ");
            bioView.setText(content);
        }
        if(detail.getEdu()!=null)
            eduView.setText(detail.getEdu().replace("<br>","\n    "));
        homepageView.setText(detail.getHomepage());
        if(detail.getWork()!=null)
            workView.setText(detail.getWork().replace("<br>","\n    "));

        UrlImageView avatarView;
        avatarView = findViewById(R.id.view_list_scholar_avatar);
        avatarView.setColorFilter(filter);
        avatarView.setImageURL(scholar.getAvatar());

        TextView nameTextView,companyTextView,profileTextView;
        MarqueeTextView dataTextView;
        nameTextView = findViewById(R.id.view_list_scholar_name);
        dataTextView = findViewById(R.id.view_list_scholar_data);
        companyTextView = findViewById(R.id.view_list_scholar_company);
        profileTextView = findViewById(R.id.view_list_scholar_profile);
        if(scholar.getName_zh().equals(""))
            nameTextView.setText(scholar.getName());
        else
            nameTextView.setText(scholar.getName_zh());
        Integer hIndex = scholar.getIndices().getHindex();
        Double activity = scholar.getIndices().getActivity();
        Double sociability = scholar.getIndices().getSociability();
        Integer citation = scholar.getIndices().getCitations();
        Integer paper = scholar.getIndices().getPubs();
        String data = "H指数:"+hIndex+" 活跃度:"+activity+" 学术合作:"+sociability+" 引用数:"+citation+" 论文数:"+paper;

        dataTextView.setText(data);
        profileTextView.setText(scholar.getProfile().getPosition());
        companyTextView.setText(scholar.getProfile().getAffiliation());
    }
}
