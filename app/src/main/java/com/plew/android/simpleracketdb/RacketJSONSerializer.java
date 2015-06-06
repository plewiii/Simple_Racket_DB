package com.plew.android.simpleracketdb;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Tim on 4/14/2015.
 */
public class RacketJSONSerializer {
    private static final String TAG = "RacketJSONSerializer";

    private Context mContext;
    private String mFilename;

    public RacketJSONSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    public ArrayList<Racket> loadRackets() throws IOException, JSONException {
        //Log.d(TAG, "RacketJSONSerializer(): calling loadRackets()");

        ArrayList<Racket> rackets = new ArrayList<Racket>();
        BufferedReader reader = null;
        try {
            // open and read the file into a StringBuilder
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                // line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            // parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            // build the array of Rackets from JSONObjects
            for (int i = 0; i < array.length(); i++) {
                rackets.add(new Racket(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            // we will ignore this one, since it happens when we start fresh
        } finally {
            if (reader != null)
                reader.close();
        }

        return rackets;
    }

    public void saveRackets(ArrayList<Racket> rackets) throws JSONException, IOException {
        //Log.d(TAG, "RacketJSONSerializer(): calling saveRackets()");

        // build an array in JSON
        JSONArray array = new JSONArray();
        for (Racket c : rackets)
            array.put(c.toJSON());

        // write the file to disk
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    public void exportRacketsJSON(ArrayList<Racket> rackets, String externalFilename) throws JSONException, IOException {
        //Log.d(TAG, "RacketJSONSerializer(): calling exportRacketsJSON()");

        // build an array in JSON
        JSONArray array = new JSONArray();
        for (Racket c : rackets)
            array.put(c.toJSON());

        // write the file to disk
        FileOutputStream fileOut = null;
        OutputStreamWriter writer = null;
        try {
            File mFile = new File(externalFilename);
            mFile.createNewFile();
            fileOut = new FileOutputStream(mFile);
            writer = new OutputStreamWriter(fileOut);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
            if (fileOut != null)
                fileOut.close();
        }
    }

    public ArrayList<Racket> importRacketsJSON(String externalFilename) throws IOException, JSONException {
        //Log.d(TAG, "RacketJSONSerializer(): calling importRacketsJSON()");

        ArrayList<Racket> rackets = new ArrayList<Racket>();
        FileInputStream fileIn = null;
        BufferedReader reader = null;
        try {
            // open and read the file into a StringBuilder
            File mFile = new File(externalFilename);
            fileIn = new FileInputStream(mFile);
            reader = new BufferedReader(new InputStreamReader(fileIn));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                // line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            // parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            // build the array of Rackets from JSONObjects
            for (int i = 0; i < array.length(); i++) {
                rackets.add(new Racket(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            // we will ignore this one, since it happens when we start fresh
        } finally {
            if (reader != null)
                reader.close();
            if (fileIn != null)
                fileIn.close();
        }

        return rackets;
    }
}
