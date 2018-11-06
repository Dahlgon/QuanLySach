package com.example.admin.quanlysach.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.quanlysach.R;

public class SpinerMonthAdapter extends BaseAdapter {
    private final String[] countryNames;
    private final LayoutInflater inflter;

    public SpinerMonthAdapter(Context context, String[] countryNames) {
        this.countryNames = countryNames;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return countryNames.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"InflateParams", "ViewHolder"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_month, null);
        TextView names =  view.findViewById(R.id.tvMonth);
        names.setText(countryNames[i]);
        return view;
    }
}
