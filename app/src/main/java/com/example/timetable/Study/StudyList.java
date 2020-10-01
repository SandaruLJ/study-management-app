package com.example.timetable.Study;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class StudyList extends FragmentStatePagerAdapter {

    int noOfTabs;

    public StudyList(FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTabs = noOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if (position == 0) {
            fragment = new StudyStatisticsFragment();
        }
        if (position == 1) {
            fragment = new StudyCardsFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}