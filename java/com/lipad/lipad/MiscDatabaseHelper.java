package com.lipad.lipad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MiscDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MiscDb.sqlite";
    public static final int DATABASE_VERSION = 1;
    public static String columnStack = "";
    public static long result;

    public MiscDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean initializeTable() {
        String CREATE_TBL = "CREATE TABLE IF NOT EXISTS misc_table (ID INTEGER PRIMARY KEY AUTOINCREMENT, tomorrowHigh TEXT, tomorrowLow TEXT, nextState TEXT, nextParameter TEXT, nextTime TEXT)";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_TBL);

        ContentValues contentValues = new ContentValues();
        contentValues.put("tomorrowHigh", "0");
        contentValues.put("tomorrowLow", "0");
        contentValues.put("nextState", "0");
        contentValues.put("nextParameter", "0");
        contentValues.put("nextTime", "0");


        result = db.insertWithOnConflict("misc_table", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

        return result != -1;
    }

    public Cursor getTomorrowData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT tomorrowHigh, tomorrowLow FROM misc_table WHERE ID == 1";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getNextData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT nextState, nextParameter, nextTime FROM misc_table WHERE ID == 1";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void addTomorrowLow(String tomorrowLow) {
        SQLiteDatabase db = this.getWritableDatabase();
        String dataEntry = "UPDATE misc_table SET tomorrowLow = \"" + tomorrowLow + "\" WHERE ID == 1";
        db.execSQL(dataEntry);
    }

    public void addTomorrowHigh(String tomorrowHigh) {
        SQLiteDatabase db = this.getWritableDatabase();
        String dataEntry = "UPDATE misc_table SET tomorrowHigh = \"" + tomorrowHigh + "\" WHERE ID == 1";
        db.execSQL(dataEntry);
    }

    public void addNextState(String nextState) {
        SQLiteDatabase db = this.getWritableDatabase();
        String dataEntry = "UPDATE misc_table SET nextState = \"" + nextState + "\" WHERE ID == 1";
        db.execSQL(dataEntry);
    }

    public void addNextParameter(String nextParameter) {
        SQLiteDatabase db = this.getWritableDatabase();
        String dataEntry = "UPDATE misc_table SET nextParameter = \"" + nextParameter + "\" WHERE ID == 1";
        db.execSQL(dataEntry);
    }

    public void addNextTime(String nextTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        String dataEntry = "UPDATE misc_table SET nextTime = \"" + nextTime + "\" WHERE ID == 1";
        db.execSQL(dataEntry);
    }

}
