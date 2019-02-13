package com.lipad.lipad;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    MiscDatabaseHelper miscDatabaseHelper;
    EditText seedPriceEditText;
    EditText seed2PriceEditText;
    EditText ipAddEditText;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        seedPriceEditText = findViewById(R.id.seedPriceEditText);
        seed2PriceEditText = findViewById(R.id.seed2PriceEditText);
        ipAddEditText = findViewById(R.id.ipAddEditText);
        saveButton = findViewById(R.id.saveButton);


        miscDatabaseHelper = new MiscDatabaseHelper(this);

        Cursor seedData = miscDatabaseHelper.getSeedPrice();
        seedData.moveToNext();
        seedPriceEditText.setHint(seedData.getString(0));

        Cursor seed2Data = miscDatabaseHelper.getSeed2Price();
        seed2Data.moveToNext();
        seed2PriceEditText.setHint(seed2Data.getString(0));

        Cursor ipData = miscDatabaseHelper.getIpAddress();
        ipData.moveToNext();
        ipAddEditText.setHint(ipData.getString(0));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!String.valueOf(seedPriceEditText.getText()).equals("") && !String.valueOf(seed2PriceEditText.getText()).equals("") && !String.valueOf(ipAddEditText.getText()).equals("")) {
                    miscDatabaseHelper.addSeedPrice(String.valueOf(seedPriceEditText.getText()));
                    miscDatabaseHelper.addSeed2Price(String.valueOf(seed2PriceEditText.getText()));
                    miscDatabaseHelper.addIpAddress(String.valueOf(ipAddEditText.getText()));
                    toastMessage("Changes saved.", "positive");
                } else if (!String.valueOf(seedPriceEditText.getText()).equals("")) {
                    miscDatabaseHelper.addSeedPrice(String.valueOf(seedPriceEditText.getText()));
                    toastMessage("Changes saved.", "positive");
                } else if (!String.valueOf(seed2PriceEditText.getText()).equals("")) {
                    miscDatabaseHelper.addSeed2Price(String.valueOf(seed2PriceEditText.getText()));
                    toastMessage("Changes saved.", "positive");
                } else if (!String.valueOf(ipAddEditText.getText()).equals("")) {
                    miscDatabaseHelper.addIpAddress(String.valueOf(ipAddEditText.getText()));
                    toastMessage("Changes saved.", "positive");
                } else {
                    toastMessage("No changes saved.", "negative");
                }
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });


    }

    private void toastMessage(String message, String messageType) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
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
