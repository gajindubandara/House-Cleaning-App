package com.example.house_cleaning_app.ui.userView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.model.Review;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    FirebaseDatabase fdb;
    List<Review> revList;

    private Context context;


    public UserAdapter(List<Review> reviews, FirebaseDatabase _db){

        revList = reviews;
        fdb = _db;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View eventItems = inflater.inflate(R.layout.review,parent,false);
        UserAdapter.ViewHolder holder = new UserAdapter.ViewHolder(eventItems);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review rev =revList.get(position);
        holder.txtName.setText(rev.getCreator());
        holder.txtRev.setText(rev.getReview());
        holder.txtDate.setText(rev.getDate());
    }

    @Override
    public int getItemCount() {
        return revList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtDate,txtRev;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtName);
            txtDate =itemView.findViewById(R.id.txtRevDate);
            txtRev=itemView.findViewById(R.id.txtRev);
        }
    }
}
