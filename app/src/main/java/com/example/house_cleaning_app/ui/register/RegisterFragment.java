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

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;
    private RadioButton radioButton;
    TextView txtxLog;
    RadioButton rbtnCon, rbtnCus;
    EditText txtName,txtAddress,txtEmail,txtNum,txtPw,txtCpw;
    RadioGroup rg;
    Button btnRegister;



    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_register, container, false);
       txtxLog =view.findViewById(R.id.txtLog);
       rbtnCon= view.findViewById(R.id.rbtnCon);
       rbtnCus= view.findViewById(R.id.rbtnCus);
       txtName = view.findViewById(R.id.txtDate);
       txtAddress = view.findViewById(R.id.Loc);
       txtEmail = view.findViewById(R.id.txtNoOfRooms);
       txtNum = view.findViewById(R.id.txtNoOfBathrooms);
       txtPw = view.findViewById(R.id.txtPw);
       txtCpw = view.findViewById(R.id.txtCpw);
       rbtnCon = view.findViewById(R.id.rbtnCon);
       rbtnCus = view.findViewById(R.id.rbtnCus);
       btnRegister = view.findViewById(R.id.btnRegister);
       rbtnCus.setChecked(true);

       rg = view.findViewById(R.id.rg);

        UserDB udb=new UserDB();


       btnRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               int selectedId = rg.getCheckedRadioButtonId();
               radioButton = view.findViewById(selectedId);

                //Validation
               if (checkValid()){
                   if(txtPw.getText().toString().equals(txtCpw.getText().toString())){
                       int id = 00;
                       String name = txtName.getText().toString();
                       String address = txtAddress.getText().toString();
                       String email = txtEmail.getText().toString();
                       String num = txtNum.getText().toString();
                       String hashPW = passwordHash.getMd5(txtPw.getText().toString());
                       String type = radioButton.getText().toString();

                       User user=new User(id,type,name,address,email,num,hashPW);

                       int result=udb.addUser(user);

                       if (result == 1) {
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

       txtxLog.setOnClickListener(new View.OnClickListener() {
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