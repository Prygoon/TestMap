package com.example.prygoon.testmap.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.prygoon.testmap.fragments.ListFragment;
import com.example.prygoon.testmap.fragments.MapFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                MapFragment mapFragment = new MapFragment();
                return mapFragment;
            }

            case 1: {
                ListFragment listFragment = new ListFragment();
                return listFragment;
            }

            default: return  null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
