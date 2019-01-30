package com.lipad.lipad;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

public class createFieldView extends AppCompatActivity {

    Context ctx;
    GridLayout field01grid;

    public createFieldView(Context ctx) {
        this.ctx = ctx;
    }

    public Button button(Context context, String text) {
        /*if (startLipad.viewIteration > 1) {
            field01grid = findViewById(R.id.field01grid);
            ViewGroup layout = (ViewGroup) field01grid;
            field01grid.removeAllViews();
        }*/
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT);
        final Button button = new Button(context);
        button.setLayoutParams(layoutParams);
        button.setTextColor(Color.rgb(0, 0, 0));
        button.setText(String.format(" %s ", text));
        return button;
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
