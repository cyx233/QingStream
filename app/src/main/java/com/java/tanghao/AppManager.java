package com.java.tanghao;

import android.content.Context;
import android.os.AsyncTask;
import android.content.Context;
import android.os.AsyncTask;

public class AppManager {
    private static AppManager Instance = null;
    private static AppDatabase appDB = null;
    private AppManager(Context context){
        this.appDB = AppDatabase.getInstance(context);
    }
    public static AppManager getAppManager(Context context){
        if(Instance == null){
            Instance = new AppManager(context);
        }
        return Instance;
    }

    public static NewsManager getNewsManager(){
        return NewsManager.getInstance(appDB);
    }

    public static CategoryManager getCategoryManager(){
        return CategoryManager.getInstance(appDB);
    }

    public static YiqingDataManager getYiqingDataManager(){
        return YiqingDataManager.getInstance(appDB);
    }

    public static YiqingScholarManager getYiqingScholarManager(){
        return YiqingScholarManager.getInstance(appDB);
    }
}
