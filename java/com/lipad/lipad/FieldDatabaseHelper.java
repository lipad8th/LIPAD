package com.lipad.lipad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FieldDatabaseHelper extends SQLiteOpenHelper {

    public static final String TBL_A = "table_a";
    public static final String COL0_A = "ID_a";
    public static final String COL1_A = "state_a";
    public static final String TBL_B = "table_b";
    public static final String COL0_B = "ID_b";
    public static final String COL1_B = "state_b";
    private final static String DATABASE_NAME = "fieldsDb.sqlite";
    private static final int DATABASE_VERSION = 1;
    public static String columnStack = "";
    public static String arrayStack = "";
    public static ContentValues contentValues = new ContentValues();
    public static long result;
    private String CREATE_TBL_A = "CREATE TABLE " + TBL_A + " (" + COL0_A + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL1_A + " INTEGER)";
    private String CREATE_TBL_B = "CREATE TABLE " + TBL_B + " (" + COL0_B + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL1_B + " INTEGER)";

    public FieldDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean initializeField(int rows, int columns, int fieldID) {
        for (int i = 1; i <= columns; i++) {
            if (i < columns)
                columnStack = new StringBuilder().append(columnStack).append("col").append(i).append(" INTEGER, ").toString();
            else
                columnStack = new StringBuilder().append(columnStack).append("col").append(i).append(" INTEGER").toString();
        }
        Log.d("fieldDatabaseHelper", "field: " + columnStack);
        String CREATE_TBL = "CREATE TABLE field" + fieldID + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + columnStack + ")";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_TBL);
        columnStack = "";
        for (int i = 1; i <= rows; i++) {
            SQLiteDatabase dbx = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();

            for (int j = 1; j <= columns; j++) {
                contentValues.put("col" + j, 0);
            }

            result = dbx.insertWithOnConflict("field" + fieldID, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
            dbx.close();
        }

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getData(int fieldId, int row, int column) {
        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "SELECT * FROM " + "field" + fieldId;
        String query = "SELECT col" + column + " FROM field" + fieldId + " WHERE ID == " + row;
        //SELECT col3 FROM field1 WHERE ID == 4
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void addData(int fieldId, int row, int column, int cellValue){
        //UPDATE field1 SET col2 = 2 WHERE ID == 3
        SQLiteDatabase db = this.getWritableDatabase();
        String dataEntry = "UPDATE field" + fieldId + " SET col" + column + " = " + cellValue + " WHERE ID == " + row;
        db.execSQL(dataEntry);
    }

}
