package com.example.house_cleaning_app.ui.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.house_cleaning_app.PreLoader;
import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.Temp;
import com.example.house_cleaning_app.model.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyPostsFragment extends Fragment {
    FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    private MyPostsViewModel mViewModel;
    String  userNIC = "";
    TextView noPosts;

    public static MyPostsFragment newInstance() {
        return new MyPostsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_posts, container, false);
        userNIC = Temp.getNIC();

        noPosts=v.findViewById(R.id.txtNoPosts);
        noPosts.setVisibility(v.GONE);

        final PreLoader preloader = new PreLoader(getActivity());
        preloader.startLoadingDialog();

        //set recycle view
        RecyclerView recyclerView = v.findViewById(R.id.rcvMyJobs);
        List<Job> jobList = new ArrayList<>();


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Job");
        Query getPosts = rootRef.orderByChild("user").equalTo(userNIC);

        getPosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                preloader.dismissDialog();
                if (snapshot.exists()) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Job job = postSnapshot.getValue(Job.class);
                        if (job.getStatus().equals("1")) {
                            jobList.add(job);
                        }
                    }
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Job job = postSnapshot.getValue(Job.class);
                        if (job.getStatus().equals("2")) {
                            jobList.add(job);
                        }
                    }
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Job job = postSnapshot.getValue(Job.class);
                        if (job.getStatus().equals("3")) {
                            jobList.add(job);
                        }
                    }
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Job job = postSnapshot.getValue(Job.class);
                        if (job.getStatus().equals("4")) {
                            jobList.add(job);
                        }
                    }

                    MyPostAdapter adapter = new MyPostAdapter(jobList, fdb);
                    recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                    recyclerView.setAdapter(adapter);
                }
                else{
                    noPosts.setVisibility(v.VISIBLE);
                }
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
        mViewModel = new ViewModelProvider(this).get(MyPostsViewModel.class);
        // TODO: Use the ViewModel
    }

}