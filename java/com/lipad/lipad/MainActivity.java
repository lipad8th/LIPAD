package com.lipad.lipad;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener {

    public static final int RequestPermissionCode = 1;
    public static TextView weatherTitle01;
    public static TextView weatherSub01;
    public static TextView weatherSub11;
    public static TextView weatherSub12;
    public static TextView weatherSub13;
    public static TextView weatherSub14;
    public static TextView weatherSub15;
    public static TextView weatherSub21;
    public static TextView weatherSub22;
    public static TextView weatherSub23;
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
    public static AppCompatImageView weatherImage21;
    public static AppCompatImageView weatherImage22;
    public static AppCompatImageView weatherImage23;
    public static TextView weatherTime11;
    public static TextView weatherTime12;
    public static TextView weatherTime13;
    public static TextView weatherTime14;
    public static TextView weatherTime15;
    public static TextView weatherDay21;
    public static TextView weatherDay22;
    public static TextView weatherDay23;
    public static TextView startLipadTitle01;
    public static int themeId = 1;
    public static int[] idList;
    public static int[] rowList;
    public static int[] columnList;
    public static int fields;
    public static int totalSeeds;
    public static int totalWater;
    Button weatherButton01;
    Button weatherButton02;
    Button darkSkyDisclaimer;
    DatabaseHelper databaseHelper;
    FieldDatabaseHelper fieldDatabaseHelper;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private FusedLocationProviderApi locationProvider = LocationServices.FusedLocationApi;

    private CardView card01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherButton01 = findViewById(R.id.weatherButton01);
        weatherButton02 = findViewById(R.id.weatherButton02);
        darkSkyDisclaimer = findViewById(R.id.darkSkyDisclaimer);

        startLipadTitle01 = findViewById(R.id.startLipadTitle01);

        weatherTitle01 = findViewById(R.id.weatherTitle01);
        weatherSub01 = findViewById(R.id.weatherSub01);
        weatherSub11 = findViewById(R.id.weatherSub11);
        weatherSub12 = findViewById(R.id.weatherSub12);
        weatherSub13 = findViewById(R.id.weatherSub13);
        weatherSub14 = findViewById(R.id.weatherSub14);
        weatherSub15 = findViewById(R.id.weatherSub15);
        weatherSub21 = findViewById(R.id.weatherSub21);
        weatherSub22 = findViewById(R.id.weatherSub22);
        weatherSub23 = findViewById(R.id.weatherSub23);
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
        weatherImage21 = findViewById(R.id.weatherImage21);
        weatherImage22 = findViewById(R.id.weatherImage22);
        weatherImage23 = findViewById(R.id.weatherImage23);
        weatherTime11 = findViewById(R.id.weatherTime11);
        weatherTime12 = findViewById(R.id.weatherTime12);
        weatherTime13 = findViewById(R.id.weatherTime13);
        weatherTime14 = findViewById(R.id.weatherTime14);
        weatherTime15 = findViewById(R.id.weatherTime15);
        weatherDay21 = findViewById(R.id.weatherDay21);
        weatherDay22 = findViewById(R.id.weatherDay22);
        weatherDay23 = findViewById(R.id.weatherDay23);

        databaseHelper = new DatabaseHelper(this);
        fieldDatabaseHelper = new FieldDatabaseHelper(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        totalSeeds = 0;
        totalWater = 0;

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        card01 = (CardView) findViewById(R.id.card01);

        card01.setOnClickListener(this);

        fetchWeather.windText = getString(R.string.windText);
        fetchWeather.humidityText = getString(R.string.humidityText);
        fetchWeather.chanceOfRainText = getString(R.string.chanceOfRainText);
        fetchWeather.humidityInitial = getString(R.string.humidityInitial);
        fetchWeather.chanceOfRainInitial = getString(R.string.chanceOfRainInitial);
        fetchWeather.partlyCloudyText = getString(R.string.partlyCloudyText);
        fetchWeather.mostlyCloudyText = getString(R.string.mostlyCloudyText);
        fetchWeather.clearText = getString(R.string.clearText);
        fetchWeather.drizzleText = getString(R.string.drizzleText);
        fetchWeather.breezyPartlyCloudyText = getString(R.string.breezyPartlyCloudyText);
        fetchWeather.breezyMostlyCloudyText = getString(R.string.breezyMostlyCloudyText);
        fetchWeather process = new fetchWeather();
        process.execute();

        weatherButton02.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://darksky.net/forecast/" + fetchWeather.latitudeValue + "," + fetchWeather.longitudeValue + "/ca12/en"));
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

        weatherButton01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchWeather process = new fetchWeather();
                process.execute();
                toastMessage("Updating weather...", "positive");
                Snackbar.make(view, "Updating weather...", Snackbar.LENGTH_LONG).show();
            }
        });

        Cursor fieldNumber = databaseHelper.getNumberOfFields();
        fieldNumber.moveToFirst();
        fields = fieldNumber.getInt(0);

        Cursor data = databaseHelper.getIdList();
        int k = 0;
        idList = new int[fields];
        rowList = new int[fields];
        columnList = new int[fields];
        //cellArray = new int[fieldSize];
        while (data.moveToNext()) {
            idList[k] = data.getInt(0);
            rowList[k] = data.getInt(1);
            columnList[k] = data.getInt(2);
            Log.d("MainActivity", "DataCursor " + String.valueOf(idList[k]));
            k++;
        }

        int n = 0;
        for (int l = 1; l <= idList.length; l++) {
            for (int i = 1; i <= rowList[n]; i++) {
                for (int j = 1; j <= columnList[n]; j++) {
                    Cursor fieldData = fieldDatabaseHelper.getData(idList[n], i, j);
                    fieldData.moveToFirst();
                    //Log.d("MainActivity", "total seed " + "hello");
                    int cellValue = fieldData.getInt(0);
                    if (cellValue == 1) {
                        totalSeeds++;
                        Log.d("MainActivity", "total seed " + totalSeeds);
                    } else if (cellValue == 2) {
                        totalWater++;
                        Log.d("MainActivity", "total water " + totalWater);
                    }
                }
            }
            n++;
        }

        GraphView graph = (GraphView) findViewById(R.id.graphView01);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(1, totalSeeds),
                new DataPoint(2, totalWater)
        });

        // set manual X bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        if (totalSeeds > totalWater) {
            graph.getViewport().setMaxY(totalSeeds + 1);
        } else if (totalWater > totalSeeds) {
            graph.getViewport().setMaxY(totalWater + 1);
        }

        //graph.getViewport().setMaxY(200);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(3);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        graph.setBackgroundColor(getResources().getColor(R.color.colorBackground));

        graph.addSeries(series);

        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return getResources().getColor(R.color.colorGray);
            }
        });

        series.setSpacing(48);

        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.WHITE);
        //series.setValuesOnTopSize(50);


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
        Log.e("fetchWeather", "Connection suspended");
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
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{ACCESS_FINE_LOCATION}, RequestPermissionCode);
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }


    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.card01:
                i = new Intent(this, fieldSelection.class);
                startActivity(i);
                break;
            default:
                break;

        }
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
