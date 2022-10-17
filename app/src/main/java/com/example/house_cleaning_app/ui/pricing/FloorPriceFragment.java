package com.example.house_cleaning_app.ui.pricing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.house_cleaning_app.PreLoader;
import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.model.FloorType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FloorPriceFragment extends Fragment {

    private FloorPriceViewModel mViewModel;
    EditText type,price;
    Button add;

    FirebaseDatabase fdb = FirebaseDatabase.getInstance();

    public static FloorPriceFragment newInstance() {
        return new FloorPriceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_floor_price, container, false);

        type=view.findViewById(R.id.txtFloorType);
        price=view.findViewById(R.id.txtFloorPrice);
        add=view.findViewById(R.id.btnAdd);

        final PreLoader preloader = new PreLoader(getActivity());
        preloader.startLoadingDialog();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkValid()){
                        DatabaseReference ref;
                        FirebaseDatabase rootNode;
                        rootNode = FirebaseDatabase.getInstance();
                        ref =rootNode.getReference("FloorType");

                        String addType = type.getText().toString();
                        String addPrice = price.getText().toString();

                        FloorType ft =new FloorType(addType,addPrice);

                        ref.child(addType).setValue(ft);
                        Toast.makeText(getActivity().getApplicationContext(),"New Floor Type Added!",Toast.LENGTH_LONG).show();

                        FloorPriceFragment fragment =new FloorPriceFragment();
                        FragmentTransaction trans=getActivity().getSupportFragmentManager().beginTransaction();
                        trans.replace(R.id.nav_host_fragment_content_main,fragment);
                        trans.detach(fragment);
                        trans.attach(fragment);
                        trans.commit();
                }

            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.rcvFP);
        List<FloorType> ftList = new ArrayList<>();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("FloorType");

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                preloader.dismissDialog();
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    FloorType ft =postSnapshot.getValue(FloorType.class);
                    ftList.add(ft);
                }

                FloorAdapter adapter= new FloorAdapter(ftList,fdb);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                recyclerView.setAdapter(adapter);
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
        mViewModel = new ViewModelProvider(this).get(FloorPriceViewModel.class);
        // TODO: Use the ViewModel
    }

    private boolean checkValid() {
        if (type.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(),"Floor Type cannot be blank",Toast.LENGTH_LONG).show();
            return false;
        }
        if (price.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(),"Price cannot be blank",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

}