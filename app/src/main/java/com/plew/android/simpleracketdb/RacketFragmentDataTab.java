package com.plew.android.simpleracketdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Tim on 4/19/2015.
 */
public class RacketFragmentDataTab extends Fragment {

    private static final String TAG = "RacketDataTabFragment";

    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;

    Racket mRacket;

    EditText mRacketNameEditText;
    EditText mRacketSerialNumberEditText;
    Button mRacketDateButton;
    EditText mRacketMfgModelEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Log.d(TAG, "onCreate(): ");
        super.onCreate(savedInstanceState);

        // chapter 10: delete: mRacket = new Racket();
        UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(RacketFragment.EXTRA_RACKET_ID);  // chapter 10: direct method:
        // chapter 10: flexible method: UUID racketId = (UUID)getArguments().getSerializable(EXTRA_RACKET_ID);  // chapter 10: flexible method:
        mRacket = RacketList.get(getActivity()).getRacket(racketId);

        setHasOptionsMenu(true);
    }

    public void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        mRacketDateButton.setText(sdf.format(mRacket.getDate()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d(TAG, "onCreateView(): ");

        View v =inflater.inflate(R.layout.fragment_racketdatatab, container, false);

        mRacketNameEditText = (EditText)v.findViewById(R.id.editText_RacketName);
        mRacketNameEditText.setText(mRacket.getName());
        mRacketNameEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketNameEditText: " + c.toString());
                mRacket.setName(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketSerialNumberEditText = (EditText)v.findViewById(R.id.editText_RacketSerialNumber);
        mRacketSerialNumberEditText.setText(mRacket.getSerialNumber());
        mRacketSerialNumberEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketSerialNumberEditText: " + c.toString());
                mRacket.setSerialNumber(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketDateButton = (Button)v.findViewById(R.id.button_racketDate);
        updateDate();
        mRacketDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d(TAG, "onClick(): mRacketChangeDateButton");

                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mRacket.getDate());
                dialog.setTargetFragment(RacketFragmentDataTab.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mRacketMfgModelEditText = (EditText)v.findViewById(R.id.editText_RacketMfgModel);
        mRacketMfgModelEditText.setText(mRacket.getMfgModel());
        mRacketMfgModelEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketMfgModelEditText: " + c.toString());
                mRacket.setMfgModel(c.toString());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_DATE) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                mRacket.setDate(date);
                updateDate();
            }
        }
    }

}
