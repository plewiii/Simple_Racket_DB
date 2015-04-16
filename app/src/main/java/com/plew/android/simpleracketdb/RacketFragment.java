package com.plew.android.simpleracketdb;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RacketFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RacketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RacketFragment extends Fragment {

    private static final String TAG = "RacketFragment";

    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_STRING = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // Orig: private static final String ARG_PARAM1 = "param1";
    // Orig: private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    // Orig: private String mParam1;
    // Orig: private String mParam2;

    // Orig: private OnFragmentInteractionListener mListener;

    public static final String EXTRA_RACKET_ID = "racket.RACKET_ID";

    Racket mRacket;

    ImageButton mRacketDetailExpandCollapseButton;
    LinearLayout mRacketDetailLayout;
    TextView mRacketDateTextView;
    Button mRacketDateButton;
    EditText mRacketNameEditText;
    EditText mRacketMfgModelEditText;
    EditText mRacketHeadSizeEditText;
    EditText mRacketLengthEditText;

    Button mRacketStringsButton;
    ListView mRacketStringListView;

    ArrayAdapter<StrngData> strngdata_adapter;
    private ArrayList<StrngData> mStrngDatas;
    //colors: String colors[] = {"red", "orange", "yellow", "greeen", "blue",
    //colors:         "indigo", "violet", "aqua", "black", "fuchsia",
    //colors:         "gray", "grey", "lime", "maroon", "navy",
    //colors:         "olive", "purple", "silver", "teal", "white"};

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

    public RacketFragment() {
        // Required empty public constructor

        Log.d(TAG, "RacketFragment(): ");
    }

    // chapter 10: flexible method:
    public static RacketFragment newInstance(UUID racketId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_RACKET_ID, racketId);

        Log.d(TAG, "newInstance");

        RacketFragment fragment = new RacketFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate(): ");
        super.onCreate(savedInstanceState);
        // Orig: if (getArguments() != null) {
        // Orig:     mParam1 = getArguments().getString(ARG_PARAM1);
        // Orig:     mParam2 = getArguments().getString(ARG_PARAM2);
        // Orig: }

        getActivity().setTitle("Racket Data");

        // chapter 10: delete: mRacket = new Racket();
        UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_RACKET_ID);  // chapter 10: direct method:
        // chapter 10: flexible method: UUID racketId = (UUID)getArguments().getSerializable(EXTRA_RACKET_ID);  // chapter 10: flexible method:
        mRacket = RacketList.get(getActivity()).getRacket(racketId);
        mStrngDatas = mRacket.getStrngDatas();

        setHasOptionsMenu(true);
    }

    public void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        mRacketDateButton.setText(sdf.format(mRacket.getDate()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView(): ");
        // Orig: TextView textView = new TextView(getActivity());
        // Orig: textView.setText(R.string.hello_blank_fragment);
        /// Orig: return textView;

        View v = inflater.inflate(R.layout.fragment_racket, container, false);

        // Following is based on BNR forum:
        if (NavUtils.getParentActivityName(getActivity()) != null) {
            ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mRacketDetailLayout = (LinearLayout)v.findViewById(R.id.layout_racketDetail);

        mRacketDetailExpandCollapseButton = (ImageButton)v.findViewById(R.id.button_racketDetailExpandCollapse);
        mRacketDetailLayout.setVisibility(View.GONE);  // hide the racket "detail" layout
        mRacketDetailExpandCollapseButton.setImageResource(R.drawable.ic_action_expand);
        mRacketDetailExpandCollapseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mRacketDetailLayout.isShown()) {
                    mRacketDetailLayout.setVisibility(View.GONE);
                    mRacketDetailExpandCollapseButton.setImageResource(R.drawable.ic_action_expand);
                }
                else {
                    mRacketDetailLayout.setVisibility(View.VISIBLE);
                    mRacketDetailExpandCollapseButton.setImageResource(R.drawable.ic_action_collapse);
                }
            }
        });

        mRacketNameEditText = (EditText)v.findViewById(R.id.editText_RacketName);
        mRacketNameEditText.setText(mRacket.getName());
        mRacketNameEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mRacket.setName(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mRacketMfgModelEditText = (EditText)v.findViewById(R.id.editText_RacketMfgModel);
        mRacketMfgModelEditText.setText(mRacket.getMfgModel());
        mRacketMfgModelEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mRacket.setMfgModel(c.toString());
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
                dialog.setTargetFragment(RacketFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mRacketStringsButton = (Button)v.findViewById(R.id.button_racketStrings);
        mRacketStringsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d(TAG, "onClick(): mRacketStringsButton");

                StrngData strngdata = new StrngData();
                mRacket.addStrngData(strngdata);
                RacketList.get(getActivity()).saveRackets();   // kluge: could not be done in addStrngData
                Intent i = new Intent(getActivity(), StrngDataActivity.class);
                i.putExtra(StrngDataFragment.EXTRA_RACKET_ID, mRacket.getId());
                i.putExtra(StrngDataFragment.EXTRA_STRINGDATA_ID, strngdata.getId());
                startActivityForResult(i, REQUEST_DATE);
            }
        });

        mRacketStringListView = (ListView)v.findViewById(R.id.list_racketStrings);
        //colors: mRacketStringListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_racket_item, R.id.name, colors));
        strngdata_adapter = new ArrayAdapter<StrngData>(getActivity(), R.layout.list_racket_item, R.id.name, mStrngDatas);
        mRacketStringListView.setAdapter(strngdata_adapter);

        mRacketStringListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView< ?> parent, View view, int position, long id) {
                //colors: String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
                //colors: Log.d(TAG, "onItemClick(): " + name);

                StrngData c = (StrngData)(strngdata_adapter.getItem(position));
                //StrngData c = mStrngDatas.get(position);      // this does not use the strngdata_adapter
                Log.d(TAG, "onItemClick(): " + c.getName());

                Intent i = new Intent(getActivity(), StrngDataActivity.class);
                i.putExtra(StrngDataFragment.EXTRA_RACKET_ID, mRacket.getId());
                i.putExtra(StrngDataFragment.EXTRA_STRINGDATA_ID, c.getId());
                startActivityForResult(i, 0);
            }
        });

        registerForContextMenu(mRacketStringListView);

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
        //else if (requestCode == REQUEST_STRING) {
            // check status?????
            strngdata_adapter.notifyDataSetChanged();
        //}
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
                //Log.d(TAG, "onOptionsItemSelected: android.R.id.home");
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            case R.id.menu_item_save:
                //Log.d(TAG, "onOptionsItemSelected: menu_item_save");
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info;
        try {
            // Casts the incoming data object into the type for AdapterView objects.
            info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        } catch (ClassCastException e) {
            // If the menu object can't be cast, logs an error.
            Log.e(TAG, "bad menuInfo", e);
            return;
        }
        StrngData strngdata = strngdata_adapter.getItem(info.position);

        menu.setHeaderTitle("Action: " + strngdata.toString());

        getActivity().getMenuInflater().inflate(R.menu.string_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        StrngData strngdata = strngdata_adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_edit_string:
                Intent i = new Intent(getActivity(), StrngDataActivity.class);
                i.putExtra(StrngDataFragment.EXTRA_RACKET_ID, mRacket.getId());
                i.putExtra(StrngDataFragment.EXTRA_STRINGDATA_ID, strngdata.getId());
                startActivityForResult(i, 0);
                return true;
            case R.id.menu_item_delete_string:
                UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_RACKET_ID);  // chapter 10: direct method:
                RacketList.get(getActivity()).getRacket(racketId).deleteStrngData(strngdata);
                RacketList.get(getActivity()).saveRackets();   // kluge: could not be done in deleteStrngData
                strngdata_adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
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
