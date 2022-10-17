package com.example.house_cleaning_app.ui.jobs;

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
import com.example.house_cleaning_app.SharedPreference;
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

public class AllPostsFragment extends Fragment {
    FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    private AllPostsViewModel mViewModel;
    String  userNIC = "";
    String key;

    TextView noData;

    public static AllPostsFragment newInstance() {
        return new AllPostsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_posts, container, false);
        userNIC = Temp.getNIC();

        noData=v.findViewById(R.id.txtNoJobList);
        noData.setVisibility(v.GONE);

        final PreLoader preloader = new PreLoader(getActivity());
        preloader.startLoadingDialog();

        //set recycle view
        RecyclerView recyclerView = v.findViewById(R.id.rcvMyJobs);
        List<Job> jobList = new ArrayList<>();
        SharedPreference preference=new SharedPreference();
        String  type=preference.GetString(getActivity().getApplicationContext(), SharedPreference.USER_TYPE);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Job");

        if (type.equals("2")) {
            Query getOpenPosts = rootRef.orderByChild("status").equalTo("1");
            Query getRequestPosts = rootRef.orderByChild("status").equalTo("2");

            getOpenPosts.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    preloader.dismissDialog();
                    if (snapshot.exists()) {

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            key = snapshot.getKey();
                            Job job = postSnapshot.getValue(Job.class);
                            jobList.add(job);

                        }

                        PostAdapter adapter = new PostAdapter(jobList, fdb);
                        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                        recyclerView.setAdapter(adapter);

                    } else {
                        noData.setVisibility(v.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            getRequestPosts.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            key = snapshot.getKey();
                            Job job = postSnapshot.getValue(Job.class);
                            jobList.add(job);

                        }

                        PostAdapter adapter = new PostAdapter(jobList, fdb);
                        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                        recyclerView.setAdapter(adapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }


        if (type.equals("3")){
            Query getOpenPosts = rootRef.orderByChild("status").equalTo("1");
            Query getRequestPosts = rootRef.orderByChild("status").equalTo("2");
            Query getAssignedPosts = rootRef.orderByChild("status").equalTo("3");
            Query getFinishedPosts = rootRef.orderByChild("status").equalTo("4");

            getOpenPosts.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    preloader.dismissDialog();
                    if (snapshot.exists()) {

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            key = snapshot.getKey();
                            Job job = postSnapshot.getValue(Job.class);
                            jobList.add(job);

                        }

                        PostAdapter adapter = new PostAdapter(jobList, fdb);
                        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                        recyclerView.setAdapter(adapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            getRequestPosts.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            key = snapshot.getKey();
                            Job job = postSnapshot.getValue(Job.class);
                            jobList.add(job);

                        }

                        PostAdapter adapter = new PostAdapter(jobList, fdb);
                        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                        recyclerView.setAdapter(adapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            getAssignedPosts.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            key = snapshot.getKey();
                            Job job = postSnapshot.getValue(Job.class);
                            jobList.add(job);
                        }
                        PostAdapter adapter = new PostAdapter(jobList, fdb);
                        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                        recyclerView.setAdapter(adapter);

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            getFinishedPosts.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            key = snapshot.getKey();
                            Job job = postSnapshot.getValue(Job.class);
                            jobList.add(job);
                        }
                        PostAdapter adapter = new PostAdapter(jobList, fdb);
                        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                        recyclerView.setAdapter(adapter);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AllPostsViewModel.class);
        // TODO: Use the ViewModel
    }

}