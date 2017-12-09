package com.example.prygoon.testmap.dagger;


import com.example.prygoon.testmap.MyApplication;
import com.example.prygoon.testmap.dagger.modules.AppModule;
import com.example.prygoon.testmap.dagger.modules.BaseModule;

import dagger.Component;

@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(MyApplication app);

    BaseComponent plus(BaseModule baseModule);
}
