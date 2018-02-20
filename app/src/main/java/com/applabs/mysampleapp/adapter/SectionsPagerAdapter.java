package com.applabs.mysampleapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.applabs.mysampleapp.fragment.InvitedFragment;
import com.applabs.mysampleapp.fragment.InviteeFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new InvitedFragment();
        else
            return new InviteeFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}