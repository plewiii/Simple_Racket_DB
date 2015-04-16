package com.plew.android.simpleracketdb;

import android.content.Context;
import android.util.Log;

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
            Log.d(TAG, "RacketList(): mRackets:" + mRackets.size());
        } catch (Exception e) {
            mRackets = new ArrayList<Racket>();
            Log.e(TAG, "Error loading rackets: ", e);
        }

        // Chapter 17: delete: mRackets = new ArrayList<Racket>();
        // Chapter 16: delete: for (int i = 0; i < 100; i++) {
        // Chapter 16: delete:     Racket c = new Racket();
        // Chapter 16: delete:     c.setMfgModel("Racket #" + i);
        // Chapter 16: delete:     mRackets.add(c);
        // Chapter 16: delete: }
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

}
