package com.java.chenyuxiang.searchUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.java.chenyuxiang.R;
import com.java.chenyuxiang.detailUI.EntityDetailActivity;
import com.java.chenyuxiang.view.SwipeRefreshView;
import com.java.tanghao.YiqingEntity;
import com.java.tanghao.YiqingEntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

public class FragmentEntityResult extends ListFragment {
    ArrayList<YiqingEntity> entityList;
    EntityListAdapter adapter;//new出适配器的实例
    private SwipeRefreshView mSwipeRefreshView;
    public static final int MIN_CLICK_DELAY_TIME = 900;
    private long lastClickTime = 0;

    public FragmentEntityResult(ArrayList<YiqingEntity> list, Integer currentPage){
        entityList = list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new EntityListAdapter(entityList);//new出适配器的实例
        setListAdapter(adapter);//和List绑定
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        mSwipeRefreshView = view.findViewById(R.id.view_news_swipe);

        // 设置下拉刷新监听器
        mSwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshView.setRefreshing(false);
            }
        });

        // 加载监听器
        mSwipeRefreshView.setOnLoadListener(new SwipeRefreshView.OnLoadListener() {
            @Override
            public void onLoad() {
                mSwipeRefreshView.setLoading(false);
            }
        });
        return view;
    }


    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime <= MIN_CLICK_DELAY_TIME)
            return;
        lastClickTime = currentTime;
        YiqingEntity detail = entityList.get(position);
        Intent intent = new Intent(this.getActivity(), EntityDetailActivity.class);
        intent.putExtra("label",detail.getLabel());
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null)
            return;
        String newEntityName = Objects.requireNonNull(data.getExtras()).getString("result");
        entityList.clear();
        entityList.addAll(Arrays.asList(YiqingEntityManager.getYiqingEntity(newEntityName)));
        adapter.notifyDataSetChanged();
    }

    class EntityListAdapter extends ArrayAdapter<YiqingEntity> {
        private ArrayList<YiqingEntity> mList;
        public EntityListAdapter(ArrayList<YiqingEntity> list) {
            super(requireActivity(), android.R.layout.simple_list_item_1, list);
            mList=list;
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (null == convertView) {
                convertView = requireActivity().getLayoutInflater().inflate(R.layout.list_item_entity, null);
            }
            YiqingEntity c = getItem(position);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.entity_list_item_name);
            TextView hotTextView = (TextView) convertView.findViewById(R.id.entity_list_item_hot);
            assert c != null;
            nameTextView.setText(c.getLabel());
            String hotText = "热度:"+ (int) (c.getHot()*100);
            hotTextView.setText(hotText);

            return convertView;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}

