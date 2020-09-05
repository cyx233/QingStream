package com.java.tanghao;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity(tableName = "category")
public class Category {
    @PrimaryKey
    @NonNull
    private String category;
    private Boolean inCategory;

    public String getCategory(){return category;}
    public boolean getInCategory(){return inCategory;}

    public void setCategory(String category){this.category = category;}
    public void setInCategory(Boolean inCategory){this.inCategory = inCategory;}
}
