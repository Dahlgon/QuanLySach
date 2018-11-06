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
import com.example.admin.quanlysach.listener.OnClick;
import com.example.admin.quanlysach.listener.OnDelete;
import com.example.admin.quanlysach.listener.OnEdit;
import com.example.admin.quanlysach.model.Bill;
import java.util.Date;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    private List<Bill> billList;
    private final OnDelete onDelete;
    private final OnClick onClick;
    private final OnEdit onEdit;

    public BillAdapter(List<Bill> billList, OnDelete onDelete, OnClick onClick, OnEdit onEdit) {
        this.billList = billList;
        this.onDelete = onDelete;
        this.onClick = onClick;
        this.onEdit = onEdit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_bill, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint({"RecyclerView", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Bill bill = billList.get(position);
        holder.tvBill_ID.setText("Mã: " + bill.getBill_ID());
        String date = new Date(bill.getDate()).toString();
        if (date.length() > 30) {
            holder.tvDate.setText(date.substring(0, 30) + "...");
        } else {
            holder.tvDate.setText(date);
        }
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete.onDelete(position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(bill.getBill_ID());
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
        if (billList == null) return 0;
        return billList.size();
    }

    public void changeDataset(List<Bill> items) {
        this.billList = items;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgDelete;
        final ImageView imgEdit;
        final TextView tvDate;
        final TextView tvBill_ID;


        ViewHolder(View itemView) {
            super(itemView);
            tvBill_ID = itemView.findViewById(R.id.tvBill_ID_itemBill);
            tvDate = itemView.findViewById(R.id.tvDate_itemBill);
            imgDelete = itemView.findViewById(R.id.imgDelete_itemBill);
            imgEdit = itemView.findViewById(R.id.imgEdit_itemBill);
        }

    }
}
