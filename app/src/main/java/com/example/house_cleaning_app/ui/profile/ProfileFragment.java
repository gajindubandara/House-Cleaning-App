package com.example.house_cleaning_app.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.ui.login.LoginCheck;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    Button btnUpdate;
    RadioButton rbtnCon, rbtnCus;
    EditText txtNIC,txtName,txtAddress,txtEmail,txtNum,txtPw,txtCpw;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_profile, container, false);



        rbtnCon= view.findViewById(R.id.rbtnCon);
        rbtnCus= view.findViewById(R.id.rbtnCus);
        txtNIC = view.findViewById(R.id.txtNIC);
        txtName = view.findViewById(R.id.Name);
        txtAddress = view.findViewById(R.id.Address);
        txtEmail = view.findViewById(R.id.Email);
        txtNum = view.findViewById(R.id.Num);
        txtPw = view.findViewById(R.id.txtPw);
        txtCpw = view.findViewById(R.id.txtCpw);

        btnUpdate = view.findViewById(R.id.btnUpdate);

        try{
//            SharedPreference preference=new SharedPreference();
            String  userNIC =  LoginCheck.getNIC();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
            Query checkUser = reference.orderByChild("userNIC").equalTo(userNIC);

            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){

                        String passwordFromDB =snapshot.child(LoginCheck.getNIC()).child("password").getValue(String.class);
                        String nameFromDB =snapshot.child(LoginCheck.getNIC()).child("name").getValue(String.class);
                        String addressFromDB =snapshot.child(LoginCheck.getNIC()).child("address").getValue(String.class);
                        String emailFromDB =snapshot.child(LoginCheck.getNIC()).child("email").getValue(String.class);
                        String typeFromDB =snapshot.child(LoginCheck.getNIC()).child("type").getValue(String.class);
                        String numberFromDB =snapshot.child(LoginCheck.getNIC()).child("number").getValue(String.class);


                        txtNIC.setText(LoginCheck.getNIC());
                        txtNIC.setEnabled(false);
                        txtName.setText(nameFromDB);
                        txtAddress.setText(addressFromDB);
                        txtEmail.setText(emailFromDB);
                        txtNum.setText(numberFromDB);
                        txtPw.setText(passwordFromDB);
                        txtCpw.setText(passwordFromDB);
                        txtName.setText(nameFromDB);
                        rbtnCus.setEnabled(false);
                        rbtnCon.setEnabled(false);

                        if (typeFromDB.equals("Customer")){
                            rbtnCus.setChecked(true);

                        }else{
                            rbtnCon.setChecked(true);
                        }

                    }
                    else{

                        Toast.makeText(getActivity().getApplicationContext(),"Wrong NIC or password",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch(Exception ex){}


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



//                UserDB udb=new UserDB();
//
//                User user=new User();
//                String nic ="10";
//
//                user=udb.getUser(nic);
//
//                if(user!=null){
//                    Toast.makeText(getActivity().getApplicationContext(),"found!",Toast.LENGTH_LONG).show();
//                }else{Toast.makeText(getActivity().getApplicationContext(),"no!",Toast.LENGTH_LONG).show();}

//                working
//                EditProfileFragment Efrag = new EditProfileFragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.nav_host_fragment_content_main, Efrag);
//                transaction.commit();

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}