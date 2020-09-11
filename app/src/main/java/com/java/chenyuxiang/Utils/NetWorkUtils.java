package com.java.chenyuxiang.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtils {
    private static final String TAG = "connect";
    private static NetWorkUtils instance ;
    private static Context mainActivity;
    private NetWorkUtils(Context m){
        mainActivity = m;
    }

    public static NetWorkUtils getInstance(Context m) {
        if(instance == null){
            instance = new NetWorkUtils(m);
        }
        return instance;
    }
    public static Context getContext(){
        return mainActivity;
    }

    public static boolean isNetworkAvailable() {
        Context context = mainActivity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (NetworkInfo info : networkInfo) {
                    // 判断当前网络状态是否为连接状态
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

