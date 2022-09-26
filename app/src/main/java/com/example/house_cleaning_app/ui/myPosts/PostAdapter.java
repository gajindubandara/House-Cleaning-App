package com.example.house_cleaning_app.ui.myPosts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.house_cleaning_app.MainActivity;
import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.model.Job;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    FirebaseDatabase fdb;
    List<Job> jobList;
    TextView text;
    private Context context;


    public PostAdapter(List<Job> jobs, FirebaseDatabase _db){

        jobList = jobs;
        fdb = _db;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View eventItems = inflater.inflate(R.layout.post_item,parent,false);
        ViewHolder holder = new ViewHolder(eventItems);
        context = parent.getContext();
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Job job = jobList.get(position);
        holder.txtDate.setText(job.getDate());
        holder.txtPrice.setText("Rs. "+job.getPrice()+".00/-");
        holder.txtStatus.setText(job.getStatus());
        if(job.getStatus().equals("Open")){
            holder.imgDot.setBackground(AppCompatResources.getDrawable(context, R.drawable.ic_green_dot));
//            holder.txtStatus.setTextColor(R.color.green);
        }else if(job.getStatus().equals("Assigned")){
           holder.imgDot.setBackground(AppCompatResources.getDrawable(context, R.drawable.ic_red_dot));
//           holder.txtStatus.setTextColor(R.color.red);
        }




        holder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobID.setNIC(job.getUser());
                ViewJobFragment fragment =new ViewJobFragment();
                FragmentTransaction ft=((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_content_main,fragment);
                ft.addToBackStack(null);
                ft.commit();
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
        ImageButton imgBtnLoc;
        Button viewBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate =itemView.findViewById(R.id.txtDate);
            txtPrice =itemView.findViewById(R.id.txtPrice);
            txtStatus =itemView.findViewById(R.id.txtStatus);
            viewBtn =itemView.findViewById(R.id.btnViewJob);
            imgDot = itemView.findViewById(R.id.imgDot);

//            imgVE = itemView.findViewById(R.id.imgVE);
//            imgbtnEdit =itemView.findViewById(R.id.imgbtnEdit);
//            imgbtnDelete =itemView.findViewById(R.id.imgbtnDelete);



        }
    }





}
