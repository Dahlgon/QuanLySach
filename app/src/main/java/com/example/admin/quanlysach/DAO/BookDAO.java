package com.example.admin.quanlysach.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.quanlysach.Constant;
import com.example.admin.quanlysach.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookDAO implements Constant {
    private final DatabaseManager databaseManager;

    public BookDAO(Context context) {
        this.databaseManager = new DatabaseManager(context);
    }

    public long insertBook(Book book) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_COLUMN_BOOK_ID, book.getBookID());
        contentValues.put(TB_COLUMN_BOOK_NAME, book.getBookName());
        contentValues.put(TB_COLUMN_TYPE_BOOK_ID, book.getGenre_ID());
        contentValues.put(TB_COLUMN_AUTHOR, book.getAuthor());
        contentValues.put(TB_COLUMN_NXB, book.getNXB());
        contentValues.put(TB_COLUMN_PRICE, book.getPrice());
        contentValues.put(TB_COLUMN_QUANTITY, book.getQuantity());
        long result = db.insert(BOOK_TABLE, null, contentValues);
        if (Constant.isDEBUG) Log.e("insertBook", "insertBook ID : " + book.getBookID());
        db.close();
        return result;
    }

    public long deleteBook(String bookID) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        long result = db.delete(BOOK_TABLE, TB_COLUMN_BOOK_ID + " = ?",
                new String[]{String.valueOf(bookID)});
        db.close();
        return result;

    }

    public long updateBook(Book book) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_COLUMN_BOOK_NAME, book.getBookName());
        contentValues.put(TB_COLUMN_AUTHOR, book.getAuthor());
        contentValues.put(TB_COLUMN_TYPE_BOOK_ID, book.getGenre_ID());
        contentValues.put(TB_COLUMN_NXB, book.getNXB());
        contentValues.put(TB_COLUMN_PRICE, book.getPrice());
        contentValues.put(TB_COLUMN_QUANTITY, book.getQuantity());
        return db.update(BOOK_TABLE, contentValues, TB_COLUMN_BOOK_ID + " = ?",
                new String[]{String.valueOf(book.getBookID())});
    }

    public List<Book> getAllBooks() {

        List<Book> books = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + BOOK_TABLE;

        SQLiteDatabase db = databaseManager.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String bookID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BOOK_ID));
                String bookName = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BOOK_NAME));
                String author = cursor.getString(cursor.getColumnIndex(TB_COLUMN_AUTHOR));
                String nxb = cursor.getString(cursor.getColumnIndex(TB_COLUMN_NXB));
                String price = cursor.getString(cursor.getColumnIndex(TB_COLUMN_PRICE));
                String quantity = cursor.getString(cursor.getColumnIndex(TB_COLUMN_QUANTITY));
                String type_Book_ID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_TYPE_BOOK_ID));
                Book book = new Book();
                book.setBookID(bookID);
                book.setBookName(bookName);
                book.setAuthor(author);
                book.setNXB(nxb);
                book.setPrice(price);
                book.setQuantity(quantity);
                book.setGenre_ID(type_Book_ID);
                books.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return books;

    }

    public List<Book> getAllBooksLike(String ID) {

        List<Book> books = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + BOOK_TABLE + " WHERE " + TB_COLUMN_BOOK_ID + " like '%" + ID + "%'";

        SQLiteDatabase db = databaseManager.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String bookID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BOOK_ID));
                String bookName = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BOOK_NAME));
                String author = cursor.getString(cursor.getColumnIndex(TB_COLUMN_AUTHOR));
                String nxb = cursor.getString(cursor.getColumnIndex(TB_COLUMN_NXB));
                String price = cursor.getString(cursor.getColumnIndex(TB_COLUMN_PRICE));
                String quantity = cursor.getString(cursor.getColumnIndex(TB_COLUMN_QUANTITY));
                String type_Book_ID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_TYPE_BOOK_ID));
                Book book = new Book();
                book.setBookID(bookID);
                book.setBookName(bookName);
                book.setAuthor(author);
                book.setNXB(nxb);
                book.setPrice(price);
                book.setQuantity(quantity);
                book.setGenre_ID(type_Book_ID);
                books.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return books;

    }

    public List<Book> getAllBooksByIDTypeBook(String ID) {

        List<Book> books = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + BOOK_TABLE + " WHERE " + TB_COLUMN_TYPE_BOOK_ID + " = ?";

        SQLiteDatabase db = databaseManager.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{ID});
        if (cursor.moveToFirst()) {
            do {
                String bookID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BOOK_ID));
                String bookName = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BOOK_NAME));
                String author = cursor.getString(cursor.getColumnIndex(TB_COLUMN_AUTHOR));
                String nxb = cursor.getString(cursor.getColumnIndex(TB_COLUMN_NXB));
                String price = cursor.getString(cursor.getColumnIndex(TB_COLUMN_PRICE));
                String quantity = cursor.getString(cursor.getColumnIndex(TB_COLUMN_QUANTITY));
                String type_Book_ID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_TYPE_BOOK_ID));
                Book book = new Book();
                book.setBookID(bookID);
                book.setBookName(bookName);
                book.setAuthor(author);
                book.setNXB(nxb);
                book.setPrice(price);
                book.setQuantity(quantity);
                book.setGenre_ID(type_Book_ID);
                books.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return books;

    }

    public Book getBookByID(String bookID) {

        Book book = null;

        SQLiteDatabase db = databaseManager.getReadableDatabase();

        Cursor cursor = db.query(BOOK_TABLE, new String[]{TB_COLUMN_BOOK_ID,
                        TB_COLUMN_TYPE_BOOK_ID,
                        TB_COLUMN_BOOK_NAME,
                        TB_COLUMN_AUTHOR,
                        TB_COLUMN_NXB,
                        TB_COLUMN_PRICE,
                        TB_COLUMN_QUANTITY},
                TB_COLUMN_BOOK_ID + "=?", new String[]{bookID},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {

            String ID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BOOK_ID));
            String bookName = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BOOK_NAME));
            String author = cursor.getString(cursor.getColumnIndex(TB_COLUMN_AUTHOR));
            String nxb = cursor.getString(cursor.getColumnIndex(TB_COLUMN_NXB));
            String price = cursor.getString(cursor.getColumnIndex(TB_COLUMN_PRICE));
            String quantity = cursor.getString(cursor.getColumnIndex(TB_COLUMN_QUANTITY));
            String type_Book_ID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_TYPE_BOOK_ID));

            book = new Book(ID, bookName, type_Book_ID, author, nxb, price, quantity);
        }
        Objects.requireNonNull(cursor).close();
        return book;

    }

    public List<Book> getSachTop10(String month) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        List<Book> listBook = new ArrayList<>();
        String sSQL = "SELECT BookID, SUM(Quantity) as quantity FROM BillDetail INNER JOIN Bill " +
                "ON Bill.BillID = BillDetail.BillID WHERE strftime('%Y-%m', " + TB_COLUMN_BILL_DATE + "/ 1000, 'unixepoch')  = '" + month + "'"+
                "GROUP BY BookID ORDER BY quantity DESC LIMIT 10";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Book s = new Book();
            s.setBookID(c.getString(c.getColumnIndex(TB_COLUMN_BOOK_ID)));
            s.setQuantity(c.getString(c.getColumnIndex("quantity")));
            s.setPrice("");
            s.setBookName("");
            s.setNXB("");
            s.setNXB("");
            listBook.add(s);
            c.moveToNext();
        }
        c.close();
        return listBook;
    }

}
