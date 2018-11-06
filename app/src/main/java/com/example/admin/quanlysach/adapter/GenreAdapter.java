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
import com.example.admin.quanlysach.model.Genre;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder>{
    private List<Genre> genreList;
    private final OnDelete onDelete;
    private final OnEdit onEdit;


    public GenreAdapter(List<Genre> genreList, OnDelete onDelete, OnEdit onEdit) {
        this.genreList = genreList;
        this.onDelete = onDelete;
        this.onEdit = onEdit;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_genre,parent,false);
        return new ViewHolder(itemView);
    }

    @SuppressLint({"RecyclerView", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Genre genre = genreList.get(position);
        if(genre.getName().length()>20){
            String name = "Tên : "+ genre.getName().substring(0,20)+"...";
            holder.tvName.setText(name);
        }else{
            holder.tvName.setText("Tên: "+ genre.getName());
        }
        if(genre.getId().length()>20){
            String id = "Mã : "+ genre.getId().substring(0,20)+"...";
            holder.tvTypeBook_ID.setText(id);
        }else{
            holder.tvTypeBook_ID.setText("Mã: "+ genre.getId());
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
        if (genreList == null) return 0;
        return genreList.size();
    }
    public void changeDataset(List<Genre> items){
        this.genreList = items;
        notifyDataSetChanged();
    }


     class ViewHolder extends RecyclerView.ViewHolder {
         final ImageView imgEdit;
         final ImageView imgDelete;
         final TextView tvName;
         final TextView tvTypeBook_ID;

         ViewHolder(View itemView) {
            super(itemView);
            tvTypeBook_ID = itemView.findViewById(R.id.tvTypeBook_ID_itemGenreID);
            tvName = itemView.findViewById(R.id.tvName_itemGenre);
            imgEdit = itemView.findViewById(R.id.imgEditTypeBook_itemGenre);
            imgDelete = itemView.findViewById(R.id.imgDeleteTypeBook_itemGenre);
        }

    }
}
