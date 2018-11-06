package com.example.admin.quanlysach;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Menu extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu,container,false);
        ImageView imgUser = view.findViewById(R.id.imgUser);
        ImageView imgGenre = view.findViewById(R.id.imgGenre);
        ImageView imgBook = view.findViewById(R.id.imgBook);
        ImageView imgBill = view.findViewById(R.id.imgBill);
        ImageView imgBookSelling = view.findViewById(R.id.imgBookSelling);
        ImageView imgStatistic = view.findViewById(R.id.imgStatistic);

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),UserActivity.class));
            }
        });
        imgGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),GenreActivity.class));
            }
        });
        imgBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),BookActivity.class));
            }
        });
        imgBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),BillActivity.class));
            }
        });
        imgBookSelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),TopBook.class));
            }
        });
        imgStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),StatisticActivtiy.class));
            }
        });
        return view;
    }

}
