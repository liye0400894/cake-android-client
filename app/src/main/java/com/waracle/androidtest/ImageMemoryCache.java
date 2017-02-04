package com.waracle.androidtest;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Li on 2017/2/3.
 */

public class ImageMemoryCache {
    private LruCache<String, Bitmap> imageMemoryCache;

    public ImageMemoryCache(Activity activityToSave){
        ActivityManager am = (ActivityManager) activityToSave.getSystemService(
                Context.ACTIVITY_SERVICE);
        int maxKb = am.getMemoryClass() * 1024;
        int cacheSize = maxKb / 8; // 1/8th of total ram
        imageMemoryCache = new LruCache<String, Bitmap>(cacheSize);
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            imageMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        if (key == null){
            return  null;
        }else{
            return imageMemoryCache.get(key);
        }
    }
}