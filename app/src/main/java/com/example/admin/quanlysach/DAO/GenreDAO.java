package com.example.admin.quanlysach.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.quanlysach.Constant;
import com.example.admin.quanlysach.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenreDAO implements Constant {

    private final DatabaseManager databaseManager;
    public GenreDAO(Context context) {
        this.databaseManager = new DatabaseManager(context);
    }

    public long insertTypeBook(Genre genre) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_COLUMN_TYPE_BOOK_ID, genre.getId());
        contentValues.put(TB_COLUMN_DESCRIPTION, genre.getDescription());
        contentValues.put(TB_COLUMN_GENRE_NAME, genre.getName());
        contentValues.put(TB_COLUMN_POSITION, genre.getPosition());
        long result = db.insert(GENRE_TABLE, null, contentValues);
        if (Constant.isDEBUG) Log.e("insertGenre", "insertGenre ID : " + genre.getId());
        db.close();
        return result;
    }
    public long deleteTypeBook(String typeId) {

        SQLiteDatabase db = databaseManager.getWritableDatabase();
        long result = db.delete(GENRE_TABLE, TB_COLUMN_TYPE_BOOK_ID + " = ?",
                new String[]{String.valueOf(typeId)});
        db.close();
        return result;

    }
    public long updateTypeBook(Genre genre) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_COLUMN_DESCRIPTION, genre.getDescription());
        contentValues.put(TB_COLUMN_GENRE_NAME, genre.getName());
        contentValues.put(TB_COLUMN_POSITION, genre.getPosition());
        return db.update(GENRE_TABLE, contentValues, TB_COLUMN_TYPE_BOOK_ID + " = ?",
                new String[]{String.valueOf(genre.getId())});
    }
    public List<Genre> getAllTypeBooks() {

        List<Genre> genres = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + GENRE_TABLE;

        SQLiteDatabase db = databaseManager.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(TB_COLUMN_TYPE_BOOK_ID));
                String name = cursor.getString(cursor.getColumnIndex(TB_COLUMN_GENRE_NAME));
                String description = cursor.getString(cursor.getColumnIndex(TB_COLUMN_DESCRIPTION));
                String position = cursor.getString(cursor.getColumnIndex(TB_COLUMN_POSITION));
                Genre genre = new Genre();
                genre.setId(id);
                genre.setName(name);
                genre.setDescription(description);
                genre.setPosition(position);
                genres.add(genre);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return genres;

    }
    public List<Genre> getAllTypeBooksLike(String ID) {

        List<Genre> genres = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + GENRE_TABLE + " WHERE " + TB_COLUMN_TYPE_BOOK_ID + " like '%" + ID + "%'";

        SQLiteDatabase db = databaseManager.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(TB_COLUMN_TYPE_BOOK_ID));
                String name = cursor.getString(cursor.getColumnIndex(TB_COLUMN_GENRE_NAME));
                String description = cursor.getString(cursor.getColumnIndex(TB_COLUMN_DESCRIPTION));
                String position = cursor.getString(cursor.getColumnIndex(TB_COLUMN_POSITION));
                Genre genre = new Genre();
                genre.setId(id);
                genre.setName(name);
                genre.setDescription(description);
                genre.setPosition(position);
                genres.add(genre);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return genres;

    }
    public Genre getTypeBookByID(String typeID) {

        Genre genre =null;

        SQLiteDatabase db = databaseManager.getReadableDatabase();

        Cursor cursor = db.query(GENRE_TABLE, new String[]{TB_COLUMN_TYPE_BOOK_ID, TB_COLUMN_GENRE_NAME, TB_COLUMN_DESCRIPTION, TB_COLUMN_POSITION},
                TB_COLUMN_TYPE_BOOK_ID + "=?", new String[]{typeID},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {

            String id = cursor.getString(cursor.getColumnIndex(TB_COLUMN_TYPE_BOOK_ID));

            String name = cursor.getString(cursor.getColumnIndex(TB_COLUMN_GENRE_NAME));

            String description = cursor.getString(cursor.getColumnIndex(TB_COLUMN_DESCRIPTION));

            String position = cursor.getString(cursor.getColumnIndex(TB_COLUMN_POSITION));

            genre = new Genre(id,name,description,position);
        }
        Objects.requireNonNull(cursor).close();
        return genre;

    }
    public Cursor getTypeBook() {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        return db.rawQuery("Select * from "+ GENRE_TABLE, null);
    }

}
