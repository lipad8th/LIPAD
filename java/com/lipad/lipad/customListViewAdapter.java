package com.lipad.lipad;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class customListViewAdapter extends ArrayAdapter<String> {

    private ArrayList<FieldClass> fieldClasses;

    public customListViewAdapter(fieldSelection fieldSelection, int simple_list_item_1, ArrayList<String> listData) {
        super(fieldSelection, simple_list_item_1, listData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*
*           ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_2, android.R.id.text1, list) {
*
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
            TextView text2 = (TextView) view.findViewById(android.R.id.text2);

            text1.setText(persons.get(position).getName());
            text2.setText(persons.get(position).getAge());
            return view;*/

/*TwoLineListItem twoLineListItem;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            twoLineListItem = (TwoLineListItem) inflater.inflate(
                    android.R.layout.simple_list_item_2, null);
        } else {
            twoLineListItem = (TwoLineListItem) convertView;
        }

        TextView text1 = twoLineListItem.getText1();
        TextView text2 = twoLineListItem.getText2();

        text1.setText(persons.get(position).getName());
        text2.setText("" + persons.get(position).getAge());

        return twoLineListItem;*/

        View view = super.getView(position, convertView, parent);

        TextView tv = (TextView) view.findViewById(android.R.id.text1);
        tv.setTextColor(view.getResources().getColor(R.color.colorText));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv.setTypeface(view.getResources().getFont(R.font.ubuntu_medium));
        }

        return view;
    }
}
