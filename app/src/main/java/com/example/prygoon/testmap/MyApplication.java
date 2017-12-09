package com.example.prygoon.testmap;

import android.app.Application;

import com.example.prygoon.testmap.dagger.AppComponent;
import com.example.prygoon.testmap.dagger.BaseComponent;
import com.example.prygoon.testmap.dagger.DaggerAppComponent;
import com.example.prygoon.testmap.dagger.modules.BaseModule;

public class MyApplication extends Application{

    final AppComponent component = DaggerAppComponent.builder().build();

    @Override
    public void onCreate() {
        super.onCreate();
        component.inject(this);
    }

    public BaseComponent getBaseComponent() {
        return component.plus(new BaseModule(this));
    }
}
