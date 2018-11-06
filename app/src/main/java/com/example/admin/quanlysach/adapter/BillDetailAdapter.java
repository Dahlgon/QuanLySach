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
import com.example.admin.quanlysach.model.BillDetail;
import com.example.admin.quanlysach.model.Book;
import com.example.admin.quanlysach.DAO.BookDAO;
import java.util.List;

public class BillDetailAdapter extends RecyclerView.Adapter<BillDetailAdapter.ViewHolder>{
    private List<BillDetail> billDetails;
    private final OnDelete onDelete;
    private final OnEdit onEdit;
    private BookDAO bookDAO;

    public BillDetailAdapter(List<BillDetail> billDetails, OnDelete onDelete, OnEdit onEdit) {
        this.billDetails = billDetails;
        this.onDelete = onDelete;
        this.onEdit = onEdit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_bill_detail,parent,false);
        bookDAO = new BookDAO(parent.getContext());
        return new ViewHolder(itemView);
    }

    @SuppressLint({"RecyclerView", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull BillDetailAdapter.ViewHolder holder, final int position) {
        BillDetail billDetail = billDetails.get(position);
        holder.tvQuantity.setText("SL: "+billDetail.getQuantity());
        Book book= bookDAO.getBookByID(billDetail.getBookID());
        holder.tvName.setText("Tên Sách: "+book.getBookName());
        holder.tvTotalMoney.setText("Tổng: "+(Integer.parseInt(book.getPrice())*Integer.parseInt(billDetail.getQuantity()))+" VNĐ");
        holder.tvPrice.setText("Giá: "+book.getPrice()+" VNĐ");
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
        if (billDetails == null) return 0;
        return billDetails.size();
    }
    public void changeDataset(List<BillDetail> items){
        this.billDetails = items;
        notifyDataSetChanged();
    }
     class ViewHolder extends RecyclerView.ViewHolder {
         final ImageView imgDelete;
         final ImageView imgEdit;
         final TextView tvName;
         final TextView tvQuantity;
         final TextView tvPrice;
         final TextView tvTotalMoney;


         ViewHolder(View itemView) {
            super(itemView);
            tvQuantity = itemView.findViewById(R.id.tvQuantity_item_BillDetail);
            tvName = itemView.findViewById(R.id.tvName_item_BillDetail);
            tvPrice =itemView.findViewById(R.id.tvPrice_item_BillDetail);
            tvTotalMoney =itemView.findViewById(R.id.tvTotalMoney_itemBillDetail);
            imgDelete=itemView.findViewById(R.id.imgDelete_item_BillDetail);
            imgEdit =itemView.findViewById(R.id.imgEdit_item_BillDetail);
        }

    }
}
