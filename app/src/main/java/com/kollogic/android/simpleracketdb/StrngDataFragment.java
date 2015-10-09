package com.kollogic.android.simpleracketdb;

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

import com.kollogic.android.common.SlidingTabLayout;
import com.kollogic.android.common.StrngViewPagerAdapter;

import java.util.UUID;

public class StrngDataFragment extends Fragment {

    private static final String TAG = "RacketStrngDataFragment";

    public static final String EXTRA_RACKET_ID = "stringdata.RACKET_ID";
    public static final String EXTRA_STRINGDATA_ID = "stringdata.STRING_ID";

    ViewPager pager;
    StrngViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"String", "Usage" };   // 2 tabs version: "String", "Usage"
    int Numboftabs = 2;   // 2 tabs version.   Also, must change data in StrngViewPagerAdapter.java

    public StrngDataFragment() {
        // Required empty public constructor

        //Log.d(TAG, "StrngDataFragment(): ");
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

        // chapter 10: delete: mStrngData = new StrngData();
        //UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_RACKET_ID);  // chapter 10: direct method:
        //UUID stringId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_STRINGDATA_ID);  // chapter 10: direct method:
        // chapter 10: flexible method: UUID stringId = (UUID)getArguments().getSerializable(EXTRA_STRINGDATA_ID);  // chapter 10: flexible method:

        // Action Bar - Title, Background
        updateActionBar();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_strngdata, container, false);

        // Following is based on BNR forum:
        if (NavUtils.getParentActivityName(getActivity()) != null) {
            ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new StrngViewPagerAdapter(getActivity().getSupportFragmentManager(),Titles,Numboftabs);

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
        RacketList.get(getActivity()).saveRackets();
    }

    private void updateActionBar() {
        UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_RACKET_ID);  // chapter 10: direct method:
        UUID stringId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_STRINGDATA_ID);  // chapter 10: direct method:
        Racket mRacket = RacketList.get(getActivity()).getRacket(racketId);  // mRacket is local
        StrngData mStrngData = RacketList.get(getActivity()).getRacket(racketId).getStrngData(stringId);  // mStrngData is local
        getActivity().setTitle(mRacket.toString() + " - String:" + mStrngData.toString());  // "String Data"
    }
}
