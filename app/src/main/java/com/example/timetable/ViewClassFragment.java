package com.example.timetable;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewClassFragment extends Fragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_class,null);
        final TabLayout tabLayout1 = (TabLayout) view.findViewById(R.id.tabLayoutWeek);
        tabLayout1.addTab(tabLayout1.newTab().setText("Week"));
        tabLayout1.addTab(tabLayout1.newTab().setText("List"));
        tabLayout1.addTab(tabLayout1.newTab().setText("Calendar"));

        tabLayout1.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout1.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager1 = (ViewPager) view.findViewById(R.id.class_pager_week);
        ClassList adapter = new ClassList(getChildFragmentManager(),tabLayout1.getTabCount());
        viewPager1.setAdapter(adapter);
        viewPager1.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout1));
        tabLayout1.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager1.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;

    }
}
class ClassList extends FragmentStatePagerAdapter {

    int noOfTabs;

    public ClassList(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.noOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;


        if (position == 0) {
            fragment = new ClassFragment();
        }
        if (position == 1) {
            fragment = new ClassListFragment();
        }
        if (position == 2) {
            fragment = new ClassCalendarFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}