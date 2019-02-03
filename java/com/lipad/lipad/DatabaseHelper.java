package com.lipad.lipad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " TEXT," + COL2 + " INTEGER,"
                + COL3 + " INTEGER," + COL4 + " TEXT," + COL5 + " TEXT," + COL6 + " TEXT," + COL7 + " TEXT)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String fieldNameString, String rowValue, String columnValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, fieldNameString);
        contentValues.put(COL2, rowValue);
        contentValues.put(COL3, columnValue);

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

    public Cursor getCoordinateData(int fieldId, String coordinateX){
        //SELECT coordinateA FROM field_table WHERE ID == 1
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT coordinate" + coordinateX + " FROM field_table WHERE ID == "
                + fieldId;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
