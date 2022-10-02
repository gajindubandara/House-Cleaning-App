package com.example.house_cleaning_app.ui.adminViewUsers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.house_cleaning_app.MainActivity;
import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.Temp;
import com.example.house_cleaning_app.model.User;
import com.example.house_cleaning_app.ui.userView.ViewUserFragment;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdminUserAdapter extends RecyclerView.Adapter<com.example.house_cleaning_app.ui.adminViewUsers.AdminUserAdapter.ViewHolder> {
    FirebaseDatabase fdb;
    List<User> userList;

    private Context context;


    public AdminUserAdapter(List<User> users, FirebaseDatabase _db){

        userList = users;
        fdb = _db;
    }

    @NonNull
    @Override
    public com.example.house_cleaning_app.ui.adminViewUsers.AdminUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View eventItems = inflater.inflate(R.layout.user_card,parent,false);
        com.example.house_cleaning_app.ui.adminViewUsers.AdminUserAdapter.ViewHolder holder = new com.example.house_cleaning_app.ui.adminViewUsers.AdminUserAdapter.ViewHolder(eventItems);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.house_cleaning_app.ui.adminViewUsers.AdminUserAdapter.ViewHolder holder, int position) {
        User u =userList.get(position);
        holder.userName.setText(u.getName());
        holder.userEmail.setText(u.getEmail());
        holder.userAddress.setText(u.getAddress());
        holder.userNo.setText(u.getNumber());


        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.setViewUserID(u.getUserNIC());
                ViewUserFragment fragment =new ViewUserFragment();
                FragmentTransaction ft=((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_content_main,fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNo,userAddress,userEmail,userName;
        ImageButton btnView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.userName);
            userAddress=itemView.findViewById(R.id.userAddress);
            userEmail=itemView.findViewById(R.id.userEmail);
            userNo=itemView.findViewById(R.id.userNo);
            btnView=itemView.findViewById(R.id.viewUser);

//            txtDate =itemView.findViewById(R.id.txtRevDate);
//            txtRev=itemView.findViewById(R.id.txtRev);
        }
    }
}
