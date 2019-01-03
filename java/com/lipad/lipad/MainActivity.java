package com.lipad.lipad;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;




public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    Button weatherButton01;
    Button weatherButton02;
    Button darkSkyDisclaimer;
    public static TextView weatherTitle01;
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

    public static TextView testView;
    public static TextView testView2;
    public static TextView testView3;

    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public static final int RequestPermissionCode = 1;

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                fetchWeather.latitudeValue = String.valueOf(location.getLatitude());
                                fetchWeather.longitudeValue = String.valueOf(location.getLongitude());

                            }
                        }
                    });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherButton01 = findViewById(R.id.weatherButton01);
        weatherButton02 = findViewById(R.id.weatherButton02);
        darkSkyDisclaimer = findViewById(R.id.darkSkyDisclaimer);

        weatherTitle01 = findViewById(R.id.weatherTitle01);
        weatherSub01 = findViewById(R.id.weatherSub01);
        weatherSub11 = findViewById(R.id.weatherSub11);
        weatherSub12 = findViewById(R.id.weatherSub12);
        weatherSub13 = findViewById(R.id.weatherSub13);
        weatherSub14 = findViewById(R.id.weatherSub14);
        weatherSub15 = findViewById(R.id.weatherSub15);
        weatherTemp01 = findViewById(R.id.weatherTemp01);
        weatherTemp11 = findViewById(R.id.weatherTemp11);
        weatherTemp12 = findViewById(R.id.weatherTemp12);
        weatherTemp13 = findViewById(R.id.weatherTemp13);
        weatherTemp14 = findViewById(R.id.weatherTemp14);
        weatherTemp15 = findViewById(R.id.weatherTemp15);
        weatherImage01 = findViewById(R.id.weatherImage01);
        weatherImage11 = findViewById(R.id.weatherImage11);
        weatherImage12 = findViewById(R.id.weatherImage12);
        weatherImage13 = findViewById(R.id.weatherImage13);
        weatherImage14 = findViewById(R.id.weatherImage14);
        weatherImage15 = findViewById(R.id.weatherImage15);
        weatherTime11 = findViewById(R.id.weatherTime11);
        weatherTime12 = findViewById(R.id.weatherTime12);
        weatherTime13 = findViewById(R.id.weatherTime13);
        weatherTime14 = findViewById(R.id.weatherTime14);
        weatherTime15 = findViewById(R.id.weatherTime15);
        testView = findViewById(R.id.testView);
        testView2 = findViewById(R.id.testView2);
        testView3 = findViewById(R.id.testView3);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        fetchWeather process = new fetchWeather();
        process.execute();


        weatherButton02.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://darksky.net/forecast/"+ fetchWeather.latitudeValue +","+ fetchWeather.longitudeValue +"/ca12/en"));
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

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("fetchWeather","Connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("fetchWeather", "Connection failed: " + connectionResult.getErrorCode());
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{ACCESS_FINE_LOCATION}, RequestPermissionCode);
    }
}
