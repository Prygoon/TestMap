package com.example.prygoon.testmap.dagger;

import com.example.prygoon.testmap.StartActivity;
import com.example.prygoon.testmap.dagger.modules.BaseModule;

import javax.inject.Singleton;

import dagger.Subcomponent;

@Singleton
@Subcomponent(modules = BaseModule.class)
public interface BaseComponent {
    void inject(StartActivity activity);
}
