package com.example.house_cleaning_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
import com.example.house_cleaning_app.ui.home.HomeFragment;
import com.example.house_cleaning_app.ui.login.LoginCheck;
import com.example.house_cleaning_app.ui.login.LoginFragment;
import com.example.house_cleaning_app.ui.myPosts.AllPostsFragment;
import com.example.house_cleaning_app.ui.newPost.AddpostFragment;
import com.example.house_cleaning_app.ui.profile.ProfileFragment;
import com.example.house_cleaning_app.ui.register.RegisterFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    NavigationView navigationView;
    boolean status= false;
    boolean register= false;

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
                R.id.nav_home,R.id.nav_login,R.id.nav_register,R.id.nav_allPosts,R.id.nav_profile)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

//        adding the navigation manually
        getSupportFragmentManager().popBackStack();
        FragmentTransaction trans =getSupportFragmentManager().beginTransaction();

        //shared preference part
        SharedPreference preference= new SharedPreference();
        register =  preference.GetBoolean(getApplicationContext(),SharedPreference.REGISTER);
        status = preference.GetBoolean(getApplicationContext(),SharedPreference.LOGIN_STATUS);


        //check register
        if (register){
            Menu menu = navigationView.getMenu();
            MenuItem item=menu.findItem(R.id.nav_register);
            item.setVisible(false);
            //check login
            if(status) {

//                    EnableOnLogin(false);
//                EnableOnLogin();
//                Menu menu = navigationView.getMenu();
//                MenuItem item=menu.findItem(R.id.nav_login);
//                item.setVisible(false);
                item=menu.findItem(R.id.nav_login);
                item.setVisible(false);

                String nic =preference.GetString(getApplicationContext(),SharedPreference.USER_NIC);
                LoginCheck.setNIC(nic);
                ProfileFragment fragment = new ProfileFragment();
                trans.replace(R.id.nav_host_fragment_content_main, fragment);
                trans.addToBackStack(null);
                trans.commit();
            }
            else {

                menu = navigationView.getMenu();
                item=menu.findItem(R.id.nav_allPosts);
                item.setVisible(false);
                item=menu.findItem(R.id.nav_add);
                item.setVisible(false);
                item=menu.findItem(R.id.nav_home);
                item.setVisible(false);
                item=menu.findItem(R.id.nav_profile);
                item.setVisible(false);
                item=menu.findItem(R.id.nav_logout);
                item.setVisible(false);


                LoginFragment fragment = new LoginFragment();
                trans.replace(R.id.nav_host_fragment_content_main, fragment);
                trans.addToBackStack(null);
                trans.commit();


            }
        }
        else {
            Menu menu = navigationView.getMenu();
            MenuItem item=menu.findItem(R.id.nav_register);
            menu = navigationView.getMenu();
            item=menu.findItem(R.id.nav_allPosts);
            item.setVisible(false);
            item=menu.findItem(R.id.nav_add);
            item.setVisible(false);
            item=menu.findItem(R.id.nav_home);
            item.setVisible(false);
            item=menu.findItem(R.id.nav_profile);
            item.setVisible(false);
            item=menu.findItem(R.id.nav_logout);
            item.setVisible(false);

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
                else if (menuID==R.id.nav_exit){
                    finish();

                }
                else if (menuID==R.id.nav_logout){
                    LoginFragment fragment = new LoginFragment();
                    trans.replace(R.id.nav_host_fragment_content_main,fragment);
                    trans.addToBackStack(null);

                    preference.SaveBool(getApplicationContext(),false,SharedPreference.LOGIN_STATUS);
//                    preference.SaveString(getApplicationContext(),null,SharedPreference.USER_TYPE);
                    preference.SaveString(getApplicationContext(),null,SharedPreference.USER_NIC);
//                    EnableMenu(false);

//                    Menu menu = navigationView.getMenu();
//                    MenuItem itemM=menu.findItem(R.id.nav_allPosts);
//                    itemM.setVisible(false);
//                    itemM=menu.findItem(R.id.nav_add);
//                    itemM.setVisible(false);
//                    itemM=menu.findItem(R.id.nav_home);
//                    itemM.setVisible(false);
//                    itemM=menu.findItem(R.id.nav_profile);
//                    itemM.setVisible(false);
//                    itemM=menu.findItem(R.id.nav_logout);
//                    itemM.setVisible(false);
//                    itemM=menu.findItem(R.id.nav_login);
//                    itemM.setVisible(true);
                }
                else if (menuID==R.id.nav_unReg){
                    RegisterFragment fragment = new RegisterFragment();
                    trans.replace(R.id.nav_host_fragment_content_main,fragment);
                    trans.addToBackStack(null);
                    preference.SaveBool(getApplicationContext(),false,SharedPreference.REGISTER);
//                    Menu menu = navigationView.getMenu();
//                    MenuItem itemM=menu.findItem(R.id.nav_allPosts);
//                    itemM.setVisible(false);
//                    itemM=menu.findItem(R.id.nav_add);
//                    itemM.setVisible(false);
//                    itemM=menu.findItem(R.id.nav_home);
//                    itemM.setVisible(false);
//                    itemM=menu.findItem(R.id.nav_profile);
//                    itemM.setVisible(false);
//                    itemM=menu.findItem(R.id.nav_logout);
//                    itemM.setVisible(false);
//                    itemM=menu.findItem(R.id.nav_login);
//                    itemM.setVisible(true);
//                    itemM=menu.findItem(R.id.nav_register);
//                    itemM.setVisible(false);
                }
                trans.addToBackStack(null);
                trans.commit();
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    public void EnableAfterLoginMenu()
    {

    }
//    //function to set menu item visibility
//    public void EnableMenu(Boolean status){
//        Menu menu = navigationView.getMenu();
//        MenuItem item=menu.findItem(R.id.nav_myPosts);
//        item.setVisible(status);
//        item=menu.findItem(R.id.nav_add);
//        item.setVisible(status);
//        item=menu.findItem(R.id.nav_home);
//        item.setVisible(status);
//        item=menu.findItem(R.id.nav_profile);
//        item.setVisible(status);
//
//    }

     public  void EnableOnLogin(Boolean status)
     {
         Menu menu = navigationView.getMenu();
        MenuItem item=menu.findItem(R.id.nav_login);
             item.setVisible(status);
//         try {

//             Toast.makeText(getApplicationContext(), "Colicked" , Toast.LENGTH_LONG).show();
//         }catch(Exception ex){
//             throw ex;
//
//     }
     }

    public  void EnableLoginMenu()
    {
        try {
            Toast.makeText(getApplicationContext(), "Colicked" , Toast.LENGTH_LONG).show();
            Menu menu = navigationView.getMenu();
            MenuItem item=menu.findItem(R.id.nav_allPosts);
            item.setVisible(false);
        }catch(Exception ex){
            throw ex;

        }
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

    public void  goToUrl(){
        try{
        Uri uri = Uri.parse("http://www.google.com/maps/place/7.296288087554101,80.63245207071304"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
//            Toast.makeText(getApplicationContext(), "Colicked" , Toast.LENGTH_LONG).show();
        }
        catch(Exception ex){throw ex;}
    }

}