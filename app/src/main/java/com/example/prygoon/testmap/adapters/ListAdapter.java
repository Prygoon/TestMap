package com.example.prygoon.testmap.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prygoon.testmap.MapActivity;
import com.example.prygoon.testmap.R;

public class ListAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return MapActivity.items.length;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mItemText;

        public ListViewHolder(View itemView) {
            super(itemView);
            mItemText = itemView.findViewById(R.id.itemText);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position) {
            mItemText.setText(MapActivity.items[position]);
        }

        @Override
        public void onClick(View view) {

        }
    }
}