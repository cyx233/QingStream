package com.java.tanghao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.*;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

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
