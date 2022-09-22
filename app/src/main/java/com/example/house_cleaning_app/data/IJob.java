package com.example.house_cleaning_app.data;

import com.example.house_cleaning_app.model.Job;

import java.util.ArrayList;

public interface IJob {
    int addJob(Job job);

    int deleteJob(int id);

    int updateJob(Job user);

    Job getJob(int id);

    ArrayList<Job> getAll();

}
