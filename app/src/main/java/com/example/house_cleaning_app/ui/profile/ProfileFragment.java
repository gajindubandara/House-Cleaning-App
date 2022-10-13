package com.example.house_cleaning_app.ui.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.house_cleaning_app.PreLoader;
import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.Temp;
import com.example.house_cleaning_app.PasswordHash.passwordHash;
import com.example.house_cleaning_app.model.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    Button btnUpdate,btnEdit,btnCpw;
    RadioButton rbtnCon, rbtnCus;
    EditText txtNIC,txtName,txtAddress,txtEmail,txtNum;
    String currentPw,nPw,cPw;
    String passwordFromDB,nameFromDB,emailFromDB,addressFromDB,numberFromDB;


    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_profile, container, false);

       String  userNIC =  Temp.getNIC();
       DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");



        rbtnCon= view.findViewById(R.id.rbtnCon);
        rbtnCus= view.findViewById(R.id.rbtnCus);
        txtNIC = view.findViewById(R.id.txtNIC);
        txtName = view.findViewById(R.id.Name);
        txtAddress = view.findViewById(R.id.Address);
        txtEmail = view.findViewById(R.id.Email);
        txtNum = view.findViewById(R.id.Num);


        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnEdit=view.findViewById(R.id.btnEdit);
        btnCpw =view.findViewById(R.id.btnCpw);
        btnUpdate.setVisibility(view.GONE);

        final PreLoader preloader = new PreLoader(getActivity());
        preloader.startLoadingDialog();

        try{
            Query checkUser = reference.orderByChild("userNIC").equalTo(userNIC);

            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    preloader.dismissDialog();
                    if(snapshot.exists()){

                        passwordFromDB =snapshot.child(Temp.getNIC()).child("password").getValue(String.class);
                        nameFromDB =snapshot.child(Temp.getNIC()).child("name").getValue(String.class);
                        addressFromDB =snapshot.child(Temp.getNIC()).child("address").getValue(String.class);
                        emailFromDB =snapshot.child(Temp.getNIC()).child("email").getValue(String.class);
                        int typeFromDB =Integer.valueOf(snapshot.child(Temp.getNIC()).child("type").getValue(String.class));
                        numberFromDB =snapshot.child(Temp.getNIC()).child("number").getValue(String.class);


                        txtNIC.setText(Temp.getNIC());
                        txtNIC.setEnabled(false);
                        txtName.setText(nameFromDB);
                        txtName.setEnabled(false);
                        txtAddress.setText(addressFromDB);
                        txtAddress.setEnabled(false);
                        txtEmail.setText(emailFromDB);
                        txtEmail.setEnabled(false);
                        txtNum.setText(numberFromDB);
                        txtNum.setEnabled(false);

                        rbtnCus.setEnabled(false);
                        rbtnCon.setEnabled(false);

                        if (typeFromDB==1){
                            rbtnCus.setChecked(true);

                        }else if (typeFromDB==2){
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

                if(txtName.getText().toString().equals(nameFromDB) && txtAddress.getText().toString().equals(addressFromDB)
                        && txtEmail.getText().toString().equals(emailFromDB) && txtNum.getText().toString().equals(numberFromDB)){

                    Toast.makeText(getActivity().getApplicationContext(),"Nothing to update!",Toast.LENGTH_LONG).show();
                }
                else{
                    btnUpdate.setVisibility(view.GONE);
                    btnEdit.setVisibility(view.VISIBLE);

                    txtName.setEnabled(false);
                    txtAddress.setEnabled(false);
                    txtEmail.setEnabled(false);
                    txtNum.setEnabled(false);


                    List<Review> revList = new ArrayList<>();
                    DatabaseReference checkRev = FirebaseDatabase.getInstance().getReference("Reviews");
                    Query getCreator = checkRev.orderByChild("creator").equalTo(nameFromDB);

                    getCreator.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    Review rev = postSnapshot.getValue(Review.class);
                                    checkRev.child(rev.getKey()).child("creator").setValue(txtName.getText().toString());

                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });


                    //update db
                    reference.child(userNIC).child("name").setValue(txtName.getText().toString());
                    reference.child(userNIC).child("email").setValue(txtEmail.getText().toString());
                    reference.child(userNIC).child("address").setValue(txtAddress.getText().toString());
                    reference.child(userNIC).child("number").setValue(txtNum.getText().toString());




                    //reload frag
                    ProfileFragment fragment =new ProfileFragment();
                    FragmentTransaction trans=getActivity().getSupportFragmentManager().beginTransaction();
                    trans.replace(R.id.nav_host_fragment_content_main,fragment);
                    trans.detach(fragment);
                    trans.attach(fragment);
                    trans.commit();

                    Toast.makeText(getActivity().getApplicationContext(),"Updated!",Toast.LENGTH_LONG).show();

                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpdate.setVisibility(view.VISIBLE);
                btnEdit.setVisibility(view.GONE);
                txtName.setEnabled(true);
                txtAddress.setEnabled(true);
                txtEmail.setEnabled(true);
                txtNum.setEnabled(true);
            }
        });

        btnCpw.setOnClickListener(new View.OnClickListener() {
            @NonNull
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(btnCpw.getContext());

                //old password
                alert.setTitle("Change Password ");
                alert.setMessage("Enter Current Password");

                final EditText inputCurrentPw = new EditText(btnCpw.getContext());
                inputCurrentPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                alert.setView(inputCurrentPw);
                alert.setCancelable(false);
                alert.setPositiveButton("Next", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        currentPw =passwordHash.getMd5(inputCurrentPw.getText().toString());


                        if(currentPw.equals(passwordFromDB)){
                            //new password
                            alert.setTitle("Change Password ");
                            alert.setMessage("Enter New Password");
                            alert.setCancelable(false);

                            final EditText inputNPw = new EditText(btnCpw.getContext());
                            inputNPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            alert.setView(inputNPw);
                            alert.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    nPw = passwordHash.getMd5(inputNPw.getText().toString());

                                    //Confirm pass
                                    alert.setTitle("Change Password ");
                                    alert.setMessage("Confirm Password");
                                    alert.setCancelable(false);

                                    final EditText inputCPw = new EditText(btnCpw.getContext());
                                    inputCPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                    alert.setView(inputCPw);
                                    alert.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                           cPw = passwordHash.getMd5(inputCPw.getText().toString());
                                                if(nPw.equals(cPw)){

                                                    //set password in db
                                                    reference.child(userNIC).child("password").setValue(cPw);
                                                    Toast.makeText(getActivity().getApplicationContext(),"Password changed!",Toast.LENGTH_LONG).show();

                                                    //reload frag
                                                    ProfileFragment fragment =new ProfileFragment();
                                                    FragmentTransaction trans=getActivity().getSupportFragmentManager().beginTransaction();
                                                    trans.replace(R.id.nav_host_fragment_content_main,fragment);
                                                    trans.detach(fragment);
                                                    trans.attach(fragment);
                                                    trans.commit();
                                                }else {
                                                    Toast.makeText(getActivity().getApplicationContext(),"Passwords does not match",Toast.LENGTH_LONG).show();
                                                }
                                        }
                                    });
                                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            // Canceled.
                                        }
                                    });
                                    alert.show();
                                }
                            });
                            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    // Canceled.
                                }
                            });
                            alert.show();
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(),"Incorrect current password",Toast.LENGTH_LONG).show();
                        }

                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });
                alert.show();
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