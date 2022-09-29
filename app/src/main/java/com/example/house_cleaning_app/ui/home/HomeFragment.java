package com.example.house_cleaning_app.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.house_cleaning_app.PreLoader;
import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.databinding.FragmentHomeBinding;
import com.example.house_cleaning_app.ui.managePost.AddpostFragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    Button homeNewPost,btnLoad;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeNewPost=root.findViewById(R.id.homeNewPost);
        btnLoad=root.findViewById(R.id.btnLoad);



        homeNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
                AddpostFragment fragment = new AddpostFragment();
                trans.replace(R.id.nav_host_fragment_content_main, fragment);
                trans.addToBackStack(null);
                trans.commit();
            }
        });


        //preloader
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreLoader preloader = new PreLoader(getActivity());
                preloader.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        preloader.dismissDialog();
                    }
                },3000);
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