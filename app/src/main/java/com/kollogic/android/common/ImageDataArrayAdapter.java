package com.kollogic.android.common;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kollogic.android.simpleracketdb.ImageData;
import com.kollogic.android.simpleracketdb.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Tim on 5/14/2015.
 */
public class ImageDataArrayAdapter extends ArrayAdapter<ImageData> {

    private final Activity context;
    private final int resource;
    private final int textViewResourceId;
    private final int imageViewResourceId;
    private final ArrayList<ImageData> objects;

    // View lookup cache
    private static class ViewHolder {
        TextView txtView;
        ImageView imageView;
    }

    public ImageDataArrayAdapter(Activity context, int resource, int textViewResourceId, int imageViewResourceId,
                                 ArrayList<ImageData> objects) {
        super(context, 0, objects);  // super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.imageViewResourceId = imageViewResourceId;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;  // view lookup cache stored in tag

        //Toast.makeText(context, "getView",
        //        Toast.LENGTH_SHORT).show();

        // Check if an existing view is being reused, otherwise inflate the view
        if(view == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(resource, null,true);

            holder = new ViewHolder();
            holder.txtView = (TextView) view.findViewById(textViewResourceId);
            holder.imageView = (ImageView) view.findViewById(imageViewResourceId);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        // Populate the data into the template view using the data object
        // Main Text
        holder.txtView.setText(objects.get(position).toString());

        // Image
        /* Original code is too slow: rotate IMG bitmap and create thumb bitmap
        File imageFile = new File(objects.get(position).getUri().getPath());
        if (imageFile.exists()){
            //Toast.makeText(context, "imageFile.exists():" + position + " : " + objects.get(position).getUri(),
            //        Toast.LENGTH_SHORT).show();

            // Peter: based off Camera2
            // Peter: original: Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            Bitmap bitmap = rotateBitmapOrientation(imageFile.getAbsolutePath());   // rotate bitmap - correct Samsung Galaxy 4

            // 1st try: holder.imageView.setImageBitmap(bitmap);    // 1st try: gap issue
            // 2nd try:  holder.imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 80, 80, false));    // 2nd try: distorted but acceptable
            holder.imageView.setImageBitmap(ThumbnailUtils.extractThumbnail(bitmap, 80, 80, ThumbnailUtils.OPTIONS_RECYCLE_INPUT));  // 3rd try: thumbnail utility
        }
        else {
            //Toast.makeText(context, "imageFile NOT exists():" + objects.get(position).getUri(),
            //        Toast.LENGTH_LONG).show();
            holder.imageView.setImageResource(R.mipmap.srdb_racket);
        }  */

        // New version: just load the THUMB bitmap. Rotation and thumb bitmap creation moved to RacketFragmentImagesTab.java
        String thumbPath = objects.get(position).getThumbUri().getPath();
        File imageFile = new File(thumbPath);   // Check for THUMB image
        if (imageFile.exists()){
            //Toast.makeText(context, "imageFile.exists():" + position + " : " + objects.get(position).getThumbUri(),
            //        Toast.LENGTH_SHORT).show();

            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            holder.imageView.setImageBitmap(bitmap);
        }
        else {
            //Toast.makeText(context, "imageFile NOT exists():" + objects.get(position).getThumbUri(),
            //        Toast.LENGTH_LONG).show();
            holder.imageView.setImageResource(R.mipmap.srdb_racket);
        }

        // Return the completed view to render on screen
        return view;
    }
}
