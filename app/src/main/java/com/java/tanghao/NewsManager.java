package com.java.tanghao;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;


public class NewsManager{
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

    public Description[] getPageNews(String url){
        News news[] = new News[0];
        Description[] d = new Description[0];
        try{
            QingUtils.GetHttpResponseTask g = new QingUtils.GetHttpResponseTask();
            QingUtils.ParseNewsTask p = new QingUtils.ParseNewsTask();
            String data = g.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url).get();
            news = p.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data).get();
            for(int i = 0; i < news.length; i++){
                Integer size = getNewsContent(news[i].get_id()).size();
                if(size == 0){
                    news[i].setIsRead(false);
                    news[i].setIsFavorite(false);
                }
                else{
                    news[i] = getNewsContent(news[i].get_id()).get(0);
                }
            }
            insertNews(news);
            d = new Description[news.length];
            for(int i = 0; i < news.length; i++){
                d[i] = new Description(news[i]);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return d;
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

    public ArrayList<Description> getAllNews(){
        try {
            GetAllNewsTask getAllNewsTask = new GetAllNewsTask();
            return new ArrayList<Description>(Arrays.asList(getAllNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"0").get()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetAllNewsTask extends AsyncTask<String, Void, Description[]>{
        @Override
        protected  Description[] doInBackground(String... params){
            News[] news = newsDao.getAllNews();
            Description[] d = new Description[news.length];
            for(int i = 0; i < news.length; i++){
                d[i] = new Description(news[i]);
            }
            return d;
        }
    }

    public ArrayList<Description> getTypeNews(String type){
        try {
            GetTypeNewsTask getTypeNewsTask = new GetTypeNewsTask();
            return new ArrayList<Description>(Arrays.asList(getTypeNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, type).get()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetTypeNewsTask extends AsyncTask<String, Void, Description[]>{
        @Override
        protected  Description[] doInBackground(String... params){
            News[] news = newsDao.getTypeNews(params[0]);
            Description[] d = new Description[news.length];
            for(int i = 0; i < news.length; i++){
                d[i] = new Description(news[i]);
            }
            return d;
        }
    }

    public ArrayList<Description> getClusterNews(String cluster){
        try {
            GetClusterNewsTask getClusterNewsTask = new GetClusterNewsTask();
            return new ArrayList<Description>(Arrays.asList(getClusterNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, cluster).get()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetClusterNewsTask extends AsyncTask<String, Void, Description[]>{
        @Override
        protected  Description[] doInBackground(String... params){
            News[] news = newsDao.getClusterNews(params[0]);
            Description[] d = new Description[news.length];
            for(int i = 0; i < news.length; i++){
                d[i] = new Description(news[i]);
            }
            return d;
        }
    }

    public ArrayList<Description> getSearchNews(String value){
        try {
            GetSearchNewsTask getSearchNewsTask = new GetSearchNewsTask();
            return new ArrayList<Description>(Arrays.asList(getSearchNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, value).get()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetSearchNewsTask extends AsyncTask<String, Void, Description[]>{
        @Override
        protected  Description[] doInBackground(String... params){
            News[] news = newsDao.getSearchNews(params[0]);
            Description[] d = new Description[news.length];
            for(int i = 0; i < news.length; i++){
                d[i] = new Description(news[i]);
            }
            return d;
        }
    }

    public void updateIsRead(News news){
        try {
            UpdateIsReadTask updateIsReadTask = new UpdateIsReadTask();
            updateIsReadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, news);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private class UpdateIsReadTask extends AsyncTask<News, Void, Void>{
        @Override
        protected Void doInBackground(News... params){
            newsDao.updateIsRead(params[0].getIsRead(), params[0].get_id());
            return null;
        }
    }

    public void updateClusterCategory(Description description){
        try {
            UpdateClusterCategoryTask updateClusterCategoryTask = new UpdateClusterCategoryTask();
            updateClusterCategoryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, description);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private class UpdateClusterCategoryTask extends AsyncTask<Description, Void, Void>{
        @Override
        protected Void doInBackground(Description... params){
            newsDao.updateClusterCategory(params[0].getClusterCategory(), params[0].getId());
            return null;
        }
    }

    public void updateIsFavorate(News news){
        try {
            UpdateIsFavorateTask updateIsFavorateTask = new UpdateIsFavorateTask();
            updateIsFavorateTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, news);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private class UpdateIsFavorateTask extends AsyncTask<News, Void, Void>{
        @Override
        protected Void doInBackground(News... params){
            newsDao.updateIsFavorite(params[0].getIsFavorite(), params[0].get_id());
            return null;
        }
    }

    public ArrayList<News> getNewsContent(String id){
        try {
            GetNewsContentTask getNewsContentTask = new GetNewsContentTask();
            return new ArrayList<News>(Arrays.asList(getNewsContentTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, id).get()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetNewsContentTask extends AsyncTask<String, Void, News[]>{
        @Override
        protected News[] doInBackground(String... params){
            return newsDao.getIdNews(params[0]);
        }
    }


    public Description[] initNews(InputStreamReader sr){
        try {
            InitNewsTask initNewsTask = new InitNewsTask();
            return initNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sr).get();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class InitNewsTask extends AsyncTask<InputStreamReader, Void, Description[]>{
        @Override
        protected Description[] doInBackground(InputStreamReader... params){
            News[] news = new News[0];
            Description[] d = new Description[0];
            StringBuilder sb = new StringBuilder();
            try {
                InputStreamReader inputReader = params[0];
                BufferedReader bufReader = new BufferedReader(inputReader);
                String line="";
                while((line = bufReader.readLine()) != null)
                    sb.append(line);
                String s = sb.toString();
                ExclusionStrategy myExclusionStrategy = new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes fa) {
                        return fa.getName().equals("isRead") || fa.getName().equals("isFavorite"); // <---
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                };

                Gson gson = new GsonBuilder()
                        .setExclusionStrategies(myExclusionStrategy)
                        .create();
                NewsApi na = gson.fromJson(s, NewsApi.class);
                if (na != null) news = na.getData();
                for(int i = 0; i < news.length; i++){
                    Integer size = getNewsContent(news[i].get_id()).size();
                    if(size == 0){
                        news[i].setIsRead(false);
                        news[i].setIsFavorite(false);
                    }
                    else{
                        news[i] = getNewsContent(news[i].get_id()).get(0);
                    }
                }
                insertNews(news);
                d = new Description[news.length];
                for(int i = 0; i < news.length; i++){
                    d[i] = new Description(news[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return d;
        }
    }
}
