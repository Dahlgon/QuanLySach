package com.example.admin.quanlysach.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.quanlysach.Constant;
import com.example.admin.quanlysach.model.Bill;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BillDAO implements Constant {
    private final DatabaseManager databaseManager;

    public BillDAO(Context context) {
        databaseManager = new DatabaseManager(context);
    }

    public long insertBill(Bill bill) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_COLUMN_BILL_ID, bill.getBill_ID());
        contentValues.put(TB_COLUMN_BILL_DATE, bill.getDate());
        long result = db.insert(BILL_TABLE, null, contentValues);
        if (Constant.isDEBUG) Log.e("insertBill", "insertBill ID : " +bill.getBill_ID());
        db.close();
        return result;
    }
    public long deleteBill(String billID) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        long result = db.delete(BILL_TABLE, TB_COLUMN_BILL_ID + " = ?",
                new String[]{String.valueOf(billID)});
        db.close();
        return result;

    }
    public long updateBill(Bill bill) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_COLUMN_BILL_ID, bill.getBill_ID());
        contentValues.put(TB_COLUMN_BILL_DATE, bill.getDate());
        return db.update(BILL_TABLE, contentValues, TB_COLUMN_BILL_ID + " = ?",
                new String[]{String.valueOf(bill.getBill_ID())});
    }
    public List<Bill> getAllBills() {

        List<Bill> bills = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + BILL_TABLE;

        SQLiteDatabase db = databaseManager.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String billID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BILL_ID));
                long billDate = cursor.getLong(cursor.getColumnIndex(TB_COLUMN_BILL_DATE));
                Bill bill = new Bill();
                bill.setBill_ID(billID);
                bill.setDate(billDate);
                bills.add(bill);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return bills;

    }
    public List<Bill> getAllBillsLike(String ID) {

        List<Bill> bills = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + BILL_TABLE +" WHERE "+TB_COLUMN_BILL_ID+" like '%"+ID+"%'";
        Log.e("getAllBillsLike",selectQuery);
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()) {
            do {
                String billID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BILL_ID));
                long billDate = cursor.getLong(cursor.getColumnIndex(TB_COLUMN_BILL_DATE));
                Bill bill = new Bill();
                bill.setBill_ID(billID);
                bill.setDate(billDate);
                bills.add(bill);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return bills;

    }
    public Bill getBillByID(String billID) {
        Bill bill =null;
        SQLiteDatabase db = databaseManager.getReadableDatabase();

        Cursor cursor = db.query(BILL_TABLE, new String[]{TB_COLUMN_BILL_ID, TB_COLUMN_BILL_DATE},
                TB_COLUMN_BILL_ID + "=?", new String[]{billID},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String ID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BILL_ID));
            long date = cursor.getLong(cursor.getColumnIndex(TB_COLUMN_BILL_DATE));
            bill = new Bill(ID,date);
        }
        Objects.requireNonNull(cursor).close();
        return bill;
    }

}
