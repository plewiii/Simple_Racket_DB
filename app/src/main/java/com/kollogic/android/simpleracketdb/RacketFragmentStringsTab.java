package com.kollogic.android.simpleracketdb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.kollogic.android.common.StrngDataArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Created by Tim on 4/19/2015.
 */
public class RacketFragmentStringsTab extends Fragment {

    private static final String TAG = "RacketStringsTabFragment";

    private static final int REQUEST_STRING = 1;

    Racket mRacket;

    Button mRacketCalendarButton;
    Button mRacketStringsButton;
    ListView mRacketStringListView;

    StrngDataArrayAdapter strngdata_adapter;  // ArrayAdapter<StrngData> strngdata_adapter;
    private ArrayList<StrngData> mStrngDatas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Log.d(TAG, "onCreate(): ");
        super.onCreate(savedInstanceState);

        // chapter 10: delete: mRacket = new Racket();
        UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(RacketFragment.EXTRA_RACKET_ID);  // chapter 10: direct method:
        // chapter 10: flexible method: UUID racketId = (UUID)getArguments().getSerializable(EXTRA_RACKET_ID);  // chapter 10: flexible method:
        mRacket = RacketList.get(getActivity()).getRacket(racketId);
        mStrngDatas = mRacket.getStrngDatas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_racketstringstab,container,false);

        mRacketCalendarButton = (Button)v.findViewById(R.id.button_racketCalendar);
        mRacketCalendarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d(TAG, "onClick(): mRacketCalendarButton");

                // Perform action on click
                addCalendarEvent();
            }
        });

        mRacketStringsButton = (Button)v.findViewById(R.id.button_racketStrings);
        mRacketStringsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d(TAG, "onClick(): mRacketStringsButton");

                StrngData strngdata = new StrngData();

                // int size = mRacket.getStrngDatas().size();  // "String #" + Integer.toString(size)
                strngdata.setName("My String");  // kluge: rename the string

                mRacket.addStrngData(strngdata);
                RacketList.get(getActivity()).saveRackets();   // kluge: could not be done in addStrngData
                Intent i = new Intent(getActivity(), StrngDataActivity.class);
                i.putExtra(StrngDataFragment.EXTRA_RACKET_ID, mRacket.getId());
                i.putExtra(StrngDataFragment.EXTRA_STRINGDATA_ID, strngdata.getId());
                startActivityForResult(i, REQUEST_STRING);
            }
        });

        mRacketStringListView = (ListView)v.findViewById(R.id.list_racketStrings);
        // Peter: delete: strngdata_adapter = new ArrayAdapter<StrngData>(getActivity(), R.layout.list_racket_item, R.id.racket_item_text, mStrngDatas);
        strngdata_adapter = new StrngDataArrayAdapter(getActivity(), R.layout.list_racket_item, R.id.racket_item_text,
                R.id.racket_item_image, R.id.racket_item_text2, R.id.racket_item_text3, mStrngDatas);
        mRacketStringListView.setAdapter(strngdata_adapter);

        mRacketStringListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView< ?> parent, View view, int position, long id) {
                StrngData c = (StrngData)(strngdata_adapter.getItem(position));
                //StrngData c = mStrngDatas.get(position);      // this does not use the strngdata_adapter
                //Log.d(TAG, "onItemClick(): " + c.getName());

                Intent i = new Intent(getActivity(), StrngDataActivity.class);
                i.putExtra(StrngDataFragment.EXTRA_RACKET_ID, mRacket.getId());
                i.putExtra(StrngDataFragment.EXTRA_STRINGDATA_ID, c.getId());
                startActivityForResult(i, REQUEST_STRING);
            }
        });

        registerForContextMenu(mRacketStringListView);

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_STRING) {
            // check status?????
            //Log.d(TAG, "onActivityResult(): REQUEST_STRING");
            strngdata_adapter.notifyDataSetChanged();
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
                startActivityForResult(i, REQUEST_STRING);
                return true;
            case R.id.menu_item_delete_string:
                alertMessageDeleteString(strngdata);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void alertMessageDeleteString(final StrngData strngdata) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Delete " + strngdata.toString() + "?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                // Yes button clicked
                UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(RacketFragment.EXTRA_RACKET_ID);  // chapter 10: direct method:
                RacketList.get(getActivity()).getRacket(racketId).deleteStrngData(strngdata);
                RacketList.get(getActivity()).saveRackets();   // kluge: could not be done in deleteStrngData
                strngdata_adapter.notifyDataSetChanged();
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

    private void addCalendarEvent(){
        // Peter: http://code.tutsplus.com/tutorials/android-essentials-adding-events-to-the-users-calendar--mobile-8363
        // Crashes: Intent intent = new Intent(Intent.ACTION_INSERT);
        // Crashes: intent.setData(CalendarContract.Events.CONTENT_URI);
        Intent intent = new Intent(Intent.ACTION_EDIT);   // ACTION_INSERT causes crash
        intent.setType("vnd.android.cursor.item/event");

        intent.putExtra(CalendarContract.Events.TITLE, "Restring: " + mRacket.getName());

        StrngData c = mRacket.getStrngDataByLastDate();  // retrieve string data
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        if (c == null) {
            // No string data - use racket data
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, mRacket.getPurchaseLocation());
            intent.putExtra(CalendarContract.Events.DESCRIPTION, "String Tension: " + mRacket.getStringTension());
        }
        else {
            // Use string data
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, c.getLocation());
            intent.putExtra(CalendarContract.Events.DESCRIPTION, "Last String Date: " + sdf.format(c.getDate()) +
                    "\nMains: " + c.getMainTension() + "\nCrosses: "  + c.getCrossTension());
        }

        GregorianCalendar calDate = new GregorianCalendar();  // default to today's date
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                calDate.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                calDate.getTimeInMillis());

        startActivity(intent);
    }
}
