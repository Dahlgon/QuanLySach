package com.example.admin.quanlysach;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.admin.quanlysach.R;
import com.example.admin.quanlysach.DAO.StatisticDAO;

import java.util.Objects;

public class StatisticActivtiy extends AppCompatActivity {
    private Toolbar toolbarStatistic;
    private TextView tvStatisticDay;
    private TextView tvStatisticMonth;
    private TextView tvStatisticYear;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);

        initView();
        initAction();
        StatisticDAO statisticDAO = new StatisticDAO(this);
        tvStatisticDay.setText(statisticDAO.getStatisticByDay()+" VNĐ");
        tvStatisticMonth.setText(statisticDAO.getStatisticByMonth()+" VNĐ");
        tvStatisticYear.setText(statisticDAO.getStatisticByYear()+" VNĐ");
    }

    private void initView() {
        toolbarStatistic = findViewById(R.id.toolbarStatistic);
        tvStatisticDay = findViewById(R.id.tvStatisticDay);
        tvStatisticMonth = findViewById(R.id.tvStatisticMonth);
        tvStatisticYear = findViewById(R.id.tvStatisticYear);
        toolbarStatistic = findViewById(R.id.toolbarStatistic);
        setSupportActionBar(toolbarStatistic);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbarStatistic.setTitleTextColor(Color.WHITE);
        toolbarStatistic.setTitle(getString(R.string.title_statistc));
        toolbarStatistic.setNavigationIcon(R.drawable.undo);

    }

    private void initAction() {
        toolbarStatistic.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
