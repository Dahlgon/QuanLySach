package com.example.admin.quanlysach.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.quanlysach.Constant;
import com.example.admin.quanlysach.model.BillDetail;

import java.util.ArrayList;
import java.util.List;

public class BillDetailDAO  implements Constant {
    private final DatabaseManager databaseManager;

    public BillDetailDAO(Context context) {
        databaseManager = new DatabaseManager(context);

    }

    public long insertBillDetail(String billID,String bookID,String Quantity) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_COLUMN_BILL_ID,billID );
        contentValues.put(TB_COLUMN_BOOK_ID,bookID );
        contentValues.put(TB_COLUMN_QUANTITY,Quantity);
        long result = db.insert(BILL_DETAIL_TABLE, null, contentValues);
        if (Constant.isDEBUG) Log.e("insertBillDetail", "insertBillDetail ID : " +result);
        db.close();
        return result;
    }
    public long deleteBillDetail(int billDetailID) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        long result = db.delete(BILL_DETAIL_TABLE, TB_COLUMN_BILL_DETAIL_ID + " = ?",
                new String[]{String.valueOf(billDetailID)});
        db.close();
        return result;

    }
    public long updateBillDetail(int billDetailID,String billID,String bookID,String Quantity) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_COLUMN_BILL_ID,billID );
        contentValues.put(TB_COLUMN_BOOK_ID,bookID );
        contentValues.put(TB_COLUMN_QUANTITY,Quantity);
        return db.update(BILL_DETAIL_TABLE, contentValues, TB_COLUMN_BILL_DETAIL_ID + " = ?",
                new String[]{String.valueOf(billDetailID)});
    }
    public List<BillDetail> getAllBillDetailsByBillID(String ID) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        List<BillDetail> billDetails = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + BILL_DETAIL_TABLE+" WHERE "+TB_COLUMN_BILL_ID+ " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{ID});
        if (cursor.moveToFirst()) {
            do {
                Integer billDetailID = cursor.getInt(cursor.getColumnIndex(TB_COLUMN_BILL_DETAIL_ID));
                String billID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BILL_ID));
                String quantity = cursor.getString(cursor.getColumnIndex(TB_COLUMN_QUANTITY));
                String bookID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BOOK_ID));
                BillDetail billDetail = new BillDetail();
                billDetail.setBillDetailID(billDetailID);
                billDetail.setBillID(billID);
                billDetail.setBookID(bookID);
                billDetail.setQuantity(quantity);
                billDetails.add(billDetail);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return billDetails;
    }
    public List<BillDetail> getAllBillDetailsByBookID(String ID) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        List<BillDetail> billDetails = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + BILL_DETAIL_TABLE+" WHERE "+TB_COLUMN_BOOK_ID+ " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{ID});
        if (cursor.moveToFirst()) {
            do {
                Integer billDetailID = cursor.getInt(cursor.getColumnIndex(TB_COLUMN_BILL_DETAIL_ID));
                String billID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BILL_ID));
                String quantity = cursor.getString(cursor.getColumnIndex(TB_COLUMN_QUANTITY));
                String bookID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BOOK_ID));
                BillDetail billDetail = new BillDetail();
                billDetail.setBillDetailID(billDetailID);
                billDetail.setBillID(billID);
                billDetail.setBookID(bookID);
                billDetail.setQuantity(quantity);
                billDetails.add(billDetail);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return billDetails;
    }
}