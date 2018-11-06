package com.example.admin.quanlysach.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.admin.quanlysach.Constant;

public class DatabaseManager extends SQLiteOpenHelper implements Constant {
    private static final String DATABASE_NAME = "BookManager";
    private static final int DATABASE_VERSION = 1;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_TYPE_TABLE);
        sqLiteDatabase.execSQL(CREATE_BOOK_TABLE);
        sqLiteDatabase.execSQL(CREATE_BILL_TABLE);
        sqLiteDatabase.execSQL(CREATE_BILL_DETAIL_TABLE);
        if (isDEBUG) Log.e("CREATE_USER_TABLE", CREATE_USER_TABLE);
        if (isDEBUG) Log.e("CREATE_TYPE_TABLE", CREATE_TYPE_TABLE);
        if (isDEBUG) Log.e("CREATE_BOOK_TABLE", CREATE_BOOK_TABLE);
        if(isDEBUG)Log.e("CREATE_BILL_TABLE",CREATE_BILL_TABLE);
        if(isDEBUG)Log.e("CREATE_BILL_DETAIL",CREATE_BILL_DETAIL_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GENRE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BOOK_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BILL_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BILL_DETAIL_TABLE);
        onCreate(sqLiteDatabase);
    }
}
