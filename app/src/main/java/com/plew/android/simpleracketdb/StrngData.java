package com.plew.android.simpleracketdb;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private static final String JSON_STRNG_LOCATION = "string_location";
    private static final String JSON_STRNG_COST = "string_cost";

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

    // Future development: private static final String JSON_STRNG_TEST1 = "string_test1";  // Placeholder for future development
    // Future development: private static final String JSON_STRNG_TEST2 = "string_test2";

    private static final String JSON_STRING_USAGEDATA = "string_usagedata";

    private static SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");

    private UUID mId;
    private Date mDate;

    private String mName;
    private String mLocation;
    private String mCost;

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

    // Future development: private Integer mTest1;  // Placeholder for future development
    // Future development: private Integer mTest2;

    private ArrayList<UsageData> mUsageDatas;

    public StrngData() {
        mId = UUID.randomUUID();
        mDate = new Date();
        //Log.d(TAG, "StrngData(): " + mDate);

        mName = "My String";
        mLocation = "My Favorite Tennis Store";
        mCost = "$25";

        mMainMfgModel = "Prince Synthetic Gut Duraflex";
        mMainGauge = "17";
        mMainTension = "58";
        mMainTensionUnits = "lbs";
        mMainPrestretch = false;

        mCrossMfgModel = "Prince Original Synthetic Gut";
        mCrossGauge = "16";
        mCrossTension = "55";
        mCrossTensionUnits = "lbs";
        mCrossPrestretch = false;

        mComments = "None";

        // Future development: mTest1 = 3333;   // Placeholder for future development
        // Future development: mTest2 = 4444;

        mUsageDatas = new ArrayList<UsageData>();
    }

    public StrngData(JSONObject json, int jsonVersion) throws JSONException {
        //Log.d(TAG, "StrngData(JSONObject json, int jsonVersion): ");
        mId = UUID.fromString(json.getString(JSON_STRNG_ID));
        mDate = new Date(json.getLong(JSON_STRNG_DATE));

        mName = json.getString(JSON_STRNG_NAME);
        mLocation = json.getString(JSON_STRNG_LOCATION);
        mCost = json.getString(JSON_STRNG_COST);

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

        // Version test - Placeholder for future development
        // Future development: Log.d(TAG, "StrngData(JSONObject json, int jsonVersion): jsonVersion:" + jsonVersion);
        // Future development: if (jsonVersion == 1) {
        // Future development:     mTest1 = json.getInt(JSON_STRNG_TEST1);
        // Future development:     mTest2 = json.getInt(JSON_STRNG_TEST2);
        // Future development:     Log.d(TAG, "StrngData(JSONObject json, int jsonVersion): JSON_STRNG_TEST1:" + mTest1);
        // Future development:     Log.d(TAG, "StrngData(JSONObject json, int jsonVersion): JSON_STRNG_TEST2:" + mTest2);
        // Future development: }
        // Future development: else {  // jsonVersion == 0
        // Future development:     mTest1 = 103;  // set to bogus value
        // Future development:     mTest2 = 104;
        // Future development: }

        JSONArray jsonUsageDataArray = json.getJSONArray(JSON_STRING_USAGEDATA);
        mUsageDatas = new ArrayList<UsageData>();
        for (int i = 0; i < jsonUsageDataArray.length(); i++) {
            JSONObject jsonUsageDataObj = jsonUsageDataArray.getJSONObject(i);
            UsageData c = new UsageData(jsonUsageDataObj, jsonVersion);
            mUsageDatas.add(c);
        }
        //Log.d(TAG, "Racket(JSONObject json, int jsonVersion): mStrngDatas.size():" + mStrngDatas.size());
    }

    public JSONObject toJSON(int jsonVersion) throws JSONException {
        //Log.d(TAG, "toJSON(int jsonVersion): ");
        JSONObject json = new JSONObject();

        json.put(JSON_STRNG_ID, mId.toString());
        json.put(JSON_STRNG_DATE, mDate.getTime());

        json.put(JSON_STRNG_NAME, mName);
        json.put(JSON_STRNG_LOCATION, mLocation);
        json.put(JSON_STRNG_COST, mCost);

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

        // Version test  - Placeholder for future development
        // Future development: Log.d(TAG, "toJSON(int jsonVersion): jsonVersion:" + jsonVersion);
        // Future development: if (jsonVersion == 1) {
        // Future development:     Log.d(TAG, "toJSON(int jsonVersion): JSON_RACKET_TEST1:" + mTest1);
        // Future development:     Log.d(TAG, "toJSON(int jsonVersion): JSON_RACKET_TEST2:" + mTest2);
        // Future development:     json.put(JSON_STRNG_TEST1, mTest1);
        // Future development:     json.put(JSON_STRNG_TEST2, mTest2);
        // Future development: }

        // Add Usage - need json array to hold the list
        JSONArray jsonUsageDataArray = new JSONArray();
        for (UsageData c : mUsageDatas) {
            JSONObject jsonUsageDataObj = c.toJSON(jsonVersion);
            jsonUsageDataArray.put(jsonUsageDataObj);
        }
        json.put(JSON_STRING_USAGEDATA, jsonUsageDataArray);

        return json;
    }

    @Override
    public String toString() {
        return sdf.format(mDate);
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

    public String getLocation() {
        //Log.d(TAG, "getLocation(): " + mLocation);
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
        //Log.d(TAG, "setLocation(): " + mLocation);
    }

    public String getCost() {
        //Log.d(TAG, "getCost(): " + mCost);
        return mCost;
    }

    public void setCost(String cost) {
        mCost = cost;
        //Log.d(TAG, "setCost(): " + mCost);
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

    public UsageData getUsageData(UUID id) {
        for (UsageData c : mUsageDatas) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    public void addUsageData(UsageData c) {
        // Original: add to end: mUsageDatas.add(c);
        mUsageDatas.add(0, c);  // Add to beginning
        // kluge: moved to RacketFragment: RacketList.get(getActivity()).saveRackets();
    }

    public void deleteUsageData(UsageData c) {
        mUsageDatas.remove(c);
        // kluge: moved to RacketFragment: RacketList.get(getActivity()).saveRackets();
    }

    public ArrayList<UsageData> getUsageDatas() {
        return mUsageDatas;
    }
}
