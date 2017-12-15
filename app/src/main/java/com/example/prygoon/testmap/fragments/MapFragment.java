package com.example.prygoon.testmap.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.prygoon.testmap.DataManager;
import com.example.prygoon.testmap.R;
import com.example.prygoon.testmap.activities.MapActivity;
import com.example.prygoon.testmap.model.Coordinates;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.map.MapEvent;
import ru.yandex.yandexmapkit.map.OnMapListener;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.utils.GeoPoint;
import ru.yandex.yandexmapkit.utils.ScreenPoint;


public class MapFragment extends Fragment implements OnMapListener {

    MapController mMapController;
    LinearLayout mView;
    AlertDialog.Builder mBuilder;
    DataManager mDataManager;
    Overlay mOverlay;
    OverlayItem mOverlayItem;
    Drawable drawable;
    GeoPoint mGeoPoint;
    MapView mMapView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mView = (LinearLayout) inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = mView.findViewById(R.id.map);
        mMapView.showBuiltInScreenButtons(true);
        mMapController = mMapView.getMapController();
        mMapController.addMapListener(this);
        mDataManager = DataManager.getInstance(this.getContext());
        mOverlay = new Overlay(mMapController);
        drawable = getActivity().getResources().getDrawable(R.drawable.ymk_user_location_lbs);

        return mView;
    }

    @Override
    public void onMapActionEvent(final MapEvent mapEvent) {
        boolean chkLongPress = false;

        switch (mapEvent.getMsg()) {
            case MapEvent.MSG_LONG_PRESS: {
                float x = mapEvent.getX();
                float y = mapEvent.getY();
                final double lat = mMapController.getGeoPoint(new ScreenPoint(x, y)).getLat();
                final double lon = mMapController.getGeoPoint(new ScreenPoint(x, y)).getLon();

                mGeoPoint = new GeoPoint(lat, lon);
                mOverlayItem = new OverlayItem(mGeoPoint, drawable);
                mBuilder = new AlertDialog.Builder(this.getContext());
                mOverlay.addOverlayItem(mOverlayItem);
                mMapController.getOverlayManager().addOverlay(mOverlay);
                mBuilder.setMessage(R.string.save_coords);
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Coordinates coordinates = new Coordinates(null, lat, lon, MapActivity.getmUser().getId());
                        mDataManager.getDaoSession().getCoordinatesDao().insert(coordinates);
                        Toast.makeText(getContext(), "Координаты сохранены", Toast.LENGTH_SHORT).show();
                    }
                });
                mBuilder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                chkLongPress = true;
                break;
            }
            default:
                break;
        }


        final boolean finalChkLongPress = chkLongPress;
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (finalChkLongPress) {
                    mOverlayItem = new OverlayItem(mGeoPoint, drawable);
                    mOverlay.clearOverlayItems();
                    mOverlay.addOverlayItem(mOverlayItem);
                    mBuilder.create().show();
                }
            }
        });
    }
}