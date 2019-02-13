package com.lipad.lipad;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SendToDrone extends AppCompatActivity {

    public static String wifiModuleIp = "";
    public static int wifiModulePort = 0;
    public static String CMD = "0";
    public static String fieldId;
    public static String fieldName;
    public static String fieldRows;
    public static String fieldColumns;
    public static String coordinateA;
    public static String coordinateB;
    public static String coordinateC;
    public static String coordinateD;
    public static String altitudeValue;
    public static String groundSpeedValue;
    int seeds;
    int seeds2;
    DatabaseHelper databaseHelper;
    FieldDatabaseHelper fieldDatabaseHelper;
    MiscDatabaseHelper miscDatabaseHelper;
    Button sendButton;
    TextView connectText;
    EditText ipAddressEditText;
    Socket myAppSocket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to_drone);

        sendButton = findViewById(R.id.sendButton);
        databaseHelper = new DatabaseHelper(this);
        fieldDatabaseHelper = new FieldDatabaseHelper(this);
        miscDatabaseHelper = new MiscDatabaseHelper(this);
        connectText = findViewById(R.id.connectText);

        Cursor data = databaseHelper.getRowData(fieldDefinition.selectedId);
        while (data.moveToNext()) {
            fieldId = String.valueOf(data.getInt(0));
            fieldName = data.getString(1);
            fieldRows = String.valueOf(data.getInt(2));
            fieldColumns = String.valueOf(data.getInt(3));
            coordinateA = data.getString(4);
            coordinateB = data.getString(5);
            coordinateC = data.getString(6);
            coordinateD = data.getString(7);
            altitudeValue = data.getString(10);
            groundSpeedValue = data.getString(11);
        }

        Cursor seedData = miscDatabaseHelper.getLifetimeSeeds();
        seedData.moveToNext();
        seeds = Integer.parseInt(seedData.getString(0));

        Cursor seed2Data = miscDatabaseHelper.getLifetimeSeeds2();
        seed2Data.moveToNext();
        seeds2 = Integer.parseInt(seed2Data.getString(0));

        Cursor ipData = miscDatabaseHelper.getIpAddress();
        ipData.moveToNext();
        connectText.setText("Connect to: " + ipData.getString(0));

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD = ";" + coordinateA + ";" + coordinateB + ";" + coordinateC + ";" + coordinateD + ";" + altitudeValue + ";" + groundSpeedValue + ";";
                for (int i = 1; i <= fieldDefinition.selectedRow; i++) {
                    for (int j = 1; j <= fieldDefinition.selectedColumn; j++) {
                        Cursor fieldData = fieldDatabaseHelper.getData(fieldDefinition.selectedId, i, j);
                        fieldData.moveToNext();
                        CMD = CMD + String.valueOf(fieldData.getInt(0));
                        if (fieldData.getInt(0) == 1) {
                            MainActivity.lifetimeSeeds++;
                            seeds++;
                            Log.d("SendToDrone", "seeds: " + seeds);
                            miscDatabaseHelper.addLifetimeSeeds(String.valueOf(seeds));
                        } else if (fieldData.getInt(0) == 2) {
                            MainActivity.lifetimeWater++;
                            seeds2++;
                            Log.d("SendToDrone", "seeds2: " + seeds2);
                            miscDatabaseHelper.addLifetimeSeeds2(String.valueOf(seeds2));
                        }
                        if (i * j != fieldDefinition.selectedRow * fieldDefinition.selectedColumn) {
                            CMD = CMD + ";";
                        }

                    }
                }
                Log.d("SendToDrone", "lifetimeSeeds/Water = " + MainActivity.lifetimeSeeds + " " + MainActivity.lifetimeWater);
                Log.d("SendToDrone", "CMD = " + CMD);
                Socket_AsyncTask cmd_send_data = new Socket_AsyncTask();
                cmd_send_data.execute();
                Intent intent = new Intent(SendToDrone.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
                toastMessage("Details sent to drone successfully. Drone will now start.", "positive");
            }
        });

    }

    public void getIPandPort() {
        Cursor ipData = miscDatabaseHelper.getIpAddress();
        ipData.moveToNext();
        String iPandPort = ipData.getString(0);
        Log.d("MYTEST", "IP String: " + iPandPort);
        String temp[] = iPandPort.split(":");
        wifiModuleIp = temp[0];
        wifiModulePort = Integer.valueOf(temp[1]);
        Log.d("MY TEST", "IP:" + wifiModuleIp);
        Log.d("MY TEST", "PORT:" + wifiModulePort);
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

    public class Socket_AsyncTask extends AsyncTask<Void, Void, Void> {
        Socket socket;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                InetAddress inetAddress = InetAddress.getByName(SendToDrone.wifiModuleIp);
                socket = new java.net.Socket(inetAddress, SendToDrone.wifiModulePort);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeBytes(CMD);
                dataOutputStream.close();
                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
