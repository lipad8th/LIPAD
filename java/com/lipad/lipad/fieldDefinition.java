package com.lipad.lipad;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ScrollView;
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
    Button deleteButton;
    Button button0;
    Button button1;
    Button button2;
    ScrollView scrollView01;
    ScrollView scrollView02;
    private TextView fieldTextView;
    private TextView fieldSizeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_definition);

        fieldTextView = findViewById(R.id.fieldTextView);
        fieldSizeTextView = findViewById(R.id.fieldSizeTextView);
        databaseHelper = new DatabaseHelper(this);
        fieldDatabaseHelper = new FieldDatabaseHelper(this);
        field01grid = findViewById(R.id.field01grid);

        nextButton = findViewById(R.id.nextButton);
        deleteButton = findViewById(R.id.deleteButton);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        scrollView01 = findViewById(R.id.scrollView01);
        scrollView02 = findViewById(R.id.scrollView02);

        scrollView01.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v(TAG, "PARENT TOUCH");

                findViewById(R.id.scrollView02).getParent()
                        .requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        scrollView02.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v(TAG, "CHILD TOUCH");

                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k = 1;
                for (int i = 1; i <= selectedRow; i++) {
                    for (int j = 1; j <= selectedColumn; j++) {
                        Button button = findViewById(k);
                        String buttonText = String.valueOf(button.getText());
                        if (buttonText.contains("·")) {
                            fieldDatabaseHelper.addData(selectedId, i, j, 0);
                        } else if (buttonText.contains("🌱")) {
                            fieldDatabaseHelper.addData(selectedId, i, j, 1);
                        } else if (buttonText.contains("💧")) {
                            fieldDatabaseHelper.addData(selectedId, i, j, 2);
                        }
                        Log.d("fieldDefinition", "buttonId" + k);
                        k++;
                    }
                }
                Intent intent = new Intent(fieldDefinition.this, CoordinateAActivity.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteField(selectedId, selectedField);
                fieldDatabaseHelper.removeTable(selectedId);
                Intent intent = new Intent(fieldDefinition.this, MainActivity.class);
                startActivity(intent);
                Intent intent1 = new Intent(fieldDefinition.this, fieldSelection.class);
                startActivity(intent1);
                finishAffinity();
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 1; i <= fieldSize; i++) {
                    Button button = findViewById(i);
                    button.setText("·");

                }

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 1; i <= fieldSize; i++) {
                    Button button = findViewById(i);
                    button.setText("🌱");

                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 1; i <= fieldSize; i++) {
                    Button button = findViewById(i);
                    button.setText("💧");

                }
            }
        });




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
            field01grid.addView(mcreateFieldView.button(getApplicationContext(), buttonText, i + 1), i);
            buttonX.setOnClickListener(this);
        }
    }

    private void initializeDatabase() {
        Log.d(TAG, "populateCells: Displaying data through the array of cells.");
        //public Cursor getData(int fieldId, int row, int column)
        Log.d(TAG, "Id,Row,Col: " + selectedId + " " + selectedRow + " " + selectedColumn);
        /*Cursor data = fieldDatabaseHelper.getData(selectedId, selectedRow, selectedColumn);
        cellArray = new int[fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            while (data.moveToNext()) {
                cellArray[i] = data.getInt(0);
            }
        }*/
        cellArray = new int[fieldSize];
        int k = 0;
        for (int i = 1; i <= selectedRow; i++) {
            for (int j = 1; j <= selectedColumn; j++) {
                Log.d(TAG, "i, j, k: " + i + " " + j + " " + k);
                Cursor data = fieldDatabaseHelper.getData(selectedId, i, j);
                data.moveToFirst();
                cellArray[k] = data.getInt(0);
                k++;
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
                Button button = findViewById(v.getId());
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
        /*if (v.getId() == R.id.nextButton)
        {
            Intent intent = new Intent(this, );
            startActivity(intent);
            int k = 1;
            for (int i = 1; i <= selectedRow; i++) {
                for (int j = 1; j <= selectedColumn; j++) {
                    Button button = (Button) findViewById(k);
                    String buttonText = String.valueOf(button.getText());
                    if (buttonText.contains("·")) {
                        fieldDatabaseHelper.addData(selectedId, i, j, 0);
                    } else if (buttonText.contains("🌱")) {
                        fieldDatabaseHelper.addData(selectedId, i, j, 1);
                    } else if (buttonText.contains("💧")) {
                        fieldDatabaseHelper.addData(selectedId, i, j, 2);
                    }
                    Log.d("fieldDefinition", "buttonId" + k);
                    k++;
                }
            }
        }*/
    }
}
