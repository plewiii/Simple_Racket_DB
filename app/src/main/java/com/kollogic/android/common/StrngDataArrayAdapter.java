package com.kollogic.android.common;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kollogic.android.simpleracketdb.StrngData;
import com.kollogic.android.simpleracketdb.R;
import com.kollogic.android.simpleracketdb.UsageData;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Tim on 5/14/2015.
 */
public class StrngDataArrayAdapter extends ArrayAdapter<StrngData> {

    private final Activity context;
    private final int resource;
    private final int textViewResourceId;
    private final int imageViewResourceId;
    private final int textView2ResourceId;
    private final int textView3ResourceId;
    private final ArrayList<StrngData> objects;

    // View lookup cache
    private static class ViewHolder {
        TextView txtView;
        ImageView imageView;
        TextView txtView2;
        TextView txtView3;
    }

    public StrngDataArrayAdapter(Activity context, int resource, int textViewResourceId, int imageViewResourceId,
                                 int textView2ResourceId, int textView3ResourceId,
                                 ArrayList<StrngData> objects) {
        super(context, 0, objects);  // super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.imageViewResourceId = imageViewResourceId;
        this.textView2ResourceId = textView2ResourceId;
        this.textView3ResourceId = textView3ResourceId;
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
            holder.txtView2 = (TextView) view.findViewById(textView2ResourceId);
            holder.txtView3 = (TextView) view.findViewById(textView3ResourceId);
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
        holder.imageView.setImageResource(R.mipmap.mystring);

        // Text2 and Text 3
        holder.txtView2.setText("Mains/Crosses: " + objects.get(position).getMainTension()
                + "/" + objects.get(position).getCrossTension());

        ArrayList<UsageData> mUsageDatas = objects.get(position).getUsageDatas();
        double total_hours = 0.0;
        for (UsageData c : mUsageDatas) {
            total_hours = total_hours + c.getHours();
        }
        DecimalFormat df = new DecimalFormat("0.0");
        holder.txtView3.setText("Hours Played: " + df.format(total_hours));   // String.valueOf(total_hours)

        // Return the completed view to render on screen
        return view;
    }
}
