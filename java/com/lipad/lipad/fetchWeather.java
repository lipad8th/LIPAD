package com.lipad.lipad;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class fetchWeather extends AsyncTask<Void, Void, Void> {
    public static String tomorrowHigh = "";
    public static String tomorrowLow = "";
    //initially public static
    static String longitudeValue;
    static String latitudeValue;
    static String windText;
    static String humidityText;
    static String chanceOfRainText;
    static String humidityInitial;
    static String chanceOfRainInitial;
    static String partlyCloudyText;
    static String mostlyCloudyText;
    static String clearText;
    static String drizzleText;
    static String breezyPartlyCloudyText;
    static String breezyMostlyCloudyText;
    String darkSkyData = "";
    String mapQuestData = "";
    MiscDatabaseHelper miscDatabaseHelper;
    private String weatherTitle01 = "";
    private String weatherSub01 = "";
    private String weatherSub11 = "";
    private String weatherSub12 = "";
    private String weatherSub13 = "";
    private String weatherSub14 = "";
    private String weatherSub15 = "";
    private String weatherTemp01 = "";
    private String weatherTemp11 = "";
    private String weatherTemp12 = "";
    private String weatherTemp13 = "";
    private String weatherTemp14 = "";
    private String weatherTemp15 = "";
    private String weatherTemp21 = "";
    private String weatherTemp22 = "";
    private String weatherTemp23 = "";
    private String weatherImage01 = "";
    private String weatherImage11 = "";
    private String weatherImage12 = "";
    private String weatherImage13 = "";
    private String weatherImage14 = "";
    private String weatherImage15 = "";
    private String weatherImage21 = "";
    private String weatherImage22 = "";
    private String weatherImage23 = "";
    private String weatherTime11 = "";
    private String weatherTime12 = "";
    private String weatherTime13 = "";
    private String weatherTime14 = "";
    private String weatherTime15 = "";
    private String weatherDay21 = "";
    private String weatherDay22 = "";
    private String weatherDay23 = "";
    private String darkSkyURL = "";
    private String mapQuestURL = "";
    MainActivity mainActivity;

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        darkSkyURL = "https://api.darksky.net/forecast/25518f5c378769e6bca4ac90e65f3010/" + latitudeValue + "," + longitudeValue + "?units=si";
        mapQuestURL = "http://open.mapquestapi.com/geocoding/v1/reverse?key=mJg3p2K8gwlMWtM9pOiku2ZTu9UB5SAl&location=" + latitudeValue + "," + longitudeValue;

        try {
            URL url = new URL(darkSkyURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                darkSkyData = darkSkyData + line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL(mapQuestURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                mapQuestData = mapQuestData + line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            JSONObject JMap = new JSONObject(mapQuestData);
            JSONArray JResults = JMap.getJSONArray("results");
            JSONObject JResultsElement = JResults.getJSONObject(0);
            JSONArray JLocations = JResultsElement.getJSONArray("locations");
            JSONObject JLocationsElement = JLocations.getJSONObject(0);

            weatherTitle01 = "" + JLocationsElement.getString("adminArea5");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {

            JSONObject JDark = new JSONObject(darkSkyData);
            JSONObject JCurrently = JDark.getJSONObject("currently");
            JSONObject JHourly = JDark.getJSONObject("hourly");
            JSONObject JDaily = JDark.getJSONObject("daily");

            JSONArray JHourlyData = JHourly.getJSONArray("data");
            JSONObject JHourly11 = JHourlyData.getJSONObject(1);
            JSONObject JHourly12 = JHourlyData.getJSONObject(2);
            JSONObject JHourly13 = JHourlyData.getJSONObject(3);
            JSONObject JHourly14 = JHourlyData.getJSONObject(4);
            JSONObject JHourly15 = JHourlyData.getJSONObject(5);

            JSONArray JDailyData = JDaily.getJSONArray("data");
            JSONObject JDaily20 = JDailyData.getJSONObject(0);
            JSONObject JDaily21 = JDailyData.getJSONObject(1);
            JSONObject JDaily22 = JDailyData.getJSONObject(2);
            JSONObject JDaily23 = JDailyData.getJSONObject(3);

            weatherTemp01 = Math.round(JCurrently.getDouble("temperature")) + "°C";
            weatherTemp11 = Math.round(JHourly11.getDouble("temperature")) + "°C";
            weatherTemp12 = Math.round(JHourly12.getDouble("temperature")) + "°C";
            weatherTemp13 = Math.round(JHourly13.getDouble("temperature")) + "°C";
            weatherTemp14 = Math.round(JHourly14.getDouble("temperature")) + "°C";
            weatherTemp15 = Math.round(JHourly15.getDouble("temperature")) + "°C";

            weatherTemp21 = Math.round(JDaily21.getDouble("temperatureHigh")) + "°C" + " / " + Math.round(JDaily21.getDouble("temperatureLow")) + "°C";
            weatherTemp22 = Math.round(JDaily22.getDouble("temperatureHigh")) + "°C" + " / " + Math.round(JDaily22.getDouble("temperatureLow")) + "°C";
            weatherTemp23 = Math.round(JDaily23.getDouble("temperatureHigh")) + "°C" + " / " + Math.round(JDaily23.getDouble("temperatureLow")) + "°C";

            String time0 = String.valueOf(Math.round(JDaily20.getDouble("time")));
            Long tsLong = System.currentTimeMillis() / 1000;
            String ts = tsLong.toString();

            if ((Integer.parseInt(ts) - Integer.parseInt(time0)) <= 18000) {
                tomorrowHigh = String.valueOf(Math.round(JDaily20.getDouble("temperatureHigh"))) + "°C";
                tomorrowLow = String.valueOf(Math.round(JDaily20.getDouble("temperatureLow"))) + "°C";
                Log.d("fetchWeather", "High and low: " + tomorrowHigh + " " + tomorrowLow);

            } else {
                tomorrowHigh = String.valueOf(Math.round(JDaily21.getDouble("temperatureHigh"))) + "°C";
                tomorrowLow = String.valueOf(Math.round(JDaily21.getDouble("temperatureLow"))) + "°C";
                Log.d("fetchWeather", "High and low: " + tomorrowHigh + " " + tomorrowLow);
            }

            String weatherCondition = "" + JCurrently.get("summary");
            switch (weatherCondition) {
                case "Partly Cloudy":
                    weatherCondition = partlyCloudyText;
                    break;
                case "Mostly Cloudy":
                    weatherCondition = mostlyCloudyText;
                    break;
                case "Clear":
                    weatherCondition = clearText;
                    break;
                case "Drizzle":
                    weatherCondition = drizzleText;
                    break;
                case "Breezy and Partly Cloudy":
                    weatherCondition = breezyPartlyCloudyText;
                    break;
                case "Breezy and Mostly Cloudy":
                    weatherCondition = breezyMostlyCloudyText;
                    break;
            }

            weatherSub01 = "" + weatherCondition + "\n" +
                    windText + JCurrently.getDouble("windSpeed") + " m/s" + "\n" +
                    humidityText + Math.round(JCurrently.getDouble("humidity") * 100) + "%" + "\n" +
                    chanceOfRainText + Math.round(JCurrently.getDouble("precipProbability")) + "%" + "\n";
            weatherSub11 = JHourly11.getDouble("windSpeed") + "m/s" + "\n" +
                    humidityInitial + Math.round(JHourly11.getDouble("humidity") * 100) + "%" + "\n" +
                    chanceOfRainInitial + Math.round(JHourly11.getDouble("precipProbability") * 100) + "%" + "\n";
            weatherSub12 = JHourly12.getDouble("windSpeed") + "m/s" + "\n" +
                    humidityInitial + Math.round(JHourly12.getDouble("humidity") * 100) + "%" + "\n" +
                    chanceOfRainInitial + Math.round(JHourly12.getDouble("precipProbability") * 100) + "%" + "\n";
            weatherSub13 = JHourly13.getDouble("windSpeed") + "m/s" + "\n" +
                    humidityInitial + Math.round(JHourly13.getDouble("humidity") * 100) + "%" + "\n" +
                    chanceOfRainInitial + Math.round(JHourly13.getDouble("precipProbability") * 100) + "%" + "\n";
            weatherSub14 = JHourly14.getDouble("windSpeed") + "m/s" + "\n" +
                    humidityInitial + Math.round(JHourly14.getDouble("humidity") * 100) + "%" + "\n" +
                    chanceOfRainInitial + Math.round(JHourly14.getDouble("precipProbability") * 100) + "%" + "\n";
            weatherSub15 = JHourly15.getDouble("windSpeed") + "m/s" + "\n" +
                    humidityInitial + Math.round(JHourly15.getDouble("humidity") * 100) + "%" + "\n" +
                    chanceOfRainInitial + Math.round(JHourly15.getDouble("precipProbability") * 100) + "%" + "\n";

            weatherImage01 = JCurrently.getString("icon");
            weatherImage11 = JHourly11.getString("icon");
            weatherImage12 = JHourly12.getString("icon");
            weatherImage13 = JHourly13.getString("icon");
            weatherImage14 = JHourly14.getString("icon");
            weatherImage15 = JHourly15.getString("icon");
            weatherImage21 = JDaily21.getString("icon");
            weatherImage22 = JDaily22.getString("icon");
            weatherImage23 = JDaily23.getString("icon");

            weatherTime11 = JHourly11.getString("time") + "";
            long unixTime11 = Long.valueOf(weatherTime11) * 1000;
            Date javaTime11 = new java.util.Date(unixTime11);
            weatherTime11 = new SimpleDateFormat("ha").format(javaTime11);

            weatherTime12 = JHourly12.getString("time") + "";
            long unixTime12 = Long.valueOf(weatherTime12) * 1000;
            Date javaTime12 = new java.util.Date(unixTime12);
            weatherTime12 = new SimpleDateFormat("ha").format(javaTime12);

            weatherTime13 = JHourly13.getString("time") + "";
            long unixTime13 = Long.valueOf(weatherTime13) * 1000;
            Date javaTime13 = new java.util.Date(unixTime13);
            weatherTime13 = new SimpleDateFormat("ha").format(javaTime13);

            weatherTime14 = JHourly14.getString("time") + "";
            long unixTime14 = Long.valueOf(weatherTime14) * 1000;
            Date javaTime14 = new java.util.Date(unixTime14);
            weatherTime14 = new SimpleDateFormat("ha").format(javaTime14);

            weatherTime15 = JHourly15.getString("time") + "";
            long unixTime15 = Long.valueOf(weatherTime15) * 1000;
            Date javaTime15 = new java.util.Date(unixTime15);
            weatherTime15 = new SimpleDateFormat("ha").format(javaTime15);

            weatherDay21 = JDaily21.getString("time") + "";
            long unixTime21 = Long.valueOf(weatherDay21) * 1000;
            Date javaTime21 = new java.util.Date(unixTime21);
            weatherDay21 = new SimpleDateFormat("EEEE").format(javaTime21);

            weatherDay22 = JDaily22.getString("time") + "";
            long unixTime22 = Long.valueOf(weatherDay22) * 1000;
            Date javaTime22 = new java.util.Date(unixTime22);
            weatherDay22 = new SimpleDateFormat("EEEE").format(javaTime22);

            weatherDay23 = JDaily23.getString("time") + "";
            long unixTime23 = Long.valueOf(weatherDay23) * 1000;
            Date javaTime23 = new java.util.Date(unixTime23);
            weatherDay23 = new SimpleDateFormat("EEEE").format(javaTime23);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        switch (weatherImage01) {
            case "clear-night":
                MainActivity.weatherImage01.setImageResource(R.drawable.ic_clearnight);
                break;
            case "clear-day":
                MainActivity.weatherImage01.setImageResource(R.drawable.ic_clearday);
                break;
            case "rain":
                MainActivity.weatherImage01.setImageResource(R.drawable.ic_rain);
                break;
            case "wind":
                MainActivity.weatherImage01.setImageResource(R.drawable.ic_wind);
                break;
            case "fog":
                MainActivity.weatherImage01.setImageResource(R.drawable.ic_fog);
                break;
            case "cloudy":
                MainActivity.weatherImage01.setImageResource(R.drawable.ic_cloudy);
                break;
            case "partly-cloudy-day":
                MainActivity.weatherImage01.setImageResource(R.drawable.ic_partlycloudyday);
                break;
            case "partly-cloudy-night":
                MainActivity.weatherImage01.setImageResource(R.drawable.ic_partlycloudynight);
                break;
            case "thunderstorm":
                MainActivity.weatherImage01.setImageResource(R.drawable.ic_thunderstorm);
                break;
        }

        switch (weatherImage11) {
            case "clear-night":
                MainActivity.weatherImage11.setImageResource(R.drawable.ic_clearnight);
                break;
            case "clear-day":
                MainActivity.weatherImage11.setImageResource(R.drawable.ic_clearday);
                break;
            case "rain":
                MainActivity.weatherImage11.setImageResource(R.drawable.ic_rain);
                break;
            case "wind":
                MainActivity.weatherImage11.setImageResource(R.drawable.ic_wind);
                break;
            case "fog":
                MainActivity.weatherImage11.setImageResource(R.drawable.ic_fog);
                break;
            case "cloudy":
                MainActivity.weatherImage11.setImageResource(R.drawable.ic_cloudy);
                break;
            case "partly-cloudy-day":
                MainActivity.weatherImage11.setImageResource(R.drawable.ic_partlycloudyday);
                break;
            case "partly-cloudy-night":
                MainActivity.weatherImage11.setImageResource(R.drawable.ic_partlycloudynight);
                break;
            case "thunderstorm":
                MainActivity.weatherImage11.setImageResource(R.drawable.ic_thunderstorm);
                break;
        }

        switch (weatherImage12) {
            case "clear-night":
                MainActivity.weatherImage12.setImageResource(R.drawable.ic_clearnight);
                break;
            case "clear-day":
                MainActivity.weatherImage12.setImageResource(R.drawable.ic_clearday);
                break;
            case "rain":
                MainActivity.weatherImage12.setImageResource(R.drawable.ic_rain);
                break;
            case "wind":
                MainActivity.weatherImage12.setImageResource(R.drawable.ic_wind);
                break;
            case "fog":
                MainActivity.weatherImage12.setImageResource(R.drawable.ic_fog);
                break;
            case "cloudy":
                MainActivity.weatherImage12.setImageResource(R.drawable.ic_cloudy);
                break;
            case "partly-cloudy-day":
                MainActivity.weatherImage12.setImageResource(R.drawable.ic_partlycloudyday);
                break;
            case "partly-cloudy-night":
                MainActivity.weatherImage12.setImageResource(R.drawable.ic_partlycloudynight);
                break;
            case "thunderstorm":
                MainActivity.weatherImage12.setImageResource(R.drawable.ic_thunderstorm);
                break;
        }

        switch (weatherImage13) {
            case "clear-night":
                MainActivity.weatherImage13.setImageResource(R.drawable.ic_clearnight);
                break;
            case "clear-day":
                MainActivity.weatherImage13.setImageResource(R.drawable.ic_clearday);
                break;
            case "rain":
                MainActivity.weatherImage13.setImageResource(R.drawable.ic_rain);
                break;
            case "wind":
                MainActivity.weatherImage13.setImageResource(R.drawable.ic_wind);
                break;
            case "fog":
                MainActivity.weatherImage13.setImageResource(R.drawable.ic_fog);
                break;
            case "cloudy":
                MainActivity.weatherImage13.setImageResource(R.drawable.ic_cloudy);
                break;
            case "partly-cloudy-day":
                MainActivity.weatherImage13.setImageResource(R.drawable.ic_partlycloudyday);
                break;
            case "partly-cloudy-night":
                MainActivity.weatherImage13.setImageResource(R.drawable.ic_partlycloudynight);
                break;
            case "thunderstorm":
                MainActivity.weatherImage13.setImageResource(R.drawable.ic_thunderstorm);
                break;
        }

        switch (weatherImage14) {
            case "clear-night":
                MainActivity.weatherImage14.setImageResource(R.drawable.ic_clearnight);
                break;
            case "clear-day":
                MainActivity.weatherImage14.setImageResource(R.drawable.ic_clearday);
                break;
            case "rain":
                MainActivity.weatherImage14.setImageResource(R.drawable.ic_rain);
                break;
            case "wind":
                MainActivity.weatherImage14.setImageResource(R.drawable.ic_wind);
                break;
            case "fog":
                MainActivity.weatherImage14.setImageResource(R.drawable.ic_fog);
                break;
            case "cloudy":
                MainActivity.weatherImage14.setImageResource(R.drawable.ic_cloudy);
                break;
            case "partly-cloudy-day":
                MainActivity.weatherImage14.setImageResource(R.drawable.ic_partlycloudyday);
                break;
            case "partly-cloudy-night":
                MainActivity.weatherImage14.setImageResource(R.drawable.ic_partlycloudynight);
                break;
            case "thunderstorm":
                MainActivity.weatherImage14.setImageResource(R.drawable.ic_thunderstorm);
                break;
        }

        switch (weatherImage15) {
            case "clear-night":
                MainActivity.weatherImage15.setImageResource(R.drawable.ic_clearnight);
                break;
            case "clear-day":
                MainActivity.weatherImage15.setImageResource(R.drawable.ic_clearday);
                break;
            case "rain":
                MainActivity.weatherImage15.setImageResource(R.drawable.ic_rain);
                break;
            case "wind":
                MainActivity.weatherImage15.setImageResource(R.drawable.ic_wind);
                break;
            case "fog":
                MainActivity.weatherImage15.setImageResource(R.drawable.ic_fog);
                break;
            case "cloudy":
                MainActivity.weatherImage15.setImageResource(R.drawable.ic_cloudy);
                break;
            case "partly-cloudy-day":
                MainActivity.weatherImage15.setImageResource(R.drawable.ic_partlycloudyday);
                break;
            case "partly-cloudy-night":
                MainActivity.weatherImage15.setImageResource(R.drawable.ic_partlycloudynight);
                break;
            case "thunderstorm":
                MainActivity.weatherImage15.setImageResource(R.drawable.ic_thunderstorm);
                break;
        }

        switch (weatherImage21) {
            case "clear-night":
                MainActivity.weatherImage21.setImageResource(R.drawable.ic_clearnight);
                break;
            case "clear-day":
                MainActivity.weatherImage21.setImageResource(R.drawable.ic_clearday);
                break;
            case "rain":
                MainActivity.weatherImage21.setImageResource(R.drawable.ic_rain);
                break;
            case "wind":
                MainActivity.weatherImage21.setImageResource(R.drawable.ic_wind);
                break;
            case "fog":
                MainActivity.weatherImage21.setImageResource(R.drawable.ic_fog);
                break;
            case "cloudy":
                MainActivity.weatherImage21.setImageResource(R.drawable.ic_cloudy);
                break;
            case "partly-cloudy-day":
                MainActivity.weatherImage21.setImageResource(R.drawable.ic_partlycloudyday);
                break;
            case "partly-cloudy-night":
                MainActivity.weatherImage21.setImageResource(R.drawable.ic_partlycloudynight);
                break;
            case "thunderstorm":
                MainActivity.weatherImage21.setImageResource(R.drawable.ic_thunderstorm);
                break;
        }

        switch (weatherImage22) {
            case "clear-night":
                MainActivity.weatherImage22.setImageResource(R.drawable.ic_clearnight);
                break;
            case "clear-day":
                MainActivity.weatherImage22.setImageResource(R.drawable.ic_clearday);
                break;
            case "rain":
                MainActivity.weatherImage22.setImageResource(R.drawable.ic_rain);
                break;
            case "wind":
                MainActivity.weatherImage22.setImageResource(R.drawable.ic_wind);
                break;
            case "fog":
                MainActivity.weatherImage22.setImageResource(R.drawable.ic_fog);
                break;
            case "cloudy":
                MainActivity.weatherImage22.setImageResource(R.drawable.ic_cloudy);
                break;
            case "partly-cloudy-day":
                MainActivity.weatherImage22.setImageResource(R.drawable.ic_partlycloudyday);
                break;
            case "partly-cloudy-night":
                MainActivity.weatherImage22.setImageResource(R.drawable.ic_partlycloudynight);
                break;
            case "thunderstorm":
                MainActivity.weatherImage22.setImageResource(R.drawable.ic_thunderstorm);
                break;
        }

        switch (weatherImage23) {
            case "clear-night":
                MainActivity.weatherImage23.setImageResource(R.drawable.ic_clearnight);
                break;
            case "clear-day":
                MainActivity.weatherImage23.setImageResource(R.drawable.ic_clearday);
                break;
            case "rain":
                MainActivity.weatherImage23.setImageResource(R.drawable.ic_rain);
                break;
            case "wind":
                MainActivity.weatherImage23.setImageResource(R.drawable.ic_wind);
                break;
            case "fog":
                MainActivity.weatherImage23.setImageResource(R.drawable.ic_fog);
                break;
            case "cloudy":
                MainActivity.weatherImage23.setImageResource(R.drawable.ic_cloudy);
                break;
            case "partly-cloudy-day":
                MainActivity.weatherImage23.setImageResource(R.drawable.ic_partlycloudyday);
                break;
            case "partly-cloudy-night":
                MainActivity.weatherImage23.setImageResource(R.drawable.ic_partlycloudynight);
                break;
            case "thunderstorm":
                MainActivity.weatherImage23.setImageResource(R.drawable.ic_thunderstorm);
                break;
        }

        MainActivity.weatherTitle01.setText(this.weatherTitle01);
        MainActivity.weatherSub01.setText(this.weatherSub01);
        MainActivity.weatherTemp01.setText(this.weatherTemp01);
        MainActivity.weatherTemp11.setText(this.weatherTemp11);
        MainActivity.weatherTemp12.setText(this.weatherTemp12);
        MainActivity.weatherTemp13.setText(this.weatherTemp13);
        MainActivity.weatherTemp14.setText(this.weatherTemp14);
        MainActivity.weatherTemp15.setText(this.weatherTemp15);
        MainActivity.weatherSub21.setText(this.weatherTemp21);
        MainActivity.weatherSub22.setText(this.weatherTemp22);
        MainActivity.weatherSub23.setText(this.weatherTemp23);
        MainActivity.weatherSub11.setText(this.weatherSub11);
        MainActivity.weatherSub12.setText(this.weatherSub12);
        MainActivity.weatherSub13.setText(this.weatherSub13);
        MainActivity.weatherSub14.setText(this.weatherSub14);
        MainActivity.weatherSub15.setText(this.weatherSub15);
        MainActivity.weatherTime11.setText(this.weatherTime11);
        MainActivity.weatherTime12.setText(this.weatherTime12);
        MainActivity.weatherTime13.setText(this.weatherTime13);
        MainActivity.weatherTime14.setText(this.weatherTime14);
        MainActivity.weatherTime15.setText(this.weatherTime15);
        MainActivity.weatherDay21.setText(this.weatherDay21);
        MainActivity.weatherDay22.setText(this.weatherDay22);
        MainActivity.weatherDay23.setText(this.weatherDay23);


    }

}
