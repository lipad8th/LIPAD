package com.lipad.lipad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "field_table";
    private static final String COL0 = "ID";
    private static final String COL1 = "field";
    private static final String COL2 = "rows";
    private static final String COL3 = "columns";
    private static final String COL4 = "coordinateA";
    private static final String COL5 = "coordinateB";
    private static final String COL6 = "coordinateC";
    private static final String COL7 = "coordinateD";
    private static final String COL8 = "creationDate";
    private static final String COL9 = "latestDate";
    private static final String COL10 = "altitude";
    private static final String COL11 = "groundSpeed";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " TEXT," + COL2 + " INTEGER,"
                + COL3 + " INTEGER," + COL4 + " TEXT," + COL5 + " TEXT," + COL6 + " TEXT," + COL7 + " TEXT," + COL8 + " TEXT," + COL9 + " TEXT," + COL10 + " TEXT," + COL11 + " TEXT)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String fieldNameString, String rowValue, String columnValue) {
        SQLiteDatabase db = this.getWritableDatabase();

        DateFormat df = new SimpleDateFormat("MM/dd h:mma");
        String dateToday = df.format(Calendar.getInstance().getTime());

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, fieldNameString);
        contentValues.put(COL2, rowValue);
        contentValues.put(COL3, columnValue);
        contentValues.put(COL8, dateToday);
        contentValues.put(COL9, dateToday);

        Log.d(TAG, "addData: Adding " + fieldNameString + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getRowData(int fieldId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID == " + fieldId;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemId(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL0 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getRowValue(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL2 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getColumnValue(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL3 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void addCoordinateData(int fieldId, String coordinateX, String coordinates) {
        //UPDATE field_table SET coordinateA = "1.23,4.56" WHERE ID == 1
        SQLiteDatabase db = this.getWritableDatabase();
        String dataEntry = "UPDATE field_table SET coordinate" + coordinateX + " = \""
                + coordinates + "\" WHERE ID == " + fieldId;
        db.execSQL(dataEntry);
    }

    public Cursor getCoordinateData(int fieldId, String coordinateX) {
        //SELECT coordinateA FROM field_table WHERE ID == 1
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT coordinate" + coordinateX + " FROM field_table WHERE ID == "
                + fieldId;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void addAltitudeData(int fieldId, String altitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        String dataEntry = "UPDATE field_table SET altitude = \"" + altitude + "\" WHERE ID == " + fieldId;
        db.execSQL(dataEntry);
    }

    public Cursor getAltitudeData(int fieldId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT altitude FROM field_table WHERE ID == " + fieldId;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void addGroundSpeedData(int fieldId, String groundSpeed) {
        SQLiteDatabase db = this.getWritableDatabase();
        String dataEntry = "UPDATE field_table SET groundSpeed = \"" + groundSpeed + "\" WHERE ID == " + fieldId;
        db.execSQL(dataEntry);
    }

    public Cursor getGroundSpeedData(int fieldId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT groundSpeed FROM field_table WHERE ID == " + fieldId;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getIdList() {
        //SELECT ID FROM field_table
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT ID, rows, columns FROM field_table";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getNumberOfFields() {
        //SELECT COUNT (*)
        //  FROM field_table
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT COUNT (*) FROM field_table";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /*
    * public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
    * */

    public void deleteField(int fieldId, String fieldName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL0 + " = '"
                + fieldId + "'" + " AND " + COL1 + " = '" + fieldName + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + fieldName + " from database.");
        db.execSQL(query);

    }

    public void addLatestDate(int fieldId, String currentDate) {
        //UPDATE field_table SET latestDate = "yyyy/MM/dd" WHERE ID == 1
        SQLiteDatabase db = this.getWritableDatabase();
        String dataEntry = "UPDATE " + TABLE_NAME + " SET " + COL9 + " = \"" + currentDate + "\" WHERE " + COL0 + " == " + fieldId;
        db.execSQL(dataEntry);
    }

    public Cursor getDates(int fieldId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL8 + ", " + COL9 + " FROM field_table WHERE ID == " + fieldId;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

}
