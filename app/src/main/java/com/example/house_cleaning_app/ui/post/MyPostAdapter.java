package com.example.house_cleaning_app.ui.post;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.house_cleaning_app.MainActivity;
import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.Temp;
import com.example.house_cleaning_app.model.Job;
import com.example.house_cleaning_app.ui.managePost.EditPostFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.ViewHolder> {
    FirebaseDatabase fdb;
    List<Job> jobList;
    private Context context;
    DatabaseReference referance;
    FirebaseDatabase rootNode;


    public MyPostAdapter(List<Job> jobs, FirebaseDatabase _db){

        jobList = jobs;
        fdb = _db;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View eventItems = inflater.inflate(R.layout.my_posts,parent,false);
        ViewHolder holder = new ViewHolder(eventItems);
        context = parent.getContext();
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Job job = jobList.get(position);
        holder.txtDate.setText(job.getDate());
        holder.txtPrice.setText("Rs. "+job.getPrice()+".00/-");

        int status =Integer.valueOf(job.getStatus());
        if(status==1){
            holder.imgDot.setBackground(AppCompatResources.getDrawable(context, R.drawable.ic_green_dot));
            holder.txtStatus.setText("Open");

        }else if(status==2){
           holder.imgDot.setBackground(AppCompatResources.getDrawable(context, R.drawable.ic_yellow_dot));
            holder.txtStatus.setText("Request Received");
            holder.editBtn.setVisibility(View.GONE);
            holder.btnDel.setVisibility(View.GONE);

        }
        else if(status==3){
            holder.imgDot.setBackground(AppCompatResources.getDrawable(context, R.drawable.ic_blue_dot));
            holder.txtStatus.setText("Assigned");
            holder.editBtn.setVisibility(View.GONE);
            holder.btnDel.setVisibility(View.GONE);
        }
        else if(status==3){
            holder.imgDot.setBackground(AppCompatResources.getDrawable(context, R.drawable.ic_blue_dot));
            holder.txtStatus.setText("Assigned");
            holder.editBtn.setVisibility(View.GONE);
            holder.btnDel.setVisibility(View.GONE);
        }
        else if(status==4){
            holder.imgDot.setBackground(AppCompatResources.getDrawable(context, R.drawable.ic_red_dot));
            holder.txtStatus.setText("Finished");
            holder.editBtn.setVisibility(View.GONE);
            holder.btnDel.setVisibility(View.GONE);
        }





        holder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Temp.setJobID(job.getJobID());
                ViewMyPostFragment fragment =new ViewMyPostFragment();
                FragmentTransaction ft=((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_content_main,fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.setJobID(job.getJobID());
                EditPostFragment fragment =new EditPostFragment();
                FragmentTransaction ft=((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_content_main,fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder =new AlertDialog.Builder(holder.btnDel.getContext());
                builder.setMessage("Are you sure,You want to remove the post").setTitle("Confirm Delete").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rootNode = FirebaseDatabase.getInstance();
                        referance = rootNode.getReference("Job");


                        referance.child(job.getJobID()).removeValue();

                        StorageReference storageReference= FirebaseStorage.getInstance().getReference();
                        String   imgR = "images/"+job.getJobID()+"/Room";
                        String   imgB = "images/"+job.getJobID()+"/Bathroom";
                        storageReference.child(imgB).delete();
                        storageReference.child(imgR).delete();

//                        referance.child(job.getJobID()).child("status").setValue("5");
                        Toast.makeText((MainActivity)v.getContext(),"Post Removed!",Toast.LENGTH_LONG).show();
                        FragmentTransaction trans =((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction();
                        MyPostsFragment fragment = new MyPostsFragment();
                        trans.replace(R.id.nav_host_fragment_content_main, fragment);
                        trans.addToBackStack(null);
                        trans.commit();

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


    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate, txtPrice, txtStatus;
        ImageView imgDot;
        ImageButton btnDel,viewBtn,editBtn;
//        Button viewBtn,editBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate =itemView.findViewById(R.id.txtDate);
            txtPrice =itemView.findViewById(R.id.txtPrice);
            txtStatus =itemView.findViewById(R.id.txtStatus);
            viewBtn =itemView.findViewById(R.id.btnViewMyPost);
            imgDot = itemView.findViewById(R.id.imgDot);
            editBtn =itemView.findViewById(R.id.btnEditMyPost);
            btnDel =itemView.findViewById(R.id.imgBtnDel);



        }
    }





}
