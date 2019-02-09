package com.lipad.lipad;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    DatabaseHelper databaseHelper;
    FieldDatabaseHelper fieldDatabaseHelper;
    Button sendButton;
    EditText ipAddressEditText;
    Socket myAppSocket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to_drone);

        sendButton = findViewById(R.id.sendButton);
        ipAddressEditText = findViewById(R.id.ipAddressEditText);
        databaseHelper = new DatabaseHelper(this);
        fieldDatabaseHelper = new FieldDatabaseHelper(this);

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
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD = ";" + coordinateA + ";" + coordinateB + ";" + coordinateC + ";" + coordinateD + ";";
                for (int i = 1; i <= fieldDefinition.selectedRow; i++) {
                    for (int j = 1; j <= fieldDefinition.selectedColumn; j++) {
                        Cursor fieldData = fieldDatabaseHelper.getData(fieldDefinition.selectedId, i, j);
                        fieldData.moveToNext();
                        CMD = CMD + String.valueOf(fieldData.getInt(0));
                        if (i * j != fieldDefinition.selectedRow * fieldDefinition.selectedColumn) {
                            CMD = CMD + ";";
                        }

                    }
                }
                Log.d("SendToDrone", "CMD = " + CMD);
                Socket_AsyncTask cmd_send_data = new Socket_AsyncTask();
                cmd_send_data.execute();
            }
        });

    }

    public void getIPandPort() {
        String iPandPort = ipAddressEditText.getText().toString();
        Log.d("MYTEST", "IP String: " + iPandPort);
        String temp[] = iPandPort.split(":");
        wifiModuleIp = temp[0];
        wifiModulePort = Integer.valueOf(temp[1]);
        Log.d("MY TEST", "IP:" + wifiModuleIp);
        Log.d("MY TEST", "PORT:" + wifiModulePort);
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
