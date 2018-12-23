package com.lipad.lipad;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {
    Button weatherButton01;
    Button weatherButton02;
    Button darkSkyDisclaimer;
    public static TextView weatherSub01;
    public static TextView weatherSub11;
    public static TextView weatherSub12;
    public static TextView weatherSub13;
    public static TextView weatherSub14;
    public static TextView weatherSub15;
    public static TextView weatherTemp01;
    public static TextView weatherTemp11;
    public static TextView weatherTemp12;
    public static TextView weatherTemp13;
    public static TextView weatherTemp14;
    public static TextView weatherTemp15;
    public static AppCompatImageView weatherImage01;
    public static AppCompatImageView weatherImage11;
    public static AppCompatImageView weatherImage12;
    public static AppCompatImageView weatherImage13;
    public static AppCompatImageView weatherImage14;
    public static AppCompatImageView weatherImage15;
    public static TextView weatherTime11;
    public static TextView weatherTime12;
    public static TextView weatherTime13;
    public static TextView weatherTime14;
    public static TextView weatherTime15;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherButton01 = (Button) findViewById(R.id.weatherButton01);
        weatherButton02 = (Button) findViewById(R.id.weatherButton02);
        darkSkyDisclaimer = (Button) findViewById(R.id.darkSkyDisclaimer);
        weatherSub01 = (TextView) findViewById(R.id.weatherSub01);
        weatherSub11 = (TextView) findViewById(R.id.weatherSub11);
        weatherSub12 = (TextView) findViewById(R.id.weatherSub12);
        weatherSub13 = (TextView) findViewById(R.id.weatherSub13);
        weatherSub14 = (TextView) findViewById(R.id.weatherSub14);
        weatherSub15 = (TextView) findViewById(R.id.weatherSub15);
        weatherTemp01 = (TextView) findViewById(R.id.weatherTemp01);
        weatherTemp11 = (TextView) findViewById(R.id.weatherTemp11);
        weatherTemp12 = (TextView) findViewById(R.id.weatherTemp12);
        weatherTemp13 = (TextView) findViewById(R.id.weatherTemp13);
        weatherTemp14 = (TextView) findViewById(R.id.weatherTemp14);
        weatherTemp15 = (TextView) findViewById(R.id.weatherTemp15);
        weatherImage01 = (AppCompatImageView) findViewById(R.id.weatherImage01);
        weatherImage11 = (AppCompatImageView) findViewById(R.id.weatherImage11);
        weatherImage12 = (AppCompatImageView) findViewById(R.id.weatherImage12);
        weatherImage13 = (AppCompatImageView) findViewById(R.id.weatherImage13);
        weatherImage14 = (AppCompatImageView) findViewById(R.id.weatherImage14);
        weatherImage15 = (AppCompatImageView) findViewById(R.id.weatherImage15);
        weatherTime11 = (TextView) findViewById(R.id.weatherTime11);
        weatherTime12 = (TextView) findViewById(R.id.weatherTime12);
        weatherTime13 = (TextView) findViewById(R.id.weatherTime13);
        weatherTime14 = (TextView) findViewById(R.id.weatherTime14);
        weatherTime15 = (TextView) findViewById(R.id.weatherTime15);




      /*  mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                        }
                    }
                });*/

        fetchWeather process = new fetchWeather();
        process.execute();

        weatherButton02.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://darksky.net/forecast/15.1324,120.5896/ca12/en"));
                startActivity(intent);
            }
        });

        darkSkyDisclaimer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://darksky.net/poweredby/"));
                startActivity(intent);
            }
        });

        //Button startLipadButton01 = (Button)findViewById(R.id.startLipadButton01);

       /* startLipadButton01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, lipadPanel.class));
                Intent cameraIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                startActivity(cameraIntent);
            }
        });
        */
        weatherButton01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchWeather process = new fetchWeather();
                process.execute();
                Toast.makeText(getApplicationContext(), "Updating weather...", Toast.LENGTH_SHORT).show();

            }
        });





    }

}
