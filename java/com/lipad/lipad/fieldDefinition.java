package com.lipad.lipad;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.TextView;

public class fieldDefinition extends AppCompatActivity {

    private static final String TAG = "fieldDefinition";

    public static int selectedId;
    public static String selectedField;
    public static int selectedRow;
    public static int selectedColumn;
    public int fieldSize;
    DatabaseHelper databaseHelper;
    createFieldView mcreateFieldView;
    GridLayout field01grid;
    Context context;
    FieldDatabaseHelper fieldDatabaseHelper;
    private TextView fieldTextView;
    private TextView fieldSizeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_definition);

        fieldTextView = (TextView) findViewById(R.id.fieldTextView);
        fieldSizeTextView = (TextView) findViewById(R.id.fieldSizeTextView);
        fieldDatabaseHelper = new FieldDatabaseHelper(this);
        field01grid = (GridLayout) findViewById(R.id.field01grid);


        Intent receivedIntent = getIntent();
        selectedId = receivedIntent.getIntExtra("id", -1);
        selectedField = receivedIntent.getStringExtra("name");
        selectedRow = receivedIntent.getIntExtra("rows", 1);
        selectedColumn = receivedIntent.getIntExtra("columns", 1);

        fieldSize = selectedRow * selectedColumn;

        fieldTextView.setText(selectedField);
        fieldSizeTextView.setText(selectedRow + "×" + selectedColumn + " field");

        //initializeDatabase();
        initializeGrid();

    }

    private void initializeGrid() {
        mcreateFieldView = new createFieldView(context);
        field01grid.setColumnCount(selectedColumn);
        for (int i = 0; i < fieldSize; i++) {
            field01grid.addView(mcreateFieldView.button(getApplicationContext(), "X"), i);
        }
    }

    private void initializeDatabase() {

        Log.d(TAG, "populateCells: Displaying data through the array of cells.");
        Cursor data = fieldDatabaseHelper.getData();
        int[][] cellArray = new int[selectedRow][selectedColumn];
        while (data.moveToNext()) {
            int cellState = data.getInt(1);


        }

    }
}
