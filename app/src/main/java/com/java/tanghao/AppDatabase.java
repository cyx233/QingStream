package com.java.tanghao;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Dao
interface NewsDao{
    @Query("SELECT * FROM news ORDER BY time")
    News[] getAllNews();

    @Query("SELECT * FROM news WHERE type = :type ORDER BY time")
    News[] getTypeNews(String type);

    @Query("SELECT * FROM news WHERE clusterCategory = :clusterCategory ORDER BY time")
    News[] getClusterNews(String clusterCategory);

    @Query("SELECT * FROM news WHERE title LIKE '%' || :value || '%'")
    News[] getSearchNews(String value);

    @Query("SELECT * FROM news WHERE id = :id")
    News[] getIdNews(String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(News... news);

    @Query("UPDATE news SET isRead = :isRead where id = :id")
    void updateIsRead(Boolean isRead, String id);

    @Query("UPDATE news SET isRead = :isFavorite where id = :id")
    void updateIsFavorite(Boolean isFavorite, String id);

    @Delete
    void delete(News... news);
}

@Dao
interface CategoryDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category... categories);

    @Query("SELECT * FROM category")
    Category[] getAllCategories();

    @Query("UPDATE category SET inCategory = :inCategory WHERE category = :category")
    void updateInCategory(String category, Boolean inCategory);

    @Query("UPDATE category SET inCategory = :inCategory WHERE clusterCategory = :clusterCategory")
    void updateClusterCategory(String clusterCategory, Boolean inCategory);

    @Delete
    void delete(Category... categories);
}

@Dao
interface YiqingDataDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(YiqingData... yiqingData);

    @Query("SELECT * FROM yiqingdata")
    YiqingData[] getAllYiqingData();

    @Query("SELECT * FROM yiqingdata WHERE location = :location")
    YiqingData[] getLocationYiqingData(String location);

}

@Dao
interface YiqingScholarDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(YiqingScholar... yiqingScholars);

    @Query("SELECT * FROM scholar WHERE passedaway = :passedaway")
    YiqingScholar[] getYiqingScholar(Boolean passedaway);

    @Query("SELECT * FROM scholar WHERE id = :id")
    YiqingScholar[] getIdYiqingScholar(String id);

//    @Query("SELECT * FROM yiqingdata WHERE location = :location")
//    YiqingData[] getLocationYiqingData(String location);
}

@Dao
interface HistoryDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(History... histories);

    @Query("SELECT * FROM history")
    History[] getAllHistories();

    @Delete
    void delete(History... histories);
}

@Database(entities = {
        News.class, Category.class, YiqingData.class, YiqingScholar.class, History.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static final Object sLock = new Object();
    public abstract NewsDao newsDao();
    public abstract CategoryDao categoryDao();
    public abstract YiqingDataDao yiqingDataDao();
    public abstract YiqingScholarDao yiqingScholarDao();
    public abstract HistoryDao historyDao();

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
