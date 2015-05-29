package com.plew.android.simpleracketdb;

import android.net.Uri;

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

    private static SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");   // for testing

    private UUID mId;
    private Date mDate;

    private Uri mUri;

    public ImageData() {
        mId = UUID.randomUUID();
        mDate = new Date();
        //Log.d(TAG, "ImageData(): " + mDate);

        mUri = Uri.EMPTY;
    }

    public ImageData(JSONObject json) throws JSONException {
        //Log.d(TAG, "ImageData(JSONObject json): ");
        mId = UUID.fromString(json.getString(JSON_IMAGE_ID));
        mDate = new Date(json.getLong(JSON_IMAGE_DATE));

        mUri = Uri.parse(json.getString(JSON_IMAGE_URI));
    }

    public JSONObject toJSON() throws JSONException {
        //Log.d(TAG, "toJSON(): ");
        JSONObject json = new JSONObject();

        json.put(JSON_IMAGE_ID, mId.toString());
        json.put(JSON_IMAGE_DATE, mDate.getTime());

        json.put(JSON_IMAGE_URI, mUri);

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
