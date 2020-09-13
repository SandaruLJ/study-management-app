package com.example.timetable.Goals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetable.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllGoalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllGoalsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_all_goals,null);
            final TabLayout tabLayout1 = (TabLayout) view.findViewById(R.id.goalsTab);
            tabLayout1.addTab(tabLayout1.newTab().setText("Upcoming "));
            tabLayout1.addTab(tabLayout1.newTab().setText("Completed"));
            tabLayout1.setTabGravity(TabLayout.GRAVITY_CENTER);
            tabLayout1.setTabGravity(TabLayout.GRAVITY_FILL);

            final ViewPager viewPager1 = (ViewPager) view.findViewById(R.id.goals_pager);
            GoalsAdapter adapter = new GoalsAdapter(getChildFragmentManager(),tabLayout1.getTabCount());
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
class GoalsAdapter extends FragmentStatePagerAdapter {

    int noOfTabs;

    public GoalsAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.noOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;


        if (position == 0) {
            fragment = new upcoming_goals_fragment();
        }
        if (position == 1) {
            fragment = new GoalsCompletedFragment();
        }


        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}