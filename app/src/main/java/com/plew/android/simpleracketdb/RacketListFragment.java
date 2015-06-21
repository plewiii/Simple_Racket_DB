package com.plew.android.simpleracketdb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.plew.android.common.tabview.RacketArrayAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.UUID;

public class RacketListFragment extends Fragment {

    private static final String TAG = "RacketListFragment";

    Button mRacketListAddButton;
    ListView mRacketListView;

    RacketArrayAdapter racket_adapter;  // ArrayAdapter<Racket> racket_adapter;
    private ArrayList<Racket> mRackets;

    public RacketListFragment() {
        // Required empty public constructor

        //Log.d(TAG, "RacketListFragment(): ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRackets = RacketList.get(getActivity()).getRackets();
        //Log.d(TAG, "RacketListFragment(): size: " + mRackets.size());
        //for (int i = 0; i < mRackets.size(); i++) {
        //    mRackets.get(i).toString();
        //    Log.d(TAG, "RacketListFragment(): " + i + ": " + mRackets.get(i).toString());
        //}

        // Action Bar - Title, Background
        getActivity().setTitle("Racket List");
        // Icon does not display: ((ActionBarActivity) getActivity()).getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        // Icon does not display: ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayUseLogoEnabled(true);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33B5E5")));


        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_racketlist, container, false);

        mRacketListAddButton = (Button)v.findViewById(R.id.button_racketListAdd);
        mRacketListAddButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d(TAG, "onClick(): mRacketListAddButton");

                Racket racket = new Racket();

                int size = RacketList.get(getActivity()).getRackets().size();
                racket.setName("Racket #" + Integer.toString(size));  // kluge: rename the racket

                RacketList.get(getActivity()).addRacket(racket);
                Intent i = new Intent(getActivity(), RacketActivity.class);
                i.putExtra(RacketFragment.EXTRA_RACKET_ID, racket.getId());
                startActivityForResult(i, 0);

            }
        });

        mRacketListView = (ListView)v.findViewById(R.id.list_racketList);
        // Peter: delete: racket_adapter = new ArrayAdapter<Racket>(getActivity(), R.layout.list_racket_item, R.id.racket_item_text, mRackets);
        racket_adapter = new RacketArrayAdapter(getActivity(), R.layout.list_racket_item, R.id.racket_item_text,
                R.id.racket_item_image, R.id.racket_item_text2, R.id.racket_item_text3, mRackets);
        mRacketListView.setAdapter(racket_adapter);

        mRacketListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView< ?> parent, View view, int position, long id) {
                Racket c = (Racket)(racket_adapter.getItem(position));
                //Racket c = mRackets.get(position);      // this does not use the racket_adapter
                //Log.d(TAG, "onItemClick(): " + c.getName());

                Intent i = new Intent(getActivity(), RacketActivity.class);
                i.putExtra(RacketFragment.EXTRA_RACKET_ID, c.getId());
                startActivityForResult(i, 0);
            }
        });


        registerForContextMenu(mRacketListView);

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        racket_adapter.notifyDataSetChanged();
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
        Racket racket = racket_adapter.getItem(info.position);

        menu.setHeaderTitle("Action: " + racket.toString());
        getActivity().getMenuInflater().inflate(R.menu.racket_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        Racket racket = racket_adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_edit_racket:
                Intent i = new Intent(getActivity(), RacketActivity.class);
                i.putExtra(RacketFragment.EXTRA_RACKET_ID, racket.getId());
                startActivityForResult(i, 0);
                return true;
            case R.id.menu_item_delete_racket:
                alertMessageDeleteRacket(racket);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_racket, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_export_json:
                // Pop up dialog to confirm export
                alertMessageExportJSON();
                return true;
            case R.id.action_import_json:
                // Pop up dialog to confirm import
                alertMessageImportJSON();
                return true;
            case R.id.action_version:
                // Pop up dialog to display version
                alertMessageVersion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void alertMessageDeleteRacket(final Racket racket) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Delete " + racket.toString() + "?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                // Yes button clicked
                RacketList.get(getActivity()).deleteRacket(racket);
                racket_adapter.notifyDataSetChanged();
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

    private void alertMessageExportJSON() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Export ...");

        // Setting Dialog Message
        alertDialog.setMessage("rackets.json will be written to the Download folder.");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                // Yes button clicked

                boolean export_flag = RacketList.get(getActivity()).exportRacketsJSON();
                if (export_flag)
                    Toast.makeText(getActivity(), "Export success", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "Export failed", Toast.LENGTH_SHORT).show();
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

    private void alertMessageImportJSON() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Import ...");

        // Setting Dialog Message
        alertDialog.setMessage("rackets.json must be located in the Download folder.\n\n" +
                "Warning: racket data and images will be deleted!!!!");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                // Yes button clicked

                boolean import_flag = RacketList.get(getActivity()).importRacketsJSON();
                if (import_flag) {
                    Toast.makeText(getActivity(), "Import success", Toast.LENGTH_SHORT).show();
                    mRackets = RacketList.get(getActivity()).getRackets();
                    // this call did not work: racket_adapter.notifyDataSetChanged();
                    // Peter: delete: racket_adapter = new ArrayAdapter<Racket>(getActivity(), R.layout.list_racket_item, R.id.racket_item_text, mRackets);
                    racket_adapter = new RacketArrayAdapter(getActivity(), R.layout.list_racket_item, R.id.racket_item_text,
                            R.id.racket_item_image, R.id.racket_item_text2, R.id.racket_item_text3, mRackets);
                    mRacketListView.setAdapter(racket_adapter);
                }
                else
                    Toast.makeText(getActivity(), "Import failed", Toast.LENGTH_SHORT).show();
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

    private void alertMessageVersion() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Version");

        // Setting Dialog Message
        alertDialog.setMessage("App Name: " + getString(R.string.app_name) + "\n" +
                "Version: " + getString(R.string.app_version));

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                // Yes button clicked
                // do nothing
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
