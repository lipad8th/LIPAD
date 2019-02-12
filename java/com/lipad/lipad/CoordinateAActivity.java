package com.lipad.lipad;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.lipad.lipad.MainActivity.RequestPermissionCode;

public class CoordinateAActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    String coordinateA;
    String coordinateB;
    String coordinateC;
    String coordinateD;
    String altitudeValue;
    String groundSpeedValue;
    Button compassAButton;
    Button compassBButton;
    Button compassCButton;
    Button compassDButton;
    Button coordinateANextButton;
    EditText latitudeAEditText;
    EditText longitudeAEditText;
    EditText latitudeBEditText;
    EditText longitudeBEditText;
    EditText latitudeCEditText;
    EditText longitudeCEditText;
    EditText latitudeDEditText;
    EditText longitudeDEditText;
    DatabaseHelper databaseHelper;
    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    FusedLocationProviderApi locationProviderApi = LocationServices.FusedLocationApi;
    EditText altitudeEditText;
    EditText groundSpeedEditText;

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
        altitudeEditText = findViewById(R.id.altitudeEditText);
        groundSpeedEditText = findViewById(R.id.groundSpeedEditText);
        coordinateANextButton = findViewById(R.id.coordinateANextButton);

        databaseHelper = new DatabaseHelper(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        Cursor coordinateAData = databaseHelper.getCoordinateData(fieldDefinition.selectedId, "A");
        Cursor coordinateBData = databaseHelper.getCoordinateData(fieldDefinition.selectedId, "B");
        Cursor coordinateCData = databaseHelper.getCoordinateData(fieldDefinition.selectedId, "C");
        Cursor coordinateDData = databaseHelper.getCoordinateData(fieldDefinition.selectedId, "D");
        Cursor altitudeData = databaseHelper.getAltitudeData(fieldDefinition.selectedId);
        Cursor groundSpeedData = databaseHelper.getGroundSpeedData(fieldDefinition.selectedId);

        while (coordinateAData.moveToNext()) {
            String coordinateAString = coordinateAData.getString(0);
            if (coordinateAString != null) {
                String[] coordinateAValue = coordinateAString.split(",");
                latitudeAEditText.setText(coordinateAValue[0]);
                longitudeAEditText.setText(coordinateAValue[1]);
            }
        }
        while (coordinateBData.moveToNext()) {
            String coordinateBString = coordinateBData.getString(0);
            if (coordinateBString != null) {
                String[] coordinateBValue = coordinateBString.split(",");
                latitudeBEditText.setText(coordinateBValue[0]);
                longitudeBEditText.setText(coordinateBValue[1]);
            }
        }
        while (coordinateCData.moveToNext()) {
            String coordinateCString = coordinateCData.getString(0);
            if (coordinateCString != null) {
                String[] coordinateCValue = coordinateCString.split(",");
                latitudeCEditText.setText(coordinateCValue[0]);
                longitudeCEditText.setText(coordinateCValue[1]);
            }
        }
        while (coordinateDData.moveToNext()) {
            String coordinateDString = coordinateDData.getString(0);
            if (coordinateDString != null) {
                String[] coordinateDValue = coordinateDString.split(",");
                latitudeDEditText.setText(coordinateDValue[0]);
                longitudeDEditText.setText(coordinateDValue[1]);
            }
        }

        while (altitudeData.moveToNext()) {
            altitudeEditText.setText(altitudeData.getString(0));
        }

        while (groundSpeedData.moveToNext()) {
            groundSpeedEditText.setText(groundSpeedData.getString(0));
        }

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

        coordinateANextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(latitudeAEditText.getText()).equals("") || String.valueOf(latitudeBEditText.getText()).equals("")
                        || String.valueOf(latitudeCEditText.getText()).equals("") || String.valueOf(latitudeDEditText.getText()).equals("")
                        || String.valueOf(altitudeEditText.getText()).equals("") || String.valueOf(groundSpeedEditText.getText()).equals("")) {
                    toastMessage("Incomplete details.", "negative");
                } else {
                    coordinateA = latitudeAEditText.getText() + "," + longitudeAEditText.getText();
                    coordinateB = latitudeBEditText.getText() + "," + longitudeBEditText.getText();
                    coordinateC = latitudeCEditText.getText() + "," + longitudeCEditText.getText();
                    coordinateD = latitudeDEditText.getText() + "," + longitudeDEditText.getText();
                    altitudeValue = String.valueOf(altitudeEditText.getText());
                    groundSpeedValue = String.valueOf(groundSpeedEditText.getText());
                    databaseHelper.addCoordinateData(fieldDefinition.selectedId, "A", coordinateA);
                    databaseHelper.addCoordinateData(fieldDefinition.selectedId, "B", coordinateB);
                    databaseHelper.addCoordinateData(fieldDefinition.selectedId, "C", coordinateC);
                    databaseHelper.addCoordinateData(fieldDefinition.selectedId, "D", coordinateD);
                    databaseHelper.addAltitudeData(fieldDefinition.selectedId, altitudeValue);
                    databaseHelper.addGroundSpeedData(fieldDefinition.selectedId, groundSpeedValue);
                    Intent intent = new Intent(CoordinateAActivity.this, SendToDrone.class);
                    startActivity(intent);
                }
            }
        });

        latitudeAEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        latitudeBEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        latitudeCEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        latitudeDEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        longitudeAEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        longitudeBEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        longitudeCEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        longitudeDEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        altitudeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        groundSpeedEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
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

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

}
