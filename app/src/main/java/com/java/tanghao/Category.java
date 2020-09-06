package com.java.tanghao;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity(tableName = "category")
public class Category extends Thread{
    @PrimaryKey
    @NonNull
    private String category;
    @NonNull
    private Boolean inCategory;

    public String getCategory(){return category;}
    public Boolean getInCategory(){return inCategory;}

    public void setCategory(String category){this.category = category;}
    public void setInCategory(Boolean inCategory){this.inCategory = inCategory;}

    public Category(String category, Boolean inCategory){
        this.category = category;
        this.inCategory = inCategory;
    }
}
