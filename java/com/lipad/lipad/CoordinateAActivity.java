package com.lipad.lipad;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.lipad.lipad.MainActivity.RequestPermissionCode;

public class CoordinateAActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    Button compassAButton;
    Button compassBButton;
    Button compassCButton;
    Button compassDButton;
    EditText latitudeAEditText;
    EditText longitudeAEditText;
    EditText latitudeBEditText;
    EditText longitudeBEditText;
    EditText latitudeCEditText;
    EditText longitudeCEditText;
    EditText latitudeDEditText;
    EditText longitudeDEditText;

    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    FusedLocationProviderApi locationProviderApi = LocationServices.FusedLocationApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate_a);
        compassAButton = findViewById(R.id.compassAButton);
        compassBButton = findViewById(R.id.compassBButton);
        compassCButton = findViewById(R.id.compassCButton);
        compassDButton = findViewById(R.id.compassDButton);
        latitudeAEditText = findViewById(R.id.latitudeAEditText);
        latitudeBEditText = findViewById(R.id.latitudeBEditText);
        latitudeCEditText = findViewById(R.id.latitudeCEditText);
        latitudeDEditText = findViewById(R.id.latitudeDEditText);
        longitudeAEditText = findViewById(R.id.longitudeAEditText);
        longitudeBEditText = findViewById(R.id.longitudeBEditText);
        longitudeCEditText = findViewById(R.id.longitudeCEditText);
        longitudeDEditText = findViewById(R.id.longitudeDEditText);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        compassAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitudeAEditText.setText(fetchWeather.latitudeValue);
                longitudeAEditText.setText(fetchWeather.longitudeValue);
            }
        });

        compassBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitudeBEditText.setText(fetchWeather.latitudeValue);
                longitudeBEditText.setText(fetchWeather.longitudeValue);
            }
        });

        compassCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitudeCEditText.setText(fetchWeather.latitudeValue);
                longitudeCEditText.setText(fetchWeather.longitudeValue);
            }
        });

        compassDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitudeDEditText.setText(fetchWeather.latitudeValue);
                longitudeDEditText.setText(fetchWeather.longitudeValue);
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
    protected void onResume() {
        super.onResume();
        if (googleApiClient.isConnected()) {
            requestLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }
        requestLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("fetchWeather", "Connection failed: " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        fetchWeather.latitudeValue = String.valueOf(location.getLatitude());
        fetchWeather.longitudeValue = String.valueOf(location.getLongitude());
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(CoordinateAActivity.this, new
                String[]{ACCESS_FINE_LOCATION}, RequestPermissionCode);
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }
}
