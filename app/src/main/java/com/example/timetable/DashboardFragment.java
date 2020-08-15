package com.example.timetable;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.timetable.R;
import com.google.android.material.tabs.TabLayout;

public class DashboardFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard,null);
        final TabLayout tabLayout1 = (TabLayout) view.findViewById(R.id.tabDashboard);
        tabLayout1.addTab(tabLayout1.newTab().setText("All"));
        tabLayout1.addTab(tabLayout1.newTab().setText("Class"));
        tabLayout1.addTab(tabLayout1.newTab().setText("Homework"));
        tabLayout1.addTab(tabLayout1.newTab().setText("Exam"));
        tabLayout1.addTab(tabLayout1.newTab().setText("Study"));
        tabLayout1.addTab(tabLayout1.newTab().setText("Goals"));
        tabLayout1.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout1.setTabMode(TabLayout.MODE_SCROLLABLE);

        final ViewPager viewPager1 = (ViewPager) view.findViewById(R.id.dashboard_pager);
        MyAdapter adapter = new MyAdapter(getChildFragmentManager(),tabLayout1.getTabCount());
        viewPager1.setAdapter(adapter);
        viewPager1.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout1));

        for (int i = 0; i < tabLayout1.getTabCount(); i++) {

            TabLayout.Tab tab = tabLayout1.getTabAt(i);
            if (tab != null) {

                TextView tabTextView = new TextView(getActivity().getApplicationContext());
                tab.setCustomView(tabTextView);

                tabTextView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.setTypeface(null, Typeface.NORMAL);
                tabTextView.setTextColor(Color.parseColor("#FFFFFF"));
                tabTextView.setText(tab.getText());
                tabTextView.setTextSize(18);
                if (i == 0) {
                    tabTextView.setTextSize(20);

                    tabTextView.setTypeface(null, Typeface.BOLD);
                }

            }

        }
        tabLayout1.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager1.setCurrentItem(tab.getPosition());
                TextView text = (TextView) tab.getCustomView();

                text.setTypeface(null, Typeface.BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView text = (TextView) tab.getCustomView();

                text.setTypeface(null, Typeface.NORMAL);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
}
class MyAdapter extends FragmentPagerAdapter {

    int noOfTabs;

    public MyAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.noOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if(position == 0){
            fragment = new AllFragment();
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

        return fragment;
    }

    @Override
    public int getCount() {
        return 6;
    }
}
