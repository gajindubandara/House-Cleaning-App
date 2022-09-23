package com.example.house_cleaning_app.data;

import com.example.house_cleaning_app.model.Job;
import com.example.house_cleaning_app.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class JobDB implements IJob{
    private DatabaseReference dbr;
    private DatabaseReference myRef;

    public JobDB() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbr =db.getReference(Job.class.getSimpleName());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
    }

    @Override
    public int addJob(Job job) {
        try{
            dbr.push().setValue(job);

            return 1;
        }
        catch(Exception ex){

            return 0;
        }
    }

    @Override
    public int deleteJob(int id) {
        return 0;
    }

    @Override
    public int updateJob(Job user) {
        return 0;
    }

    @Override
    public Job getJob(int id) {
        return null;
    }

    @Override
    public ArrayList<Job> getAll() {
        return null;
    }
}
