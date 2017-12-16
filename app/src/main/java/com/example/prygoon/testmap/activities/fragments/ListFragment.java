package com.example.prygoon.testmap.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.prygoon.testmap.R;
import com.example.prygoon.testmap.activities.MapActivity;
import com.example.prygoon.testmap.adapters.ListAdapter;
import com.example.prygoon.testmap.model.Coordinates;
import com.example.prygoon.testmap.utils.DataManager;
import com.example.prygoon.testmap.utils.PolyLineDrawer;
import com.example.prygoon.testmap.utils.RecycleListItemAdder;
import com.example.prygoon.testmap.utils.ViewPagerMovement;

import java.util.List;

public class ListFragment extends Fragment implements View.OnClickListener, RecycleListItemAdder {

    private PolyLineDrawer mPolyLineDrawer;
    private ViewPagerMovement mViewPagerMovement;
    private ListAdapter mListAdapter;
    private RecyclerView mRecyclerView;
    private DataManager mDataManager;
    private List<Coordinates> mCoordinatesList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        mRecyclerView = rootView.findViewById(R.id.list);
        mRecyclerView.setAdapter(mListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mCoordinatesList = MapActivity.getCoordinates();
        mDataManager = DataManager.getInstance(getContext());

        Button button = rootView.findViewById(R.id.draw_path_btn);
        button.setOnClickListener(this);


        return rootView;
    }

    public void addItem(Coordinates coords) {

        mCoordinatesList.add(coords);
        mDataManager.getDaoSession().getCoordinatesDao().insert(coords);

        if (mRecyclerView != null) {
            mRecyclerView.getAdapter().notifyItemInserted(mCoordinatesList.size() - 1);
        }

    }

    @Override
    public void onClick(View view) {
        if (mPolyLineDrawer != null) {
            mPolyLineDrawer.drawPolyLine(mCoordinatesList);
        }
        if (mViewPagerMovement != null) {
            mViewPagerMovement.moveToMapFragment();
        }
    }

    public void setPolyLineDrawer(PolyLineDrawer polyLineDrawer) {
        this.mPolyLineDrawer = polyLineDrawer;
    }

    public void setViewPagerMovement(ViewPagerMovement mViewPagerMovement) {
        this.mViewPagerMovement = mViewPagerMovement;
    }

    public void setListAdapter(ListAdapter mListAdapter) {
        this.mListAdapter = mListAdapter;
    }

}
