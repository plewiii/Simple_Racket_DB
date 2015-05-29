package com.plew.android.common.tabview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.plew.android.simpleracketdb.RacketFragmentDataTab;
import com.plew.android.simpleracketdb.RacketFragmentImagesTab;
import com.plew.android.simpleracketdb.RacketFragmentStringsTab;
import com.plew.android.simpleracketdb.RacketFragmentSpecsTab;

/**
 * Created by Tim on 4/19/2015.
 */
public class RacketViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public RacketViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        // See RacketFragment.java for 2 tab or 3 tab version

        // 2 tabs version: Racket, Strings
        /*
        if(position == 0) // if the position is 0 we are returning the First tab
        {
            RacketFragmentDataTab racketFragmentDataTab = new RacketFragmentDataTab();
            return racketFragmentDataTab;
        }
        else // if the position is 1 we are returning the Second tab
        {
            RacketFragmentStringsTab racket_stringsTabFragment = new RacketFragmentStringsTab();
            return racket_stringsTabFragment;
        }
        */


        // 3 tabs: Racket, Strings, Images
        /*
        if(position == 0) // if the position is 0 we are returning the First tab
        {
            RacketFragmentDataTab racketFragmentDataTab = new RacketFragmentDataTab();
            return racketFragmentDataTab;
        }
        else if(position == 1) // if the position is 1 we are returning the Second tab
        {
            RacketFragmentStringsTab racket_stringsTabFragment = new RacketFragmentStringsTab();
            return racket_stringsTabFragment;
        }
        else // return Third tab
        {
            RacketFragmentImagesTab racket_imagesTabFragment = new RacketFragmentImagesTab();
            return racket_imagesTabFragment;
        }
        */

        // 4 tabs: Racket, Strings, Images, Specs
        if(position == 0) // if the position is 0 we are returning the First tab
        {
            RacketFragmentDataTab racketFragmentDataTab = new RacketFragmentDataTab();
            return racketFragmentDataTab;
        }
        else if(position == 1) // if the position is 1 we are returning the Second tab
        {
            RacketFragmentStringsTab racket_stringsTabFragment = new RacketFragmentStringsTab();
            return racket_stringsTabFragment;
        }
        else if(position == 2) // if the position is 2 we are returning the Third tab
        {
            RacketFragmentImagesTab racket_imagesTabFragment = new RacketFragmentImagesTab();
            return racket_imagesTabFragment;
        }
        else // return Fourth tab
        {
            RacketFragmentSpecsTab racket_stringsSpecsFragment = new RacketFragmentSpecsTab();
            return racket_stringsSpecsFragment;
        }

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}