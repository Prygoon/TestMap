package com.example.prygoon.testmap;

import com.example.prygoon.testmap.model.DaoSession;

public class DataManager {
    private static DataManager INSTANCE = null;
    private DaoSession mDaoSession;

    private DataManager() {
        this.mDaoSession = MapApplication.getsDaoSession();
    }

    public static DataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }
}
