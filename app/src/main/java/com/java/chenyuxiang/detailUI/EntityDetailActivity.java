package com.java.chenyuxiang.detailUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.java.chenyuxiang.R;
import com.java.chenyuxiang.view.MyListView;
import com.java.chenyuxiang.view.UrlImageView;
import com.java.tanghao.CovidRelation;
import com.java.tanghao.YiqingEntity;
import com.java.tanghao.YiqingEntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

public class EntityDetailActivity extends AppCompatActivity {
    private TextView nameView;
    private TextView hotView;
    private TextView bioView;
    private UrlImageView imgView;
    private MyListView relationListView;
    private MyListView propListView;
    private ArrayList<CovidRelation> relationList;
    private ArrayList<Map.Entry<String,String>> propList;
    PropListAdapter mPropListAdapter;
    RelationListAdapter mRelationListAdapter;
    private Intent backIntent = new Intent();
    public static final int MIN_CLICK_DELAY_TIME = 900;
    private long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_entity_detail);
        relationListView = findViewById(R.id.view_entity_detail_relation);
        propListView = findViewById(R.id.view_entity_detail_prop);
        nameView = findViewById(R.id.view_entity_detail_name);
        hotView = findViewById(R.id.view_entity_detail_hot);
        bioView = findViewById(R.id.view_entity_detail_bio);
        imgView = findViewById(R.id.view_entity_detail_img);

        updateList(getIntent());
        mPropListAdapter = new PropListAdapter(this,propList);
        mRelationListAdapter = new RelationListAdapter(this,relationList);

        relationListView.setAdapter(mRelationListAdapter);
        propListView.setAdapter(mPropListAdapter);

        relationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long currentTime = Calendar.getInstance().getTimeInMillis();
                if (currentTime - lastClickTime <= MIN_CLICK_DELAY_TIME) {
                    return;
                }
                lastClickTime = currentTime;
                Toast.makeText(parent.getContext(),relationList.get(position).getLabel(),Toast.LENGTH_SHORT).show();
                backIntent.putExtra("result", relationList.get(position).getLabel());
                //设置返回数据
                EntityDetailActivity.this.setResult(RESULT_OK, backIntent);
                //关闭Activity
                EntityDetailActivity.this.finish();
            }
        });
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        updateList(getIntent());
        mRelationListAdapter.notifyDataSetChanged();
        mPropListAdapter.notifyDataSetChanged();
    }

    private void updateList(Intent intent) {
        String entityLabel = intent.getStringExtra("label");
        YiqingEntity entity= YiqingEntityManager.getYiqingEntity(entityLabel)[0];
        backIntent.putExtra("result",entity.getLabel());
        String hotText = "热度:"+(int)(entity.getHot()*100);
        nameView.setText(entity.getLabel());
        hotView.setText(hotText);
        bioView.setText(entity.getAbstractInfo().getBaidu());
        if(entity.getImg()!=null){
            imgView.setImageURL(entity.getImg());
        }
        relationList = new ArrayList<>(Arrays.asList(entity.getAbstractInfo().getCOVID().getRelations()));
        propList = new ArrayList<>(entity.getAbstractInfo().getCOVID().getProperties().entrySet());
    }

    class PropListAdapter extends ArrayAdapter<Map.Entry<String,String>> {
        private ArrayList<Map.Entry<String,String>> mList;
        public PropListAdapter(Context context, ArrayList<Map.Entry<String,String>> list) {
            super(context, android.R.layout.simple_list_item_1, list);
            mList=list;
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (null == convertView) {
                convertView = getLayoutInflater().inflate(R.layout.item_entity_prop, null);
            }
            Map.Entry<String,String> c = getItem(position);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.view_entity_prop_name);
            TextView contentTextView = (TextView) convertView.findViewById(R.id.view_entity_prop_content);
            assert c != null;

            nameTextView.setText(c.getKey());
            contentTextView.setText(c.getValue());
            return convertView;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }

    class RelationListAdapter extends ArrayAdapter<CovidRelation> {
        private ArrayList<CovidRelation> mList;
        public RelationListAdapter(Context context, ArrayList<CovidRelation> list) {
            super(context, android.R.layout.simple_list_item_1, list);
            mList=list;
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (null == convertView) {
                convertView = getLayoutInflater().inflate(R.layout.item_entity_relation, null);
            }
            CovidRelation c = getItem(position);
            TextView relationTextView = (TextView) convertView.findViewById(R.id.view_entity_relation);
            ImageView imgView = (ImageView) convertView.findViewById(R.id.view_entity_relation_img);
            TextView otherTextView = (TextView) convertView.findViewById(R.id.view_entity_relation_other);
            assert c != null;

            relationTextView.setText(c.getRelation());
            otherTextView.setText(c.getLabel());
            if(c.getForward()){

            } else {

            }
            return convertView;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
