package com.example.prygoon.testmap;

import android.app.Application;

import com.example.prygoon.testmap.model.DaoMaster;
import com.example.prygoon.testmap.model.DaoSession;

import org.greenrobot.greendao.database.Database;

public class MapApplication extends Application{

    private static DaoSession sDaoSession;

    public static DaoSession getsDaoSession() {
        return sDaoSession;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "testmap-db");
        Database db = helper.getWritableDb();
        sDaoSession = new DaoMaster(db).newSession();

    }

}
