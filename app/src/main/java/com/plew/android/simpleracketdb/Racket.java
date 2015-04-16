package com.plew.android.simpleracketdb;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Tim on 3/31/2015.
 */
public class Racket {
    private static final String TAG = "Racket";

    private static final String JSON_RACKET_ID = "racket_id";
    private static final String JSON_RACKET_DATE = "racket_date";
    private static final String JSON_RACKET_NAME = "racket_name";
    private static final String JSON_RACKET_SERIALNUMBER = "racket_serialnumber";
    private static final String JSON_RACKET_MFGMODEL = "racket_mfgmodel";
    private static final String JSON_RACKET_HEADSIZE = "racket_headsize";
    private static final String JSON_RACKET_LENGTH = "racket_length";
    private static final String JSON_RACKET_STRUNGWEIGHT = "racket_strungweight";
    private static final String JSON_RACKET_BALANCE = "racket_balance";
    private static final String JSON_RACKET_SWINGWEIGHT = "racket_swingweight";
    private static final String JSON_RACKET_STIFFNESS = "racket_stiffness";
    private static final String JSON_RACKET_BEAMWIDTH = "racket_beamwidth";
    private static final String JSON_RACKET_COMPOSITION = "racket_composition";
    private static final String JSON_RACKET_POWERLEVEL = "racket_powerlevel";
    private static final String JSON_RACKET_STROKESTYLE = "racket_strokestyle";
    private static final String JSON_RACKET_SWINGSPEED = "racket_swingspeed";
    private static final String JSON_RACKET_COLORS = "racket_colors";
    private static final String JSON_RACKET_GRIPTYPE = "racket_griptype";
    private static final String JSON_RACKET_STRINGPATTERN = "racket_stringpattern";
    private static final String JSON_RACKET_STRINGTENSION = "racket_stringtension";
    private static final String JSON_RACKET_COMMENTS = "racket_comments";
    private static final String JSON_RACKET_NUMSTRNGDATA = "racket_numstrngdata";

    private static int count = 0;   // counter used for testing

    private UUID mId;
    private Date mDate;

    private String mName;
    private String mSerialNumber;
    private String mMfgModel;
    private String mHeadSize;
    private String mLength;
    private String mStrungWeight;
    private String mBalance;
    private String mSwingweight;
    private String mStiffness;
    private String mBeamWidth;
    private String mComposition;
    private String mPowerLevel;
    private String mStrokeStyle;
    private String mSwingSpeed;
    private String mRacketColors;
    private String mGripType;
    private String mStringPattern;
    private String mStringTension;
    private String mComments;

    private ArrayList<StrngData> mStrngDatas;

    public Racket() {
        mId = UUID.randomUUID();
        mDate = new Date();
        //Log.d(TAG, "Racket(): " + mId + " " + mDate);

        mName = "Racket #" + Integer.toString(count++);
        mSerialNumber = "00000";
        mMfgModel = "Mfg/Model";
        mHeadSize = "98 sq. in.";
        mLength = "27 in";
        mStrungWeight = "11.5 oz";
        mBalance = "12.75 in";
        mSwingweight = "326";
        mStiffness = "68";
        mBeamWidth = "20.5 mm/ 23.5mm/ 21.5mm";
        mComposition = "Graphite";
        mPowerLevel = "Low";
        mStrokeStyle = "Full";
        mSwingSpeed = "Fast";
        mRacketColors = "Orange and Black";
        mGripType = "Tourna-Grip";
        mStringPattern = "16 Mains/ 19 Crosses/nMain skip: 8T 8H/nTwo Pieces/nNoShgared Holes";
        mStringTension = "48-57 lbs";

        mComments = "None";

        mStrngDatas = new ArrayList<StrngData>();
        //for (int i = 0; i < 10; i++) {
        //    StrngData c = new StrngData();
        //    c.setMainName("String #" + i);
        //    mStrngDatas.add(c);
        //}
    }

