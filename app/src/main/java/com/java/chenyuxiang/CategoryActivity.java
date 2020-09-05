package com.java.chenyuxiang;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.java.chenyuxiang.ui.FragmentCategory;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory);
        FragmentManager mFragmentManager = getSupportFragmentManager();
        ArrayList<String> labelList = new ArrayList<>();
        labelList.add("paper");
        labelList.add("event");
        Fragment openListFragment = new FragmentCategory(labelList, true);
        Fragment closeListFragment = new FragmentCategory(new ArrayList<String>(), false);
        mFragmentManager.beginTransaction().add(R.id.view_open_list, openListFragment).commitAllowingStateLoss();
        mFragmentManager.beginTransaction().add(R.id.view_close_list, closeListFragment).commitAllowingStateLoss();
    }
}
