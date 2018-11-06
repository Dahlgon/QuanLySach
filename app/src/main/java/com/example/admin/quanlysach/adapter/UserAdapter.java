package com.example.admin.quanlysach.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.quanlysach.R;
import com.example.admin.quanlysach.listener.OnDelete;
import com.example.admin.quanlysach.listener.OnEdit;
import com.example.admin.quanlysach.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private final List<User> userList;
    private final OnDelete onDelete;
    private final OnEdit onEdit;

    public UserAdapter(List<User> userList, OnDelete onDelete, OnEdit onEdit) {
        this.userList = userList;
        this.onDelete = onDelete;
        this.onEdit = onEdit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_user,parent,false);
        return new ViewHolder(itemView);
    }

    @SuppressLint({"RecyclerView", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final User user = userList.get(position);



        if(user.getName().length()>20){
            String name = "Tên : "+user.getName().substring(0,20)+"...";
            holder.tvName.setText(name);
        }else{
            holder.tvName.setText("Tên: "+user.getName());
        }
        if(user.getUsername().length()>20){
            String id = "User : "+user.getUsername().substring(0,20)+"...";
            holder.tvUser.setText(id);
        }else{
            holder.tvUser.setText("User: "+user.getUsername());
        }
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete.onDelete(position);
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEdit.onEdit(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (userList == null) return 0;
        return userList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
         final ImageView imgEdit;
         final ImageView imgDelete;
         final TextView tvName;
         final TextView tvUser;

         ViewHolder(View itemView) {
            super(itemView);
            tvUser = itemView.findViewById(R.id.tvUserName_itemUser);
            tvName = itemView.findViewById(R.id.tvName_itemUser);
            imgEdit = itemView.findViewById(R.id.imgEditUser_itemUser);
            imgDelete = itemView.findViewById(R.id.imgDeleteUser_itemUser);
        }

    }
}
