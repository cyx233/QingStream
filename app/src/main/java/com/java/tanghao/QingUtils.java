package com.java.tanghao;

import android.os.AsyncTask;

import androidx.room.*;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;





public class QingUtils {
    static class GetHttpResponseTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder sb = new StringBuilder();
            for(String s: strings)sb.append(s);
            String httpurl = sb.toString();
            HttpURLConnection connection = null;
            InputStream is = null;
            BufferedReader br = null;
            String result = null;
            try {
                URL url = new URL(httpurl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(15000);
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
            Gson gson = new Gson();
            String s = null;
            if(strings[0] != null)s = strings[0];
            NewsApi na = gson.fromJson(s, NewsApi.class);
            if (na != null) return na.getData();
            return new News[0];
        }
    }
}

class NewsApi{
    private News data[];
    class Pagination{
        private Long page;
        private Long size;
        private Long total;
    }
    private Pagination pagination;
    private Boolean status;
    public News[] getData(){return data;}
}
