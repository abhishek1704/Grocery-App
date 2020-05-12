package com.example.groceryapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.groceryapp.GroceryContract.*;

import androidx.annotation.Nullable;

public class GroceryDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "groceryList.db";
    public static final int DATABASE_VERSION = 1;

    public GroceryDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_GROCERYLIST_TABLE = "CREATE TABLE " +
                GroceryEntry.TABLE_NAME + " (" +
                GroceryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GroceryEntry.NAME_COLUMN + " TEXT NOT NULL, " +
                GroceryEntry.AMOUNT_COLUMN + " INTEGER NOT NULL, " +
                GroceryEntry.TIMESTAMP_COLUMN + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE_GROCERYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GroceryEntry.TABLE_NAME);
        onCreate(db);
    }
}
