package com.java.tanghao;

import android.content.Context;

import androidx.room.*;
import com.google.gson.Gson;

@Dao
interface NewsDao{
    @Query("SELECT * FROM news")
    News[] getAllNews();

    @Query("SELECT * FROM news WHERE type = '%'||:type||'%'")
    News[] getTypeNews(String type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(News... news);

    @Update
    void update(News... news);

    @Delete
    void delete(News... news);

}

@Database(entities = {News.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static final Object sLock = new Object();
    public abstract NewsDao newsDao();

    public static AppDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE =
                        Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "qing.db")
                                .build();
            }
            return INSTANCE;
        }
    }
}
