package com.plew.android.simpleracketdb;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Tim on 3/31/2015.
 */
public class ImageData {
    private static final String TAG = "RacketImageData";

    private static final String JSON_IMAGE_ID = "image_id";
    private static final String JSON_IMAGE_DATE = "image_date";

    private static final String JSON_IMAGE_URI = "image_uri";

    // Future development: private static final String JSON_IMAGE_TEST1 = "image_test1";  // Placeholder for future development
    // Future development: private static final String JSON_IMAGE_TEST2 = "image_test2";

    private static SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");   // for testing

    private UUID mId;
    private Date mDate;

    private Uri mUri;

    // Future development: private Integer mTest1;  // Placeholder for future development
    // Future development: private Integer mTest2;

    public ImageData() {
        mId = UUID.randomUUID();
        mDate = new Date();
        //Log.d(TAG, "ImageData(): " + mDate);

        mUri = Uri.EMPTY;

        // Future development: mTest1 = 5555;   // Placeholder for future development
        // Future development: mTest2 = 6666;
    }

    public ImageData(JSONObject json, int jsonVersion) throws JSONException {
        //Log.d(TAG, "ImageData(JSONObject json, int jsonVersion): ");
        mId = UUID.fromString(json.getString(JSON_IMAGE_ID));
        mDate = new Date(json.getLong(JSON_IMAGE_DATE));

        mUri = Uri.parse(json.getString(JSON_IMAGE_URI));

        // Version test - Placeholder for future development
        // Future development: Log.d(TAG, "ImageData(JSONObject json, int jsonVersion): jsonVersion:" + jsonVersion);
        // Future development: if (jsonVersion == 1) {
        // Future development:     mTest1 = json.getInt(JSON_IMAGE_TEST1);
        // Future development:     mTest2 = json.getInt(JSON_IMAGE_TEST2);
        // Future development:     Log.d(TAG, "ImageData(JSONObject json, int jsonVersion): JSON_IMAGE_TEST1:" + mTest1);
        // Future development:     Log.d(TAG, "ImageData(JSONObject json, int jsonVersion): JSON_IMAGE_TEST2:" + mTest2);
        // Future development: }
        // Future development: else {  // jsonVersion == 0
        // Future development:     mTest1 = 105;  // set to bogus value
        // Future development:     mTest2 = 106;
        // Future development: }
    }

    public JSONObject toJSON(int jsonVersion) throws JSONException {
        //Log.d(TAG, "toJSON(int jsonVersion): ");
        JSONObject json = new JSONObject();

        json.put(JSON_IMAGE_ID, mId.toString());
        json.put(JSON_IMAGE_DATE, mDate.getTime());

        json.put(JSON_IMAGE_URI, mUri);

        // Version test  - Placeholder for future development
        // Future development: Log.d(TAG, "toJSON(int jsonVersion): jsonVersion:" + jsonVersion);
        // Future development: if (jsonVersion == 1) {
        // Future development:     Log.d(TAG, "toJSON(int jsonVersion): JSON_IMAGE_TEST1:" + mTest1);
        // Future development:     Log.d(TAG, "toJSON(int jsonVersion): JSON_IMAGE_TEST2:" + mTest2);
        // Future development:     json.put(JSON_IMAGE_TEST1, mTest1);
        // Future development:     json.put(JSON_IMAGE_TEST2, mTest2);
        // Future development: }

        return json;
    }

    @Override
    public String toString() {
        return getUri().getLastPathSegment();
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

    public Uri getUri() {
        //Log.d(TAG, "getUri(): " + mUri);
        return mUri;
    }

    public void setUri(Uri uri) {
        mUri = uri;
        //Log.d(TAG, "setUri(): " + mUri);
    }

}
