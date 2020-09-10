package com.java.tanghao;

public class AbstractInfo{
    private String enwiki;
    private String baidu;
    private String zhwiki;
    private Covid COVID;

    public void setEnwiki(String enwiki) {
        this.enwiki = enwiki;
    }

    public void setBaidu(String baidu) {
        this.baidu = baidu;
    }

    public void setZhwiki(String zhwiki) {
        this.zhwiki = zhwiki;
    }

    public void setCOVID(Covid COVID) {
        this.COVID = COVID;
    }

    public String getEnwiki() {
        return enwiki;
    }

    public String getBaidu() {
        return baidu;
    }

    public String getZhwiki() {
        return zhwiki;
    }

    public Covid getCOVID() {
        return COVID;
    }
}
