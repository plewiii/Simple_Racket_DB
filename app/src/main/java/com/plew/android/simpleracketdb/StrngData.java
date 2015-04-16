package com.plew.android.simpleracketdb;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Tim on 3/31/2015.
 */
public class StrngData {
    private static final String TAG = "RacketStrngData";

    private static final String JSON_STRNG_ID = "string_id";
    private static final String JSON_STRNG_DATE = "string_date";
    private static final String JSON_STRNG_NAME = "string_name";
    private static final String JSON_STRNG_MAIN_MFGMODEL = "string_main_mfgmodel";
    private static final String JSON_STRNG_MAIN_GAUGE = "string_main_gauge";
    private static final String JSON_STRNG_MAIN_TENSION = "string_main_tension";
    private static final String JSON_STRNG_MAIN_TENSIONUNITS = "string_main_tensionunits";
    private static final String JSON_STRNG_MAIN_PRESTRETCH = "string_main_prestretch";
    private static final String JSON_STRNG_CROSS_MFGMODEL = "string_cross_mfgmodel";
    private static final String JSON_STRNG_CROSS_GAUGE = "string_cross_gauge";
    private static final String JSON_STRNG_CROSS_TENSION = "string_cross_tension";
    private static final String JSON_STRNG_CROSS_TENSIONUNITS = "string_cross_tensionunits";
    private static final String JSON_STRNG_CROSS_PRESTRETCH = "string_cross_prestretch";
    private static final String JSON_STRNG_COMMENTS = "string_comments";

    private static int count = 0;   // counter used for testing
    private static SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");   // for testing

    private UUID mId;
    private Date mDate;

    private String mName;
    private String mMainMfgModel;
    private String mMainGauge;
    private String mMainTension;
    private String mMainTensionUnits;
    private boolean mMainPrestretch;
    private String mCrossMfgModel;
    private String mCrossGauge;
    private String mCrossTension;
    private String mCrossTensionUnits;
    private boolean mCrossPrestretch;
    private String mComments;

    public StrngData() {
        mId = UUID.randomUUID();
        mDate = new Date();
        //Log.d(TAG, "StrngData(): " + mDate);

        mName = "String #" + Integer.toString(count++);

        mMainMfgModel = "Main Mfg/Model";
        mMainGauge = "16";
        mMainTension = "55";
        mMainTensionUnits = "lbs";
        mMainPrestretch = false;

        mCrossMfgModel = "Crosses Mfg/Model";
        mCrossGauge = "16";
        mCrossTension = "55";
        mCrossTensionUnits = "lbs";
        mCrossPrestretch = false;

        mComments = "None";
    }

    public StrngData(JSONObject json) throws JSONException {
        Log.d(TAG, "StrngData(JSONObject json): ");
        mId = UUID.fromString(json.getString(JSON_STRNG_ID));
        mDate = new Date(json.getLong(JSON_STRNG_DATE));

        mName = json.getString(JSON_STRNG_NAME);
        mMainMfgModel = json.getString(JSON_STRNG_MAIN_MFGMODEL);
        mMainGauge = json.getString(JSON_STRNG_MAIN_GAUGE);
        mMainTension = json.getString(JSON_STRNG_MAIN_TENSION);
        mMainTensionUnits = json.getString(JSON_STRNG_MAIN_TENSIONUNITS);
        mMainPrestretch = json.getBoolean(JSON_STRNG_MAIN_PRESTRETCH);
        mCrossMfgModel = json.getString(JSON_STRNG_CROSS_MFGMODEL);
        mCrossGauge = json.getString(JSON_STRNG_CROSS_GAUGE);
        mCrossTension = json.getString(JSON_STRNG_CROSS_TENSION);
        mCrossTensionUnits = json.getString(JSON_STRNG_CROSS_TENSIONUNITS);
        mCrossPrestretch = json.getBoolean(JSON_STRNG_CROSS_PRESTRETCH);
        mComments = json.getString(JSON_STRNG_COMMENTS);
    }

