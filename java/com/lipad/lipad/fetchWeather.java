package com.lipad.lipad;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;




public class fetchWeather extends AsyncTask<Void, Void, Void> {
    String data = "";
    private String details = "";
    private String temperature = "";
    private String icon = "";
    private static final int SHOW_LOG = 1;
    private static final int HIDE_LOG = 0;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://api.darksky.net/forecast/25518f5c378769e6bca4ac90e65f3010/15.132353,120.589614?units=si");
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
            //JSONArray JA = new JSONArray(data); //[Multiple Arrays] only.
            //for(int i=0; i<JA.length(); i++){
            JSONObject JO = (JSONObject) new JSONObject(data);
          /* singleParsed =  "Latitude:" + JO.get("latitude") + "\n" +
           *                     "Longitude:" + JO.get("longitude") + "\n" +
           *                    "Timezone:" + JO.get("timezone") + "\n";
           *
           *
           * dataParsed = dataParsed + singleParsed;
           */
                JSONObject JP = (JSONObject) JO.getJSONObject("currently");
           //   singleParsed = "Time:" + JP.get("time") + "\n";

            details =   "" + JP.get("summary") + "\n" +
                    "Wind: " + JP.getDouble("windSpeed") + " m/s" + "\n" +
                    "Humidity: " + Math.round(JP.getDouble("humidity") * 100) + "%" + "\n" +
                    "Chance of Rain: " + Math.round(JP.getDouble("precipProbability")) + "%" + "\n"; // fix to percentages



          //temperature  =  String.valueOf(Integer.parseInt(JP.get("temperature") + ""));
            temperature = Math.round(JP.getDouble("temperature")) + "°C";
            icon = JP.getString("icon");
            /*if (icon.equals("clear-night")) {
                MainActivity.weatherImage01.setImageResource(R.drawable.lipadlogo);
            } else if (icon.equals("clear-day")) {
                MainActivity.weatherImage01.setImageResource(R.drawable.lipadlogo);
            } else if (icon.equals("rain")) {
                MainActivity.weatherImage01.setImageResource(R.drawable.lipadlogo);
            } else if (icon.equals("wind")) {
                MainActivity.weatherImage01.setImageResource(R.drawable.lipadlogo);
            } else if (icon.equals("fog")) {
                MainActivity.weatherImage01.setImageResource(R.drawable.lipadlogo);
            } else if (icon.equals("cloudy")) {
                MainActivity.weatherImage01.setImageResource(R.drawable.lipadlogo);
            } else if (icon.equals("partly-cloudy-day")) {
                MainActivity.weatherImage01.setImageResource(R.drawable.lipadlogo);
            } else if (icon.equals("partly-cloudy-night")) {
                MainActivity.weatherImage01.setImageResource(R.drawable.lipadlogo);
            } else if (icon.equals("thunderstorm")) {
                MainActivity.weatherImage01.setImageResource(R.drawable.lipadlogo);
            }*/



            } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (icon.equals("clear-night")) {
            MainActivity.weatherImage01.setImageResource(R.drawable.clearnightmdpi);
        } else if (icon.equals("clear-day")) {
            MainActivity.weatherImage01.setImageResource(R.drawable.cleardaymdpi);
        } else if (icon.equals("rain")) {
            MainActivity.weatherImage01.setImageResource(R.drawable.rainmdpi);
        } else if (icon.equals("wind")) {
            MainActivity.weatherImage01.setImageResource(R.drawable.windmdpi);
        } else if (icon.equals("fog")) {
            MainActivity.weatherImage01.setImageResource(R.drawable.fogmdpi);
        } else if (icon.equals("cloudy")) {
            MainActivity.weatherImage01.setImageResource(R.drawable.cloudymdpi);
        } else if (icon.equals("partly-cloudy-day")) {
            MainActivity.weatherImage01.setImageResource(R.drawable.partlycloudydaymdpi);
        } else if (icon.equals("partly-cloudy-night")) {
            MainActivity.weatherImage01.setImageResource(R.drawable.partlycloudynightmdpi);
        } else if (icon.equals("thunderstorm")) {
            MainActivity.weatherImage01.setImageResource(R.drawable.thunderstormmdpi);
        }

        MainActivity.weatherSub01.setText(this.details);
        MainActivity.weatherTitle01.setText(this.temperature);


    }

}
