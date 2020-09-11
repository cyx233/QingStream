package com.java.tanghao;

import android.content.Context;

public class ClusterParam {
    private Context context;
    private String clusterNum;

    public ClusterParam(Context context, String clusterNum){
        this.context = context;
        this.clusterNum = clusterNum;
    }

    public Context getContext() {
        return context;
    }

    public String getClusterNum() {
        return clusterNum;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setClusterNum(String clusterNum) {
        this.clusterNum = clusterNum;
    }
}
