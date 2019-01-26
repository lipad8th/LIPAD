package com.lipad.lipad;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class fieldSelection extends AppCompatActivity implements classDialog.ExampleDialogListener, View.OnClickListener {

    private static final String TAG = "MainActivity";
    public static boolean fieldListStatus;
    DatabaseHelper databaseHelper;
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

        populateListView();

    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");
        Cursor data = databaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(1));

            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
            listView01.setAdapter(adapter);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card01:
                //Tapping card01
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

    public void addData(String newEntry) {
        boolean insertData = databaseHelper.addData(newEntry);
        if (insertData) {
            toastMessage("Data added.");
            populateListView();
        } else {
            toastMessage("Something went wrong. Please try again.");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void applyTexts(String fieldNameString) {
        addData(fieldNameString);
    }
}
