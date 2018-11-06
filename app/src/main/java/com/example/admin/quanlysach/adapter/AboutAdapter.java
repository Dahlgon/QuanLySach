package com.example.admin.quanlysach.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.quanlysach.R;
import com.example.admin.quanlysach.model.About;

import java.util.List;

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ViewHolder> {
    private final List<About> aboutList;

    public AboutAdapter(List<About> aboutList) {
        this.aboutList = aboutList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_about, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        About about = aboutList.get(position);
        holder.tvName.setText(about.getName());
        holder.tvDes.setText(about.getDes());
    }

    @Override
    public int getItemCount() {
        if (aboutList == null) return 0;
        return aboutList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
         final TextView tvName;
         final TextView tvDes;

         ViewHolder(View itemView) {
            super(itemView);
            tvDes = itemView.findViewById(R.id.tvDes);
            tvName = itemView.findViewById(R.id.tvName);
        }

    }
}
