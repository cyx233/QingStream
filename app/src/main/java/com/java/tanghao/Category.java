package com.java.tanghao;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity(tableName = "category")
public class Category{
    @PrimaryKey
    @NonNull
    private String category;
    @NonNull
    private Boolean inCategory;

    private String clusterCategory;

    public String getCategory(){return category;}
    public Boolean getInCategory(){return inCategory;}

    public String getClusterCategory() {
        return clusterCategory;
    }

    public void setCategory(String category){this.category = category;}
    public void setInCategory(Boolean inCategory){this.inCategory = inCategory;}

    public void setClusterCategory(String clusterCategory) {
        this.clusterCategory = clusterCategory;
    }

    public Category(String category, Boolean inCategory){
        this.category = category;
        this.inCategory = inCategory;
    }
}
