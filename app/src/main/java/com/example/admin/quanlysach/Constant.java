package com.example.admin.quanlysach;

public interface Constant {
    boolean isDEBUG = true;
    //User
    String USER_TABLE = "users";
    String COLUMN_USERNAME = "username";
    String COLUMN_PASSWORD = "password";
    String COLUMN_NAME = "name";
    String COLUMN_PHONE_NUMBER = "phone_number";

    String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + "(" +
            COLUMN_USERNAME + " VARCHAR PRIMARY KEY," +
            COLUMN_PASSWORD + " VARCHAR," +
            COLUMN_NAME + " VARCHAR," +
            COLUMN_PHONE_NUMBER + " VARCHAR" +
            ")";
    //Genre
    String GENRE_TABLE = "TypeBooks";
    String TB_COLUMN_TYPE_BOOK_ID = "_id";
    String TB_COLUMN_GENRE_NAME = "TypeName";
    String TB_COLUMN_DESCRIPTION = "Description";
    String TB_COLUMN_POSITION = "Position";

    String CREATE_TYPE_TABLE = "CREATE TABLE IF NOT EXISTS " + GENRE_TABLE + "(" +
            "" + TB_COLUMN_TYPE_BOOK_ID + " VARCHAR PRIMARY KEY," +
            "" + TB_COLUMN_GENRE_NAME + " VARCHAR," +
            "" + TB_COLUMN_DESCRIPTION + " VARCHAR," +
            "" + TB_COLUMN_POSITION + " VARCHAR" +
            ")";

    //Books
    String BOOK_TABLE = "Books";
    String TB_COLUMN_BOOK_ID = "BookID";
    String TB_COLUMN_BOOK_NAME = "BookName";
    String TB_COLUMN_AUTHOR = "Author";
    String TB_COLUMN_NXB = "NXB";
    String TB_COLUMN_PRICE = "Price";
    String TB_COLUMN_QUANTITY = "Quantity";

    String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS " + BOOK_TABLE + "(" +
            "" + TB_COLUMN_BOOK_ID + " VARCHAR PRIMARY KEY," +
            "" + TB_COLUMN_TYPE_BOOK_ID + " VARCHAR," +
            "" + TB_COLUMN_BOOK_NAME + " VARCHAR," +
            "" + TB_COLUMN_AUTHOR + " VARCHAR," +
            "" + TB_COLUMN_NXB + " VARCHAR," +
            "" + TB_COLUMN_PRICE + " VARCHAR," +
            "" + TB_COLUMN_QUANTITY + " VARCHAR" +
            ")";

    //Bill
    String BILL_TABLE = "Bill";
    String TB_COLUMN_BILL_ID = "BillID";
    String TB_COLUMN_BILL_DATE = "BillDate";

    String CREATE_BILL_TABLE = "CREATE TABLE IF NOT EXISTS " + BILL_TABLE + "(" +
            "" + TB_COLUMN_BILL_ID + " VARCHAR PRIMARY KEY," +
            "" + TB_COLUMN_BILL_DATE + " VARCHAR" +
            ")";

    //BillDetai

    String BILL_DETAIL_TABLE = "BillDetail";
    String TB_COLUMN_BILL_DETAIL_ID = "_id";

    String CREATE_BILL_DETAIL_TABLE = "CREATE TABLE IF NOT EXISTS " + BILL_DETAIL_TABLE + "(" +
            "" + TB_COLUMN_BILL_DETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "" + TB_COLUMN_BILL_ID + " VARCHAR," +
            "" + TB_COLUMN_BOOK_ID + " VARCHAR," +
            "" + TB_COLUMN_QUANTITY + " VARCHAR" +
            ")";
}