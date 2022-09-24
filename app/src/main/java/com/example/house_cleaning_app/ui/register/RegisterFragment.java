package com.example.house_cleaning_app.ui.register;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.data.UserDB;
import com.example.house_cleaning_app.data.passwordHash;
import com.example.house_cleaning_app.model.User;
import com.example.house_cleaning_app.ui.login.LoginFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;
    private RadioButton radioButton;
    TextView txtLog;
    RadioButton rbtnCon, rbtnCus;
    EditText txtNIC,txtName,txtAddress,txtEmail,txtNum,txtPw,txtCpw;
    RadioGroup rg;
    Button btnRegister;
    DatabaseReference ref;
    FirebaseDatabase rootNode;



    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_register, container, false);
       txtLog =view.findViewById(R.id.txtLog);
       rbtnCon= view.findViewById(R.id.rbtnCon);
       rbtnCus= view.findViewById(R.id.rbtnCus);
       txtNIC = view.findViewById(R.id.txtNIC);
       txtName = view.findViewById(R.id.Name);
       txtAddress = view.findViewById(R.id.Address);
       txtEmail = view.findViewById(R.id.Email);
       txtNum = view.findViewById(R.id.Num);
       txtPw = view.findViewById(R.id.txtPw);
       txtCpw = view.findViewById(R.id.txtCpw);

       btnRegister = view.findViewById(R.id.btnRegister);
       rbtnCus.setChecked(true);

       rg = view.findViewById(R.id.rg);

        UserDB udb=new UserDB();


       btnRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

                boolean check =false;
               int selectedId = rg.getCheckedRadioButtonId();
               radioButton = view.findViewById(selectedId);

                //Validation
               if (checkValid()){
                   if(txtPw.getText().toString().equals(txtCpw.getText().toString())){
                       String nic = txtNIC.getText().toString();
                       String name = txtName.getText().toString();
                       String address = txtAddress.getText().toString();
                       String email = txtEmail.getText().toString();
                       String num = txtNum.getText().toString();
                       String hashPW = passwordHash.getMd5(txtPw.getText().toString());
                       String type = radioButton.getText().toString();

                       User user=new User(nic,type,name,address,email,num,hashPW);

                       //saving data to DB
                       try{
                           rootNode = FirebaseDatabase.getInstance();
                           ref =rootNode.getReference("User");
                           ref.child(user.getUserNIC()).setValue(user);
                           check=true;
                       }
                       catch(Exception ex){
                         throw ex;
                       }

                       if (check) {
                           Toast.makeText(getActivity().getApplicationContext(),"New record added",Toast.LENGTH_LONG).show();
                       }
                       else{
                           Toast.makeText(getActivity().getApplicationContext(),"New record not added",Toast.LENGTH_LONG).show();
                       }
                   }
                   else{
                       Toast.makeText(getActivity().getApplicationContext(),"Passwords dose not match",Toast.LENGTH_LONG).show();
                   }
               }
           }
       });

       txtLog.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
               LoginFragment fragment = new LoginFragment();
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
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        // TODO: Use the ViewModel
    }


    private boolean checkValid() {
        if (txtNIC.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(),"NIC cannot be blank",Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtName.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(),"Name cannot be blank",Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtAddress.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(),"Address cannot be blank",Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtEmail.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(),"Email cannot be blank",Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtNum.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(),"Number cannot be blank",Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtPw.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(),"Password cannot be blank",Toast.LENGTH_LONG).show();
            return false;
        }
        if (txtCpw.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(),"Confirm password cannot be blank",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }
}