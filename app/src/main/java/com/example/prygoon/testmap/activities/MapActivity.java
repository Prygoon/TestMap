package com.example.prygoon.testmap.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.prygoon.testmap.MapApplication;
import com.example.prygoon.testmap.adapters.ListAdapter;
import com.example.prygoon.testmap.utils.DataManager;
import com.example.prygoon.testmap.R;
import com.example.prygoon.testmap.adapters.PagerAdapter;
import com.example.prygoon.testmap.activities.fragments.ListFragment;
import com.example.prygoon.testmap.activities.fragments.MapFragment;
import com.example.prygoon.testmap.model.Coordinates;
import com.example.prygoon.testmap.model.CoordinatesDao;
import com.example.prygoon.testmap.model.User;
import com.example.prygoon.testmap.utils.ViewPagerMovement;

import org.greenrobot.greendao.DaoException;

import java.util.List;

public class MapActivity extends AppCompatActivity implements ViewPagerMovement {

    private DataManager mDataManager;
    private User mUser;
    private ViewPager mViewPager;
    private static List<Coordinates> coordinates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        mDataManager = DataManager.getInstance(this);
        mUser = (User) intent.getSerializableExtra("user");

        TextView currentUser = findViewById(R.id.current_user);
        currentUser.setText(R.string.current_user_text);
        currentUser.append(" ");
        currentUser.append(mUser.getName());

        Button logOutButton = findViewById(R.id.logout_button);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUser = null;
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);
            }
        });

        try {
            coordinates = getCoordinatesFromDB();
        } catch (DaoException ex) {
            ex.printStackTrace();
        }


        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        //tab id
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        ListAdapter listAdapter = new ListAdapter();
        MapFragment mapFragment = new MapFragment();
        ListFragment listFragment = new ListFragment();
        listAdapter.setRecyclerListItemDeleter(listFragment);
        listFragment.setPolyLineDrawer(mapFragment);
        listFragment.setViewPagerMovement(this);
        listFragment.setListAdapter(listAdapter);
        mapFragment.setRecycleListRefresher(listFragment);
        adapter.addFragment(mapFragment, "Карта");
        adapter.addFragment(listFragment, "Координаты");
        viewPager.setAdapter(adapter);
    }


    public static List<Coordinates> getCoordinates() {
        return coordinates;
    }

    private List<Coordinates> getCoordinatesFromDB() throws DaoException {
        return mDataManager.getDaoSession()
                .getCoordinatesDao()
                .queryBuilder()
                .where(CoordinatesDao.Properties.UserId.eq(mUser.getId()))
                .list();
    }

    public User getUser() {
        return mUser;
    }


    @Override
    public void onBackPressed() {
        MapApplication.exit(this);
        /*Toast.makeText(this, R.string.back_pressed_once, Toast.LENGTH_SHORT).show();
        if (++backPressedCount > 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.close_app);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finishAffinity();
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    backPressedCount = 0;
                    dialogInterface.cancel();
                }
            });
            builder.create().show();
        }*/
    }

    @Override
    public void moveToMapFragment() {
        mViewPager.setCurrentItem(0);
    }

}
