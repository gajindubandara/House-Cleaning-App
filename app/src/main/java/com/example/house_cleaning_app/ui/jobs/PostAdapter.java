package com.example.house_cleaning_app.ui.jobs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.house_cleaning_app.MainActivity;
import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.Temp;
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

        int status =Integer.valueOf(job.getStatus());
        if(status==1){
            holder.imgDot.setBackground(AppCompatResources.getDrawable(context, R.drawable.ic_green_dot));
            holder.txtStatus.setText("Open");

        }else if(status==2){
            holder.imgDot.setBackground(AppCompatResources.getDrawable(context, R.drawable.ic_yellow_dot));
            holder.txtStatus.setText("Request Pending");

        }else if(status==3){
            holder.imgDot.setBackground(AppCompatResources.getDrawable(context, R.drawable.ic_blue_dot));
            holder.txtStatus.setText("Assigned");
        }
        else if(status==4){
            holder.imgDot.setBackground(AppCompatResources.getDrawable(context, R.drawable.ic_red_dot));
            holder.txtStatus.setText("Finished");
        }





        holder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle =new Bundle();
//                bundle.putString("key",job.getJobID());
//                ViewJobFragment frag =new ViewJobFragment();
//                frag.setArguments(bundle);

//                JobID.setID(job.getJobID());
                Temp.setJobID(job.getJobID());
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
        ImageButton viewBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate =itemView.findViewById(R.id.txtDate);
            txtPrice =itemView.findViewById(R.id.txtPrice);
            txtStatus =itemView.findViewById(R.id.txtStatus);
            viewBtn =itemView.findViewById(R.id.btnViewJob);
            imgDot = itemView.findViewById(R.id.imgDot);
;



        }
    }





}
