package com.example.house_cleaning_app;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.house_cleaning_app.ui.home.HomeFragment;
import com.example.house_cleaning_app.ui.login.LoginFragment;
import com.example.house_cleaning_app.ui.myPosts.MyPostsFragment;
import com.example.house_cleaning_app.ui.profile.ProfileFragment;
import com.example.house_cleaning_app.ui.reg.RegisterFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.house_cleaning_app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_login,R.id.nav_register)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

//        adding the navigation manually
        getSupportFragmentManager().popBackStack();
        FragmentTransaction trans =getSupportFragmentManager().beginTransaction();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuID =item.getItemId();
                getSupportFragmentManager().popBackStack();
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

                if(menuID==R.id.nav_register){
                    RegisterFragment fragment =new RegisterFragment();
                    trans.replace(R.id.nav_host_fragment_content_main,fragment);
                }
                else if(menuID ==R.id.nav_login){
                     LoginFragment fragment =new LoginFragment();
                    trans.replace(R.id.nav_host_fragment_content_main,fragment);
                }
                else if(menuID ==R.id.nav_home){
                    HomeFragment fragment =new HomeFragment();
                    trans.replace(R.id.nav_host_fragment_content_main,fragment);
                }
                else if(menuID ==R.id.nav_profile){
                    ProfileFragment fragment =new ProfileFragment();
                    trans.replace(R.id.nav_host_fragment_content_main,fragment);
                }
                else if(menuID ==R.id.nav_myPosts){
                     MyPostsFragment fragment =new MyPostsFragment();
                    trans.replace(R.id.nav_host_fragment_content_main,fragment);
                }
                else if (menuID==R.id.nav_exit){
                    finish();
                }
                trans.addToBackStack(null);
                trans.commit();
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}