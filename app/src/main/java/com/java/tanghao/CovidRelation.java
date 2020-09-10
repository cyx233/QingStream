package com.java.tanghao;

public class CovidRelation{
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