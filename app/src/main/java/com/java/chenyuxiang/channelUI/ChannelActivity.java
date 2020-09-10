package com.java.chenyuxiang.channelUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.java.chenyuxiang.R;
import com.java.tanghao.Category;
import com.java.tanghao.CategoryManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 频道 增删改查 排序
 * Created by YoKeyword on 15/12/29.
 */
public class ChannelActivity extends AppCompatActivity {

    private RecyclerView mRecy;
    final List<ChannelEntity> otherItems = new ArrayList<>();
    final List<ChannelEntity> items = new ArrayList<>();
    String currentCategory;
    Intent backintent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        mRecy = (RecyclerView) findViewById(R.id.recy);
        currentCategory = Objects.requireNonNull(getIntent().getExtras()).getString("currentCategory");
        backintent.putExtra("result", currentCategory);
        ChannelActivity.this.setResult(RESULT_OK, backintent);
        init();
    }

    private void init() {
        CategoryManager mCategoryManager = AppManager.getCategoryManager();
        ArrayList<Category> categoryList = mCategoryManager.getAllCategories();

        categoryList = mCategoryManager.getAllCategories();
        for(int i=0;i<categoryList.size();++i){
            if(categoryList.get(i).getInCategory()){
                ChannelEntity entity = new ChannelEntity();
                entity.setName(categoryList.get(i).getCategory());
                items.add(entity);
            }
            else{
                ChannelEntity entity = new ChannelEntity();
                entity.setName(categoryList.get(i).getCategory());
                otherItems.add(entity);
            }
        }


        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecy.setLayoutManager(manager);

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecy);

        final ChannelAdapter adapter = new ChannelAdapter(this, helper, items, otherItems,currentCategory);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == ChannelAdapter.TYPE_MY || viewType == ChannelAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        mRecy.setAdapter(adapter);

        adapter.setOnMyChannelItemClickListener(new ChannelAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                backintent.putExtra("result", items.get(position).getName());
                //设置返回数据
                ChannelActivity.this.setResult(RESULT_OK, backintent);
                //关闭Activity
                ChannelActivity.this.finish();
            }
        });
    }
}
