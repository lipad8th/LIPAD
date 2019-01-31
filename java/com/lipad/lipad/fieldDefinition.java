package com.lipad.lipad;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import static com.lipad.lipad.createFieldView.buttonX;

public class fieldDefinition extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "fieldDefinition";

    public static int selectedId;
    public static int cellValue;
    public static String selectedField;
    public static int selectedRow;
    public static int selectedColumn;
    public static String buttonText;
    public static int[] cellArray;
    public int fieldSize;
    public float gridWidth;
    public float gridHeight;
    DatabaseHelper databaseHelper;
    createFieldView mcreateFieldView;
    GridLayout field01grid;
    Context context;
    FieldDatabaseHelper fieldDatabaseHelper;
    Button nextButton;
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

        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);


        Intent receivedIntent = getIntent();
        selectedId = receivedIntent.getIntExtra("id", -1);
        selectedField = receivedIntent.getStringExtra("name");
        selectedRow = receivedIntent.getIntExtra("rows", 1);
        selectedColumn = receivedIntent.getIntExtra("columns", 1);

        fieldSize = selectedRow * selectedColumn;

        fieldTextView.setText(selectedField);
        fieldSizeTextView.setText(selectedRow + "×" + selectedColumn + " field");

        initializeDatabase();
        initializeGrid();

    }

    private void initializeGrid() {
        mcreateFieldView = new createFieldView(context);
        //Log.d("fieldDefinition: ", "initializeGrid: " + gridWidth + gridHeight);
        for (int i = 0; i < fieldSize; i++) {
            Log.d("fieldDefinition: ", "initializeGrid: " + cellArray[i]);
            switch (cellArray[i]) {
                case 0:
                    buttonText = "·";
                    break;
                case 1:
                    buttonText = "🌱";
                    break;
                case 2:
                    buttonText = "💧";
                    break;
            }
            field01grid.setColumnCount(selectedColumn);
            field01grid.addView(mcreateFieldView.button(getApplicationContext(), buttonText, (i + 1)), i);
            buttonX.setOnClickListener(this);
        }
    }

    private void initializeDatabase() {
        Log.d(TAG, "populateCells: Displaying data through the array of cells.");
        //public Cursor getData(int fieldId, int row, int column)
        Cursor data = fieldDatabaseHelper.getData(selectedId, selectedRow, selectedColumn);
        cellArray = new int[fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            while (data.moveToNext()) {
                cellArray[i] = data.getInt(0);
            }
        }

    }


    /*
    * @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.card01:
                i = new Intent(this, fieldSelection.class);
                startActivity(i);
                break;
            default:
                break;

        }
    }
    * */
    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.nextButton:
                Toast.makeText(this, "Hello world.", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "Hello din sayo world.", Toast.LENGTH_SHORT).show();
                break;
        }*/
        for (int i = 1; i <= fieldSize; i++) {
            if ((v.getId()) == i) {
                Button button = (Button) findViewById(v.getId());
                String buttonText = String.valueOf(button.getText());
                if (buttonText.contains("·")) {
                    button.setText("🌱");
                } else if (buttonText.contains("🌱")) {
                    button.setText("💧");
                } else if (buttonText.contains("")) {
                    button.setText("·");
                }
                Log.d("fieldDefinition", "onClick:" + buttonText);
            }
        }
    }
}
