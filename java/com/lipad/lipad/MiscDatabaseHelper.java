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
        String CREATE_TBL = "CREATE TABLE IF NOT EXISTS misc_table (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " tomorrowHigh TEXT, tomorrowLow TEXT, nextState TEXT, nextParameter TEXT, nextTime TEXT," +
                " lifetimeSeeds TEXT, lifetimeSeeds2 TEXT, seedPrice TEXT, seed2Price TEXT, ipAddress TEXT)";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_TBL);

        ContentValues contentValues = new ContentValues();
        contentValues.put("tomorrowHigh", "0");
        contentValues.put("tomorrowLow", "0");
        contentValues.put("nextState", "0");
        contentValues.put("nextParameter", "0");
        contentValues.put("nextTime", "0");
        contentValues.put("lifetimeSeeds", "0");
        contentValues.put("lifetimeSeeds2", "0");
        contentValues.put("seedPrice", "0.08");
        contentValues.put("seed2Price", "0.10");
        contentValues.put("ipAddress", "192.168.43.77:21567");


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

    public void addLifetimeSeeds(String lifetimeSeeds) {
        SQLiteDatabase db = this.getWritableDatabase();
        String dataEntry = "UPDATE misc_table SET lifetimeSeeds = \"" + lifetimeSeeds + "\" WHERE ID == 1";
        db.execSQL(dataEntry);
    }

    public void addLifetimeSeeds2(String lifetimeSeeds2) {
        SQLiteDatabase db = this.getWritableDatabase();
        String dataEntry = "UPDATE misc_table SET lifetimeSeeds2 = \"" + lifetimeSeeds2 + "\" WHERE ID == 1";
        db.execSQL(dataEntry);
    }

    public Cursor getLifetimeSeeds() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT lifetimeSeeds FROM misc_table WHERE ID == 1";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getLifetimeSeeds2() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT lifetimeSeeds2 FROM misc_table WHERE ID == 1";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void addSeedPrice(String seedPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        String dataEntry = "UPDATE misc_table SET seedPrice = \"" + seedPrice + "\" WHERE ID == 1";
        db.execSQL(dataEntry);
    }

    public void addSeed2Price(String seed2Price) {
        SQLiteDatabase db = this.getWritableDatabase();
        String dataEntry = "UPDATE misc_table SET seed2Price = \"" + seed2Price + "\" WHERE ID == 1";
        db.execSQL(dataEntry);
    }

    public void addIpAddress(String ipAddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        String dataEntry = "UPDATE misc_table SET ipAddress = \"" + ipAddress + "\" WHERE ID == 1";
        db.execSQL(dataEntry);
    }

    public Cursor getSeedPrice() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT seedPrice FROM misc_table WHERE ID == 1";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getSeed2Price() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT seed2Price FROM misc_table WHERE ID == 1";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getIpAddress() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT ipAddress FROM misc_table WHERE ID == 1";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

}
