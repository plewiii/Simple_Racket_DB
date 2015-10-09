package com.kollogic.android.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kollogic.android.simpleracketdb.StrngDataFragmentDataTab;
import com.kollogic.android.simpleracketdb.StrngDataFragmentUsageTab;

/**
 * Created by Tim on 4/19/2015.
 */
public class StrngViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public StrngViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        // See StrngDataFragment.java for number of tabs

        // 2 tabs: Data, Usage
        if(position == 0) // if the position is 0 we are returning the First tab
        {
            StrngDataFragmentDataTab strngdata_dataTabFragment = new StrngDataFragmentDataTab();
            return strngdata_dataTabFragment;
        }
        else // if the position is 1 we are returning the Second tab
        {
            StrngDataFragmentUsageTab strngdata_usageTabFragment = new StrngDataFragmentUsageTab();
            return strngdata_usageTabFragment;
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