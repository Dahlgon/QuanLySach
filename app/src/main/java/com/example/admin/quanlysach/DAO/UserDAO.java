package com.example.admin.quanlysach.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.quanlysach.model.User;

import java.util.Objects;

public class UserDAO {
    private final SQLiteDatabase database;

    public UserDAO(Context context) {
       DatabaseManager databaseManager =new DatabaseManager(context);
       database= databaseManager.getWritableDatabase();
    }
    public int insertUser(String user, String pass, String name, String sdt) {
        ContentValues values = new ContentValues();
        values.put(DatabaseManager.COLUMN_USERNAME,user);
        values.put(DatabaseManager.COLUMN_PASSWORD,pass);
        values.put(DatabaseManager.COLUMN_NAME,name);
        values.put(DatabaseManager.COLUMN_PHONE_NUMBER,sdt);
        try {
            if( database.insert(DatabaseManager.USER_TABLE,null,values)==-1){
                return -1;
            }
        }catch (Exception e){
            Log.e("Lỗi","Lỗi");
        }
        return 1;
    }
    public int updateUser(String user,String name,String sdt) {
        ContentValues values = new ContentValues();
        values.put(DatabaseManager.COLUMN_NAME,name);
        values.put(DatabaseManager.COLUMN_PHONE_NUMBER,sdt);
        int result=database.update(DatabaseManager.USER_TABLE, values, DatabaseManager.COLUMN_USERNAME +" = ?", new String[]{user});
        if (result == 0){
            return -1;
        }
        return 1;
    }
    public int updatePassWord(String user,String password) {
        ContentValues values = new ContentValues();
        values.put(DatabaseManager.COLUMN_PASSWORD,password);
        int result = database.update(DatabaseManager.USER_TABLE, values, DatabaseManager.COLUMN_USERNAME +" = ?", new String[]{user});
        if (result == 0){
            return -1;
        }
        return 1;
    }
    public Cursor getUser() {
        return database.rawQuery("Select * from "+ DatabaseManager.USER_TABLE, null);
    }
    public int deleteUser(String user) {
        int result=database.delete(DatabaseManager.USER_TABLE, DatabaseManager.COLUMN_USERNAME + " = ?",new String[]{user});
        if (result == 0){
            return -1;
        }
        return 1;
    }
    public User getUser(String username) {
        User user = null;
        Cursor cursor = database.query(DatabaseManager.USER_TABLE, new String[]{DatabaseManager.COLUMN_USERNAME, DatabaseManager.COLUMN_PASSWORD, DatabaseManager.COLUMN_NAME, DatabaseManager.COLUMN_PHONE_NUMBER}, DatabaseManager.COLUMN_USERNAME + "=?", new String[]{username}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {

            String user_name = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_USERNAME));

            String password = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_PASSWORD));

            String name = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_NAME));

            String phoneNumber = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_PHONE_NUMBER));

            user = new User(user_name, password, name, phoneNumber);
        }
        Objects.requireNonNull(cursor).close();
        return user;
    }
}
