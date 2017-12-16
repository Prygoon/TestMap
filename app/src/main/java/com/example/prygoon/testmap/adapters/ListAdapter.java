package com.example.prygoon.testmap.adapters;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prygoon.testmap.R;
import com.example.prygoon.testmap.activities.MapActivity;
import com.example.prygoon.testmap.model.Coordinates;


public class ListAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return MapActivity.getCoordinates().size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemText;

        ListViewHolder(View itemView) {
            super(itemView);
            mItemText = itemView.findViewById(R.id.itemText);
        }

        @SuppressLint("DefaultLocale")
        void bindView(int position) {
            Coordinates item = MapActivity.getCoordinates().get(position);
            mItemText.setText(String.format("%s. Широта %.7f. Долгота %.7f", position + 1, item.getLatitude(), item.getLongitude()));
        }

    }
}
