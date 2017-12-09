package com.example.prygoon.testmap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.prygoon.testmap.model.Coordinates;
import com.example.prygoon.testmap.model.User;

import java.util.List;

public class DbHelper extends SQLiteOpenHelper implements IDbHelper{

    public DbHelper(Context context) {
        super(context, "DBname", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public void addCoordinates(Coordinates coordinates) {

    }

    @Override
    public List<Coordinates> getUsersCoordinates(String username) {
        return null;
    }
}
