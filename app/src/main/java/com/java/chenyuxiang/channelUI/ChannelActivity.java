package com.java.chenyuxiang.channelUI;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.java.chenyuxiang.R;
import com.java.tanghao.AppManager;
import com.java.tanghao.Category;
import com.java.tanghao.CategoryManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道 增删改查 排序
 * Created by YoKeyword on 15/12/29.
 */
public class ChannelActivity extends AppCompatActivity {

    private RecyclerView mRecy;
    final List<ChannelEntity> otherItems = new ArrayList<>();
    final List<ChannelEntity> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mRecy = (RecyclerView) findViewById(R.id.recy);
        init();
    }

    private void init() {
        CategoryManager mCategoryManager = AppManager.getCategoryManager();
        ArrayList<Category> categoryList = mCategoryManager.getAllCategories();

        if(categoryList==null || categoryList.size()==0){
            mCategoryManager.insertCategory(new Category("论文",true));
            mCategoryManager.insertCategory(new Category("事件",true));
            mCategoryManager.insertCategory(new Category("国内",false));
            mCategoryManager.insertCategory(new Category("国外",false));

        }

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

        final ChannelAdapter adapter = new ChannelAdapter(this, helper, items, otherItems);
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
                chooseChannel(items.get(position));
                Toast.makeText(ChannelActivity.this, items.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void chooseChannel(ChannelEntity chosenOne){
        for (ChannelEntity item:items) {
            if(item.getName().equals(chosenOne.getName())){
                item.setCurrent(true);
            }
            else{
                item.setCurrent(false);
            }
        }
    }
}
