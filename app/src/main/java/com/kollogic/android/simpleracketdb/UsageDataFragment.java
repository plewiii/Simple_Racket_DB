package com.kollogic.android.simpleracketdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UsageDataFragment extends Fragment {

    private static final String TAG = "RacketUsageDataFragment";

    public static final String EXTRA_RACKET_ID = "stringdata.RACKET_ID";
    public static final String EXTRA_STRINGDATA_ID = "stringdata.STRING_ID";
    public static final String EXTRA_USAGEDATA_ID = "stringdata.USAGE_ID";

    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;

    UsageData mUsageData;

    Button mDateButton;
    EditText mHoursField;
    EditText mCommentsField;

    public UsageDataFragment() {
        // Required empty public constructor

        //Log.d(TAG, "UsageDataFragment(): ");
    }

    // chapter 10: flexible method: public static UsageDataFragment newInstance(UUID stringdataId) {
    // chapter 10: flexible method:     Bundle args = new Bundle();
    // chapter 10: flexible method:     args.putSerializable(EXTRA_STRINGDATA_ID, stringdataId);

    // chapter 10: flexible method:     UsageDataFragment fragment = new UsageDataFragment();
    // chapter 10: flexible method:     fragment.setArguments(args);

    // chapter 10: flexible method:    return fragment;
    // chapter 10: flexible method: }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // chapter 10: delete: mUsageData = new UsageData();
        UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_RACKET_ID);  // chapter 10: direct method:
        UUID stringId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_STRINGDATA_ID);  // chapter 10: direct method:
        UUID usageId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_USAGEDATA_ID);  // chapter 10: direct method:
        // chapter 10: flexible method: UUID stringId = (UUID)getArguments().getSerializable(EXTRA_STRINGDATA_ID);  // chapter 10: flexible method:

        mUsageData = RacketList.get(getActivity()).getRacket(racketId).getStrngData(stringId).getUsageData(usageId);

        // Action Bar - Title, Background
        updateActionBar();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_usagedata, container, false);

        // Following is based on BNR forum:
        if (NavUtils.getParentActivityName(getActivity()) != null) {
            ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mDateButton = (Button)v.findViewById(R.id.usagedata_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d(TAG, "onClick(): mDateButton");

                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mUsageData.getDate());
                dialog.setTargetFragment(UsageDataFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mHoursField = (EditText)v.findViewById(R.id.usagedata_hours);
        mHoursField.setText(String.valueOf(mUsageData.getHours()));
        // addTextChangedListener crashes: kluge solution in onPause
        /* mHoursField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                // this space intentionally left blank
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                mUsageData.setHours(Double.valueOf(c.toString()).doubleValue());
            }
        });  */
        mHoursField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String text = v.getText().toString();
                    //Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                    if (text.isEmpty())
                        mUsageData.setHours(0.0);
                    else
                        mUsageData.setHours(Double.valueOf(text).doubleValue());
                    return true;
                }
                return false;
            }
        });

        mCommentsField = (EditText)v.findViewById(R.id.usagedata_comments);
        mCommentsField.setText(mUsageData.getComments());
        mCommentsField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mUsageData.setComments(c.toString());
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
                mUsageData.setDate(date);
                updateDate();

                updateActionBar();
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
        //Log.d(TAG, "onPause()");
        super.onPause();
        if (mHoursField.getText().toString().isEmpty())
            mUsageData.setHours(0.0);
        else
            mUsageData.setHours(Double.valueOf(mHoursField.getText().toString()).doubleValue());  // kluge
        RacketList.get(getActivity()).saveRackets();
    }

    private void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        mDateButton.setText(sdf.format(mUsageData.getDate()));
    }

    private void updateActionBar() {
        UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_RACKET_ID);  // chapter 10: direct method:
        UUID stringId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_STRINGDATA_ID);  // chapter 10: direct method:
        Racket mRacket = RacketList.get(getActivity()).getRacket(racketId);
        StrngData mStrngData = RacketList.get(getActivity()).getRacket(racketId).getStrngData(stringId);
        getActivity().setTitle(mRacket.toString() + " - String:" + mStrngData.toString() + " - Usage:" + mUsageData.toString());  // "Usage Data"
    }
}
