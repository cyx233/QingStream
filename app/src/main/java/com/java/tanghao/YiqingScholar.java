package com.java.tanghao;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

class YiqingScholarApi{
    private Integer status;
    private String message;
    private YiqingScholar[] data;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(YiqingScholar[] data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public YiqingScholar[] getData() {
        return data;
    }
}

@Entity(tableName = "scholar")
@TypeConverters({StringConverter.class, DoubleConverter.class, ProfileConverter.class, IndiceConverter.class})
public class YiqingScholar{
    //    Aff aff;
    private String avatar;
    private Boolean bind;
    @PrimaryKey
    @NonNull
    private String id;
    private Indice indices;
    private String name;
    private String name_zh;
    private Integer num_followed;
    private Integer num_viewed;
    private Profile profile;
    private Integer score;
    private String sourcetype;
    private String[] tags;
    private Double[] tags_score;
    private Integer index;
    private Integer tab;
    @ColumnInfo(name="passedaway")
    private Boolean is_passedaway;

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setBind(Boolean bind) {
        this.bind = bind;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIndices(Indice indices) {
        this.indices = indices;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setName_zh(String name_zh) {
        this.name_zh = name_zh;
    }

    public void setNum_followed(Integer num_followed) {
        this.num_followed = num_followed;
    }

    public void setNum_viewed(Integer num_viewed) {
        this.num_viewed = num_viewed;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public void setTags_score(Double[] tags_score) {
        this.tags_score = tags_score;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public void setTab(Integer tab) {
        this.tab = tab;
    }

    public void setIs_passedaway(Boolean is_passedaway) {
        this.is_passedaway = is_passedaway;
    }

    public String getAvatar() {
        return avatar;
    }

    public Boolean getBind() {
        return bind;
    }

    public String getId() {
        return id;
    }

    public Indice getIndices() {
        return indices;
    }

    public String getName() {
        return name;
    }

    public String getName_zh() {
        return name_zh;
    }

    public Integer getNum_followed() {
        return num_followed;
    }

    public Integer getNum_viewed() {
        return num_viewed;
    }

    public Profile getProfile() {
        return profile;
    }

    public Integer getScore() {
        return score;
    }

    public String getSourcetype() {
        return sourcetype;
    }

    public String[] getTags() {
        return tags;
    }

    public Double[] getTags_score() {
        return tags_score;
    }

    public Integer getIndex() {
        return index;
    }

    public Integer getTab() {
        return tab;
    }

    public Boolean getIs_passedaway() {
        return is_passedaway;
    }
}

//class Aff{
//
//}



class EmailsU{
    private String address;
    private String src;
    private Double weight;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getAddress() {
        return address;
    }

    public String getSrc() {
        return src;
    }

    public Double getWeight() {
        return weight;
    }
}