    public Racket(JSONObject json) throws JSONException {
        Log.d(TAG, "Racket(JSONObject json): ");
        mId = UUID.fromString(json.getString(JSON_RACKET_ID));
        mDate = new Date(json.getLong(JSON_RACKET_DATE));

        mName = json.getString(JSON_RACKET_NAME);
        mSerialNumber = json.getString(JSON_RACKET_SERIALNUMBER);
        mMfgModel = json.getString(JSON_RACKET_MFGMODEL);
        mHeadSize = json.getString(JSON_RACKET_HEADSIZE);
        mLength = json.getString(JSON_RACKET_LENGTH);
        mStrungWeight = json.getString(JSON_RACKET_STRUNGWEIGHT);
        mBalance = json.getString(JSON_RACKET_BALANCE);
        mSwingweight = json.getString(JSON_RACKET_SWINGWEIGHT);
        mStiffness = json.getString(JSON_RACKET_STIFFNESS);
        mBeamWidth = json.getString(JSON_RACKET_BEAMWIDTH);
        mComposition = json.getString(JSON_RACKET_COMPOSITION);
        mPowerLevel = json.getString(JSON_RACKET_POWERLEVEL);
        mStrokeStyle = json.getString(JSON_RACKET_STROKESTYLE);
        mSwingSpeed = json.getString(JSON_RACKET_SWINGSPEED);
        mRacketColors = json.getString(JSON_RACKET_COLORS);
        mGripType = json.getString(JSON_RACKET_GRIPTYPE);
        mStringPattern = json.getString(JSON_RACKET_STRINGPATTERN);
        mStringTension = json.getString(JSON_RACKET_STRINGTENSION);
        mComments = json.getString(JSON_RACKET_COMMENTS);

        int count = json.getInt(JSON_RACKET_NUMSTRNGDATA);
        Log.d(TAG, "Racket(JSONObject json): count:" + count);
        mStrngDatas = new ArrayList<StrngData>();  // Peter
        // parse the JSON using JSONTokener
        //JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
        // build the array of Rackets from JSONObjects
        //for (int i = 0; i < array.length(); i++) {     // array.length()
        //    mStrngDatas.add(new StrngData(json);
        //}
        for (int i = 0; i < count; i++) {
            StrngData c = new StrngData(json);
            mStrngDatas.add(c);
        }
        Log.d(TAG, "Racket(JSONObject json): mStrngDatas.size():" + mStrngDatas.size());

    }

    public JSONObject toJSON() throws JSONException {
        Log.d(TAG, "toJSON(): ");
        JSONObject json = new JSONObject();

        json.put(JSON_RACKET_ID, mId.toString());
        json.put(JSON_RACKET_DATE, mDate.getTime());

        json.put(JSON_RACKET_NAME, mName);
        json.put(JSON_RACKET_SERIALNUMBER, mSerialNumber);
        json.put(JSON_RACKET_MFGMODEL, mMfgModel);
        json.put(JSON_RACKET_HEADSIZE, mHeadSize);
        json.put(JSON_RACKET_LENGTH, mLength);
        json.put(JSON_RACKET_STRUNGWEIGHT, mStrungWeight);
        json.put(JSON_RACKET_BALANCE, mBalance);
        json.put(JSON_RACKET_SWINGWEIGHT, mSwingweight);
        json.put(JSON_RACKET_STIFFNESS, mStiffness);
        json.put(JSON_RACKET_BEAMWIDTH, mBeamWidth);
        json.put(JSON_RACKET_COMPOSITION, mComposition);
        json.put(JSON_RACKET_POWERLEVEL, mPowerLevel);
        json.put(JSON_RACKET_STROKESTYLE, mStrokeStyle);
        json.put(JSON_RACKET_SWINGSPEED, mSwingSpeed);
        json.put(JSON_RACKET_COLORS, mRacketColors);
        json.put(JSON_RACKET_GRIPTYPE, mGripType);
        json.put(JSON_RACKET_STRINGPATTERN, mStringPattern);
        json.put(JSON_RACKET_STRINGTENSION, mStringTension);
        json.put(JSON_RACKET_COMMENTS, mComments);

        int count = mStrngDatas.size();
        json.put(JSON_RACKET_NUMSTRNGDATA, count);
        //for (StrngData c : mStrngDatas) {
        //    json.put(c.toJSON());
        //}
        for (int i = 0; i < count; i++) {
            StrngData c = mStrngDatas.get(i);
            c.toJSON(json);
        }

        return json;
    }

