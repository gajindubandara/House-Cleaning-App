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
import com.example.house_cleaning_app.ui.reg.RegisterFragment;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    TextView txtReg;
    EditText txtEmail,txtPw;
    Button btnLog;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_login, container, false);

        txtReg = view.findViewById(R.id.txtLog);
        txtEmail =view.findViewById(R.id.txtLogEmail);
        txtPw=view.findViewById(R.id.txtLogPw);
        btnLog =view.findViewById(R.id.btnLogin);


        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validation
                if (checkValid()){

                    Toast.makeText(getActivity().getApplicationContext(),"DONE",Toast.LENGTH_LONG).show();
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
        if (txtEmail.getText().toString().equals("")) {
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