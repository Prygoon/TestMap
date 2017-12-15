package com.example.prygoon.testmap.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.prygoon.testmap.DataManager;
import com.example.prygoon.testmap.R;
import com.example.prygoon.testmap.adapters.PagerAdapter;
import com.example.prygoon.testmap.model.Coordinates;
import com.example.prygoon.testmap.model.CoordinatesDao;
import com.example.prygoon.testmap.model.User;

import org.greenrobot.greendao.DaoException;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private DataManager mDataManager;
    private PagerAdapter mPagerAdapter;
    //private RecyclerView mRecyclerView;
    static private User mUser;
    private Intent intent;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    public static List<Coordinates> coordinates = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        intent = getIntent();
        mDataManager = DataManager.getInstance(this);
        mUser = (User) intent.getSerializableExtra("user");

        try {
            coordinates = mDataManager.getDaoSession()
                    .getCoordinatesDao()
                    .queryBuilder()
                    .where(CoordinatesDao.Properties.UserId.eq(mUser.getId()))
                    .list();
        } catch (DaoException ex) {
            Toast.makeText(this, R.string.empty_coords, Toast.LENGTH_SHORT).show();
        }


        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/


        //tab id
        mTabLayout = findViewById(R.id.tabs);
        //add tabs
        mTabLayout.addTab(mTabLayout.newTab().setText("Map"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Coords"));

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    static public User getmUser() {
        return mUser;
    }

    @Override
    public void onBackPressed() {

    }
}
