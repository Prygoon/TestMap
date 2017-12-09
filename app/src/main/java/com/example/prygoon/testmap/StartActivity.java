package com.example.prygoon.testmap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import javax.inject.Inject;


public class StartActivity extends AppCompatActivity {
    @Inject
    DbHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        new MyApplication().getBaseComponent().inject(this);

    }
}
