package com.example.house_cleaning_app.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.house_cleaning_app.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserDB implements IUser{
    private DatabaseReference dbr;
    private DatabaseReference myRef;
    private DatabaseReference mDatabase;
    DatabaseReference referance;
    FirebaseDatabase rootNode;


    public UserDB() {

    }

    @Override
    public int addUser(User user) {

//         try{
//            rootNode =FirebaseDatabase.getInstance();
//            referance=rootNode.getReference("User");
//
//            referance.child(user.getUserNIC()).setValue(user);
//            return 1;
//        }
//        catch(Exception ex){
////            throw ex;
//            return 0;
//        }
        return 0;
    }

    @Override
    public int deleteUser(String nic) {
        return 0;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    @Override
    public User getUser(String nic) {
//        try{
//
//            User user = null;
//            mDatabase.child("users").child(nic).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DataSnapshot> task) {
//
//                }
//
//                DataSnapshot dataSnapshot;
//                //            User u = dataSnapshot.getValue(User.class);
//                User u = dataSnapshot.child("User").child(nic).getValue(User.class);
//
//                String name = u.getName();
//                String nic1 = u.getUserNIC();
//                String type  = u.getType();
//                String address = u.getAddress();
//                String email =u.getEmail();
//                String num =u.getNumber();
//                String pw =u.getPassword();
//
//
//               User user = new User(nic1,type,name,address,email,num,pw);
//
////               return user;
//
//            });
//            return user;
//        }catch(Exception ex){
//        return null;
//        }

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
