package com.plew.android.simpleracketdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Tim on 4/19/2015.
 */
public class RacketFragmentSpecsTab extends Fragment {

    private static final String TAG = "RacketDataSpecsFragment";

    Racket mRacket;

    EditText mRacketHeadSize;
    EditText mRacketLength;
    EditText mRacketWeight;
    EditText mRacketBalance;
    EditText mRacketSwingweight;
    EditText mRacketStiffness;
    EditText mRacketBeamWidth;
    EditText mRacketComposition;
    EditText mRacketPowerLevel;
    EditText mRacketStrokeStyle;
    EditText mRacketSwingSpeed;
    EditText mRacketColors;
    EditText mRacketGripType;
    EditText mRacketStringPattern;
    EditText mRacketStringTension;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Log.d(TAG, "onCreate(): ");
        super.onCreate(savedInstanceState);

        // chapter 10: delete: mRacket = new Racket();
        UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(RacketFragment.EXTRA_RACKET_ID);  // chapter 10: direct method:
        // chapter 10: flexible method: UUID racketId = (UUID)getArguments().getSerializable(EXTRA_RACKET_ID);  // chapter 10: flexible method:
        mRacket = RacketList.get(getActivity()).getRacket(racketId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d(TAG, "onCreateView(): ");

        View v = inflater.inflate(R.layout.fragment_racketspecstab, container, false);

        mRacketHeadSize = (EditText)v.findViewById(R.id.editText_RacketHeadSize);
        mRacketHeadSize.setText(mRacket.getHeadSize());
        mRacketHeadSize.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketHeadSize: " + c.toString());
                mRacket.setHeadSize(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketLength = (EditText)v.findViewById(R.id.editText_RacketLength);
        mRacketLength.setText(mRacket.getLength());
        mRacketLength.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketLength: " + c.toString());
                mRacket.setLength(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketWeight = (EditText)v.findViewById(R.id.editText_RacketWeight);
        mRacketWeight.setText(mRacket.getStrungWeight());
        mRacketWeight.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketWeight: " + c.toString());
                mRacket.setStrungWeight(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketBalance = (EditText)v.findViewById(R.id.editText_RacketBalance);
        mRacketBalance.setText(mRacket.getBalance());
        mRacketBalance.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketBalance: " + c.toString());
                mRacket.setBalance(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketSwingweight = (EditText)v.findViewById(R.id.editText_RacketSwingweight);
        mRacketSwingweight.setText(mRacket.getSwingweight());
        mRacketSwingweight.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketSwingweight: " + c.toString());
                mRacket.setSwingweight(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketStiffness = (EditText)v.findViewById(R.id.editText_RacketStiffness);
        mRacketStiffness.setText(mRacket.getStiffness());
        mRacketStiffness.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketStiffness: " + c.toString());
                mRacket.setStiffness(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketBeamWidth = (EditText)v.findViewById(R.id.editText_RacketBeamWidth);
        mRacketBeamWidth.setText(mRacket.getBeamWidth());
        mRacketBeamWidth.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketBeamWidth: " + c.toString());
                mRacket.setBeamWidth(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketComposition = (EditText)v.findViewById(R.id.editText_RacketComposition);
        mRacketComposition.setText(mRacket.getComposition());
        mRacketComposition.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketComposition: " + c.toString());
                mRacket.setComposition(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketPowerLevel = (EditText)v.findViewById(R.id.editText_RacketPowerLevel);
        mRacketPowerLevel.setText(mRacket.getPowerLevel());
        mRacketPowerLevel.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketPowerLevel: " + c.toString());
                mRacket.setPowerLevel(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketStrokeStyle = (EditText)v.findViewById(R.id.editText_RacketStrokeStyle);
        mRacketStrokeStyle.setText(mRacket.getStrokeStyle());
        mRacketStrokeStyle.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketStrokeStyle: " + c.toString());
                mRacket.setStrokeStyle(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketSwingSpeed = (EditText)v.findViewById(R.id.editText_RacketSwingSpeed);
        mRacketSwingSpeed.setText(mRacket.getSwingSpeed());
        mRacketSwingSpeed.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketSwingSpeed: " + c.toString());
                mRacket.setSwingSpeed(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketColors = (EditText)v.findViewById(R.id.editText_RacketColors);
        mRacketColors.setText(mRacket.getRacketColors());
        mRacketColors.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketColors: " + c.toString());
                mRacket.setRacketColors(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketGripType = (EditText)v.findViewById(R.id.editText_RacketGripType);
        mRacketGripType.setText(mRacket.getGripType());
        mRacketGripType.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketGripType: " + c.toString());
                mRacket.setGripType(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketStringPattern = (EditText)v.findViewById(R.id.editText_RacketStringPattern);
        mRacketStringPattern.setText(mRacket.getStringPattern());
        mRacketStringPattern.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketStringPattern: " + c.toString());
                mRacket.setStringPattern(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketStringTension = (EditText)v.findViewById(R.id.editText_RacketStringTension);
        mRacketStringTension.setText(mRacket.getStringTension());
        mRacketStringTension.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketStringTension: " + c.toString());
                mRacket.setStringTension(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        return v;
    }

}
