package com.example.house_cleaning_app.ui.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.Temp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewMyPostFragment extends Fragment {

    private ViewMyPostViewModel mViewModel;
    TextView postDate,postPrice, NOR,NOBr,RFT,BrFT,txtMsg;
    String date,price,NoR,NoBr,RFt,BrFt,loc;
    ImageView imgR,imgBr;
    Button btnLoc,btnDel,btnAccept,btnCancel,btnCon;
    DatabaseReference referance;
    FirebaseDatabase rootNode;
    int status;

    public static ViewMyPostFragment newInstance() {
        return new ViewMyPostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_view_my_post, container, false);
        String jobID = Temp.getJobID();

        postDate =view.findViewById(R.id.postDate);
        postPrice =view.findViewById(R.id.postPrice);
        NOR =view.findViewById(R.id.postNoR);
        NOBr =view.findViewById(R.id.postNOBr);
        RFT =view.findViewById(R.id.postRFT);
        BrFT=view.findViewById(R.id.postBrFT);
        imgBr =view.findViewById(R.id.imgPostBr);
        imgR =view.findViewById(R.id.imgPostR);
        txtMsg =view.findViewById(R.id.txtMsg);
        btnLoc =view.findViewById(R.id.btnPostLocation);
        btnDel =view.findViewById(R.id.btnDel);
        btnAccept =view.findViewById(R.id.btnAccept);
        btnCancel =view.findViewById(R.id.btnCancel);
        btnCon=view.findViewById(R.id.btnViewContractor);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Job");
        Query checkUser = reference.orderByChild("jobID").equalTo(jobID);
//        Toast.makeText(getActivity().getApplicationContext(),key,Toast.LENGTH_LONG).show();

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String rUrl = snapshot.child(jobID).child("imageR").getValue(String.class);
                    String brUrl = snapshot.child(jobID).child("imageBr").getValue(String.class);
                    loc = snapshot.child(jobID).child("location").getValue(String.class);
                    status =Integer.valueOf(snapshot.child(jobID).child("status").getValue(String.class));

                    Picasso.get().load(rUrl).into(imgR);
                    Picasso.get().load(brUrl).into(imgBr);
                    postDate.setText(snapshot.child(jobID).child("date").getValue(String.class));
                    postPrice.setText("Rs. "+snapshot.child(jobID).child("price").getValue(String.class)+".00/-");
                    NOR.setText(snapshot.child(jobID).child("noOfRooms").getValue(String.class));
                    NOBr.setText(snapshot.child(jobID).child("noOfBathrooms").getValue(String.class));
                    RFT.setText(snapshot.child(jobID).child("rFloorType").getValue(String.class));
                    BrFT.setText(snapshot.child(jobID).child("bFloorType").getValue(String.class));

                    if(status!=2) {
                        btnAccept.setVisibility(view.GONE);
                        btnCancel.setVisibility(view.GONE);
                        txtMsg.setVisibility(view.GONE);
                        btnCon.setVisibility(view.GONE);
                        btnDel.setVisibility(view.GONE);
                    }
                    if(status==5||status==3) {
                        btnDel.setVisibility(view.GONE);
                    }

                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),"no data",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        rootNode = FirebaseDatabase.getInstance();
        referance = rootNode.getReference("Job");

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAccept.setVisibility(view.GONE);
                btnCancel.setVisibility(view.GONE);
                txtMsg.setVisibility(view.GONE);
                btnCon.setVisibility(view.GONE);
                referance.child(jobID).child("status").setValue("3");
                Toast.makeText(getActivity().getApplicationContext(),"Contractor Accepted!",Toast.LENGTH_LONG).show();
                FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
                MyPostsFragment fragment = new MyPostsFragment();
                trans.replace(R.id.nav_host_fragment_content_main, fragment);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAccept.setVisibility(view.GONE);
                btnCancel.setVisibility(view.GONE);
                txtMsg.setVisibility(view.GONE);
                btnCon.setVisibility(view.GONE);
                referance.child(jobID).child("status").setValue("1");
                referance.child(jobID).child("contractor").setValue("");
                Toast.makeText(getActivity().getApplicationContext(),"Contractor Declined",Toast.LENGTH_LONG).show();
                FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
                MyPostsFragment fragment = new MyPostsFragment();
                trans.replace(R.id.nav_host_fragment_content_main, fragment);
                trans.addToBackStack(null);
                trans.commit();
            }
        });



        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referance.child(jobID).child("status").setValue("5");
                Toast.makeText(getActivity().getApplicationContext(),"Post Removed!",Toast.LENGTH_LONG).show();
                FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
                MyPostsFragment fragment = new MyPostsFragment();
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
        mViewModel = new ViewModelProvider(this).get(ViewMyPostViewModel.class);
        // TODO: Use the ViewModel
    }

}