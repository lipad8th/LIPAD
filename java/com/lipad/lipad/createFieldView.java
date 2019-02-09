package com.lipad.lipad;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

public class createFieldView extends AppCompatActivity {

    public static String buttonValue;
    public static Button buttonX;
    Context ctx;
    GridLayout field01grid;

    public createFieldView(Context ctx) {
        this.ctx = ctx;
    }

    public Button button(Context context, String text, int index) {

        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(120, 120);
        buttonX = new Button(context);
        buttonX.setLayoutParams(layoutParams);
        buttonX.setTextColor(Color.rgb(0,0,0)); //17170432
        buttonX.setText(String.format(" %s ", text));
        buttonX.setBackgroundResource(R.drawable.cellshape);
        Log.d("createFieldView: ", "createFieldView: " + index);
        buttonX.setId(index);
        buttonValue = (String) buttonX.getText();

        return buttonX;
    }

    public Button buttonY(Context context, String text) {
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final Button button = new Button(context);
        button.setLayoutParams(layoutParams);
        button.setTextSize(10);
        button.setTextColor(Color.rgb(0, 0, 0));
        button.setText(String.format(" %s ", text));
        button.setMaxEms(8);
        return button;
    }


}
