package com.java.tanghao;

import android.content.Context;

import androidx.room.*;
import com.google.gson.Gson;

@Dao
interface NewsDao{
    @Query("SELECT * FROM news")
    News[] getAllNews();

    @Query("SELECT * FROM news WHERE type = :type")
    News[] getTypeNews(String type);

    @Query("SELECT * FROM news WHERE title like :value")
    News[] getSearchNews(String value);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(News... news);

    @Query("UPDATE news SET isRead = :isRead")
    void updateIsRead(Boolean isRead);

    @Query("UPDATE news SET isRead = :isFavorite")
    void updateIsFavorite(Boolean isFavorite);

    @Delete
    void delete(News... news);
}

@Dao
interface CategoryDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category... categories);

    @Query("SELECT * FROM category")
    Category[] getAllCategories();

    @Query("UPDATE category SET inCategory = :inCategory")
    void updateInCategory(Boolean inCategory);

}

@Database(entities = {News.class, Category.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static final Object sLock = new Object();
    public abstract NewsDao newsDao();
    public abstract CategoryDao categoryDao();

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
