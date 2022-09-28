
package com.example.house_cleaning_app.ui.userView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.Temp;
import com.example.house_cleaning_app.model.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewUserFragment extends Fragment {

    private ViewUserViewModel mViewModel;
    TextView name,address,email,no,userRev;
    FirebaseDatabase fdb = FirebaseDatabase.getInstance();

    public static ViewUserFragment newInstance() {
        return new ViewUserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user_view, container, false);

        String viewUserID= Temp.getViewUserID();
        name=view.findViewById(R.id.userName);
        address=view.findViewById(R.id.userAddress);
        email=view.findViewById(R.id.userEmail);
        no=view.findViewById(R.id.userNo);
        userRev=view.findViewById(R.id.txtNoRev);




        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        Query checkUser = reference.orderByChild("userNIC").equalTo(viewUserID);
//        Toast.makeText(getActivity().getApplicationContext(),key,Toast.LENGTH_LONG).show();

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    name.setText(snapshot.child(viewUserID).child("name").getValue(String.class));
                    address.setText(snapshot.child(viewUserID).child("address").getValue(String.class));
                    email.setText(snapshot.child(viewUserID).child("email").getValue(String.class));
                    no.setText(snapshot.child(viewUserID).child("number").getValue(String.class));
//                    viewUserID=snapshot.child(viewUserID).child("user").getValue(String.class);

                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),"no data",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //set recycle view
        RecyclerView recyclerView = view.findViewById(R.id.rcvUR);
        List<Review> revList = new ArrayList<>();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Reviews");
        Query getPosts = rootRef.orderByChild("user").equalTo(viewUserID);

        getPosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    userRev.setVisibility(view.GONE);
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                    key = snapshot.getKey();
                    Review rev = postSnapshot.getValue(Review.class);
                    revList.add(rev);
                }
                UserAdapter adapter = new UserAdapter(revList, fdb);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                recyclerView.setAdapter(adapter);
            }
                else{
//
//                    Toast.makeText(getActivity().getApplicationContext(),"no rev",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ViewUserViewModel.class);
        // TODO: Use the ViewModel
    }

}