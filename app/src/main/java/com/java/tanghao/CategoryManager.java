package com.java.tanghao;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Arrays;


public class CategoryManager {
    private static CategoryManager Instance = null;
    private final CategoryDao categoryDao;
    private final AppDatabase appDB;

    private CategoryManager(AppDatabase appDB) {
        this.appDB = appDB;
        this.categoryDao = this.appDB.categoryDao();
    }

    public static CategoryManager getInstance(AppDatabase appDB) {
        if (Instance == null) {
            Instance = new CategoryManager(appDB);
        }
        return Instance;
    }

    public void insertCategory(Category... categories){
        try {
            CategoryManager.InsertCategoryTask insertCategoryTask = new CategoryManager.InsertCategoryTask();
            insertCategoryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,categories);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private class InsertCategoryTask extends AsyncTask<Category, Void, Void>{
        @Override
        protected Void doInBackground(Category... categories){
            categoryDao.insert(categories);
            return null;
        }
    }

    public ArrayList<Category> getAllCategories(){
        try {
            CategoryManager.GetAllCategoriesTask getAllCategoriesTask = new CategoryManager.GetAllCategoriesTask();
            return new ArrayList<Category>(Arrays.asList(getAllCategoriesTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"0").get()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetAllCategoriesTask extends AsyncTask<String, Void, Category[]>{
        @Override
        protected Category[] doInBackground(String... s){
            return categoryDao.getAllCategories();
        }
    }

    public void updateInCategory(Boolean inCategory){
        try {
            CategoryManager.UpdateInCategoryTask updateInCategoryTask = new CategoryManager.UpdateInCategoryTask();
            updateInCategoryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,inCategory);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private class UpdateInCategoryTask extends AsyncTask<Boolean, Void, Void>{
        @Override
        protected Void doInBackground(Boolean... params){
            categoryDao.updateInCategory(params[0]);
            return null;
        }
    }
}