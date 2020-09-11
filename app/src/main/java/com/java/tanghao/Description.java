package com.java.tanghao;

public class Description{
    private String id;
    private String date;
    private String title;
    private Boolean isRead;
    private String[] label;
    private String source;
    private String clusterCategory = "";

    Description(String id, String date, String title, Boolean isRead, String[] label, String source, String clusterLabel){
        this.id = id;
        this.date = date;
        this.title = title;
        this.isRead = isRead;
        this.label = label;
        this.source = source;
        this.clusterCategory = clusterCategory;
    }

    Description(String id, String clusterCategory){
        this.id = id;
        this.clusterCategory = clusterCategory;
    }

    Description(News news){
        this.id = news.get_id();
        this.date = news.getDate();
        this.title = news.getTitle();
        this.isRead = news.getIsRead();
        this.label = news.getLabel();
        this.source = news.getSource();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsRead(Boolean isRead){
        this.isRead = isRead;
    }

    public void setLabel(String[] label) {
        this.label = label;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setClusterLabel(String clusterCategory) {
        this.clusterCategory = clusterCategory;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getIsRead(){
        return isRead;
    }

    public String[] getLabel() {
        return label;
    }

    public String getSource() {
        return source;
    }

    public String getClusterCategory() {
        return clusterCategory;
    }
}
