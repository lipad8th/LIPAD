package com.lipad.lipad;

import android.os.AsyncTask;

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
    String data = "";
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
    private String weatherImage01 = "";
    private String weatherImage11 = "";
    private String weatherImage12 = "";
    private String weatherImage13 = "";
    private String weatherImage14 = "";
    private String weatherImage15 = "";
    private String weatherTime11 = "";
    private String weatherTime12 = "";
    private String weatherTime13 = "";
    private String weatherTime14 = "";
    private String weatherTime15 = "";
    private String darkSkyURL = "";

    public static String longitudeValue;
    public static String latitudeValue;
    public static String windText;
    public static String humidityText;
    public static String chanceOfRainText;
    public static String humidityInitial;
    public static String chanceOfRainInitial;
    public static String partlyCloudyText;
    public static String mostlyCloudyText;
    public static String clearText;
    public static String drizzleText;

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        darkSkyURL = "https://api.darksky.net/forecast/25518f5c378769e6bca4ac90e65f3010/" + latitudeValue + "," +  longitudeValue + "?units=si";

        try {
            URL url = new URL(darkSkyURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
        } catch (MalformedURLException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }

        try {

            JSONObject JDark = (JSONObject) new JSONObject(data);
            JSONObject JCurrently = (JSONObject) JDark.getJSONObject("currently");
            JSONObject JHourly = (JSONObject) JDark.getJSONObject("hourly");

            JSONArray JHourlyData = (JSONArray) JHourly.getJSONArray("data");
            JSONObject JHourly11 = (JSONObject) JHourlyData.getJSONObject(1);
            JSONObject JHourly12 = (JSONObject) JHourlyData.getJSONObject(2);
            JSONObject JHourly13 = (JSONObject) JHourlyData.getJSONObject(3);
            JSONObject JHourly14 = (JSONObject) JHourlyData.getJSONObject(4);
            JSONObject JHourly15 = (JSONObject) JHourlyData.getJSONObject(5);

            weatherTemp01 = Math.round(JCurrently.getDouble("temperature")) + "°C";
            weatherTemp11 = Math.round(JHourly11.getDouble("temperature")) + "°C";
            weatherTemp12 = Math.round(JHourly12.getDouble("temperature")) + "°C";
            weatherTemp13 = Math.round(JHourly13.getDouble("temperature")) + "°C";
            weatherTemp14 = Math.round(JHourly14.getDouble("temperature")) + "°C";
            weatherTemp15 = Math.round(JHourly15.getDouble("temperature")) + "°C";

            String weatherCondition = "" + JCurrently.get("summary");
            switch (weatherCondition){
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
            }

            weatherSub01 =   "" + weatherCondition + "\n" +
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

            weatherTime11 = JHourly11.getString("time") + "";
            long unixTime11 = Long.valueOf(weatherTime11) * 1000;
            Date javaTime11 = new java.util.Date(unixTime11);
            weatherTime11 = new SimpleDateFormat("hha").format(javaTime11);

            weatherTime12 = JHourly12.getString("time") + "";
            long unixTime12 = Long.valueOf(weatherTime12) * 1000;
            Date javaTime12 = new java.util.Date(unixTime12);
            weatherTime12 = new SimpleDateFormat("hha").format(javaTime12);

            weatherTime13 = JHourly13.getString("time") + "";
            long unixTime13 = Long.valueOf(weatherTime13) * 1000;
            Date javaTime13 = new java.util.Date(unixTime13);
            weatherTime13 = new SimpleDateFormat("hha").format(javaTime13);

            weatherTime14 = JHourly14.getString("time") + "";
            long unixTime14 = Long.valueOf(weatherTime14) * 1000;
            Date javaTime14 = new java.util.Date(unixTime14);
            weatherTime14 = new SimpleDateFormat("hha").format(javaTime14);

            weatherTime15 = JHourly15.getString("time") + "";
            long unixTime15 = Long.valueOf(weatherTime15) * 1000;
            Date javaTime15 = new java.util.Date(unixTime15);
            weatherTime15 = new SimpleDateFormat("hha").format(javaTime15);

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

        MainActivity.weatherTitle01.setText("Now");
        MainActivity.weatherSub01.setText(this.weatherSub01);
        MainActivity.weatherTemp01.setText(this.weatherTemp01);
        MainActivity.weatherTemp11.setText(this.weatherTemp11);
        MainActivity.weatherTemp12.setText(this.weatherTemp12);
        MainActivity.weatherTemp13.setText(this.weatherTemp13);
        MainActivity.weatherTemp14.setText(this.weatherTemp14);
        MainActivity.weatherTemp15.setText(this.weatherTemp15);
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

        MainActivity.testView3.setText(this.darkSkyURL);

    }


}
