package com.louis.myzhihudemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louis on 17-4-19.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> mTitles;
    private List<Fragment> mFragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
