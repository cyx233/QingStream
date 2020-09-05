package com.java.chenyuxiang.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.donkingliang.labels.LabelsView;
import com.java.chenyuxiang.R;

import java.util.ArrayList;

public class FragmentCategory extends Fragment {
    private ArrayList<String> mList;
    private LabelsView mLabelsView;
    private TextView mTextView;
    private boolean mType;

    public FragmentCategory(ArrayList<String>list,boolean isOpenList){
        mList = list;
        mType = isOpenList;
        String op;
        if(isOpenList){
            op="-";
        }else{
            op="+";
        }
        for(int i=0;i<mList.size();++i){
            mList.set(i,mList.get(i)+op);
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catagory,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLabelsView = view.findViewById(R.id.view_labels);
        mTextView = view.findViewById(R.id.view_category_text);
        mLabelsView.setLabels(mList);
        if(mType){
            mTextView.setText("已选择");
            ArrayList<Integer> list = new ArrayList<>();
            for(int i=0;i<mList.size();++i)
                list.add(i);
            mLabelsView.setSelects(list);
        } else{
            mTextView.setText("未选择");
        }
    }
}
