package com.plew.android.simpleracketdb;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import java.util.UUID;

public class RacketFragment extends Fragment {

    private static final String TAG = "RacketFragment";

    public static final String EXTRA_RACKET_ID = "racket.RACKET_ID";

    ViewPager pager;
    RacketViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Racket", "Strings", "Images", "Specs" };   // 4 tabs version: "Racket", "Strings", "Images", "Specs"
    int Numboftabs = 4;   // Change to 4 if 4 tabs version.   Also, must change data in RacketViewPagerAdapter.java

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

        // chapter 10: delete: mRacket = new Racket();
        //UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_RACKET_ID);  // chapter 10: direct method:
        // chapter 10: flexible method: UUID racketId = (UUID)getArguments().getSerializable(EXTRA_RACKET_ID);  // chapter 10: flexible method:

        // Action Bar - Title, Background
        updateActionBar();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

    private void updateActionBar() {
        UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_RACKET_ID);  // chapter 10: direct method:
        Racket mRacket = RacketList.get(getActivity()).getRacket(racketId);  // mRacket is local
        getActivity().setTitle(mRacket.toString());    // "Racket Data"
    }
}
