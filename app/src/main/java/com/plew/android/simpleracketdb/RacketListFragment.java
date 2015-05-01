package com.plew.android.simpleracketdb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.plew.android.simpleracketdb.RacketListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.plew.android.simpleracketdb.RacketListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RacketListFragment extends Fragment {

    private static final String TAG = "RacketListFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // Orig: private static final String ARG_PARAM1 = "param1";
    // Orig: private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    // Orig: private String mParam1;
    // Orig: private String mParam2;

    // Orig: private OnFragmentInteractionListener mListener;

    Button mRacketListAddButton;
    ListView mRacketListView;

    ArrayAdapter<Racket> racket_adapter;
    private ArrayList<Racket> mRackets;
    // Peter: delete: String colors[] = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5",
    // Peter: delete:         "Item 6", "Item 7", "Item 8", "Item 9", "Item 10",
    // Peter: delete:         "Item 11", "Item 12", "Item 13", "Item 14", "Item 15",
    // Peter: delete:         "Item 16", "Item 17", "Item 18", "Item 19", "Item 20"};

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

    public RacketListFragment() {
        // Required empty public constructor

        //Log.d(TAG, "RacketListFragment(): ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Orig: if (getArguments() != null) {
        // Orig:     mParam1 = getArguments().getString(ARG_PARAM1);
        // Orig:     mParam2 = getArguments().getString(ARG_PARAM2);
        // Orig: }

        getActivity().setTitle("Racket List");

        mRackets = RacketList.get(getActivity()).getRackets();
        //Log.d(TAG, "RacketListFragment(): size: " + mRackets.size());
        //for (int i = 0; i < mRackets.size(); i++) {
        //    mRackets.get(i).toString();
        //    Log.d(TAG, "RacketListFragment(): " + i + ": " + mRackets.get(i).toString());
        //}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Orig: TextView textView = new TextView(getActivity());
        // Orig: textView.setText(R.string.hello_blank_fragment);
        /// Orig: return textView;

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
        //colors: mRacketListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_racket_item, R.id.name, colors));
        racket_adapter = new ArrayAdapter<Racket>(getActivity(), R.layout.list_racket_item, R.id.name, mRackets);
        mRacketListView.setAdapter(racket_adapter);

        mRacketListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView< ?> parent, View view, int position, long id) {
                //colors: String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
                //colors: Log.d(TAG, "onItemClick(): " + name);

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
                RacketList.get(getActivity()).deleteRacket(racket);
                racket_adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
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

/* Peter

//ArrayAdapter<Racket> adapter =
        //        new ArrayAdapter<Racket>(getActivity(),
        //                android.R.layout.simple_list_item_1,
        //                mRackets);
        //setListAdapter(adapter);

RacketAdapter adapter = new RacketAdapter(mRackets);
        setListAdapter(adapter);


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Racket c = (Racket)(getListAdapter()).getItem(position);
        Log.d(TAG, c.getMfgModel() + " was clicked");
    }

    private class RacketAdapter extends ArrayAdapter<Racket> {
        public RacketAdapter(ArrayList<Racket> rackets) {
            super(getActivity(), android.R.layout.simple_list_item_1, rackets);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_racket_item, null);
            }

            // configure the view for this Racket
            Racket c = getItem(position);

            //TextView titleTextView =
            //        (TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
            //titleTextView.setText(c.getTitle());
            //TextView dateTextView =
            //        (TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
            //dateTextView.setText(c.getDate().toString());
            //CheckBox solvedCheckBox =
            //        (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            //solvedCheckBox.setChecked(c.isSolved());

            return convertView;
        }
    }

 */
