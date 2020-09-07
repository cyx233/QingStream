package com.java.tanghao;

class YiqingScholarApi{
    Integer status;
    String message;
    YiqingScholar[] data;
}

public class YiqingScholar{
    //    Aff aff;
    String avatar;
    Boolean bind;
    String id;
    Indice indices;
    String name;
    String name_zh;
    Integer num_followed;
    Integer num_viewed;
    Profile profile;
    Integer score;
    String sourcetype;
    String[] tages;
    Double[] tags_score;
    Integer index;
    Integer tab;
    Boolean is_passedaway;
}

class Aff{

}

class Indice{
    Double activity;
    Integer citations;
    Double diversity;
    Integer gindex;
    Integer hindex;
    Double newStar;
    Integer pubs;
    Double risingStar;
    Double sociability;
}

class Profile{
    String address;
    String affiliation;
    String affiliation_zh;
    String bio;
    String edu;
    String email;
    String email_cr;
    EmailsU[] emails_u;
    String fax;
    String homepage;
    String note;
    String phone;
    String position;
    String work;
}

class EmailsU{
    String address;
    String src;
    Double weight;
}