package com.example.house_cleaning_app.ui.myJobs;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.house_cleaning_app.MainActivity;
import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.Temp;
import com.example.house_cleaning_app.model.Review;
import com.example.house_cleaning_app.ui.userView.ViewUserFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewMyJobFragment extends Fragment {

    private ViewMyJobViewModel mViewModel;
    TextView postDate,postPrice, RSqFt, BSqFt,RFT,BrFT,txtMsg,NoR,NoBR;
    String loc;
    ImageView imgR,imgBr;
    CardView btnFinish,btnReviewCus;
    ImageButton btnLoc,btnViewCus;
    DatabaseReference referance,refReview;
    FirebaseDatabase rootNode;
    int status;
    String stat;
    String review ="";
    String user;
    String creator;
    String creatorName ="";
    String viewUserID;



    public static ViewMyJobFragment newInstance() {
        return new ViewMyJobFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_my_job, container, false);
        String jobID = Temp.getJobID();
        String ID = Temp.getNIC();

        postDate = view.findViewById(R.id.postDate);
        postPrice = view.findViewById(R.id.postPrice);
        RSqFt = view.findViewById(R.id.postRSqFt);
        BSqFt = view.findViewById(R.id.postBSqFr);
        RFT = view.findViewById(R.id.postRFT);
        BrFT = view.findViewById(R.id.postBrFT);
        imgBr = view.findViewById(R.id.imgPostBr);
        imgR = view.findViewById(R.id.imgPostR);
        txtMsg = view.findViewById(R.id.txtMsg);
        btnLoc = view.findViewById(R.id.btnPostLocation);
        btnFinish = view.findViewById(R.id.btnFinish);
        NoR = view.findViewById(R.id.postNoR);
        NoBR = view.findViewById(R.id.postNoBR);
        btnFinish.setVisibility(view.GONE);
        btnViewCus=view.findViewById(R.id.btnViewCus);
        btnReviewCus=view.findViewById(R.id.btnAddReviewCustomer);
        btnReviewCus.setVisibility(view.GONE);



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Job");
        Query checkUser = reference.orderByChild("contractor").equalTo(ID);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    String rUrl = snapshot.child(jobID).child("imageR").getValue(String.class);
                    String brUrl = snapshot.child(jobID).child("imageBr").getValue(String.class);
                    loc = snapshot.child(jobID).child("location").getValue(String.class);
                    status = Integer.valueOf(snapshot.child(jobID).child("status").getValue(String.class));
                    stat = snapshot.child(jobID).child("status").getValue(String.class);
                    user = snapshot.child(jobID).child("user").getValue(String.class);
                    creator = snapshot.child(jobID).child("contractor").getValue(String.class);

                    Picasso.get().load(rUrl).into(imgR);
                    Picasso.get().load(brUrl).into(imgBr);
                    postDate.setText(snapshot.child(jobID).child("date").getValue(String.class));
                    postPrice.setText("Rs. " + snapshot.child(jobID).child("price").getValue(String.class) + ".00/-");
                    RSqFt.setText(snapshot.child(jobID).child("rsqFt").getValue(String.class));
                    BSqFt.setText(snapshot.child(jobID).child("bsqFt").getValue(String.class));
                    NoR.setText(snapshot.child(jobID).child("noOfRooms").getValue(String.class));
                    NoBR.setText(snapshot.child(jobID).child("noOfBathrooms").getValue(String.class));
                    RFT.setText(snapshot.child(jobID).child("rFloorType").getValue(String.class));
                    BrFT.setText(snapshot.child(jobID).child("bFloorType").getValue(String.class));
                    viewUserID =snapshot.child(jobID).child("user").getValue(String.class);

                    if (stat.equals("3")){
                        btnFinish.setVisibility(view.VISIBLE);
                    }
                    if (stat.equals("4")){
                        btnReviewCus.setVisibility(view.VISIBLE);
                    }

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "no data", Toast.LENGTH_LONG).show();
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

        btnViewCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.setViewUserID(viewUserID);
                FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
                ViewUserFragment fragment = new ViewUserFragment();
                trans.replace(R.id.nav_host_fragment_content_main, fragment);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        rootNode = FirebaseDatabase.getInstance();
        referance = rootNode.getReference("Job");



        btnFinish.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                btnFinish.setVisibility(view.GONE);
                Toast.makeText(getActivity().getApplicationContext(), "Job Finished!", Toast.LENGTH_LONG).show();
                referance.child(jobID).child("status").setValue("4");

                ViewMyJobFragment fragment =new ViewMyJobFragment();
                FragmentTransaction trans=getActivity().getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.nav_host_fragment_content_main,fragment);
                trans.detach(fragment);
                trans.attach(fragment);
                trans.commit();

            }
        });

        btnReviewCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(btnReviewCus.getContext());
                ViewGroup viewGroup = view.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.review_dialog, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();

                final EditText et = dialogView.findViewById(R.id.editDate);
                Button btnOk = (Button) dialogView.findViewById(R.id.buttonOk);
                RatingBar rb = (RatingBar) dialogView.findViewById(R.id.simpleRatingBar);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        float rt = rb.getRating();
                        String rating =String.valueOf(rt);
                        review=et.getText().toString();

                        if(review.equals("")) {
                            //change frag
                            FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                            ViewMyJobFragment fragment = new ViewMyJobFragment();
                            trans.replace(R.id.nav_host_fragment_content_main, fragment);
                            trans.addToBackStack(null);
                            trans.commit();
                            Toast.makeText(getActivity().getApplicationContext(), "Invalid Review!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            //get date
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                            LocalDateTime now = LocalDateTime.now();
                            String date = dtf.format(now);

                            //get user name
                            DatabaseReference refGetUser = FirebaseDatabase.getInstance().getReference("User");
                            Query getUser = refGetUser.orderByChild("userNIC").equalTo(creator);

                            getUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        creatorName = snapshot.child(creator).child("name").getValue(String.class);

                                        //setReview
                                        refReview = rootNode.getReference("Reviews");
                                        String key = refReview.push().getKey();
                                        Review re = new Review(creatorName, viewUserID, date, review, key, rating);
                                        refReview.child(key).setValue(re);
                                        Toast.makeText(getActivity().getApplicationContext(), "Review Added!", Toast.LENGTH_LONG).show();

                                        //change frag
                                        FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                                        ViewMyJobFragment fragment = new ViewMyJobFragment();
                                        trans.replace(R.id.nav_host_fragment_content_main, fragment);
                                        trans.addToBackStack(null);
                                        trans.commit();

                                    } else {
                                        Toast.makeText(getActivity().getApplicationContext(), "No data", Toast.LENGTH_LONG).show();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();
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