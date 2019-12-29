package com.example.checkzip;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, String> {
    String result = "";
    URL url;
    HttpURLConnection urlConnection = null;

    protected String doInBackground(String... urls) {
        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();
            while (data != -1) {
                char current = (char) data;
                result += current;
                data = reader.read();
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;

    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            String weatherInfo = jsonObject.getString("weather");
            JSONObject weatherDatas = new JSONObject(jsonObject.getString("main"));
            double tempInt = Double.parseDouble(weatherDatas.getString("temp"));
            int tempIn = (int) (tempInt * 1.8 - 459.67);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
