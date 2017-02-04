package com.waracle.androidtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


class ImageLoaderSyncTask extends AsyncTask<String, Integer, Bitmap> {
    private URL url = null;
    private HttpURLConnection urlConnection = null;
    private InputStream inputStream = null;

    ImageLoaderSyncTask(String imageUrl) throws IOException, JSONException {
        url = new URL(imageUrl);
        urlConnection = (HttpURLConnection) url.openConnection();
    }

    @Override
    protected Bitmap doInBackground(String... params){
        try {
            inputStream = urlConnection.getInputStream();
            return StreamUtils.readBitmap(inputStream);
        } catch (IOException e) {
            inputStream = urlConnection.getErrorStream();
        } finally {
            StreamUtils.close(inputStream);
            urlConnection.disconnect();
        }
        return null;
    }
}