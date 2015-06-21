package com.plew.android.common.tabview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
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

            // delete???: Drawable oldDrawable = holder.imageView.getDrawable();
            // delete???: if (oldDrawable != null) {
            // delete???:     ((BitmapDrawable)oldDrawable).getBitmap().recycle();
            // delete???: }

            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            // 1st try: holder.imageView.setImageBitmap(bitmap);    // gap issue
            // 2nd try: holder.imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 120, 120, false));    // 120x120

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            int scaleToUse = 20; // this will be our percentage
            int sizeY = height * scaleToUse / 100;
            int sizeX = bitmap.getWidth() * sizeY / bitmap.getHeight();
            holder.imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, sizeX, sizeY, false));

            // this works too: BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
            // this works too: holder.imageView.setImageDrawable(drawable);
        }
        else {
            //Toast.makeText(context, "imageFile NOT exists():" + objects.get(position).getUri(),
            //        Toast.LENGTH_LONG).show();
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        }

        // Return the completed view to render on screen
        return view;
    }
}
