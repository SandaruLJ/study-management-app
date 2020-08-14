package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    int noOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.noOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if(position == 0){
            fragment = new ClassDayFragment();
        }
        if(position == 1){
            fragment = new AllFragment();
        }
        if(position == 2){
            fragment = new AllFragment();
        }
        if(position == 3){
            fragment = new AllFragment();
        }
        if(position == 4){
            fragment = new AllFragment();
        }
        if(position == 5){
            fragment = new AllFragment();
        }
        if(position == 6){
            fragment = new AllFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 7;
    }
}
