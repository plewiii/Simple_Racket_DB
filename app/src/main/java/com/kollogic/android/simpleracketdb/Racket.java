package com.kollogic.android.simpleracketdb;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
    private static final String JSON_RACKET_PURCHASE_LOCATION = "racket_purchase_location";
    private static final String JSON_RACKET_PURCHASE_PRICE = "racket_purchase_price";

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

    // Future development: private static final String JSON_RACKET_TEST1 = "racket_test1";  // Placeholder for future development
    // Future development: private static final String JSON_RACKET_TEST2 = "racket_test2";

    private static final String JSON_RACKET_STRNGDATA = "racket_strngdata";

    private static final String JSON_RACKET_IMAGEDATA = "racket_imagedata";

    private UUID mId;
    private Date mDate;

    private String mName;
    private String mSerialNumber;
    private String mPurchaseLocation;
    private String mPurchasePrice;

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

    // Future development: private Integer mTest1;  // Placeholder for future development
    // Future development: private Integer mTest2;

    private ArrayList<StrngData> mStrngDatas;

    private ArrayList<ImageData> mImageDatas;

    public Racket() {
        mId = UUID.randomUUID();
        mDate = new Date();
        //Log.d(TAG, "Racket(): " + mId + " " + mDate);

        mName = "My Racket";
        mSerialNumber = "00000";
        mPurchaseLocation = "My Favorite Tennis Store";
        mPurchasePrice = "$200";

        mMfgModel = "Head Graphene Radical Pro";
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
        mGripType = "Head Hydrosorb Pro";
        mStringPattern = "16 Mains/ 19 Crosses\nMains skip: 8T 8H\nTwo Pieces\nNo Shared Holes";
        mStringTension = "48-57 lbs";

        mComments = "None";

        // Future development: mTest1 = 1111;   // Placeholder for future development
        // Future development: mTest2 = 2222;

        mStrngDatas = new ArrayList<StrngData>();

        mImageDatas = new ArrayList<ImageData>();
    }

    public Racket(JSONObject json, int jsonVersion) throws JSONException {
        //Log.d(TAG, "Racket(JSONObject json, int jsonVersion): ");
        mId = UUID.fromString(json.getString(JSON_RACKET_ID));
        mDate = new Date(json.getLong(JSON_RACKET_DATE));

        mName = json.getString(JSON_RACKET_NAME);
        mSerialNumber = json.getString(JSON_RACKET_SERIALNUMBER);
        mPurchaseLocation = json.getString(JSON_RACKET_PURCHASE_LOCATION);
        mPurchasePrice = json.getString(JSON_RACKET_PURCHASE_PRICE);

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

        // Version test - Placeholder for future development
        // Future development: Log.d(TAG, "Racket(JSONObject json, int jsonVersion): jsonVersion:" + jsonVersion);
        // Future development: if (jsonVersion == 1) {
        // Future development:     mTest1 = json.getInt(JSON_RACKET_TEST1);
        // Future development:     mTest2 = json.getInt(JSON_RACKET_TEST2);
        // Future development:     Log.d(TAG, "Racket(JSONObject json, int jsonVersion): JSON_RACKET_TEST1:" + mTest1);
        // Future development:     Log.d(TAG, "Racket(JSONObject json, int jsonVersion): JSON_RACKET_TEST2:" + mTest2);
        // Future development: }
        // Future development: else {  // jsonVersion == 0
        // Future development:     mTest1 = 101;  // set to bogus value
        // Future development:     mTest2 = 102;
        // Future development: }

        JSONArray jsonStrngDataArray = json.getJSONArray(JSON_RACKET_STRNGDATA);
        mStrngDatas = new ArrayList<StrngData>();
        for (int i = 0; i < jsonStrngDataArray.length(); i++) {
            JSONObject jsonStrngDataObj = jsonStrngDataArray.getJSONObject(i);
            StrngData c = new StrngData(jsonStrngDataObj, jsonVersion);
            mStrngDatas.add(c);
        }
        //Log.d(TAG, "Racket(JSONObject json, int jsonVersion): mStrngDatas.size():" + mStrngDatas.size());

        JSONArray jsonImageDataArray = json.getJSONArray(JSON_RACKET_IMAGEDATA);
        mImageDatas = new ArrayList<ImageData>();
        for (int i = 0; i < jsonImageDataArray.length(); i++) {
            JSONObject jsonImageDataObj = jsonImageDataArray.getJSONObject(i);
            ImageData c = new ImageData(jsonImageDataObj, jsonVersion);
            mImageDatas.add(c);
        }
        //Log.d(TAG, "Racket(JSONObject json, int jsonVersion): mImageDatas.size():" + mImageDatas.size());
    }

    public JSONObject toJSON(int jsonVersion) throws JSONException {
        //Log.d(TAG, "toJSON(int jsonVersion): ");
        JSONObject json = new JSONObject();

        json.put(JSON_RACKET_ID, mId.toString());
        json.put(JSON_RACKET_DATE, mDate.getTime());

        json.put(JSON_RACKET_NAME, mName);
        json.put(JSON_RACKET_SERIALNUMBER, mSerialNumber);
        json.put(JSON_RACKET_PURCHASE_LOCATION, mPurchaseLocation);
        json.put(JSON_RACKET_PURCHASE_PRICE, mPurchasePrice);

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

        // Version test  - Placeholder for future development
        // Future development: Log.d(TAG, "toJSON(int jsonVersion): jsonVersion:" + jsonVersion);
        // Future development: if (jsonVersion == 1) {
        // Future development:     Log.d(TAG, "toJSON(int jsonVersion): JSON_RACKET_TEST1:" + mTest1);
        // Future development:     Log.d(TAG, "toJSON(int jsonVersion): JSON_RACKET_TEST2:" + mTest2);
        // Future development:     json.put(JSON_RACKET_TEST1, mTest1);
        // Future development:     json.put(JSON_RACKET_TEST2, mTest2);
        // Future development: }

        // Add Strings - need json array to hold the list
        JSONArray jsonStrngDataArray = new JSONArray();
        for (StrngData c : mStrngDatas) {
            JSONObject jsonStrngDataObj = c.toJSON(jsonVersion);
            jsonStrngDataArray.put(jsonStrngDataObj);
        }
        json.put(JSON_RACKET_STRNGDATA, jsonStrngDataArray);

        // Add Images - need json array to hold the list
        JSONArray jsonImageDataArray = new JSONArray();
        for (ImageData c : mImageDatas) {
            JSONObject jsonImageDataObj = c.toJSON(jsonVersion);
            jsonImageDataArray.put(jsonImageDataObj);
        }
        json.put(JSON_RACKET_IMAGEDATA, jsonImageDataArray);

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

    public String getPurchaseLocation() {
        //Log.d(TAG, "getPurchaseLocation(): " + mPurchaseLocation);
        return mPurchaseLocation;
    }

    public void setPurchaseLocation(String purchaseLocation) {
        mPurchaseLocation = purchaseLocation;
        //Log.d(TAG, "setPurchaseLocation(): " + mPurchaseLocation);
    }

    public String getPurchasePrice() {
        //Log.d(TAG, "getPurchasePrice(): " + mPurchasePrice);
        return mPurchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        mPurchasePrice = purchasePrice;
        //Log.d(TAG, "setPurchasePrice(): " + mPurchasePrice);
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

    public String getBalance() {
        //Log.d(TAG, "getBalance(): " + mBalance);
        return mBalance;
    }

    public void setBalance(String balance) {
        mBalance = balance;
        //Log.d(TAG, "setBalance(): " + mBalance);
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

    public String getSwingSpeed() {
        //Log.d(TAG, "getSwingSpeed(): " + mSwingSpeed);
        return mSwingSpeed;
    }

    public void setSwingSpeed(String swingSpeed) {
        mSwingSpeed = swingSpeed;
        //Log.d(TAG, "setSwingSpeed(): " + mSwingSpeed);
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
        // Original: add to end: mStrngDatas.add(c);
        mStrngDatas.add(0, c);  // Add to beginning
        // kluge: moved to RacketFragment: RacketList.get(getActivity()).saveRackets();
    }

    public void deleteStrngData(StrngData c) {
        mStrngDatas.remove(c);
        // kluge: moved to RacketFragment: RacketList.get(getActivity()).saveRackets();
    }

    public StrngData getStrngDataByLastDate() {
        Date last_date;
        StrngData returnStrngData;

        returnStrngData = null;
        if (mStrngDatas.size() > 0) {
            last_date = mStrngDatas.get(0).getDate();
            returnStrngData = mStrngDatas.get(0);

            for (StrngData c : mStrngDatas) {
                if (c.getDate().after(last_date)) {
                    last_date = c.getDate();
                    returnStrngData = c;
                }

            }
        }

        return returnStrngData;
    }

    public ArrayList<StrngData> getStrngDatas() {
        return mStrngDatas;
    }

    public ImageData getImageData(UUID id) {
        for (ImageData c : mImageDatas) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    public void addImageData(ImageData c) {
        // Original: add to end: mImageDatas.add(c);
        mImageDatas.add(0, c);  // Add to beginning
        // kluge: moved to RacketFragment: RacketList.get(getActivity()).saveRackets();
    }

    public void deleteImageData(ImageData c) {
        mImageDatas.remove(c);
        // kluge: moved to RacketFragment: RacketList.get(getActivity()).saveRackets();
    }

    public ArrayList<ImageData> getImageDatas() {
        return mImageDatas;
    }

    // Tried adding to deleteImageData but caused crash (see RacketList:deleteRacket())
    public boolean deletePhoto(Uri photoUri) {
        File imageFile = new File(photoUri.getPath());    // Peter: kluge   photoUri causes compile error

        boolean deleted = false;

        if (imageFile.exists()){
            deleted = imageFile.delete();
            //if (deleted) {
                //Toast.makeText(getActivity(), "Deleting Image...:" + photoUri.toString(),
                //        Toast.LENGTH_LONG).show();
            //}
            //else {
                //Toast.makeText(getActivity(), "Error: Image NOT deleted:" + photoUri.toString(),
                //        Toast.LENGTH_LONG).show();
            //}
        }
        else {
            deleted = false;
            //Toast.makeText(getActivity(), "Image NOT deleted:" + photoUri.toString(),
            //        Toast.LENGTH_LONG).show();
        }

        return deleted;
    }
}
