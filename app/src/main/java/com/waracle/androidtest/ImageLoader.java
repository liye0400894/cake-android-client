package com.waracle.androidtest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;

import org.json.JSONException;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.concurrent.ExecutionException;

class ImageLoader {

    private ImageMemoryCache imageMemoryCache;

    public ImageLoader(Activity activity) {
        /**
         * */
        imageMemoryCache = new ImageMemoryCache(activity);
    }

    /**
     *
     * @param imageUrl   image rul
     * @return null | Bitmap
     * @throws JSONException
     */
    Bitmap load(String imageUrl) throws JSONException {
        if (TextUtils.isEmpty(imageUrl)) {
            throw new InvalidParameterException("URL is empty!");
        }

        Bitmap bitmap = imageMemoryCache.getBitmapFromMemCache(imageUrl);

        try {
            if (bitmap != null) {
                return bitmap;
            } else {
                ImageLoaderSyncTask imageLoadingSyncTask = new ImageLoaderSyncTask(imageUrl);
                bitmap = imageLoadingSyncTask.execute().get();
                imageMemoryCache.addBitmapToMemoryCache(imageUrl, bitmap);
                return bitmap;
            }
        }catch (IOException | ExecutionException | InterruptedException e){
            e.printStackTrace();
        }

        return null;
    }
}
