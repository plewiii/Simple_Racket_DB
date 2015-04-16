package com.plew.android.simpleracketdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.plew.android.simpleracketdb.StrngDataFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.plew.android.simpleracketdb.StrngDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StrngDataFragment extends Fragment {

    private static final String TAG = "RacketStrngDataFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // Orig: private static final String ARG_PARAM1 = "param1";
    // Orig: private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    // Orig: private String mParam1;
    // Orig: private String mParam2;

    // Orig: private OnFragmentInteractionListener mListener;

    public static final String EXTRA_RACKET_ID = "stringdata.RACKET_ID";
    public static final String EXTRA_STRINGDATA_ID = "stringdata.STRING_ID";

    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;

    StrngData mStrngData;

    EditText mNameField;
    Button mDateButton;
    EditText mMainMfgModelField;
    EditText mMainGaugeField;
    EditText mMainTensionField;
    RadioButton mMainTensionUnitslbsRadioButton;
    RadioButton mMainTensionUnitskgRadioButton;
    CheckBox mMainPrestretchCheckBox;
    EditText mCrossMfgModelField;
    EditText mCrossGaugeField;
    EditText mCrossTensionField;
    RadioButton mCrossTensionUnitslbsRadioButton;
    RadioButton mCrossTensionUnitskgRadioButton;
    CheckBox mCrossPrestretchCheckBox;
    EditText mCommentsField;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RacketFragment.
     */
    // Orig: // TODO: Rename and change types and number of parameters
    // Orig: public static RacketFragment newInstance(String param1, String param2) {
    // Orig:    RacketFragment fragment = new RacketFragment();
    // Orig:     Bundle args = new Bundle();
    // Orig:     args.putString(ARG_PARAM1, param1);
    // Orig:     args.putString(ARG_PARAM2, param2);
    // Orig:     fragment.setArguments(args);
    // Orig:     return fragment;
    // Orig: }

    public StrngDataFragment() {
        // Required empty public constructor

        Log.d(TAG, "StrngDataFragment(): ");
    }

    // chapter 10: flexible method: public static StrngDataFragment newInstance(UUID stringdataId) {
    // chapter 10: flexible method:     Bundle args = new Bundle();
    // chapter 10: flexible method:     args.putSerializable(EXTRA_STRINGDATA_ID, stringdataId);

    // chapter 10: flexible method:     StrngDataFragment fragment = new StrngDataFragment();
    // chapter 10: flexible method:     fragment.setArguments(args);

    // chapter 10: flexible method:    return fragment;
    // chapter 10: flexible method: }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Orig: if (getArguments() != null) {
        // Orig:     mParam1 = getArguments().getString(ARG_PARAM1);
        // Orig:     mParam2 = getArguments().getString(ARG_PARAM2);
        // Orig: }

        getActivity().setTitle("String Data");

        // chapter 10: delete: mStrngData = new StrngData();
        UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_RACKET_ID);  // chapter 10: direct method:
        UUID stringId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_STRINGDATA_ID);  // chapter 10: direct method:
        // chapter 10: flexible method: UUID stringId = (UUID)getArguments().getSerializable(EXTRA_STRINGDATA_ID);  // chapter 10: flexible method:
        // Peter: crashes: mStrngData = Racket.get(getActivity()).getStrngData(stringId);    // mRacket = RacketList.get(getActivity()).getRacket(racketId);
        mStrngData = RacketList.get(getActivity()).getRacket(racketId).getStrngData(stringId);

        setHasOptionsMenu(true);
    }

    public void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        mDateButton.setText(sdf.format(mStrngData.getDate()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Orig: TextView textView = new TextView(getActivity());
        // Orig: textView.setText(R.string.hello_blank_fragment);
        /// Orig: return textView;

        View v = inflater.inflate(R.layout.fragment_strngdata, container, false);

        // Following is based on BNR forum:
        if (NavUtils.getParentActivityName(getActivity()) != null) {
            ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
        });

        mDateButton = (Button)v.findViewById(R.id.stringdata_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //FragmentManager fm = getActivity()
                //        .getSupportFragmentManager();
                //DatePickerFragment dialog = DatePickerFragment
                //        .newInstance(mStringData.getDate());
                //dialog.setTargetFragment(StringDataFragment.this, REQUEST_DATE);
                //dialog.show(fm, DIALOG_DATE);

                Log.d(TAG, "onClick(): mDateButton");

                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mStrngData.getDate());
                dialog.setTargetFragment(StrngDataFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mMainMfgModelField = (EditText)v.findViewById(R.id.stringdata_main_mfgmodel);
        mMainMfgModelField.setText(mStrngData.getMainMfgModel());
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

        mCrossMfgModelField = (EditText)v.findViewById(R.id.stringdata_cross_mfgmodel);
        mCrossMfgModelField.setText(mStrngData.getCrossMfgModel());
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
            }
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.generic_options_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            case R.id.menu_item_save:
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause()");
        super.onPause();
        RacketList.get(getActivity()).saveRackets();
    }

    // Orig: // TODO: Rename method, update argument and hook method into UI event
    // Orig: public void onButtonPressed(Uri uri) {
    // Orig:     if (mListener != null) {
    // Orig:         mListener.onFragmentInteraction(uri);
    // Orig:     }
    // Orig: }

    // Orig: @Override
    // Orig: public void onAttach(Activity activity) {
    // Orig:     super.onAttach(activity);
    // Orig:     try {
    // Orig:         mListener = (OnFragmentInteractionListener) activity;
    // Orig:    } catch (ClassCastException e) {
    // Orig:        throw new ClassCastException(activity.toString()
    // Orig:                 + " must implement OnFragmentInteractionListener");
    // Orig:     }
    // Orig: }

    // Orig: @Override
    // Orig: public void onDetach() {
    // Orig:     super.onDetach();
    // Orig:     mListener = null;
    // Orig: }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    // Orig: public interface OnFragmentInteractionListener {
    // Orig:     // TODO: Update argument type and name
    // Orig:     public void onFragmentInteraction(Uri uri);
    // Orig: }

}
