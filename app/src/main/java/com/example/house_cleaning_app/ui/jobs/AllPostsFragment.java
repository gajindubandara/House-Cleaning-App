package com.example.house_cleaning_app.ui.jobs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.model.Job;
import com.example.house_cleaning_app.Temp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllPostsFragment extends Fragment {
    FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    private AllPostsViewModel mViewModel;
    String  userNIC = "";
    String key;


    public static AllPostsFragment newInstance() {
        return new AllPostsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_posts, container, false);
        userNIC = Temp.getNIC();


        //set recycle view
        RecyclerView recyclerView = v.findViewById(R.id.rcvJobs);
        List<Job> jobList = new ArrayList<>();
//        Job job2 =new Job("2","2","2","2","2","2","2","2","2","2","2");
//        jobList.add(job2);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Job");
//        Query job  = rootRef.orderByChild("user").equalTo(userNIC);
//        job.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//
//                    String date =snapshot.child(userNIC).child("date").getValue(String.class);
//                    String status =snapshot.child(userNIC).child("status").getValue(String.class);
//                    String price =snapshot.child(userNIC).child("price").getValue(String.class);
//                    Job job = new Job("1",date,"1","1","1","1",price,"1",status,"1","1");
//                    jobList.add(job);

//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
            rootRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot postSnapshot: snapshot.getChildren()){
                        key = snapshot.getKey();
                        Job job = postSnapshot.getValue(Job.class);
                        jobList.add(job);

                    }

                    PostAdapter adapter= new PostAdapter(jobList,fdb);
                    recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                    recyclerView.setAdapter(adapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AllPostsViewModel.class);
        // TODO: Use the ViewModel
    }

}