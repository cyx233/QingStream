package com.java.tanghao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.*;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.util.HashMap;

@Entity(tableName = "yiqingdata")
@TypeConverters({Integer2DConverter.class})
public class YiqingData {
    @PrimaryKey
    @NonNull
    private String location;
    private String begin;
    private Integer[][] data;

    public YiqingData(String location, YiqingDataApi a){
        this.location = location;
        this.begin = a.getBegin();
        this.data = a.getData();
    }

    public YiqingData(String location, String begin, Integer[][] data){
        this.location = location;
        this.begin = begin;
        this.data = data;
    }

    public HashMap<String, Integer> getToday(){
        HashMap<String, Integer> hm = new HashMap<>();
        hm.put("confirmed", data[data.length - 1][0]);
        hm.put("suspected", data[data.length - 1][1]);
        hm.put("cure", data[data.length - 1][2]);
        hm.put("dead", data[data.length - 1][3]);
        return hm;
    }

    public HashMap<String, Integer> getYesterday(){
        HashMap<String, Integer> hm = new HashMap<>();
        hm.put("confirmed", data[data.length - 2][0]);
        hm.put("suspected", data[data.length - 2][1]);
        hm.put("cure", data[data.length - 2][2]);
        hm.put("dead", data[data.length - 2][3]);
        return hm;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setData(Integer[][] data) {
        this.data = data;
    }

    public String getLocation() {
        return location;
    }

    public String getBegin() {
        return begin;
    }

    public Integer[][] getData() {
        return data;
    }
}

class YiqingDataApi{
    private String begin;
    private Integer[][] data;

    YiqingDataApi(String begin, Integer[][] data){
        this.begin = begin;
        this.data = data;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setData(Integer[][] data) {
        this.data = data;
    }

    public String getBegin() {
        return begin;
    }

    public Integer[][] getData() {
        return data;
    }
}
