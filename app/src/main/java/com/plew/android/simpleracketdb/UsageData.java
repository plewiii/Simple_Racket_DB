package com.plew.android.simpleracketdb;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Tim on 3/31/2015.
 */
public class UsageData {
    private static final String TAG = "RacketUsageData";

    private static final String JSON_USAGE_ID = "usage_id";
    private static final String JSON_USAGE_DATE = "usage_date";

    private static final String JSON_USAGE_NAME = "usage_name";
    private static final String JSON_USAGE_HOURS = "usage_hours";

    private static final String JSON_USAGE_COMMENTS = "usage_comments";

    // Future development: private static final String JSON_USAGE_TEST1 = "usage_test1";  // Placeholder for future development
    // Future development: private static final String JSON_USAGE_TEST2 = "usage_test2";

    private static SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");

    private UUID mId;
    private Date mDate;

    private String mName;
    private double mHours;

    private String mComments;

    // Future development: private Integer mTest1;  // Placeholder for future development
    // Future development: private Integer mTest2;

    public UsageData() {
        mId = UUID.randomUUID();
        mDate = new Date();
        //Log.d(TAG, "StrngData(): " + mDate);

        mName = "My Usage";
        mHours = 1.25d;

        mComments = "None";

        // Future development: mTest1 = 3333;   // Placeholder for future development
        // Future development: mTest2 = 4444;
    }

    public UsageData(JSONObject json, int jsonVersion) throws JSONException {
        //Log.d(TAG, "StrngData(JSONObject json, int jsonVersion): ");
        mId = UUID.fromString(json.getString(JSON_USAGE_ID));
        mDate = new Date(json.getLong(JSON_USAGE_DATE));

        mName = json.getString(JSON_USAGE_NAME);
        mHours = json.getDouble(JSON_USAGE_HOURS);

        mComments = json.getString(JSON_USAGE_COMMENTS);

        // Version test - Placeholder for future development
        // Future development: Log.d(TAG, "StrngData(JSONObject json, int jsonVersion): jsonVersion:" + jsonVersion);
        // Future development: if (jsonVersion == 1) {
        // Future development:     mTest1 = json.getInt(JSON_USAGE_TEST1);
        // Future development:     mTest2 = json.getInt(JSON_USAGE_TEST2);
        // Future development:     Log.d(TAG, "StrngData(JSONObject json, int jsonVersion): JSON_USAGE_TEST1:" + mTest1);
        // Future development:     Log.d(TAG, "StrngData(JSONObject json, int jsonVersion): JSON_USAGE_TEST2:" + mTest2);
        // Future development: }
        // Future development: else {  // jsonVersion == 0
        // Future development:     mTest1 = 103;  // set to bogus value
        // Future development:     mTest2 = 104;
        // Future development: }
    }

    public JSONObject toJSON(int jsonVersion) throws JSONException {
        //Log.d(TAG, "toJSON(int jsonVersion): ");
        JSONObject json = new JSONObject();

        json.put(JSON_USAGE_ID, mId.toString());
        json.put(JSON_USAGE_DATE, mDate.getTime());

        json.put(JSON_USAGE_NAME, mName);
        json.put(JSON_USAGE_HOURS, mHours);

        json.put(JSON_USAGE_COMMENTS, mComments);

        // Version test  - Placeholder for future development
        // Future development: Log.d(TAG, "toJSON(int jsonVersion): jsonVersion:" + jsonVersion);
        // Future development: if (jsonVersion == 1) {
        // Future development:     Log.d(TAG, "toJSON(int jsonVersion): JSON_RACKET_TEST1:" + mTest1);
        // Future development:     Log.d(TAG, "toJSON(int jsonVersion): JSON_RACKET_TEST2:" + mTest2);
        // Future development:     json.put(JSON_USAGE_TEST1, mTest1);
        // Future development:     json.put(JSON_USAGE_TEST2, mTest2);
        // Future development: }

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

    public double getHours() {
        //Log.d(TAG, "getHours(): " + mHours);
        return mHours;
    }

    public void setHours(double hours) {
        mHours = hours;
        //Log.d(TAG, "setHours(): " + mHours);
    }

    public String getComments() {
        return mComments;
    }

    public void setComments(String comments) { mComments = comments; }
}
