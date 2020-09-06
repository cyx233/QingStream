package com.java.tanghao;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Arrays;

public class YiqingDataManager {
    private static YiqingDataManager Instance = null;
    private final YiqingDataDao yiqingDataDao;
    private final AppDatabase appDB;

    private YiqingDataManager(AppDatabase appDB){
        this.appDB = appDB;
        this.yiqingDataDao = this.appDB.yiqingDataDao();
    }

    public static YiqingDataManager getInstance(AppDatabase appDB){
        if(Instance == null){
            Instance = new YiqingDataManager(appDB);
        }
        return Instance;
    }

    public YiqingData[] getPageYiqingData(String url){
        YiqingData yiqingData[] = new YiqingData[0];
        try{
            QingUtils.GetHttpResponseTask g = new QingUtils.GetHttpResponseTask();
            QingUtils.ParseYiqingDataTask p = new QingUtils.ParseYiqingDataTask();
            String data = g.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url).get();
            yiqingData = p.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data).get();
            insertYiqingData(yiqingData);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return yiqingData;
    }

    public void insertYiqingData(YiqingData... yiqingData){
        try {
            YiqingDataManager.InsertYiqingDataTask insertNewsTask = new YiqingDataManager.InsertYiqingDataTask();
            insertNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,yiqingData);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private class InsertYiqingDataTask extends AsyncTask<YiqingData, Void, Void>{
        @Override
        protected Void doInBackground(YiqingData... yiqingData){
            yiqingDataDao.insert(yiqingData);
            return null;
        }
    }

    public ArrayList<YiqingData> getAllYiqingData(){
        try {
            YiqingDataManager.GetAllYiqingDataTask getAllYiqingDataTask = new YiqingDataManager.GetAllYiqingDataTask();
            return new ArrayList<YiqingData>(Arrays.asList(getAllYiqingDataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"0").get()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetAllYiqingDataTask extends AsyncTask<String, Void, YiqingData[]>{
        @Override
        protected  YiqingData[] doInBackground(String... params){
            return yiqingDataDao.getAllYiqingData();
        }
    }

    public ArrayList<YiqingData> getLocationYiqingData(String location){
        try {
            YiqingDataManager.GetLocationYiqingDataTask getLocationYiqingDataTask = new YiqingDataManager.GetLocationYiqingDataTask();
            return new ArrayList<YiqingData>(Arrays.asList(getLocationYiqingDataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,location).get()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetLocationYiqingDataTask extends AsyncTask<String, Void, YiqingData[]>{
        @Override
        protected  YiqingData[] doInBackground(String... params){
            return yiqingDataDao.getLocationYiqingData(params[0]);
        }
    }

}
