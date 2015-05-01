package com.plew.android.simpleracketdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Tim on 4/19/2015.
 */
public class RacketFragmentStringsTab extends Fragment {

    private static final String TAG = "RacketStringsTabFragment";

    private static final int REQUEST_STRING = 1;

    Racket mRacket;

    Button mRacketStringsButton;
    ListView mRacketStringListView;

    ArrayAdapter<StrngData> strngdata_adapter;
    private ArrayList<StrngData> mStrngDatas;
    //String colors[] = {"red", "orange", "yellow", "greeen", "blue",
    //        "indigo", "violet", "aqua", "black", "fuchsia",
    //        "gray", "grey", "lime", "maroon", "navy",
    //        "olive", "purple", "silver", "teal", "white"};

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

        mRacketStringsButton = (Button)v.findViewById(R.id.button_racketStrings);
        mRacketStringsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d(TAG, "onClick(): mRacketStringsButton");

                StrngData strngdata = new StrngData();

                int size = mRacket.getStrngDatas().size();
                strngdata.setName("String #" + Integer.toString(size));  // kluge: rename the string

                mRacket.addStrngData(strngdata);
                RacketList.get(getActivity()).saveRackets();   // kluge: could not be done in addStrngData
                Intent i = new Intent(getActivity(), StrngDataActivity.class);
                i.putExtra(StrngDataFragment.EXTRA_RACKET_ID, mRacket.getId());
                i.putExtra(StrngDataFragment.EXTRA_STRINGDATA_ID, strngdata.getId());
                startActivityForResult(i, REQUEST_STRING);
            }
        });

        mRacketStringListView = (ListView)v.findViewById(R.id.list_racketStrings);
        // colors: mRacketStringListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_racket_item, R.id.name, colors));
        strngdata_adapter = new ArrayAdapter<StrngData>(getActivity(), R.layout.list_racket_item, R.id.name, mStrngDatas);
        mRacketStringListView.setAdapter(strngdata_adapter);

        mRacketStringListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView< ?> parent, View view, int position, long id) {
                //colors: String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
                //colors: Log.d(TAG, "onItemClick(): " + name);

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
                UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(RacketFragment.EXTRA_RACKET_ID);  // chapter 10: direct method:
                RacketList.get(getActivity()).getRacket(racketId).deleteStrngData(strngdata);
                RacketList.get(getActivity()).saveRackets();   // kluge: could not be done in deleteStrngData
                strngdata_adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

}
