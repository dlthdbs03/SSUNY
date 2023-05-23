package com.example.ssuny;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper instance = null;

    private static final String DATABASE_NAME = "search_history.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "search_history";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_KEYWORD = "keyword";

    public static DBHelper getInstance(Context context) {
        if(instance == null) {
            instance = new DBHelper(context);
        }

        return instance;
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_KEYWORD + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertSearchHistory(String keyword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KEYWORD, keyword);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<String> getAllSearchHistory() {
        List<String> searchHistoryList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_KEYWORD + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " DESC";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String keyword = cursor.getString(0);
                    searchHistoryList.add(keyword);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        db.close();

        return searchHistoryList;
    }
}
