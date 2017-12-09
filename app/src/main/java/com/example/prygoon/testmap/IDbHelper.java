package com.example.prygoon.testmap;

import com.example.prygoon.testmap.model.Coordinates;
import com.example.prygoon.testmap.model.User;

import java.util.List;

public interface IDbHelper {
    void addUser(User user);

    User getUser(String username);

    void addCoordinates(Coordinates coordinates);

    List<Coordinates> getUsersCoordinates (String username);
}
