package com.java.tanghao;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity(tableName = "history")
public class History{
    @PrimaryKey
    @NonNull
    private String content;

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

