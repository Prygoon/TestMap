package com.example.prygoon.testmap;

import android.content.Context;

import com.example.prygoon.testmap.model.DaoMaster;
import com.example.prygoon.testmap.model.DaoSession;

import org.greenrobot.greendao.database.Database;


public class DataManager {

    private static DataManager instance = null;
    private static DaoSession mDaoSession;
    private static Database db;

    private DataManager() {

    }

    public static DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager();
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "testmap_db");
            db = helper.getWritableDb();
            mDaoSession = new DaoMaster(db).newSession();
        }
        return instance;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public Database getDatabase() {
        return db;
    }
}
