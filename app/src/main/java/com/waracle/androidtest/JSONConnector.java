package com.waracle.androidtest;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class JSONConnector extends AsyncTask<String, Integer, String> {
    private HttpURLConnection urlConnection = null;
    private InputStream inputStream = null;

    JSONConnector(String inputUrl) throws IOException, JSONException {
        URL url = new URL(inputUrl);
        urlConnection = (HttpURLConnection) url.openConnection();
    }

    @Override
    protected String doInBackground(String... params){
        try{
            inputStream = urlConnection.getInputStream();

            // Can you think of a way to improve the performance of loading data
            // using HTTP headers???

            // Also, Do you trust any utils thrown your way????

            String JSONString = StreamUtils.readJson(inputStream);

            // Read in charset of HTTP content.
            String charset = StreamUtils.parseCharset(urlConnection.getRequestProperty("Content-Type"));

            // Convert byte array to appropriate encoded string.
            return new String(JSONString.getBytes(), charset);

        } catch (IOException e){
            Log.d("JsonOutput Error:", e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        StreamUtils.close(inputStream);
        urlConnection.disconnect();
    }

}