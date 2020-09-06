package com.java.tanghao;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Arrays;


public class NewsManager extends Thread{
    private static NewsManager Instance = null;
    private final NewsDao newsDao;
    private final AppDatabase appDB;

    private NewsManager(AppDatabase appDB){
        this.appDB = appDB;
        this.newsDao = this.appDB.newsDao();
    }

    public static NewsManager getInstance(AppDatabase appDB){
        if(Instance == null){
            Instance = new NewsManager(appDB);
        }
        return Instance;
    }

    public News[] getPageNews(String url){
        News news[] = new News[0];
        try{
            QingUtils.GetHttpResponseTask g = new QingUtils.GetHttpResponseTask();
            QingUtils.ParseNewsTask p = new QingUtils.ParseNewsTask();
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
        try {
            InsertNewsTask insertNewsTask = new InsertNewsTask();
            insertNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,news);
        }
        catch (Exception e){
            e.printStackTrace();
        }
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

    public ArrayList<News> getTypeNews(String type){
        try {
            GetTypeNewsTask getTypeNewsTask = new GetTypeNewsTask();
            return new ArrayList<News>(Arrays.asList(getTypeNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, type).get()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetTypeNewsTask extends AsyncTask<String, Void, News[]>{
        @Override
        protected  News[] doInBackground(String... params){
            return newsDao.getTypeNews(params[0]);
        }
    }

    public ArrayList<News> getSearchNews(String value){
        try {
            GetSearchNewsTask getSearchNewsTask = new GetSearchNewsTask();
            return new ArrayList<News>(Arrays.asList(getSearchNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, value).get()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetSearchNewsTask extends AsyncTask<String, Void, News[]>{
        @Override
        protected  News[] doInBackground(String... params){
            return newsDao.getTypeNews(params[0]);
        }
    }

    public void updateIsRead(Boolean isRead){
        try {
            UpdateIsReadTask updateIsReadTask = new UpdateIsReadTask();
            updateIsReadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, isRead);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private class UpdateIsReadTask extends AsyncTask<Boolean, Void, Void>{
        @Override
        protected Void doInBackground(Boolean... params){
            newsDao.updateIsRead(params[0]);
            return null;
        }
    }

    public void updateIsFavorate(Boolean isFavorite){
        try {
            UpdateIsFavorateTask updateIsFavorateTask = new UpdateIsFavorateTask();
            updateIsFavorateTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, isFavorite);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private class UpdateIsFavorateTask extends AsyncTask<Boolean, Void, Void>{
        @Override
        protected Void doInBackground(Boolean... params){
            newsDao.updateIsFavorite(params[0]);
            return null;
        }
    }

}
