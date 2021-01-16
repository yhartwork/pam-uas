package com.example.myscore.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myscore.PastFragment;
import com.example.myscore.R;
import com.example.myscore.UpcomingFragment;
import com.example.myscore.model.Event;

import java.util.ArrayList;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2 };
    private final Context mContext;

    UpcomingFragment mUpcomingFragment;
    PastFragment mPastFragment;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

        mUpcomingFragment = new UpcomingFragment();
        mPastFragment = new PastFragment();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = mUpcomingFragment;
                break;
            case 1:
                fragment = mPastFragment;
                break;
        }
        return fragment;
    }

    public void updateUpcomingFragment(ArrayList<Event> data) {
        mUpcomingFragment.updateData(data);
    }

    public void updatePastFragment(ArrayList<Event> data) {
        mPastFragment.updateData(data);
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