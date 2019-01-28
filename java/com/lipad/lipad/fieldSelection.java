package com.lipad.lipad;

import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

    public void populateListView() {

        FieldClass fieldClass;

        Log.d(TAG, "populateListView: Displaying data in the ListView.");
        Cursor data = databaseHelper.getData();
        final ArrayList<String> listData = new ArrayList<>();
        /*final ArrayList<FieldClass> fieldClasses = new ArrayList<FieldClass>();*/
        while (data.moveToNext()) {

            listData.add(data.getString(1));

            /*
            String rowValue = data.getString(2);
            String columnValue = data.getString(3);
            String fieldSize = rowValue + " × " + columnValue + " field";

            fieldClass = new FieldClass();
            fieldClass.setName(data.getString(1));
            fieldClass.setSize(fieldSize);
            */


            ListAdapter adapter = new customListViewAdapter(this, android.R.layout.simple_list_item_1, listData);
            listView01.setAdapter(adapter);
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

    @Override
    public void applyTexts(String fieldNameString, String rowValue, String columnValue) {
        addData(fieldNameString, rowValue, columnValue);
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
