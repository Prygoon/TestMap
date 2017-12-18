package com.example.prygoon.testmap.activities.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.prygoon.testmap.model.User;
import com.example.prygoon.testmap.R;
import com.example.prygoon.testmap.activities.MapActivity;
import com.example.prygoon.testmap.model.Coordinates;
import com.example.prygoon.testmap.utils.PolyLineDrawer;
import com.example.prygoon.testmap.utils.RecyclerListItemChangeable;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback, PolyLineDrawer {

    private RecyclerListItemChangeable mRecyclerListItemChangeable;
    private AlertDialog.Builder mBuilder;
    private MapView mMapView;
    private GoogleMap googleMap;
    private LatLng mLatLng;
    private User mUser;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {

        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
        mUser = ((MapActivity) getActivity()).getUser();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(55.751970, 38.008176)).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        mLatLng = new LatLng(latLng.latitude, latLng.longitude);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(mLatLng).title("Нажми для сохранения").snippet(String.format("%.7f  %.7f", latLng.latitude, latLng.longitude));
                        googleMap.addMarker(markerOptions);
                    }
                });

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        if (!(marker.getTitle().equals("Начало") || marker.getTitle().equals("Конец") || marker.getTitle().equals("Точка"))) {
                            mBuilder = new AlertDialog.Builder(getContext());
                            mBuilder.setMessage(R.string.save_coords);
                            mBuilder.setCancelable(false);
                            mBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Coordinates coordinates = new Coordinates(null, mLatLng.latitude, mLatLng.longitude, mUser.getId());
                                    mRecyclerListItemChangeable.addItem(coordinates);
                                    Toast.makeText(getContext(), R.string.coords_saved, Toast.LENGTH_SHORT).show();
                                }
                            });
                            mBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            mBuilder.create().show();
                        }
                    }
                });
            }
        });

        return rootView;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    @Override
    public void drawPolyLine(List<Coordinates> coordinates) {
        final List<LatLng> latLngs = new ArrayList<>();
        for (int i = 0; i < coordinates.size(); i++) {
            latLngs.add(new LatLng(coordinates.get(i).getLatitude(), coordinates.get(i).getLongitude()));
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.clear();
                if (latLngs.size() > 1) {
                    googleMap.addPolyline(new PolylineOptions().addAll(latLngs).width(6f));
                    googleMap.addMarker(new MarkerOptions().position(latLngs.get(0)).title("Начало"));
                    googleMap.addMarker(new MarkerOptions().position(latLngs.get(latLngs.size() - 1)).title("Конец"));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(0), 10));
                } else if (latLngs.size() == 1) {
                    googleMap.addMarker(new MarkerOptions().position(latLngs.get(0)).title("Точка"));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(0), 10));
                    Toast.makeText(getContext(), R.string.only_one_coord, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.empty_coords, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void setRecycleListItemChangeable(RecyclerListItemChangeable mRecyclerListItemChangeable) {
        this.mRecyclerListItemChangeable = mRecyclerListItemChangeable;
    }
}
