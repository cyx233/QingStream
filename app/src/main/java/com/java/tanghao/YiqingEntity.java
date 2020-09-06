package com.java.tanghao;

import com.google.gson.Gson;

public class YiqingEntity {
    private Double hot;
    private String label;
    private String url;
    private AbstractInfo abstractInfo;
    private String img;

    public void setHot(Double hot) {
        this.hot = hot;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAbstractInfo(AbstractInfo abstractInfo) {
        this.abstractInfo = abstractInfo;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Double getHot() {
        return hot;
    }

    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }

    public AbstractInfo getAbstractInfo() {
        return abstractInfo;
    }

    public String getImg() {
        return img;
    }
}

class YiqingEntityApi{
    private Integer code;
    private String msg;
    private YiqingEntity[] data;

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(YiqingEntity[] data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public YiqingEntity[] getData() {
        return data;
    }
}

class AbstractInfo{
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

class Covid{
    private CovidProperties properties;
    private CovidRelation[] relations;

    public void setProperties(CovidProperties properties) {
        this.properties = properties;
    }

    public void setRelations(CovidRelation[] relations) {
        this.relations = relations;
    }

    public CovidProperties getProperties() {
        return properties;
    }

    public CovidRelation[] getRelations() {
        return relations;
    }
}

class CovidProperties{
    private String 定义;
    private String 特征;
    private String 包括;
    private String 生存条件;
    private String 传播方式;
    private String 应用;

    public void set定义(String 定义) {
        this.定义 = 定义;
    }

    public void set特征(String 特征) {
        this.特征 = 特征;
    }

    public void set包括(String 包括) {
        this.包括 = 包括;
    }

    public void set生存条件(String 生存条件) {
        this.生存条件 = 生存条件;
    }

    public void set传播方式(String 传播方式) {
        this.传播方式 = 传播方式;
    }

    public void set应用(String 应用) {
        this.应用 = 应用;
    }

    public String get定义() {
        return 定义;
    }

    public String get特征() {
        return 特征;
    }

    public String get包括() {
        return 包括;
    }

    public String get生存条件() {
        return 生存条件;
    }

    public String get传播方式() {
        return 传播方式;
    }

    public String get应用() {
        return 应用;
    }
}


class CovidRelation{
    private String relation;
    private String url;
    private String label;
    private Boolean forward;

    public String getRelation() {
        return relation;
    }

    public String getUrl() {
        return url;
    }

    public String getLabel() {
        return label;
    }

    public Boolean getForward() {
        return forward;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setForward(Boolean forward) {
        this.forward = forward;
    }
}