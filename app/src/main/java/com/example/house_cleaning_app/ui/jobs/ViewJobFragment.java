package com.example.house_cleaning_app.ui.jobs;

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
import com.example.house_cleaning_app.Temp;
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
    Button btnLoc,btnGet,btnFinish;
    DatabaseReference referance;
    FirebaseDatabase rootNode;
    int status;


    public static ViewJobFragment newInstance() {
        return new ViewJobFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_view_job, container, false);
//        String jobID =JobID.getID();
        String jobID =Temp.getJobID();
        String  ID =  Temp.getNIC();
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

//                    CircularProgressDrawable cpd =new CircularProgressDrawable();
//
//                    cpd.strokeWidth = 5f;
//                    cpd.centerRadius = 30f;
//                    cpd.start();
                    Picasso.get().load(rUrl).into(imgR);
                    Picasso.get().load(brUrl).into(imgBr);
                    jobDate.setText(snapshot.child(jobID).child("date").getValue(String.class));
                    jobPrice.setText("Rs. "+snapshot.child(jobID).child("price").getValue(String.class)+".00/-");
                    NOR.setText(snapshot.child(jobID).child("noOfRooms").getValue(String.class));
                    NOBr.setText(snapshot.child(jobID).child("noOfBathrooms").getValue(String.class));
                    RFT.setText(snapshot.child(jobID).child("rFloorType").getValue(String.class));
                    BrFT.setText(snapshot.child(jobID).child("bFloorType").getValue(String.class));

                    if(status!=1) {
                        btnGet.setVisibility(view.GONE);
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

        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).goToUrl();
            }
        });






        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGet.setVisibility(view.GONE);
                rootNode = FirebaseDatabase.getInstance();
                referance = rootNode.getReference("Job");
                referance.child(jobID).child("status").setValue("2");
                referance.child(jobID).child("contractor").setValue(ID);
                Toast.makeText(getActivity().getApplicationContext(),"Request Sent!",Toast.LENGTH_LONG).show();
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