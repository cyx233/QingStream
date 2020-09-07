package com.java.tanghao;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Arrays;

public class YiqingScholarManager {
    private static YiqingScholarManager Instance = null;
    private final YiqingScholarDao yiqingScholarDao;
    private final AppDatabase appDB;

    private YiqingScholarManager(AppDatabase appDB){
        this.appDB = appDB;
        this.yiqingScholarDao = this.appDB.yiqingScholarDao();
    }

    public static YiqingScholarManager getInstance(AppDatabase appDB){
        if(Instance == null){
            Instance = new YiqingScholarManager(appDB);
        }
        return Instance;
    }

    public YiqingScholar[] getPageScholar(String url){
        YiqingScholar yiqingScholars[] = new YiqingScholar[0];
        try{
            QingUtils.GetHttpResponseTask g = new QingUtils.GetHttpResponseTask();
            QingUtils.ParseYiqingScholarTask p = new QingUtils.ParseYiqingScholarTask();
            String data = g.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url).get();
            yiqingScholars = p.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data).get();
            insertScholar(yiqingScholars);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return yiqingScholars;
    }

    public void insertScholar(YiqingScholar... yiqingScholars){
        try {
            InsertScholarTask insertScholarTask = new YiqingScholarManager.InsertScholarTask();
            insertScholarTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,yiqingScholars);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private class InsertScholarTask extends AsyncTask<YiqingScholar, Void, Void>{
        @Override
        protected Void doInBackground(YiqingScholar... yiqingScholars){
            yiqingScholarDao.insert(yiqingScholars);
            return null;
        }
    }

    public ArrayList<YiqingScholar> getAllScholar(){
        try {
            YiqingScholarManager.GetAllScholarTask getAllScholarTask = new YiqingScholarManager.GetAllScholarTask();
            return new ArrayList<YiqingScholar>(Arrays.asList(getAllScholarTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"0").get()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetAllScholarTask extends AsyncTask<String, Void, YiqingScholar[]>{
        @Override
        protected  YiqingScholar[] doInBackground(String... params){
            return yiqingScholarDao.getAllYiqingScholar();
        }
    }
}
