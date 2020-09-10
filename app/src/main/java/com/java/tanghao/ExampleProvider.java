package com.java.tanghao;

import android.content.Context;

public class ExampleProvider {
    private AppDatabase appDB;
    private HistoryDao historyDao;

    public boolean OnCreate(){
        return true;
    }

    ExampleProvider(AppDatabase appDB){
        this.appDB = appDB;
        historyDao = appDB.historyDao();
    }
}
