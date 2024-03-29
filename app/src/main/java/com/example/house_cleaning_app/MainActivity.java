package com.example.house_cleaning_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.house_cleaning_app.databinding.ActivityMainBinding;
import com.example.house_cleaning_app.ui.adminViewUsers.AdminViewUsersFragment;
import com.example.house_cleaning_app.ui.home.HomeFragment;
import com.example.house_cleaning_app.ui.jobs.AllPostsFragment;
import com.example.house_cleaning_app.ui.login.LoginFragment;
import com.example.house_cleaning_app.ui.managePost.AddpostFragment;
import com.example.house_cleaning_app.ui.myJobs.MyJobsFragment;
import com.example.house_cleaning_app.ui.post.MyPostsFragment;
import com.example.house_cleaning_app.ui.pricing.FloorPriceFragment;
import com.example.house_cleaning_app.ui.profile.ProfileFragment;
import com.example.house_cleaning_app.ui.register.RegisterFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    boolean status= false;
    boolean register= false;
    String userType;
    String NIC;


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
                R.id.nav_home,R.id.nav_login,R.id.nav_register,R.id.nav_profile)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);

       //adding the navigation manually
        getSupportFragmentManager().popBackStack();
        FragmentTransaction trans =getSupportFragmentManager().beginTransaction();

        //shared preference part
        SharedPreference preference= new SharedPreference();
        register =  preference.GetBoolean(getApplicationContext(),SharedPreference.REGISTER);
        status = preference.GetBoolean(getApplicationContext(),SharedPreference.LOGIN_STATUS);
        userType =preference.GetString(getApplicationContext(),SharedPreference.USER_TYPE);
        NIC=preference.GetString(getApplicationContext(),SharedPreference.USER_NIC);


        Temp.setNIC(NIC);


        Menu menu1 = navigationView.getMenu();
        MenuItem item1=menu1.findItem(R.id.nav_allPosts);
        item1.setVisible(false);
        item1=menu1.findItem(R.id.nav_home);
        item1.setVisible(false);
        item1=menu1.findItem(R.id.nav_add);
        item1.setVisible(false);
        item1=menu1.findItem(R.id.nav_myPosts);
        item1.setVisible(false);
        item1=menu1.findItem(R.id.nav_myJobs);
        item1.setVisible(false);
        item1=menu1.findItem(R.id.nav_logout);
        item1.setVisible(false);
        item1=menu1.findItem(R.id.nav_register);
        item1.setVisible(false);
        item1=menu1.findItem(R.id.nav_floorPrice);
        item1.setVisible(false);
        item1=menu1.findItem(R.id.nav_profile);
        item1.setVisible(false);
        item1=menu1.findItem(R.id.nav_adminViewUsers);
        item1.setVisible(false);


        //check register
        if (register){
            Menu menu = navigationView.getMenu();
            MenuItem item;

            //check login
            if(status) {
                item=menu.findItem(R.id.nav_profile);
                item.setVisible(true);
                item1=menu1.findItem(R.id.nav_logout);
                item1.setVisible(true);
                item1=menu1.findItem(R.id.nav_home);
                item1.setVisible(true);

                //check user type
                if (userType.equals("1")){
                    item=menu.findItem(R.id.nav_add);
                    item.setVisible(true);
                    item=menu.findItem(R.id.nav_myPosts);
                    item.setVisible(true);

                }
                else if (userType.equals("2")){

                    item=menu.findItem(R.id.nav_allPosts);
                    item.setVisible(true);
                    item=menu.findItem(R.id.nav_myJobs);
                    item.setVisible(true);

                }
                else if(userType.equals("3")){
                    item=menu.findItem(R.id.nav_floorPrice);
                    item.setVisible(true);
                    item=menu.findItem(R.id.nav_allPosts);
                    item.setVisible(true);
                    item=menu.findItem(R.id.nav_adminViewUsers);
                    item.setVisible(true);

                }

                item=menu.findItem(R.id.nav_login);
                item.setVisible(false);


                //moving to frag
                HomeFragment fragment = new HomeFragment();
                trans.replace(R.id.nav_host_fragment_content_main, fragment);
                trans.addToBackStack(null);
                trans.commit();
            }
            else {
                //moving to frag
                LoginFragment fragment = new LoginFragment();
                trans.replace(R.id.nav_host_fragment_content_main, fragment);
                trans.addToBackStack(null);
                trans.commit();
            }
        }
        else {
            Menu menu = navigationView.getMenu();
            MenuItem item=menu.findItem(R.id.nav_register);
            item.setVisible(true);

            //moving to frag
            RegisterFragment fragment = new RegisterFragment();
            trans.replace(R.id.nav_host_fragment_content_main,fragment);
            trans.addToBackStack(null);
            trans.commit();

        }



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
                else if(menuID ==R.id.nav_allPosts){
                     AllPostsFragment fragment =new AllPostsFragment();
                    trans.replace(R.id.nav_host_fragment_content_main,fragment);
                }
                else if(menuID ==R.id.nav_add){
                    AddpostFragment fragment =new AddpostFragment();
                    trans.replace(R.id.nav_host_fragment_content_main,fragment);
                }
                else if(menuID ==R.id.nav_myPosts){
                    MyPostsFragment fragment =new MyPostsFragment();
                    trans.replace(R.id.nav_host_fragment_content_main,fragment);
                }
                else if(menuID ==R.id.nav_myJobs){
                    MyJobsFragment fragment =new MyJobsFragment();
                    trans.replace(R.id.nav_host_fragment_content_main,fragment);
                }
                else if(menuID ==R.id.nav_floorPrice){
                    FloorPriceFragment fragment =new FloorPriceFragment();
                    trans.replace(R.id.nav_host_fragment_content_main,fragment);
                }
                else if(menuID ==R.id.nav_adminViewUsers){
                    AdminViewUsersFragment fragment =new AdminViewUsersFragment();
                    trans.replace(R.id.nav_host_fragment_content_main,fragment);
                }

                else if (menuID==R.id.nav_exit){
                    finish();

                }
                else if (menuID==R.id.nav_logout){
                    LoginFragment fragment = new LoginFragment();
                    trans.replace(R.id.nav_host_fragment_content_main,fragment);
                    trans.addToBackStack(null);

                    preference.SaveBool(getApplicationContext(),false,SharedPreference.LOGIN_STATUS);
                    preference.SaveString(getApplicationContext(),null,SharedPreference.USER_TYPE);
                    preference.SaveString(getApplicationContext(),null,SharedPreference.USER_NIC);

                    Menu menu1 = navigationView.getMenu();
                    MenuItem item1=menu1.findItem(R.id.nav_allPosts);
                    item1.setVisible(false);
                    item1=menu1.findItem(R.id.nav_home);
                    item1.setVisible(false);
                    item1=menu1.findItem(R.id.nav_add);
                    item1.setVisible(false);
                    item1=menu1.findItem(R.id.nav_myPosts);
                    item1.setVisible(false);
                    item1=menu1.findItem(R.id.nav_myJobs);
                    item1.setVisible(false);
                    item1=menu1.findItem(R.id.nav_logout);
                    item1.setVisible(false);
                    item1=menu1.findItem(R.id.nav_register);
                    item1.setVisible(false);
                    item1=menu1.findItem(R.id.nav_floorPrice);
                    item1.setVisible(false);
                    item1=menu1.findItem(R.id.nav_profile);
                    item1.setVisible(false);
                    item1=menu1.findItem(R.id.nav_adminViewUsers);
                    item1.setVisible(false);

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

    public void  goToUrl(String url){
        try{
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
        }
        catch(Exception ex){throw ex;}
    }

}