    @Override
    public String toString() {
        return mName;
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

    public String getSerialNumber() {
        //Log.d(TAG, "getSerialNumber(): " + mSerialNumber);
        return mSerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        mSerialNumber = serialNumber;
        //Log.d(TAG, "setSerialNumber(): " + mSerialNumber);
    }

    public String getMfgModel() {
        //Log.d(TAG, "getMfgModel(): " + mMfgModel);
        return mMfgModel;
    }

    public void setMfgModel(String mfgModel) {
        mMfgModel = mfgModel;
        //Log.d(TAG, "setMfgModel(): " + mMfgModel);
    }

    public String getHeadSize() {
        //Log.d(TAG, "getHeadSize(): " + mHeadSize);
        return mHeadSize;
    }

    public void setHeadSize(String headSize) {
        mHeadSize = headSize;
        //Log.d(TAG, "setHeadSize(): " + mHeadSize);
    }

    public String getLength() {
        //Log.d(TAG, "getLength(): " + mLength);
        return mLength;
    }

    public void setLength(String length) {
        mLength = length;
        //Log.d(TAG, "setLength(): " + mLength);
    }

    public String getStrungWeight() {
        //Log.d(TAG, "getStrungWeight(): " + mStrungWeight);
        return mStrungWeight;
    }

    public void setStrungWeight(String strungWeight) {
        mStrungWeight = strungWeight;
        //Log.d(TAG, "setStrungWeight(): " + mStrungWeight);
    }

    public String getSwingweight() {
        //Log.d(TAG, "getSwingweight(): " + mSwingweight);
        return mSwingweight;
    }

    public void setSwingweight(String swingweight) {
        mSwingweight = swingweight;
        //Log.d(TAG, "setSwingweight(): " + mSwingweight);
    }

    public String getStiffness() {
        //Log.d(TAG, "getStiffness(): " + mStiffness);
        return mStiffness;
    }

    public void setStiffness(String stiffness) {
        mStiffness = stiffness;
        //Log.d(TAG, "setStiffness(): " + mStiffness);
    }

    public String getBeamWidth() {
        //Log.d(TAG, "getBeamWidth(): " + mBeamWidth);
        return mBeamWidth;
    }

    public void setBeamWidth(String beamWidth) {
        mBeamWidth = beamWidth;
        //Log.d(TAG, "setBeamWidth(): " + mBeamWidth);
    }

    public String getComposition() {
        //Log.d(TAG, "getComposition(): " + mComposition);
        return mComposition;
    }

    public void setComposition(String composition) {
        mComposition = composition;
        //Log.d(TAG, "setComposition(): " + mComposition);
    }

    public String getPowerLevel() {
        //Log.d(TAG, "getPowerLevel(): " + mPowerLevel);
        return mPowerLevel;
    }

    public void setPowerLevel(String powerLevel) {
        mPowerLevel = powerLevel;
        //Log.d(TAG, "setPowerLevel(): " + mPowerLevel);
    }

    public String getStrokeStyle() {
        //Log.d(TAG, "getStrokeStyle(): " + mStrokeStyle);
        return mStrokeStyle;
    }

    public void setStrokeStyle(String strokeStyle) {
        mStrokeStyle = strokeStyle;
        //Log.d(TAG, "setStrokeStyle(): " + mStrokeStyle);
    }

    public String getRacketColors() {
        //Log.d(TAG, "getRacketColors(): " + mRacketColors);
        return mRacketColors;
    }

    public void setRacketColors(String racketColors) {
        mRacketColors = racketColors;
        //Log.d(TAG, "setRacketColors(): " + mRacketColors);
    }

    public String getGripType() {
        //Log.d(TAG, "getGripType(): " + mGripType);
        return mGripType;
    }

    public void setGripType(String gripType) {
        mGripType = gripType;
        //Log.d(TAG, "setGripType(): " + mGripType);
    }

    public String getStringPattern() {
        //Log.d(TAG, "getStringPattern(): " + mStringPattern);
        return mStringPattern;
    }

    public void setStringPattern(String stringPattern) {
        mStringPattern = stringPattern;
        //Log.d(TAG, "setStringPattern(): " + mStringPattern);
    }

    public String getStringTension() {
        //Log.d(TAG, "getStringTension(): " + mStringTension);
        return mStringTension;
    }

    public void setStringTension(String stringTension) {
        mStringTension = stringTension;
        //Log.d(TAG, "setStringTension(): " + mStringTension);
    }

    public String getComments() {
        //Log.d(TAG, "getComments(): " + mComments);
        return mComments;
    }

    public void setComments(String comments) {
        mComments = comments;
        //Log.d(TAG, "setComments(): " + mComments);
    }

    public StrngData getStrngData(UUID id) {
        for (StrngData c : mStrngDatas) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    public void addStrngData(StrngData c) {
        mStrngDatas.add(c);
        // not sure how to call: RacketList.get(getActivity()).saveRackets();
    }

    public void deleteStrngData(StrngData c) {
        mStrngDatas.remove(c);
        // not sure how to call: RacketList.get(getActivity()).saveRackets();
    }

    public ArrayList<StrngData> getStrngDatas() {
        return mStrngDatas;
    }
}
