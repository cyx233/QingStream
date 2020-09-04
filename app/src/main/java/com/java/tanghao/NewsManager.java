package com.java.tanghao;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.*;
import com.google.gson.Gson;

import java.util.*;

public class NewsManager {
    private static NewsManager Instance = null;
    private final NewsDao newsDao;
    private final AppDatabase appDB;

    private NewsManager(Context context){
        this.appDB = AppDatabase.getInstance(context);
        this.newsDao = this.appDB.newsDao();
    }

    public static NewsManager getNewsManager(Context context){
        if(Instance == null){
            Instance = new NewsManager(context);
        }
        return Instance;
    }

    public News[] getPageNews(String url){
        News news[] = new News[0];
        try{
            Utils.GetHttpResponseTask g = new Utils.GetHttpResponseTask();
            Utils.ParseNewsTask p = new Utils.ParseNewsTask();
            String data = g.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url).get();
            news = p.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data).get();
            insertNews(news);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return news;
    }

    public void insertNews(News... news){
        InsertNewsTask insertNewsTask = new InsertNewsTask();
        insertNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,news);
    }

    private class InsertNewsTask extends AsyncTask<News, Void, Void>{
        @Override
        protected Void doInBackground(News... news){
            newsDao.insert(news);
            return null;
        }
    }

    public ArrayList<News> getAllNews(){
        try {
            GetAllNewsTask getAllNewsTask = new GetAllNewsTask();
            return new ArrayList<News>(Arrays.asList(getAllNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"0").get()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetAllNewsTask extends AsyncTask<String, Void, News[]>{
        @Override
        protected  News[] doInBackground(String... params){
            return newsDao.getAllNews();
        }
    }

}
