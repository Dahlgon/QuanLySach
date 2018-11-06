package com.example.admin.quanlysach.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.quanlysach.R;
import com.example.admin.quanlysach.model.Book;
import com.example.admin.quanlysach.DAO.BookDAO;

import java.util.List;

public class TopBookAdapter extends RecyclerView.Adapter<TopBookAdapter.ViewHolder>{
    private final List<Book> bookList;
    private BookDAO bookDAO;

    public TopBookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public TopBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bookDAO = new BookDAO(parent.getContext());
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_top_book,parent,false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TopBookAdapter.ViewHolder holder, int position) {
        Book book = bookList.get(position);
        Book book1=bookDAO.getBookByID(book.getBookID());
        holder.tvName.setText("Tên sách: "+book1.getBookName());
        holder.tvQuantity.setText("Số lượng: "+book.getQuantity());
        holder.tvBook_ID.setText("Mã sách: "+book.getBookID());
    }

    @Override
    public int getItemCount() {
        if (bookList == null) return 0;
        return bookList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
         final TextView tvName;
         final TextView tvQuantity;
         final TextView tvBook_ID;

         ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvBookName_itemBookSelling);
            tvQuantity = itemView.findViewById(R.id.tvQuantity_itemBookSelling);
            tvBook_ID =itemView.findViewById(R.id.tvBookID_itemBookSelling);
        }

    }
}
