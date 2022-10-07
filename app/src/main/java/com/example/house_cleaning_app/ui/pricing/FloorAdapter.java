package com.example.house_cleaning_app.ui.pricing;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.house_cleaning_app.MainActivity;
import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.model.FloorType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class FloorAdapter extends RecyclerView.Adapter<FloorAdapter.ViewHolder> {
    FirebaseDatabase fdb;
    List<FloorType> ftList;

    private Context context;


    public FloorAdapter(List<FloorType> types, FirebaseDatabase _db){

        ftList = types;
        fdb = _db;
    }

    @NonNull
    @Override
    public FloorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View types = inflater.inflate(R.layout.floor_type,parent,false);
        FloorAdapter.ViewHolder holder = new FloorAdapter.ViewHolder(types);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FloorType ft =ftList.get(position);
        holder.txtType.setText(ft.getType());
        holder.txtPrice.setText(ft.getPrice());


        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =new AlertDialog.Builder(holder.imgDelete.getContext());
                builder.setMessage("Are you sure,You want to delete").setTitle("Confirm Delete").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("FloorType");
                        Query checkType = rootRef.orderByChild("type").equalTo(ft.getType());

                        checkType.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                rootRef.child(ft.getType()).removeValue();
                                FloorPriceFragment fragment =new FloorPriceFragment();
                                FragmentTransaction ft=((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.nav_host_fragment_content_main,fragment);
                                ft.detach(fragment);
                                ft.attach(fragment);
                                ft.commit();
                                Toast.makeText((MainActivity)v.getContext(),"Floor Type Removed!",Toast.LENGTH_LONG).show();

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if no action
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(holder.imgEdit.getContext());

                alert.setTitle("Change the price of "+ft.getType());
                alert.setMessage("Enter the new price -");

                // Set an EditText view to get user input
                final EditText input = new EditText(holder.imgEdit.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        if(value.equals("")){
                            Toast.makeText((MainActivity)v.getContext(),"Invalid Price",Toast.LENGTH_LONG).show();
                        }
                        else{
                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("FloorType");
                            Query checkType = rootRef.orderByChild("type").equalTo(ft.getType());

                            checkType.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    rootRef.child(ft.getType()).child("price").setValue(value);

                                    FloorPriceFragment fragment =new FloorPriceFragment();
                                    FragmentTransaction ft=((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.nav_host_fragment_content_main,fragment);
                                    ft.detach(fragment);
                                    ft.attach(fragment);
                                    ft.commit();
                                    Toast.makeText((MainActivity)v.getContext(),"Floor Type Updated!",Toast.LENGTH_LONG).show();

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
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

    }

    @Override
    public int getItemCount() {
        return ftList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtType,txtPrice;
        ImageButton imgEdit,imgDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtType=itemView.findViewById(R.id.txtType);
            txtPrice =itemView.findViewById(R.id.txtPrice);
            imgDelete=itemView.findViewById(R.id.imgbtnDel);
            imgEdit=itemView.findViewById(R.id.imgbtnEdit);

        }
    }
}
