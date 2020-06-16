package com.example.mynetworkv1.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mynetworkv1.R;
import com.example.mynetworkv1.Trader;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private final String mTraderID;

    public SectionsPagerAdapter(Context context, FragmentManager fm, String traderID) {
        super(fm);
        mTraderID = traderID;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        // TODO Define the differences between pages - have DescriptionFragment and ContactFragment
        // Description can be on position 1, Contact on position 2
        if(position == 0){
            return DescriptionFragment.newInstance(position + 1, mTraderID);
        }else{
            return PlaceholderFragment.newInstance(position + 1);

        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}