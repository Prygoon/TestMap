package com.example.prygoon.testmap.dagger.modules;

import android.content.Context;

import com.example.prygoon.testmap.DbHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseModule {
    private final Context context;

    public BaseModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    DbHelper providesDBHelper() {
        return new DbHelper(context);
    }
}
