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




public class MainActivity extends AppCompatActivity {
    Button weatherButton01;
    Button darkSkyDisclaimer;
    Button weatherButton02;
    public static TextView weatherTitle01;
    public static TextView weatherSub01;
    public static AppCompatImageView weatherImage01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherButton01 = (Button) findViewById(R.id.weatherButton01);
        darkSkyDisclaimer = (Button) findViewById(R.id.darkSkyDisclaimer);
        weatherTitle01 = (TextView) findViewById(R.id.weatherTitle01);
        weatherSub01 = (TextView) findViewById(R.id.weatherSub01);
        weatherButton02 = (Button) findViewById(R.id.weatherButton02);
        weatherImage01 = (AppCompatImageView) findViewById(R.id.weatherImage01);

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
                Toast.makeText(getApplicationContext(), "Weather updated.", Toast.LENGTH_SHORT).show();

            }
        });





    }

}