    public JSONObject toJSON(JSONObject json) throws JSONException {
        Log.d(TAG, "toJSON(): ");
        //JSONObject json = new JSONObject();  // don't need because it is passed in

        json.put(JSON_STRNG_ID, mId.toString());
        json.put(JSON_STRNG_DATE, mDate.getTime());

        json.put(JSON_STRNG_NAME, mName);
        json.put(JSON_STRNG_MAIN_MFGMODEL, mMainMfgModel);
        json.put(JSON_STRNG_MAIN_GAUGE, mMainGauge);
        json.put(JSON_STRNG_MAIN_TENSION, mMainTension);
        json.put(JSON_STRNG_MAIN_TENSIONUNITS, mMainTensionUnits);
        json.put(JSON_STRNG_MAIN_PRESTRETCH, mMainPrestretch);
        json.put(JSON_STRNG_CROSS_MFGMODEL, mCrossMfgModel);
        json.put(JSON_STRNG_CROSS_GAUGE, mCrossGauge);
        json.put(JSON_STRNG_CROSS_TENSION, mCrossTension);
        json.put(JSON_STRNG_CROSS_TENSIONUNITS, mCrossTensionUnits);
        json.put(JSON_STRNG_CROSS_PRESTRETCH, mCrossPrestretch);
        json.put(JSON_STRNG_COMMENTS, mComments);

        return json;
    }

    @Override
    public String toString() {
        return sdf.format(mDate) + ": " + getName();
    }

    public UUID getId() {
        return mId;
    }

    public Date getDate() {
        //Log.d(TAG, "getDate(): " + mDate);
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getName() {
        //Log.d(TAG, "getName(): " + mName);
        return mName;
    }

    public void setName(String name) {
        mName = name;
        //Log.d(TAG, "setName(): " + mName);
    }

    public String getMainMfgModel() {
        //Log.d(TAG, "getMainMfgModel(): " + mMainMfgModel);
        return mMainMfgModel;
    }

    public void setMainMfgModel(String mainMfgModel) {
        mMainMfgModel = mainMfgModel;
        //Log.d(TAG, "setMainMfgModel(): " + mMainMfgModel);
    }

    public String getMainGauge() {
        return mMainGauge;
    }

    public void setMainGauge(String mainGauge) { mMainGauge = mainGauge; }

    public String getMainTension() {
        return mMainTension;
    }

    public void setMainTension(String mainTension) {
        mMainTension = mainTension;
    }

    public String getMainTensionUnits() {
        return mMainTensionUnits;
    }

    public void setMainTensionUnits(String mainTensionUnits) {
        mMainTensionUnits = mainTensionUnits;
    }

    public boolean isMainPrestretch() {
        return mMainPrestretch;
    }

    public void setMainPrestretch(boolean mainPrestretch) { mMainPrestretch = mainPrestretch; }

    public String getCrossMfgModel() {
        //Log.d(TAG, "getCrossMfgModel(): " + mCrossMfgModel);
        return mCrossMfgModel;
    }

    public void setCrossMfgModel(String crossMfgModel) {
        mCrossMfgModel = crossMfgModel;
        //Log.d(TAG, "setCrossMfgModel(): " + mCrossMfgModel);
    }

    public String getCrossGauge() {
        return mCrossGauge;
    }

    public void setCrossGauge(String crossGauge) { mCrossGauge = crossGauge; }

    public String getCrossTension() {
        return mCrossTension;
    }

    public void setCrossTension(String crossTension) { mCrossTension = crossTension; }

    public String getCrossTensionUnits() {
        return mCrossTensionUnits;
    }

    public void setCrossTensionUnits(String crossTensionUnits) {
        mCrossTensionUnits = crossTensionUnits;
    }

    public boolean isCrossPrestretch() {
        return mCrossPrestretch;
    }

    public void setCrossPrestretch(boolean crossPrestretch) { mCrossPrestretch = crossPrestretch; }

    public String getComments() {
        return mComments;
    }

    public void setComments(String comments) { mComments = comments; }
}
