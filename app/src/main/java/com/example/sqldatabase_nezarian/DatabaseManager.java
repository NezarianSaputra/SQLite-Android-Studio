package com.example.sqldatabase_nezarian;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseManager extends DatabaseHelper{
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context c) {
        super(c);
    }

    public void open() {
        database = this.getWritableDatabase();
        database.isOpen();
    }

    public Cursor fetch() {
        String[] columns = new String[]{dbHelper._ID, dbHelper.TITLE, dbHelper.DESC};
        Cursor cursor = database.query(dbHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void insert(String myInputTitle, String myInputDesc) {
        SQLiteDatabase database = this.getWritableDatabase();
        String insert = "INSERT INTO " + dbHelper.TABLE_NAME + " (Title, Description) "
                + "VALUES ('" + myInputTitle + "', '" + myInputDesc + "')";
        Log.e("insert sqlite ", "" + insert);
        database.execSQL(insert);
        database.close();
    }


    public void update(int id, String newTitle, String newDesc) {
        SQLiteDatabase database = this.getWritableDatabase();
        String update = "UPDATE " + dbHelper.TABLE_NAME + " SET "
                + dbHelper.TITLE + "='" + newTitle + "', "
                + dbHelper.DESC + "='" + newDesc + "'"
                + " WHERE " + dbHelper._ID + "=" + "'" + id + "'";
        Log.e("update sqlite ", update);
        database.execSQL(update);
        database.close();
    }

    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String update = "DELETE FROM " + dbHelper.TABLE_NAME + " WHERE "
                + dbHelper._ID + "=" + "'" + id + "'";
        Log.e("update sqlite ", update);
        database.execSQL(update);
        database.close();
    }

    public void dropTable() {
        database = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(database, 0, 0);
        database.close();
    }
}
