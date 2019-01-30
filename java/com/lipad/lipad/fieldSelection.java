package com.lipad.lipad;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class fieldSelection extends AppCompatActivity implements classDialog.ExampleDialogListener, View.OnClickListener {

    private static final String TAG = "fieldSelection";
    public static boolean fieldListStatus;
    public static int fieldIdValue;
    DatabaseHelper databaseHelper;
    FieldDatabaseHelper fieldDatabaseHelper;
    private CardView card01;
    private ListView listView01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_selection);

        listView01 = (ListView) findViewById(R.id.listView01);
        card01 = (CardView) findViewById(R.id.card01);
        card01.setOnClickListener(this);
        databaseHelper = new DatabaseHelper(this);
        fieldDatabaseHelper = new FieldDatabaseHelper(this);

        populateListView();

    }

    public void populateListView() {

        FieldClass fieldClass;

        Log.d(TAG, "populateListView: Displaying data in the ListView.");
        Cursor data = databaseHelper.getData();
        final ArrayList<String> listData = new ArrayList<>();
        /*final ArrayList<FieldClass> fieldClasses = new ArrayList<FieldClass>();*/
        while (data.moveToNext()) {

            String fieldName = data.getString(1);
            final String rowValue = data.getString(2);
            final String columnValue = data.getString(3);

            String fieldSize = rowValue + "×" + columnValue;

            listData.add(fieldName);
        }

            /*
            String rowValue = data.getString(2);
            String columnValue = data.getString(3);
            String fieldSize = rowValue + " × " + columnValue + " field";

            fieldClass = new FieldClass();
            fieldClass.setName(data.getString(1));
            fieldClass.setSize(fieldSize);
            */


        final ListAdapter adapter = new customListViewAdapter(this, android.R.layout.simple_list_item_1, listData);
        listView01.setAdapter(adapter);

        listView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String fieldName = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on " + fieldName);

                Cursor data = databaseHelper.getItemId(fieldName);
                Cursor rowData = databaseHelper.getRowValue(fieldName);
                Cursor columnData = databaseHelper.getColumnValue(fieldName);
                int fieldID = -1;
                int rowValue = 1;
                int columnValue = 1;
                while (data.moveToNext()) {
                    fieldID = data.getInt(0);
                }
                while (rowData.moveToNext()) {
                    rowValue = rowData.getInt(0);
                }
                while (columnData.moveToNext()) {
                    columnValue = columnData.getInt(0);
                }
                if (fieldID > -1) {
                    Log.d(TAG, "onItemClick: The ID is: " + fieldID);
                    Intent editFieldIntent = new Intent(fieldSelection.this, fieldDefinition.class);
                    editFieldIntent.putExtra("id", fieldID);
                    editFieldIntent.putExtra("name", fieldName);
                    editFieldIntent.putExtra("rows", rowValue);
                    editFieldIntent.putExtra("columns", columnValue);
                    startActivity(editFieldIntent);
                } else {
                    toastMessage("No ID associated with that name", "negative");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card01:
                fieldDialog();
                break;
            default:
                break;

        }
    }

    public void fieldDialog() {
        classDialog mclassDialog = new classDialog();
        mclassDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String fieldNameString, String rowValue, String columnValue) {
        addData(fieldNameString, rowValue, columnValue);
        int rows = Integer.parseInt(rowValue);
        int columns = Integer.parseInt(columnValue);
        //int fieldSize = rows * columns;

        Cursor data = databaseHelper.getItemId(fieldNameString);
        int fieldID = -1;
        while (data.moveToNext()) {
            fieldID = data.getInt(0);
        }
        if (fieldID > -1) {
            Log.d(TAG, "onItemClick: The ID is: " + fieldID);
            fieldIdValue = fieldID;
        }
        initializeField(rows, columns, fieldID);
    }

    public void addData(String fieldNameString, String rowValue, String columnValue) {
        boolean insertData = databaseHelper.addData(fieldNameString, rowValue, columnValue);
        if (insertData) {
            toastMessage("\"" + fieldNameString + "\" added.", "positive");
            populateListView();
        } else {
            toastMessage("Something went wrong. Please try again.", "negative");
        }
    }

    public void initializeField(int rows, int columns, int fieldID) {

        boolean insertField = fieldDatabaseHelper.initializeField(rows, columns, fieldID);
        if (insertField) {
            toastMessage("Cells created.", "positive");
        } else {
            toastMessage("Something went wrong. Please try again.", "negative");
        }
    }

    private void toastMessage(String message, String messageType) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        View toastView = toast.getView();

        TextView text = toastView.findViewById(android.R.id.message);

        switch (messageType) {
            case "positive":
                text.setTextColor(getResources().getColor(R.color.colorAccentDark));
                toastView.getBackground().setColorFilter(getResources().getColor(R.color.colorBackgroundDark), PorterDuff.Mode.SRC_IN);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    text.setTypeface(getResources().getFont(R.font.ubuntu_medium));
                }
                break;
            case "negative":
                text.setTextColor(getResources().getColor(R.color.colorBackgroundDark));
                toastView.getBackground().setColorFilter(getResources().getColor(R.color.colorAccentDark), PorterDuff.Mode.SRC_IN);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    text.setTypeface(getResources().getFont(R.font.ubuntu_medium));
                }
        }

        toast.show();
    }

}
            /*
            ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_2, android.R.id.text1, list) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
            TextView text2 = (TextView) view.findViewById(android.R.id.text2);

            text1.setText(persons.get(position).getName());
            text2.setText(persons.get(position).getAge());
            return view;
            }
            };
            */
            /*
            customListViewAdapter listAdapter = new customListViewAdapter(this, android.R.layout.simple_list_item_1, listData);
            listView01.setAdapter(listAdapter);
            */
            /*
            customListViewAdapter listAdapter = new customListViewAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, fieldClass);
            listView01.setAdapter(listAdapter);/*
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card01:
                fieldDialog();
                break;
            default:
                break;

        }
    }

    public void fieldDialog() {
        classDialog mclassDialog = new classDialog();
        mclassDialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void addData(String fieldNameString, String rowValue, String columnValue) {
        boolean insertData = databaseHelper.addData(fieldNameString, rowValue, columnValue);
        if (insertData) {
            toastMessage("\"" + fieldNameString + "\" added.", "positive");
            populateListView();
        } else {
            toastMessage("Something went wrong. Please try again.", "negative");
        }
    }

    private void toastMessage(String message, String messageType) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        View toastView = toast.getView();

        TextView text = toastView.findViewById(android.R.id.message);

        switch (messageType) {
            case "positive":
                text.setTextColor(getResources().getColor(R.color.colorAccentDark));
                toastView.getBackground().setColorFilter(getResources().getColor(R.color.colorBackgroundDark), PorterDuff.Mode.SRC_IN);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    text.setTypeface(getResources().getFont(R.font.ubuntu_medium));
                }
                break;
            case "negative":
                text.setTextColor(getResources().getColor(R.color.colorBackgroundDark));
                toastView.getBackground().setColorFilter(getResources().getColor(R.color.colorAccentDark), PorterDuff.Mode.SRC_IN);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    text.setTypeface(getResources().getFont(R.font.ubuntu_medium));
                }
        }

        toast.show();
    }

    @Override
    public void applyTexts(String fieldNameString, String rowValue, String columnValue) {
        addData(fieldNameString, rowValue, columnValue);
    }
}*/
