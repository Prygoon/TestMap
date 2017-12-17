package com.example.prygoon.testmap.utils;


import com.example.prygoon.testmap.model.Coordinates;

public interface RecyclerListItemChangeable {
    void addItem(Coordinates coords);

    void deleteItem(int position);

}
