package com.plew.android.simpleracketdb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.plew.android.common.tabview.UsageDataArrayAdapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class StrngDataFragmentUsageTab extends Fragment {

    private static final String TAG = "RacketStrngDataFragmentDataTab";

    private static final int REQUEST_USAGE = 1;

    Racket mRacket;
    StrngData mStrngData;

    TextView mTotalHoursPlayedTextView;
    Button mUsageButton;
    ListView mStringUsageListView;

    UsageDataArrayAdapter usagedata_adapter;  // ArrayAdapter<UsageData> usagedata_adapter;
    private ArrayList<UsageData> mUsageDatas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Log.d(TAG, "onCreate(): ");
        super.onCreate(savedInstanceState);

        // chapter 10: delete: mStrngData = new StrngData();
        UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(StrngDataFragment.EXTRA_RACKET_ID);  // chapter 10: direct method:
        UUID stringId = (UUID)getActivity().getIntent().getSerializableExtra(StrngDataFragment.EXTRA_STRINGDATA_ID);  // chapter 10: direct method:
        // chapter 10: flexible method: UUID stringId = (UUID)getArguments().getSerializable(EXTRA_STRINGDATA_ID);  // chapter 10: flexible method:
        mRacket = RacketList.get(getActivity()).getRacket(racketId);
        mStrngData = RacketList.get(getActivity()).getRacket(racketId).getStrngData(stringId);
        mUsageDatas = mStrngData.getUsageDatas();

        // Following disables focus on last EditText
        // does not work: getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.d(TAG, "onCreateView(): ");

        View v = inflater.inflate(R.layout.fragment_strngdatausagetab, container, false);

        mTotalHoursPlayedTextView = (TextView)v.findViewById(R.id.textView_totalHoursPlayed);
        updateTotalHoursPlayed();

        mUsageButton = (Button)v.findViewById(R.id.stringdata_usage);
        mUsageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d(TAG, "onClick(): mUsageButton");

                UsageData usagedata = new UsageData();

                mStrngData.addUsageData(usagedata);
                RacketList.get(getActivity()).saveRackets();   // kluge: could not be done in addStrngData
                Intent i = new Intent(getActivity(), UsageDataActivity.class);
                i.putExtra(UsageDataFragment.EXTRA_RACKET_ID, mRacket.getId());
                i.putExtra(UsageDataFragment.EXTRA_STRINGDATA_ID, mStrngData.getId());
                i.putExtra(UsageDataFragment.EXTRA_USAGEDATA_ID, usagedata.getId());
                startActivityForResult(i, REQUEST_USAGE);
            }
        });

        mStringUsageListView = (ListView)v.findViewById(R.id.list_stringUsage);
        // Peter: delete: usagedata_adapter = new ArrayAdapter<UsageData>(getActivity(), R.layout.list_racket_item, R.id.racket_item_text, mUsageDatas);
        usagedata_adapter = new UsageDataArrayAdapter(getActivity(), R.layout.list_racket_item, R.id.racket_item_text,
                R.id.racket_item_image, R.id.racket_item_text2, R.id.racket_item_text3, mUsageDatas);
        mStringUsageListView.setAdapter(usagedata_adapter);

        mStringUsageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView< ?> parent, View view, int position, long id) {
                UsageData c = (UsageData)(usagedata_adapter.getItem(position));
                //UsageData c = mUsageDatas.get(position);      // this does not use the usagedata_adapter
                //Log.d(TAG, "onItemClick(): " + c.getName());

                Intent i = new Intent(getActivity(), UsageDataActivity.class);
                i.putExtra(UsageDataFragment.EXTRA_RACKET_ID, mRacket.getId());
                i.putExtra(UsageDataFragment.EXTRA_STRINGDATA_ID, mStrngData.getId());
                i.putExtra(UsageDataFragment.EXTRA_USAGEDATA_ID, c.getId());
                startActivityForResult(i, REQUEST_USAGE);
            }
        });

        registerForContextMenu(mStringUsageListView);

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_USAGE) {
            // check status?????
            //Log.d(TAG, "onActivityResult(): REQUEST_USAGE");
            usagedata_adapter.notifyDataSetChanged();

            // Update mTotalHoursPlayedTextView
            updateTotalHoursPlayed();
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
        UsageData usagedata = usagedata_adapter.getItem(info.position);

        menu.setHeaderTitle("Action: " + usagedata.toString());

        getActivity().getMenuInflater().inflate(R.menu.usage_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        UsageData usagedata = usagedata_adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_edit_usage:
                Intent i = new Intent(getActivity(), UsageDataActivity.class);
                i.putExtra(UsageDataFragment.EXTRA_RACKET_ID, mRacket.getId());
                i.putExtra(UsageDataFragment.EXTRA_STRINGDATA_ID, mStrngData.getId());
                i.putExtra(UsageDataFragment.EXTRA_USAGEDATA_ID, usagedata.getId());
                startActivityForResult(i, REQUEST_USAGE);
                return true;
            case R.id.menu_item_delete_usage:
                alertMessageDeleteString(usagedata);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void alertMessageDeleteString(final UsageData usagedata) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Delete " + usagedata.toString() + "?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                // Yes button clicked
                UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(StrngDataFragment.EXTRA_RACKET_ID);  // chapter 10: direct method:
                UUID stringId = (UUID)getActivity().getIntent().getSerializableExtra(StrngDataFragment.EXTRA_STRINGDATA_ID);  // chapter 10: direct method:
                RacketList.get(getActivity()).getRacket(racketId).getStrngData(stringId).deleteUsageData(usagedata);
                RacketList.get(getActivity()).saveRackets();   // kluge: could not be done in deleteStrngData
                usagedata_adapter.notifyDataSetChanged();

                // Update mTotalHoursPlayedTextView
                updateTotalHoursPlayed();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // No button clicked
                // do nothing
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void updateTotalHoursPlayed() {
        double total_hours = 0.0;
        for (UsageData c : mUsageDatas) {
            total_hours = total_hours + c.getHours();
        }
        DecimalFormat df = new DecimalFormat("0.0");
        mTotalHoursPlayedTextView.setText(df.format(total_hours));   // String.valueOf(total_hours)
    }

}
