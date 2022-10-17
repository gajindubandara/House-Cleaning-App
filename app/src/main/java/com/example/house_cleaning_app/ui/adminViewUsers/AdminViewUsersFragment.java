package com.example.house_cleaning_app.ui.adminViewUsers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.house_cleaning_app.PreLoader;
import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminViewUsersFragment extends Fragment {

    private AdminViewUsersViewModel mViewModel;
    CardView Cus,Con;
    TextView type,noData;
    FirebaseDatabase fdb = FirebaseDatabase.getInstance();

    public static AdminViewUsersFragment newInstance() {
        return new AdminViewUsersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_admin_view_users, container, false);

        Cus=view.findViewById(R.id.btnViewContractor);
        Con=view.findViewById(R.id.Con);
        type=view.findViewById(R.id.txtUserType);
        noData=view.findViewById(R.id.noData);
        noData.setVisibility(view.GONE);

        final PreLoader preloader = new PreLoader(getActivity());



        Con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preloader.startLoadingDialog();
                type.setText("Contractors");

                RecyclerView recyclerView = view.findViewById(R.id.rcvFP);
                List<User> userList = new ArrayList<>();
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("User");
                Query getCus = rootRef.orderByChild("type").equalTo("2");

                getCus.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        preloader.dismissDialog();
                        if(snapshot.exists()) {
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                User u = postSnapshot.getValue(User.class);
                                userList.add(u);
                            }
                        }
                        else{
                        noData.setVisibility(view.VISIBLE);
                        noData.setText("No Contractors Available");
                        }

                        AdminUserAdapter adapter= new AdminUserAdapter(userList,fdb);
                        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        recyclerView.setAdapter(adapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }
        });

        Cus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preloader.startLoadingDialog();
                type.setText("Customers");

                RecyclerView recyclerView = view.findViewById(R.id.rcvFP);
                List<User> userList = new ArrayList<>();
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("User");
                Query getCus = rootRef.orderByChild("type").equalTo("1");

                getCus.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        preloader.dismissDialog();
                        if(snapshot.exists()) {
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                User u = postSnapshot.getValue(User.class);
                                userList.add(u);
                            }
                        }
                        else {
                            noData.setVisibility(view.VISIBLE);
                            noData.setText("No Customers Available");
                        }

                        AdminUserAdapter adapter= new AdminUserAdapter(userList,fdb);
                        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        recyclerView.setAdapter(adapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AdminViewUsersViewModel.class);
        // TODO: Use the ViewModel
    }

}