package com.example.admin.quanlysach;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.admin.quanlysach.R;
import com.example.admin.quanlysach.adapter.TopBookAdapter;
import com.example.admin.quanlysach.adapter.SpinerMonthAdapter;
import com.example.admin.quanlysach.model.Book;
import com.example.admin.quanlysach.DAO.BookDAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TopBook extends AppCompatActivity {
    private Toolbar toolbarBestBookSelling;
    private RecyclerView lvListBestBookSelling;
    private List<Book> bookList;
    private TopBookAdapter bookSellingAdapter;
    private BookDAO bookDAO;
    private Spinner spMonth;
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat dateFormat =new SimpleDateFormat();
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat dateFormat2 =new SimpleDateFormat();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_book);
        initViews();
        initAction();

        final String month[] = new String[]{"Tháng 1","Tháng 2","Tháng 3","Tháng 4","Tháng 5","Tháng 6","Tháng 7",
                "Tháng 8","Tháng 9","Tháng 10","Tháng 11","Tháng 12"};

        dateFormat.applyPattern("MM");
        dateFormat2.applyPattern("yyyy");
        String monthnow = dateFormat.format(new Date());
        final String yearnow = dateFormat2.format(new Date(System.currentTimeMillis()));
        bookDAO = new BookDAO(this);
        SpinerMonthAdapter adapter = new SpinerMonthAdapter(this,month);
        spMonth.setAdapter(adapter);
        spMonth.setSelection(Integer.parseInt(monthnow)-1);
        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String month;
                if(position<9){
                    month = yearnow+"-0"+(position+1);
                }else{
                    month = yearnow+"-"+(position+1);

                }
                bookList = bookDAO.getSachTop10(month);
                bookSellingAdapter = new TopBookAdapter(bookList);
                lvListBestBookSelling.setAdapter(bookSellingAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        lvListBestBookSelling.setLayoutManager(manager);
    }
    private void initViews(){
        toolbarBestBookSelling = findViewById(R.id.toolbarBestBookSelling);
        setSupportActionBar(toolbarBestBookSelling);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbarBestBookSelling.setTitleTextColor(Color.WHITE);
        toolbarBestBookSelling.setTitle(getString(R.string.title_top_book));
        toolbarBestBookSelling.setNavigationIcon(R.drawable.undo);
        spMonth = findViewById(R.id.spMonth);
        lvListBestBookSelling = findViewById(R.id.lvListBestBookSelling);

    }
    private void initAction(){
        toolbarBestBookSelling.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
