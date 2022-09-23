package com.example.house_cleaning_app.data;

import com.example.house_cleaning_app.model.User;

import java.util.ArrayList;

public interface IUser {
    int addUser(User user);

    int deleteUser(String nic);

    int updateUser(User user);

    User getUser(String nic);

    ArrayList<User> getAll();

    int updateUserPw(User ob);
}


