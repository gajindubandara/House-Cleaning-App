package com.example.house_cleaning_app.ui.myPosts;

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
import androidx.lifecycle.ViewModelProvider;

import com.example.house_cleaning_app.MainActivity;
import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.ui.login.LoginCheck;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewJobFragment extends Fragment {

    private ViewJobViewModel mViewModel;
    TextView jobDate,jobPrice, NOR,NOBr,RFT,BrFT;
    String date,price,NoR,NoBr,RFt,BrFt,loc;
    ImageView imgR,imgBr;
    Button btnLoc,btnGet;
    DatabaseReference referance;
    FirebaseDatabase rootNode;


    public static ViewJobFragment newInstance() {
        return new ViewJobFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_view_job, container, false);
        String postUserID =JobID.getNIC();
        String  ID =  LoginCheck.getNIC();
        jobDate =view.findViewById(R.id.jobDate);
        jobPrice =view.findViewById(R.id.jobPrice);
        NOR =view.findViewById(R.id.jobNoR);
        NOBr =view.findViewById(R.id.jobNOBr);
        RFT =view.findViewById(R.id.jobRFT);
        BrFT =view.findViewById(R.id.jobBrFT);
        imgBr =view.findViewById(R.id.imgJobBr);
        imgR =view.findViewById(R.id.imgJobR);
        btnLoc =view.findViewById(R.id.btnLocation);
        btnGet =view.findViewById(R.id.btnGet);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Job");
        Query checkUser = reference.orderByChild("user").equalTo(postUserID);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String rUrl = snapshot.child(postUserID).child("imageR").getValue(String.class);
                    String brUrl = snapshot.child(postUserID).child("imageBr").getValue(String.class);
                    loc = snapshot.child(postUserID).child("location").getValue(String.class);

                    Picasso.get().load(rUrl).into(imgR);
                    Picasso.get().load(brUrl).into(imgBr);
                    jobDate.setText(snapshot.child(postUserID).child("date").getValue(String.class));
                    jobPrice.setText("Rs. "+snapshot.child(postUserID).child("price").getValue(String.class)+".00/-");
                    NOR.setText(snapshot.child(postUserID).child("noOfRooms").getValue(String.class));
                    NOBr.setText(snapshot.child(postUserID).child("noOfBathrooms").getValue(String.class));
                    RFT.setText(snapshot.child(postUserID).child("rFloorType").getValue(String.class));
                    BrFT.setText(snapshot.child(postUserID).child("bFloorType").getValue(String.class));

                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),"nodata",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).goToUrl();
            }
        });

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGet.setEnabled(false);
                rootNode = FirebaseDatabase.getInstance();
                referance = rootNode.getReference("Job");
                referance = rootNode.getReference("Job");
                referance.child(postUserID).child("status").setValue("Assigned");
                referance.child(postUserID).child("contractor").setValue(ID);
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ViewJobViewModel.class);
        // TODO: Use the ViewModel
    }

}