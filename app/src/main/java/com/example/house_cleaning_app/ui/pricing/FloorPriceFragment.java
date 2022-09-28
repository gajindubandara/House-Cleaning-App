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
import androidx.lifecycle.ViewModelProvider;

import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.model.FloorType;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FloorPriceFragment extends Fragment {

    private FloorPriceViewModel mViewModel;
    EditText type,price;
    Button add;
    DatabaseReference ref;
    FirebaseDatabase rootNode;

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


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try{
                rootNode = FirebaseDatabase.getInstance();
                ref =rootNode.getReference("FloorType");

                String addType = type.getText().toString();
                String addPrice = price.getText().toString();

                FloorType ft =new FloorType(addType,addPrice);

                ref.child(addType).setValue(ft);
                Toast.makeText(getActivity().getApplicationContext(),"New Floor Type Added!",Toast.LENGTH_LONG).show();

            }
            catch(Exception ex){

            }
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

}