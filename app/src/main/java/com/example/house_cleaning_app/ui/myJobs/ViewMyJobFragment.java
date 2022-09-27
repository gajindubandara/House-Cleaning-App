package com.example.house_cleaning_app.ui.myJobs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class ViewMyJobFragment extends Fragment {

    private ViewMyJobViewModel mViewModel;
    TextView postDate,postPrice, NOR,NOBr,RFT,BrFT,txtMsg,postReview,txtReview;
    String date,price,NoR,NoBr,RFt,BrFt,loc;
    ImageView imgR,imgBr;
    Button btnLoc,btnFinish;
    DatabaseReference referance;
    FirebaseDatabase rootNode;
    int status;
    String review ="";


    public static ViewMyJobFragment newInstance() {
        return new ViewMyJobFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_view_my_job, container, false);
        String jobID = Temp.getJobID();
        String ID=Temp.getNIC();

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
        btnFinish=view.findViewById(R.id.btnFinish);
        postReview=view.findViewById(R.id.postReview);
        txtReview=view.findViewById(R.id.txtReview);




        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Job");
        Query checkUser = reference.orderByChild("contractor").equalTo(ID);

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
                    postReview.setText(snapshot.child(jobID).child("review").getValue(String.class));

                    if(status!=3) {
                        btnFinish.setVisibility(view.GONE);
                    }
                    if(status!=4) {
                        txtReview.setVisibility(view.GONE);
                        postReview.setVisibility(view.GONE);
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

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFinish.setVisibility(view.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(btnFinish.getContext());
                builder.setTitle("Review Customer");
                final EditText input = new EditText(getActivity().getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
                builder.setView(input);
                builder.setPositiveButton("Add Review", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        review = input.getText().toString();
                        referance.child(jobID).child("review").setValue(review);
                        FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
                        MyJobsFragment fragment = new MyJobsFragment();
                        trans.replace(R.id.nav_host_fragment_content_main, fragment);
                        trans.addToBackStack(null);
                        trans.commit();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                Toast.makeText(getActivity().getApplicationContext(),"Job Finished!",Toast.LENGTH_LONG).show();
                builder.show();

                referance.child(jobID).child("status").setValue("4");

            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ViewMyJobViewModel.class);
        // TODO: Use the ViewModel
    }

}