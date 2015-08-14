package com.plew.android.common.tabview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.plew.android.simpleracketdb.ImageData;
import com.plew.android.simpleracketdb.R;

import java.io.File;
import java.io.IOException;
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
        }

        // Return the completed view to render on screen
        return view;
    }

    // https://guides.codepath.com/android/Accessing-the-Camera-and-Stored-Media#overview
    private Bitmap rotateBitmapOrientation(String photoFilePath) {
        // Create and configure BitmapFactory
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFilePath, bounds);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);

        ExifInterface exif;
        try {
            // Read EXIF Data
            exif = new ExifInterface(photoFilePath);
        } catch (IOException e) {
            return bm;
        }

        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
        // Rotate Bitmap
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        // Return result
        return rotatedBitmap;
    }
}
