package com.java.tanghao;

import android.content.Context;
import android.os.AsyncTask;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class QingUtils {
    static class GetHttpResponseTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder sb = new StringBuilder();
            for (String s : strings) sb.append(s);
            String httpurl = sb.toString();
            HttpURLConnection connection = null;
            InputStream is = null;
            BufferedReader br = null;
            String result = null;
            try {
                URL url = new URL(httpurl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(30000);
                connection.setReadTimeout(60000);
                connection.connect();
                if (connection.getResponseCode() == 200) {
                    is = connection.getInputStream();
                    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    StringBuffer sbf = new StringBuffer();
                    String temp = null;
                    while ((temp = br.readLine()) != null) {
                        sbf.append(temp);
                        sbf.append("\r\n");
                    }
                    result = sbf.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
//            System.out.println(result);
                if (null != br) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != is) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                connection.disconnect();
                return result;
            }
        }
    }


    static class ParseNewsTask extends AsyncTask<String, Void, News[]> {
        @Override
        protected News[] doInBackground(String... strings) {
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
            String s = null;
            if (strings[0] != null) s = strings[0];
            else s = "";
            NewsApi na = gson.fromJson(s, NewsApi.class);
            if (na != null) return na.getData();
            return new News[0];
        }
    }

    static class ParseYiqingDataTask extends AsyncTask<String, Void, YiqingData[]> {
        @Override
        protected YiqingData[] doInBackground(String... strings) {
            GsonBuilder builder = new GsonBuilder();
            builder.serializeNulls();
            Gson gson = builder.create();
            String s = null;
            if (strings[0] != null) s = strings[0];
            else s = "";
            Type mapType = new TypeToken<Map<String, YiqingDataApi>>() {
            }.getType();
            Map<String, YiqingDataApi> j = gson.fromJson(s, mapType);
            YiqingData[] result = new YiqingData[j.size()];
            int index = 0;
            for (Map.Entry<String, YiqingDataApi> entry : j.entrySet()) {
                result[index++] = new YiqingData(entry.getKey(), entry.getValue());
                Integer[][] data = result[index-1].getData();
                for(int i=0;i<data.length;++i){
                    for(int k=0;k<data[i].length;++k){
                        if(data[i][k]==null)
                            data[i][k]=0;
                    }
                }
                result[index-1].setData(data);
            }
            return result;
        }

    }

    static class ParseYiqingEntityTask extends AsyncTask<String, Void, YiqingEntity[]>{
        @Override
        protected YiqingEntity[] doInBackground(String... strings) {
            Gson gson = new Gson();
            String s = null;
            if (strings[0] != null) s = strings[0];
            else s = "";
            YiqingEntityApi j = gson.fromJson(s, YiqingEntityApi.class);
            if(j == null)
                return new YiqingEntity[0];
            return j.getData();
        }
    }

    static class ParseYiqingScholarTask extends AsyncTask<String, Void, YiqingScholar[]>{
        @Override
        protected YiqingScholar[] doInBackground(String... strings) {
            Gson gson = new Gson();
            String s = null;
            if (strings[0] != null) s = strings[0];
            else s = "";
            YiqingScholarApi j = gson.fromJson(s, YiqingScholarApi.class);
            if(j == null)
                return new YiqingScholar[0];
            return j.getData();
        }
    }

    static String[] clusterTask(ClusterParam clusterParam){
        String[] s = new String[0];
        try {
            ClusterTask c= new ClusterTask();
            return c.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, clusterParam).get();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }

    static class ClusterTask extends AsyncTask<ClusterParam, Void, String[]>{
        @Override
        protected String[] doInBackground(ClusterParam... clusterParams) {
            String[] result = new String[0];
            if (! Python.isStarted()) {
                Python.start(new AndroidPlatform(clusterParams[0].getContext()));
                Python py = Python.getInstance();
                ArrayList<Description> d = new ArrayList<>();
                NewsManager mNewsManager = AppManager.getNewsManager();
                d = mNewsManager.getTypeNews("event");
                Description[] dd = (Description[]) d.toArray(new Description[d.size()]);
                StringBuilder sb = new StringBuilder();
                Gson gson = new Gson();
                sb.append(clusterParams[0].getClusterNum());
                sb.append("QingSteamSplit");
                for (int j = 0; j < dd.length; j++) {
                    sb.append(gson.toJson(dd[j]));
                    sb.append("QingSteamSplit");
                }
                String param = sb.toString();
                PyObject obj = py.getModule("cluster").callAttr("cluster_func", param);
                String data = obj.toJava(String.class);
                String[] tmp = data.split("QingSteamSplit");
                result = tmp[0].split("QingClusterSplit");
                String[] des = tmp[1].split("QingNewsSplit");
                Description[] descriptions = new Description[d.size()];
                for(int j = 0; j < d.size(); j++){
                    descriptions[j] = gson.fromJson(des[j], Description.class);
                    mNewsManager.updateClusterCategory(descriptions[j]);
                }
            }
            return result;
        }
    }
}



