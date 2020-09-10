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








