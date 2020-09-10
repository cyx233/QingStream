package com.java.chenyuxiang.listViewUi;


import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.java.chenyuxiang.detailUI.PastScholarDetailActivity;
import com.java.tanghao.AppManager;
import com.java.tanghao.YiqingScholarDescription;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentPastScholar extends FragmentScholar {

    public static final int MIN_CLICK_DELAY_TIME = 900;
    private long lastClickTime = 0;

    public FragmentPastScholar(ArrayList<YiqingScholarDescription> list, Integer currentPage) {
        super(list, currentPage);
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        colorFilter = new ColorMatrixColorFilter(cm);
    }

    @Override
    protected void myLoadOperation() {
        ArrayList<YiqingScholarDescription> list;
        list = AppManager.getYiqingScholarManager().getScholar(true);
        if(list.size()<currentPage*20){
            Toast.makeText(getContext(),"没有更多了",Toast.LENGTH_SHORT).show();
        }else{
            currentPage+=1;
            if(list.size()<currentPage*20){
                scholarList.addAll(list.subList((currentPage-1)*20,list.size()));
            }else{
                scholarList.addAll(list.subList((currentPage-1)*20,currentPage*20));
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime <= MIN_CLICK_DELAY_TIME) {
            return;
        }
        lastClickTime = currentTime;
        YiqingScholarDescription detail = scholarList.get(position);
        Intent intent = new Intent(this.getActivity(), PastScholarDetailActivity.class);
        intent.putExtra("id",detail.getId());
        startActivity(intent);
    }
}
