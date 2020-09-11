package com.java.tanghao;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity(tableName = "history")
public class History{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String content;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public History(String content){
        this.content = content;
    }
}

