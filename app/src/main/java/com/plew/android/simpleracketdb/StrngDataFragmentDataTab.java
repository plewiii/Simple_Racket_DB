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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class StrngDataFragmentDataTab extends Fragment {

    private static final String TAG = "RacketStrngDataFragmentDataTab";

    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;

    StrngData mStrngData;

    ScrollView mStrngDataTabScrollView;     // kluge
    //EditText mNameField;
    Button mDateButton;
    EditText mLocationField;
    EditText mCostField;
    AutoCompleteTextView mMainMfgModelField;
    EditText mMainGaugeField;
    EditText mMainTensionField;
    RadioButton mMainTensionUnitslbsRadioButton;
    RadioButton mMainTensionUnitskgRadioButton;
    CheckBox mMainPrestretchCheckBox;
    AutoCompleteTextView mCrossMfgModelField;
    EditText mCrossGaugeField;
    EditText mCrossTensionField;
    RadioButton mCrossTensionUnitslbsRadioButton;
    RadioButton mCrossTensionUnitskgRadioButton;
    CheckBox mCrossPrestretchCheckBox;
    EditText mCommentsField;

    static final String[] STRING_MFG = new String[] { "Alien", "Alpha", "Ashaway", "Asics",
            "Babolat", "Boris Becker", "Dunlop", "Double AR", "Forten",
            "Gamma", "Gosen", "Head", "ISOSPEED", "Kirschbaum", "Klip", "L-Tec", "Luxilon",
            "MSV", "One Strings", "Pacific", "Polyfibre", "Prince", "ProKennex", "Signum Pro",
            "Solinco", "Technifibre", "Toalson", "Topspin",
            "Tourna", "Volkl", "Weiss CANNON",
            "Wilson", "Yonex", "Ytex" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Log.d(TAG, "onCreate(): ");
        super.onCreate(savedInstanceState);

        // chapter 10: delete: mStrngData = new StrngData();
        UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(StrngDataFragment.EXTRA_RACKET_ID);  // chapter 10: direct method:
        UUID stringId = (UUID)getActivity().getIntent().getSerializableExtra(StrngDataFragment.EXTRA_STRINGDATA_ID);  // chapter 10: direct method:
        // chapter 10: flexible method: UUID stringId = (UUID)getArguments().getSerializable(EXTRA_STRINGDATA_ID);  // chapter 10: flexible method:
        mStrngData = RacketList.get(getActivity()).getRacket(racketId).getStrngData(stringId);

        // Following disables focus on last EditText
        // does not work: getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.d(TAG, "onCreateView(): ");

        View v = inflater.inflate(R.layout.fragment_strngdatatab, container, false);

        mStrngDataTabScrollView = (ScrollView)v.findViewById(R.id.scrollView_StrngDataTab);  // Use for kluge

        /*
        mNameField = (EditText)v.findViewById(R.id.stringdata_name);
        mNameField.setText(mStrngData.getName());
        mNameField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mStrngData.setName(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        }); */

        mDateButton = (Button)v.findViewById(R.id.stringdata_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d(TAG, "onClick(): mDateButton");

                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mStrngData.getDate());
                dialog.setTargetFragment(StrngDataFragmentDataTab.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mLocationField = (EditText)v.findViewById(R.id.stringdata_location);
        mLocationField.setText(mStrngData.getLocation());
        mLocationField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mStrngData.setLocation(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mCostField = (EditText)v.findViewById(R.id.stringdata_cost);
        mCostField.setText(mStrngData.getCost());
        mCostField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mStrngData.setCost(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mMainMfgModelField = (AutoCompleteTextView)v.findViewById(R.id.stringdata_main_mfgmodel);
        mMainMfgModelField.setText(mStrngData.getMainMfgModel());
        ArrayAdapter<String> string_mfg_adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_dropdown_item_1line, STRING_MFG);
        mMainMfgModelField.setAdapter(string_mfg_adapter);
        mMainMfgModelField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mStrngData.setMainMfgModel(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mMainGaugeField = (EditText)v.findViewById(R.id.stringdata_main_gauge);
        mMainGaugeField.setText(mStrngData.getMainGauge());
        mMainGaugeField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mStrngData.setMainGauge(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mMainTensionField = (EditText)v.findViewById(R.id.stringdata_main_tension);
        mMainTensionField.setText(mStrngData.getMainTension());
        mMainTensionField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mStrngData.setMainTension(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mMainTensionUnitslbsRadioButton = (RadioButton)v.findViewById(R.id.stringdata_main_tension_units_lbs);
        if (mStrngData.getMainTensionUnits().equals("lbs")) {
            //Log.d(TAG, "mStrngData.getMainTensionUnits() EQUALS lbs");
            mMainTensionUnitslbsRadioButton.setChecked(true);
        }
        mMainTensionUnitslbsRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMainTensionUnitslbsRadioButton.isChecked()) {
                    mStrngData.setMainTensionUnits("lbs");
                }
            }
        });

        mMainTensionUnitskgRadioButton = (RadioButton)v.findViewById(R.id.stringdata_main_tension_units_kg);
        if (mStrngData.getMainTensionUnits().equals("kg")) {
            //Log.d(TAG, "mStrngData.getMainTensionUnits() EQUALS kg");
            mMainTensionUnitskgRadioButton.setChecked(true);
        }
        mMainTensionUnitskgRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMainTensionUnitskgRadioButton.isChecked()) {
                    mStrngData.setMainTensionUnits("kg");
                }
            }
        });

        mMainPrestretchCheckBox = (CheckBox)v.findViewById(R.id.stringdata_main_prestretch);
        mMainPrestretchCheckBox.setChecked(mStrngData.isMainPrestretch());
        mMainPrestretchCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // set the stringdata's prestretch property
                mStrngData.setMainPrestretch(isChecked);
            }
        });

        mCrossMfgModelField = (AutoCompleteTextView)v.findViewById(R.id.stringdata_cross_mfgmodel);
        mCrossMfgModelField.setText(mStrngData.getCrossMfgModel());
        // No need: initialized above: ArrayAdapter<String> string_mfg_adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_dropdown_item_1line, STRING_MFG);
        mCrossMfgModelField.setAdapter(string_mfg_adapter);
        mCrossMfgModelField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mStrngData.setCrossMfgModel(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mCrossGaugeField = (EditText)v.findViewById(R.id.stringdata_cross_gauge);
        mCrossGaugeField.setText(mStrngData.getCrossGauge());
        mCrossGaugeField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mStrngData.setCrossGauge(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mCrossTensionField = (EditText)v.findViewById(R.id.stringdata_cross_tension);
        mCrossTensionField.setText(mStrngData.getCrossTension());
        mCrossTensionField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mStrngData.setCrossTension(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mCrossTensionUnitslbsRadioButton = (RadioButton)v.findViewById(R.id.stringdata_cross_tension_units_lbs);
        if (mStrngData.getCrossTensionUnits().equals("lbs")) {
            //Log.d(TAG, "mStrngData.getCrossTensionUnits() EQUALS lbs");
            mCrossTensionUnitslbsRadioButton.setChecked(true);
        }
        mCrossTensionUnitslbsRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCrossTensionUnitslbsRadioButton.isChecked()) {
                    mStrngData.setCrossTensionUnits("lbs");
                }
            }
        });

        mCrossTensionUnitskgRadioButton = (RadioButton)v.findViewById(R.id.stringdata_cross_tension_units_kg);
        if (mStrngData.getCrossTensionUnits().equals("kg")) {
            //Log.d(TAG, "mStrngData.getCrossTensionUnits() EQUALS kg");
            mCrossTensionUnitskgRadioButton.setChecked(true);
        }
        mCrossTensionUnitskgRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCrossTensionUnitskgRadioButton.isChecked()) {
                    mStrngData.setCrossTensionUnits("kg");
                }
            }
        });

        mCrossPrestretchCheckBox = (CheckBox)v.findViewById(R.id.stringdata_cross_prestretch);
        mCrossPrestretchCheckBox.setChecked(mStrngData.isCrossPrestretch());
        mCrossPrestretchCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // set the stringdata's prestretch property
                mStrngData.setCrossPrestretch(isChecked);
            }
        });

        mCommentsField = (EditText)v.findViewById(R.id.stringdata_comments);
        mCommentsField.setText(mStrngData.getComments());
        mCommentsField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mStrngData.setComments(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        //mNameField.requestFocus();   // kluge
        mStrngDataTabScrollView.smoothScrollTo(0,0);    // kluge

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_DATE) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                mStrngData.setDate(date);
                updateDate();

                updateActionBar();
            }
        }
    }

    // Kluge - this put focus back at top of page
    //@Override
    public void onScrollToTop() {
        //Log.d(TAG, "onResume(): ");
        //super.onResume();

        //mRacketDataTabScrollView.fullScroll(View.FOCUS_UP);
        //mRacketDataTabScrollView.pageScroll(View.FOCUS_UP);
        mStrngDataTabScrollView.smoothScrollTo(0,0);
    }

    private void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        mDateButton.setText(sdf.format(mStrngData.getDate()));
    }

    private void updateActionBar() {
        UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(StrngDataFragment.EXTRA_RACKET_ID);  // chapter 10: direct method:
        Racket mRacket = RacketList.get(getActivity()).getRacket(racketId);  // mRacket is local
        getActivity().setTitle(mRacket.toString() + " - String:" + mStrngData.toString());  // "String Data"
    }
}
