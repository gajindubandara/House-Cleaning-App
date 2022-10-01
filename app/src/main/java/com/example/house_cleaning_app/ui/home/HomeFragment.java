package com.example.house_cleaning_app.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.house_cleaning_app.MainActivity;
import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.SharedPreference;
import com.example.house_cleaning_app.Temp;
import com.example.house_cleaning_app.databinding.FragmentHomeBinding;
import com.example.house_cleaning_app.ui.jobs.AllPostsFragment;
import com.example.house_cleaning_app.ui.login.LoginFragment;
import com.example.house_cleaning_app.ui.managePost.AddpostFragment;
import com.example.house_cleaning_app.ui.myJobs.MyJobsFragment;
import com.example.house_cleaning_app.ui.post.MyPostsFragment;
import com.example.house_cleaning_app.ui.pricing.FloorPriceFragment;
import com.example.house_cleaning_app.ui.profile.ProfileFragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    CardView newPost, allPosts,myPosts,myJobs,pricing,logout,myProfile,exit,users;
    TextView name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        String  userNIC =  Temp.getNIC();




        newPost =root.findViewById(R.id.homeNewPost);
        allPosts =root.findViewById(R.id.homeAllPosts);
        myPosts =root.findViewById(R.id.homeMyPosts);
        myJobs =root.findViewById(R.id.homeMyJobs);
        pricing =root.findViewById(R.id.homePricing);
        logout =root.findViewById(R.id.Logout);
        myProfile =root.findViewById(R.id.homeMyProfile);
        users =root.findViewById(R.id.homeusers);
        exit =root.findViewById(R.id.Exit);
        name=root.findViewById(R.id.txtUserName);




        SharedPreference preference= new SharedPreference();
        String  type=preference.GetString(getActivity().getApplicationContext(), SharedPreference.USER_TYPE);
        boolean status = preference.GetBoolean(getActivity().getApplicationContext(),SharedPreference.LOGIN_STATUS);

        allPosts.setVisibility(root.GONE);
        myJobs.setVisibility(root.GONE);
        pricing.setVisibility(root.GONE);
        newPost.setVisibility(root.GONE);
        myPosts.setVisibility(root.GONE);
        users.setVisibility(root.GONE);

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
//        Query checkUser = reference.orderByChild("userNIC").equalTo(userNIC);
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                   String nameFromDB =snapshot.child(Temp.getNIC()).child("name").getValue(String.class);
//                   Temp.setUserName(nameFromDB);
//
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        name.setText("hello");

        if(status){
            if (type.equals("1")){
                newPost.setVisibility(root.VISIBLE);
                myPosts.setVisibility(root.VISIBLE);
            }
            else if(type.equals("2")){
                allPosts.setVisibility(root.VISIBLE);
                myJobs.setVisibility(root.VISIBLE);
            }
            else{
                allPosts.setVisibility(root.VISIBLE);
                pricing.setVisibility(root.VISIBLE);
            }
        }
        else{
            FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
            LoginFragment fragment = new LoginFragment();
            trans.replace(R.id.nav_host_fragment_content_main, fragment);
            trans.addToBackStack(null);
            trans.commit();
        }




        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
                AddpostFragment fragment = new AddpostFragment();
                trans.replace(R.id.nav_host_fragment_content_main, fragment);
                trans.addToBackStack(null);
                trans.commit();
            }
        });



        allPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PreLoader preloader = new PreLoader(getActivity());
//                preloader.startLoadingDialog();
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        preloader.dismissDialog();
//                    }
//                },3000);
                FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
                AllPostsFragment fragment = new AllPostsFragment();
                trans.replace(R.id.nav_host_fragment_content_main, fragment);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        myPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
                MyPostsFragment fragment = new MyPostsFragment();
                trans.replace(R.id.nav_host_fragment_content_main, fragment);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        myJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
                MyJobsFragment fragment = new MyJobsFragment();
                trans.replace(R.id.nav_host_fragment_content_main, fragment);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        pricing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
                FloorPriceFragment fragment = new FloorPriceFragment();
                trans.replace(R.id.nav_host_fragment_content_main, fragment);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
                LoginFragment fragment = new LoginFragment();
                trans.replace(R.id.nav_host_fragment_content_main,fragment);
                trans.addToBackStack(null);
                trans.commit();
                preference.SaveBool(getContext(),false,SharedPreference.LOGIN_STATUS);
                preference.SaveString(getContext(),null,SharedPreference.USER_TYPE);
                preference.SaveString(getContext(),null,SharedPreference.USER_NIC);

            }
        });

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
                ProfileFragment fragment = new ProfileFragment();
                trans.replace(R.id.nav_host_fragment_content_main, fragment);
                trans.addToBackStack(null);
                trans.commit();
            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).finish();

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}