package com.example.house_cleaning_app.data;

import android.content.Context;
import android.widget.Toast;

import com.example.house_cleaning_app.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserDB implements IUser{
    private DatabaseReference dbr;
    private DatabaseReference myRef;

    public UserDB() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbr =db.getReference(User.class.getSimpleName());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");


    }

    @Override
    public int addUser(User user) {

        //exception if need
//        if(user==null){
//
//        }

        try{
            dbr.push().setValue(user);
//                    myRef.setValue("Hello, World!");
//            System.out.println(user.getUserID());
//            System.out.println(user.getType());
//            System.out.println(user.getName());
//            System.out.println(user.getAddress());
//            System.out.println(user.getLocation());
//            System.out.println(user.getEmail());
//            System.out.println(user.getNumber());
//            System.out.println(user.getPassword());
            return 1;
        }
        catch(Exception ex){
            return 0;
        }
    }

    @Override
    public int deleteUser(int id) {
        return 0;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public ArrayList<User> getAll() {
        return null;
    }

    @Override
    public int updateUserPw(User ob) {
        return 0;
    }
}
