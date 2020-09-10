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

    public ArrayList<YiqingScholarDescription> getScholar(Boolean passedaway){
        try {
            YiqingScholarManager.GetScholarTask getScholarTask = new YiqingScholarManager.GetScholarTask();
            return new ArrayList<YiqingScholarDescription>(Arrays.asList(getScholarTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,passedaway).get()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetScholarTask extends AsyncTask<Boolean, Void, YiqingScholarDescription[]>{
        @Override
        protected  YiqingScholarDescription[] doInBackground(Boolean... params){
            YiqingScholar yiqingScholar[] = yiqingScholarDao.getYiqingScholar(params[0]);
            YiqingScholarDescription d[] = new YiqingScholarDescription[yiqingScholar.length];
            for(int i = 0; i < yiqingScholar.length; i++){
                d[i] = new YiqingScholarDescription(yiqingScholar[i]);
            }
            return d;
        }
    }

    public ArrayList<YiqingScholar> getIdScholar(String id){
        try {
            YiqingScholarManager.GetIdScholarTask getIdScholarTask = new YiqingScholarManager.GetIdScholarTask();
            return new ArrayList<YiqingScholar>(Arrays.asList(getIdScholarTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, id).get()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetIdScholarTask extends AsyncTask<String, Void, YiqingScholar[]>{
        @Override
        protected  YiqingScholar[] doInBackground(String... params){
            return yiqingScholarDao.getIdYiqingScholar(params[0]);
        }
    }
}
