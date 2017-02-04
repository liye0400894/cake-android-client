package com.waracle.androidtest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

class MyAdapter extends BaseAdapter {

    // Can you think of a better way to represent these items???
    private JSONArray jsonObjects = null;
    private ImageLoader imageLoader = null;
    private Activity mainActivity = null;


    MyAdapter(Activity activityToSave) {
        this(new JSONArray());
        mainActivity = activityToSave;
        imageLoader = new ImageLoader(activityToSave);
    }

    MyAdapter(JSONArray items) {
        jsonObjects = items;
    }

    @Override
    public int getCount() {
        return jsonObjects.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return jsonObjects.getJSONObject(position);
        }
        catch (JSONException e) {
            Log.e("", e.getMessage());
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder itemViewHolder;

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mainActivity);
            convertView = inflater.inflate(R.layout.list_item_layout, parent, false);

            itemViewHolder = new ViewHolder();
            itemViewHolder.assignViewHolder(convertView);

            convertView.setTag(itemViewHolder);
        }
        else {
            itemViewHolder = (ViewHolder)convertView.getTag();
        }

        //if (!itemViewHolder.isLoaded()) {
            JSONObject item = (JSONObject) getItem(position);
            if (item != null){
                try {
                    itemViewHolder.fillViewHolderWithData(item, imageLoader);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        //}
        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView description;
        ImageView image;
        boolean loaded;

        void assignViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.desc);
            image = (ImageView) view.findViewById(R.id.image);
            loaded = false;
        }

        void fillViewHolderWithData(JSONObject object, ImageLoader imageLoader) throws JSONException{
            title.setText(object.getString("title"));
            description.setText(object.getString("desc"));
            Bitmap img = imageLoader.load(object.getString("image"));
            if (img != null) {
                image.setImageBitmap(img);
                loaded = true;
            }
        }

        boolean isLoaded() {
            return loaded;
        }
    }

    void setItems(JSONArray items) {
        jsonObjects = items;
    }
}