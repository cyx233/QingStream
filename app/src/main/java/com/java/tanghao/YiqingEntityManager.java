package com.java.tanghao;

import android.os.AsyncTask;

public class YiqingEntityManager {
    private static YiqingEntityManager Instance = null;

    private YiqingEntityManager(){
    }

    public static YiqingEntityManager getInstance(){
        if(Instance == null){
            Instance = new YiqingEntityManager();
        }
        return Instance;
    }

    public static YiqingEntity[] getYiqingEntity(String entity){
        YiqingEntity[] y = new YiqingEntity[0];
        try {
            QingUtils.GetHttpResponseTask g = new QingUtils.GetHttpResponseTask();
            QingUtils.ParseYiqingEntityTask p = new QingUtils.ParseYiqingEntityTask();
            String data = g.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery?entity="+"entity").get();
            y = p.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data).get();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return y;
    }
}
