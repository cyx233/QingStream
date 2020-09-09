package com.java.chenyuxiang.listViewUi;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.java.chenyuxiang.R;
import com.java.chenyuxiang.detailUI.MarqueeTextView;
import com.java.chenyuxiang.detailUI.ScholarDetailActivity;
import com.java.chenyuxiang.view.SwipeRefreshView;
import com.java.chenyuxiang.view.UrlImageView;
import com.java.tanghao.AppManager;
import com.java.tanghao.YiqingScholarDescription;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FragmentScholar extends ListFragment {
    ArrayList<YiqingScholarDescription> scholarList;
    ScholarListAdapter adapter;//new出适配器的实例
    private SwipeRefreshView mSwipeRefreshView;
    private Integer currentPage;

    public FragmentScholar(ArrayList<YiqingScholarDescription> list, Integer currentPage){
        scholarList = list;
        this.currentPage = currentPage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentPage = 1;
        adapter = new ScholarListAdapter(requireContext(),scholarList);//new出适配器的实例
        setListAdapter(adapter);//和List绑定
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mSwipeRefreshView = view.findViewById(R.id.view_news_swipe);
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
                Toast.makeText(getContext(), "加载数据", Toast.LENGTH_SHORT).show();
                mSwipeRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myLoadOperation();
                        adapter.notifyDataSetChanged();
                        // 加载完后调用该方法
                        mSwipeRefreshView.setLoading(false);
                    }
                }, 1500);
            }
        });
        return view;
    }

    private void myLoadOperation(){
        ArrayList<YiqingScholarDescription> list;
        list = AppManager.getYiqingScholarManager().getScholar(false);
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
        YiqingScholarDescription detail = scholarList.get(position);
        Intent intent = new Intent(this.getActivity(), ScholarDetailActivity.class);
        intent.putExtra("id",detail.getId());
        startActivity(intent);
    }

    class ScholarListAdapter extends ArrayAdapter<YiqingScholarDescription> {
        private ArrayList<YiqingScholarDescription> mList;
        private ListView mListView;
        private Bitmap mLoadingBitmap;
        /**
         * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
         */
        private LruCache<String, BitmapDrawable> mMemoryCache;

        public ScholarListAdapter(Context context, ArrayList<YiqingScholarDescription> list) {
            super(requireActivity(), android.R.layout.simple_list_item_1, list);
            mList=list;
            mLoadingBitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.empty_photo);
            // 获取应用程序最大可用内存
            int maxMemory = (int) Runtime.getRuntime().maxMemory();
            int cacheSize = maxMemory / 8;
            mMemoryCache = new LruCache<String, BitmapDrawable>(cacheSize) {
                @Override
                protected int sizeOf(String key, BitmapDrawable drawable) {
                    return drawable.getBitmap().getByteCount();
                }
            };
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (mListView == null) {
                mListView = (ListView) parent;
            }
            if (null == convertView) {
                convertView = requireActivity().getLayoutInflater().inflate(R.layout.list_item_scholar, null);
            }
            YiqingScholarDescription c = getItem(position);
            assert c != null;
            String url = c.getAvatar();
            BitmapDrawable drawable = getBitmapFromMemoryCache(url);
            UrlImageView avatarView = (UrlImageView)convertView.findViewById(R.id.view_list_scholar_avatar);
            if (drawable != null) {
                avatarView.setImageDrawable(drawable);
            } else if (cancelPotentialWork(url, avatarView)) {
                BitmapWorkerTask task = new BitmapWorkerTask(avatarView);
                AsyncDrawable asyncDrawable = new AsyncDrawable(getContext()
                        .getResources(), mLoadingBitmap, task);
                avatarView.setImageDrawable(asyncDrawable);
                task.execute(url);
            }

            TextView nameTextView,companyTextView,profileTextView;
            MarqueeTextView dataTextView;
            nameTextView = (TextView)convertView.findViewById(R.id.view_list_scholar_name);
            dataTextView = (MarqueeTextView) convertView.findViewById(R.id.view_list_scholar_data);
            companyTextView = (TextView)convertView.findViewById(R.id.view_list_scholar_company);
            profileTextView = (TextView)convertView.findViewById(R.id.view_list_scholar_profile);

            if(c.getName_zh().equals(" "))
                nameTextView.setText(c.getName());
            else
                nameTextView.setText(c.getName_zh());
            Integer hIndex = c.getIndice().getHindex();
            Double activity = c.getIndice().getActivity();
            Double sociability = c.getIndice().getSociability();
            Integer citation = c.getIndice().getCitations();
            Integer paper = c.getIndice().getPubs();
            String data = "H指数:"+hIndex+" 活跃度:"+activity+" 学术合作:"+sociability+" 引用数:"+citation+" 论文数:"+paper;

            dataTextView.setText(data);
            profileTextView.setText(c.getPosition());
            companyTextView.setText(c.getAff());
            return convertView;
        }
        /**
         * 自定义的一个Drawable，让这个Drawable持有BitmapWorkerTask的弱引用。
         */
        class AsyncDrawable extends BitmapDrawable {
            private WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;
            public AsyncDrawable(Resources res, Bitmap bitmap,
                                 BitmapWorkerTask bitmapWorkerTask) {
                super(res, bitmap);
                bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(
                        bitmapWorkerTask);
            }
            public BitmapWorkerTask getBitmapWorkerTask() {
                return bitmapWorkerTaskReference.get();
            }
        }
        /**
         * 获取传入的ImageView它所对应的BitmapWorkerTask。
         */
        private BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
            if (imageView != null) {
                Drawable drawable = imageView.getDrawable();
                if (drawable instanceof AsyncDrawable) {
                    AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                    return asyncDrawable.getBitmapWorkerTask();
                }
            }
            return null;
        }

        /**
         * 取消掉后台的潜在任务，当认为当前ImageView存在着一个另外图片请求任务时
         * ，则把它取消掉并返回true，否则返回false。
         */
        public boolean cancelPotentialWork(String url, ImageView imageView) {
            BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
            if (bitmapWorkerTask != null) {
                String imageUrl = bitmapWorkerTask.imageUrl;
                if (imageUrl == null || !imageUrl.equals(url)) {
                    bitmapWorkerTask.cancel(true);
                } else {
                    return false;
                }
            }
            return true;
        }
        /**
         * 将一张图片存储到LruCache中。
         *
         * @param key
         *            LruCache的键，这里传入图片的URL地址。
         * @param drawable
         *            LruCache的值，这里传入从网络上下载的BitmapDrawable对象。
         */
        public void addBitmapToMemoryCache(String key, BitmapDrawable drawable) {
            if (getBitmapFromMemoryCache(key) == null) {
                mMemoryCache.put(key, drawable);
            }
        }

        /**
         * 从LruCache中获取一张图片，如果不存在就返回null。
         *
         * @param key
         *            LruCache的键，这里传入图片的URL地址。
         * @return 对应传入键的BitmapDrawable对象，或者null。
         */
        public BitmapDrawable getBitmapFromMemoryCache(String key) {
            return mMemoryCache.get(key);
        }

        /**
         * 异步下载图片的任务。
         *
         * @author guolin
         */
        class BitmapWorkerTask extends AsyncTask<String, Void, BitmapDrawable> {

            String imageUrl;

            private WeakReference<ImageView> imageViewReference;

            public BitmapWorkerTask(ImageView imageView) {
                imageViewReference = new WeakReference<ImageView>(imageView);
            }

            @Override
            protected BitmapDrawable doInBackground(String... params) {
                imageUrl = params[0];
                // 在后台开始下载图片
                Bitmap bitmap = downloadBitmap(imageUrl);
                BitmapDrawable drawable = new BitmapDrawable(getContext().getResources(), bitmap);
                addBitmapToMemoryCache(imageUrl, drawable);
                return drawable;
            }

            @Override
            protected void onPostExecute(BitmapDrawable drawable) {
                ImageView imageView = getAttachedImageView();
                if (imageView != null && drawable != null) {
                    imageView.setImageDrawable(drawable);
                }
            }

            /**
             * 获取当前BitmapWorkerTask所关联的ImageView。
             */
            private ImageView getAttachedImageView() {
                ImageView imageView = imageViewReference.get();
                BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask) {
                    return imageView;
                }
                return null;
            }

            /**
             * 建立HTTP请求，并获取Bitmap对象。
             *
             * @param imageUrl
             *            图片的URL地址
             * @return 解析后的Bitmap对象
             */
            private Bitmap downloadBitmap(String imageUrl) {
                Bitmap bitmap = null;
                HttpURLConnection con = null;
                try {
                    URL url = new URL(imageUrl);
                    con = (HttpURLConnection) url.openConnection();
                    con.setConnectTimeout(5 * 1000);
                    con.setReadTimeout(10 * 1000);
                    bitmap = BitmapFactory.decodeStream(con.getInputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                }
                return bitmap;
            }

        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}


