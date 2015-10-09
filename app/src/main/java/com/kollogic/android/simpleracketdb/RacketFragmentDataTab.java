package com.kollogic.android.simpleracketdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

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

    ScrollView mRacketDataTabScrollView;     // kluge
    EditText mRacketNameEditText;
    AutoCompleteTextView mRacketMfgModelAutoCompleteTextView;
    EditText mRacketSerialNumberEditText;
    Button mRacketPurchaseDateButton;
    EditText mRacketPurchaseLocationEditText;
    EditText mRacketPurchasePriceEditText;
    EditText mRacketCommentsEditText;

    static final String[] RACKET_MFG = new String[] { "adidas", "Ashaway", "Avery",
            "Babolat", "Bancroft", "Black Knight", "Blackburne", "Boris Becker",
            "Bosworth", "Carlton", "Cornelian", "Donnay", "Dunlop", "E-Force", "Ektelon",
            "Feather", "Fischer", "Fox", "Gamma", "Gearbox", "Harrow", "Head", "HL Corp",
            "Megaage", "Natural", "Pacific", "Power Angle", "Prince", "Pro Kennex",
            "Pro Supex", "Slazenger", "Technifibre", "Topspin",
            "Vantage", "Volkl",
            "Vortex", "Weed",
            "Wilson", "Winjammer", "X-45", "Yonex" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Log.d(TAG, "onCreate(): ");
        super.onCreate(savedInstanceState);

        // chapter 10: delete: mRacket = new Racket();
        UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(RacketFragment.EXTRA_RACKET_ID);  // chapter 10: direct method:
        // chapter 10: flexible method: UUID racketId = (UUID)getArguments().getSerializable(EXTRA_RACKET_ID);  // chapter 10: flexible method:
        mRacket = RacketList.get(getActivity()).getRacket(racketId);

        // Following disables focus on last EditText
        // does not work: getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d(TAG, "onCreateView(): ");

        View v = inflater.inflate(R.layout.fragment_racketdatatab, container, false);

        mRacketDataTabScrollView = (ScrollView)v.findViewById(R.id.scrollView_RacketDataTab);  // Use for kluge

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
        mRacketNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String text = v.getText().toString();
                    mRacket.setName(text);

                    updateActionBar();

                    return true;
                }
                return false;
            }
        });

        mRacketMfgModelAutoCompleteTextView = (AutoCompleteTextView)v.findViewById(R.id.autocompleteTextView_RacketMfgModel);
        mRacketMfgModelAutoCompleteTextView.setText(mRacket.getMfgModel());
        ArrayAdapter<String> racket_mfg_adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_dropdown_item_1line, RACKET_MFG);
        mRacketMfgModelAutoCompleteTextView.setAdapter(racket_mfg_adapter);
        mRacketMfgModelAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
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

        mRacketPurchaseDateButton = (Button)v.findViewById(R.id.button_RacketPurchaseDate);
        updateDate();
        mRacketPurchaseDateButton.setOnClickListener(new View.OnClickListener() {
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

        mRacketPurchaseLocationEditText = (EditText)v.findViewById(R.id.editText_RacketPurchaseLocation);
        mRacketPurchaseLocationEditText.setText(mRacket.getPurchaseLocation());
        mRacketPurchaseLocationEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketPurchaseLocationEditText: " + c.toString());
                mRacket.setPurchaseLocation(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketPurchasePriceEditText = (EditText)v.findViewById(R.id.editText_RacketPurchasePrice);
        mRacketPurchasePriceEditText.setText(mRacket.getPurchasePrice());
        mRacketPurchasePriceEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketPurchasePriceEditText: " + c.toString());
                mRacket.setPurchasePrice(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketCommentsEditText = (EditText)v.findViewById(R.id.editText_RacketComments);
        mRacketCommentsEditText.setText(mRacket.getComments());
        mRacketCommentsEditText.clearFocus();   // kluge

        mRacketCommentsEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged(): mRacketCommentsEditText: " + c.toString());
                mRacket.setComments(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketNameEditText.requestFocus();   // kluge
        mRacketDataTabScrollView.smoothScrollTo(0,0);    // kluge

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

    // Kluge - this put focus back at top of page
    //@Override
    public void onScrollToTop() {
        //Log.d(TAG, "onResume(): ");
        //super.onResume();

        //mRacketDataTabScrollView.fullScroll(View.FOCUS_UP);
        //mRacketDataTabScrollView.pageScroll(View.FOCUS_UP);
        mRacketDataTabScrollView.smoothScrollTo(0,0);
    }

    private void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        mRacketPurchaseDateButton.setText(sdf.format(mRacket.getDate()));
    }

    private void updateActionBar() {
        getActivity().setTitle(mRacket.toString());    // "Racket Data"
    }
}
