package com.example.house_cleaning_app.ui.login;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.data.passwordHash;
import com.example.house_cleaning_app.ui.register.RegisterFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    TextView txtReg;
    EditText txtNIC,txtPw;
    Button btnLog;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_login, container, false);

        txtReg = view.findViewById(R.id.txtLog);
        txtNIC =view.findViewById(R.id.txtLogNic);
        txtPw=view.findViewById(R.id.txtLogPw);
        btnLog =view.findViewById(R.id.btnLogin);


        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validation
                if (checkValid()){

                    final String enteredNIC = txtNIC.getText().toString();
                    final String enteredPW = passwordHash.getMd5(txtPw.getText().toString());;

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
                    Query checkUser = reference.orderByChild("userNIC").equalTo(enteredNIC);

                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){

                                String passwordFromDB =snapshot.child(enteredNIC).child("password").getValue(String.class);

                                if(passwordFromDB.equals(enteredPW)){
                                    LoginCheck.NIC = enteredNIC;
                                    Toast.makeText(getActivity().getApplicationContext(),"password correct",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(getActivity().getApplicationContext(),"Wrong password",Toast.LENGTH_LONG).show();
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



//                    Toast.makeText(getActivity().getApplicationContext(),"DONE",Toast.LENGTH_LONG).show();
                }
            }
        });

        txtReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
                RegisterFragment fragment = new RegisterFragment();
                trans.replace(R.id.nav_host_fragment_content_main, fragment);
                trans.addToBackStack(null);
                trans.commit();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel
    }
    private boolean checkValid() {
        if (txtNIC.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(),"Email cannot be blank",Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtPw.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(),"Password cannot be blank",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }
}