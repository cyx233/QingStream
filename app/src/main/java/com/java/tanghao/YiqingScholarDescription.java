package com.java.tanghao;

public class YiqingScholarDescription {
    private String id;
    private String name;
    private String name_zh;
    private Indice indice;
    private String aff;
    private String position;
    private String avatar;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getName_zh() {
        return name_zh;
    }

    public Indice getIndice() {
        return indice;
    }

    public String getAff() {
        return aff;
    }

    public String getPosition() {
        return position;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setName_zh(String name_zh) {
        this.name_zh = name_zh;
    }

    public void setIndice(Indice indice) {
        this.indice = indice;
    }

    public void setAff(String aff) {
        this.aff = aff;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAvatar(String avatar){
        this.avatar = avatar;
    }

    YiqingScholarDescription(YiqingScholar yiqingScholar){
        this.id = yiqingScholar.getId();
        this.name = yiqingScholar.getName();
        this.name_zh = yiqingScholar.getName_zh();
        this.indice = yiqingScholar.getIndices();
        this.aff = yiqingScholar.getProfile().getAffiliation();
        this.position = yiqingScholar.getProfile().getPosition();
        this.avatar = yiqingScholar.getAvatar();
    }
}
