package com.lipad.lipad;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewCustomListViewAdapter extends ArrayAdapter<String> {

    private ArrayList<FieldClass> fieldClasses;

    public ViewCustomListViewAdapter(ViewFieldSelection viewFieldSelection, int simple_list_item_1, ArrayList<String> listData) {
        super(viewFieldSelection, simple_list_item_1, listData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        TextView tv = (TextView) view.findViewById(android.R.id.text1);
        tv.setTextColor(view.getResources().getColor(R.color.colorText));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv.setTypeface(view.getResources().getFont(R.font.ubuntu_medium));
        }

        return view;
    }
}
