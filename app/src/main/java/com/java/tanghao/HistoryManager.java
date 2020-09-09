package com.java.tanghao;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Arrays;


public class HistoryManager {
    private static HistoryManager Instance = null;
    private final HistoryDao historyDao;
    private final AppDatabase appDB;

    private HistoryManager(AppDatabase appDB) {
        this.appDB = appDB;
        this.historyDao = this.appDB.historyDao();
    }

    public static HistoryManager getInstance(AppDatabase appDB) {
        if (Instance == null) {
            Instance = new HistoryManager(appDB);
        }
        return Instance;
    }

    public void insertHistory(History... histories){
        try {
            HistoryManager.InsertHistoryTask insertCategoryTask = new HistoryManager.InsertHistoryTask();
            insertCategoryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,histories);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private class InsertHistoryTask extends AsyncTask<History, Void, Void>{
        @Override
        protected Void doInBackground(History... histories){
            historyDao.insert(histories);
            return null;
        }
    }

    public ArrayList<History> getAllHistories(){
        try {
            HistoryManager.GetAllHistoriesTask getAllHistoriesTask = new HistoryManager.GetAllHistoriesTask();
            return new ArrayList<History>(Arrays.asList(getAllHistoriesTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"0").get()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetAllHistoriesTask extends AsyncTask<String, Void, History[]>{
        @Override
        protected History[] doInBackground(String... s){
            return historyDao.getAllHistories();
        }
    }
}