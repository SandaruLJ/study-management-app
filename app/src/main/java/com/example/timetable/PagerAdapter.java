package com.example.timetable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.timetable.Class.ClassDayFragment;
import com.example.timetable.Class.mondayClass;

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
            fragment = ClassDayFragment.newInstance(0);
        }
        if(position == 1){
            fragment = ClassDayFragment.newInstance(1);
        }
        if(position == 2){
            fragment = ClassDayFragment.newInstance(2);
        }
        if(position == 3){
            fragment = ClassDayFragment.newInstance(3);
        }
        if(position == 4){
            fragment = ClassDayFragment.newInstance(4);
        }
        if(position == 5){
            fragment = ClassDayFragment.newInstance(5);
        }
        if(position == 6){
            fragment = ClassDayFragment.newInstance(6);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 7;
    }
}
