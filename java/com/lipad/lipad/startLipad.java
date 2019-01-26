package com.lipad.lipad;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

//TODO: Create SQLite database.
//TODO: Keep custom dialog layout and all corresponding OnClickListeners.
//TODO: On database, store field sizes, cell states, and general stats.

public class startLipad extends AppCompatActivity implements classDialog.ExampleDialogListener {
    public static int buttonIndex;
    Button field01button01;
    TextView field02text;
    TextView field03text;
    Context context;
    createFieldView mcreateFieldView;
    private GridLayout field01grid;
    public static int viewIteration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_lipad);

        field01button01 = (Button) findViewById(R.id.field01button01);
        field02text = (TextView) findViewById(R.id.field02text);
        field03text = (TextView) findViewById(R.id.field03text);
        field01grid = (GridLayout) findViewById(R.id.field01grid);

        field01button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonIndex = 1;
                viewIteration++;
                fieldDialog();
            }
        });

    }

    public void fieldDialog() {
        classDialog mclassDialog = new classDialog();
        mclassDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String fieldNameString) {

    }
}
