package com.plew.android.simpleracketdb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.plew.android.common.tabview.RacketViewPagerAdapter;
import com.plew.android.common.tabview.SlidingTabLayout;


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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // Orig: private static final String ARG_PARAM1 = "param1";
    // Orig: private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    // Orig: private String mParam1;
    // Orig: private String mParam2;

    // Orig: private OnFragmentInteractionListener mListener;

    public static final String EXTRA_RACKET_ID = "racket.RACKET_ID";

    ViewPager pager;
    RacketViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Racket","Strings"};
    int Numboftabs =2;

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

        //Log.d(TAG, "RacketFragment(): ");
    }

    // chapter 10: flexible method:
    // chapter 10: flexible method:public static RacketFragment newInstance(UUID racketId) {
    // chapter 10: flexible method:    Bundle args = new Bundle();
    // chapter 10: flexible method:    args.putSerializable(EXTRA_RACKET_ID, racketId);

    // chapter 10: flexible method:    //Log.d(TAG, "newInstance");

    // chapter 10: flexible method:    RacketFragment fragment = new RacketFragment();
    // chapter 10: flexible method:    fragment.setArguments(args);

    // chapter 10: flexible method:    return fragment;
    // chapter 10: flexible method:}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Log.d(TAG, "onCreate(): ");
        super.onCreate(savedInstanceState);
        // Orig: if (getArguments() != null) {
        // Orig:     mParam1 = getArguments().getString(ARG_PARAM1);
        // Orig:     mParam2 = getArguments().getString(ARG_PARAM2);
        // Orig: }

        getActivity().setTitle("Racket Data");

        // chapter 10: delete: mRacket = new Racket();
        // Peter: not used: UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_RACKET_ID);  // chapter 10: direct method:
        // chapter 10: flexible method: UUID racketId = (UUID)getArguments().getSerializable(EXTRA_RACKET_ID);  // chapter 10: flexible method:
        // Peter: not used: mRacket = RacketList.get(getActivity()).getRacket(racketId);
        // Peter: not used: mStrngDatas = mRacket.getStrngDatas();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.d(TAG, "onCreateView(): ");
        // Orig: TextView textView = new TextView(getActivity());
        // Orig: textView.setText(R.string.hello_blank_fragment);
        /// Orig: return textView;

        View v = inflater.inflate(R.layout.fragment_racket, container, false);

        // Following is based on BNR forum:
        if (NavUtils.getParentActivityName(getActivity()) != null) {
            ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new RacketViewPagerAdapter(getActivity().getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) v.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) v.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        /*
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        }); */

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        return v;
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
    public void onPause() {
        //Log.d(TAG, "onPause()");
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
