package com.java.chenyuxiang.channelUI;

/**
 * 频道实体类
 * Created by YoKeyword on 15/12/29.
 */
public class ChannelEntity {

    private long id;
    private String name;
    private boolean isCurrentChannel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCurrent(){return isCurrentChannel;}

    public void setCurrent(boolean isCurrent){isCurrentChannel=isCurrent;}
}
