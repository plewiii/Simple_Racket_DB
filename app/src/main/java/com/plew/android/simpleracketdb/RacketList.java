package com.plew.android.simpleracketdb;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Tim on 4/2/2015.
 */
public class RacketList {

    private static final String TAG = "RacketList";
    private static final String FILENAME = "rackets.json";

    private ArrayList<Racket> mRackets;
    private RacketJSONSerializer mSerializer;

    private static RacketList sRacketList;
    private Context mAppContext;

    private RacketList(Context appContext) {
        mAppContext = appContext;
        mSerializer = new RacketJSONSerializer(mAppContext, FILENAME);

        try {
            mRackets = mSerializer.loadRackets();
            Log.d(TAG, "RacketList(): mRackets.size():" + mRackets.size());
            for (int i = 0; i < mRackets.size(); i++) {
                Racket c = mRackets.get(i);
                int count = c.getStrngDatas().size();
                Log.d(TAG, "RacketList(): racket(" + i + "): " + count);
            }
        } catch (Exception e) {
            mRackets = new ArrayList<Racket>();
            Log.e(TAG, "Error loading rackets: ", e);
        }
    }

    public static RacketList get(Context c) {
        if (sRacketList == null) {
            sRacketList = new RacketList(c.getApplicationContext());
        }
        return sRacketList;
    }

    public Racket getRacket(UUID id) {
        for (Racket c : mRackets) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    public void addRacket(Racket c) {
        mRackets.add(c);
        saveRackets();
    }

    public ArrayList<Racket> getRackets() {
        return mRackets;
    }

    public void deleteRacket(Racket c) {
        // Delete photos associated with racket
        // Peter: not sure if this operation should be done here or if there is a more efficient way
        ArrayList<ImageData> mImageDatas = c.getImageDatas();
        for (ImageData imagedata : mImageDatas) {
            c.deletePhoto(imagedata.getUri());
        }

        mRackets.remove(c);
        saveRackets();
    }

    public boolean saveRackets() {
        try {
            mSerializer.saveRackets(mRackets);
            Log.d(TAG, "saveRackets()");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving Rackets: " + e);
            return false;
        }
    }

    public boolean exportRacketsJSON() {
        try {
            // Peter: delete: File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
            // Peter: delete:         Environment.DIRECTORY_PICTURES), mAppContext.getPackageName());
            File mediaStorageDir = mAppContext.getExternalFilesDir("");
            String externalFilename = mediaStorageDir.getPath() + File.separator + FILENAME;
            mSerializer.exportRacketsJSON(mRackets, externalFilename);
            Log.d(TAG, "exportRacketsJSON()");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "exportRacketsJSON(): Error saving Rackets to External Drive: " + e);
            return false;
        }
    }

    public boolean importRacketsJSON() {
        try {
            // Peter: use if import and export uses same location: File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
            // Peter: use if import and export uses same location:         Environment.DIRECTORY_PICTURES), mAppContext.getPackageName());
            // Peter: use if import and export uses same location: String externalFilename = mediaStorageDir.getPath() + File.separator + FILENAME;

            // racket.json is expected to be located in "download" folder
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String externalFilename = path + File.separator + FILENAME;

            ArrayList<Racket> rackets = mSerializer.importRacketsJSON(externalFilename);
            // Replace racket list if valid data is imported
            if (rackets.size() > 0) {
                for (Racket c : mRackets) {
                    // Delete photos associated with racket
                    // Peter: not sure if this operation should be done here or if there is a more efficient way
                    ArrayList<ImageData> mImageDatas = c.getImageDatas();
                    for (ImageData imagedata : mImageDatas) {
                        c.deletePhoto(imagedata.getUri());
                    }
                }

                mRackets = rackets;
                Log.d(TAG, "importRacketsJSON(): mRackets.size():" + mRackets.size());
                for (int i = 0; i < mRackets.size(); i++) {
                    Racket c = mRackets.get(i);
                    int count = c.getStrngDatas().size();
                    Log.d(TAG, "importRacketsJSON(): racket(" + i + "): " + count);
                }
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            Log.e(TAG, "importRacketsJSON(): Error loading rackets: ", e);
            return false;
        }
    }
}
