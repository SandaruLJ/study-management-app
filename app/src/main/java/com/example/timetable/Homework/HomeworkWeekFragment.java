package com.example.timetable.Homework;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.timetable.AllFragment;
import com.example.timetable.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeworkWeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeworkWeekFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class,null);

        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Monday"));
        tabLayout.addTab(tabLayout.newTab().setText("Tuesday"));
        tabLayout.addTab(tabLayout.newTab().setText("Wednesday"));
        tabLayout.addTab(tabLayout.newTab().setText("Thursday"));
        tabLayout.addTab(tabLayout.newTab().setText("Friday"));
        tabLayout.addTab(tabLayout.newTab().setText("Saturday"));
        tabLayout.addTab(tabLayout.newTab().setText("Sunday"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.class_pager);
        HomeworkWeekAdapter adapter = new HomeworkWeekAdapter(getChildFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {

                TextView tabTextView = new TextView(getActivity().getApplicationContext());
                tab.setCustomView(tabTextView);

                tabTextView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.setTypeface(null, Typeface.BOLD);
                tabTextView.setTextColor(Color.parseColor("#BFBCBC"));
                tabTextView.setText(tab.getText());
                tabTextView.setTextSize(18);
                if (i == 0) {
                    tabTextView.setTextSize(20);
                    tabTextView.setTextColor(Color.parseColor("#000000"));
                    tabTextView.setTypeface(null, Typeface.BOLD);
                }

            }

        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                TextView text = (TextView) tab.getCustomView();
                text.setTextSize(20);
                text.setTextColor(Color.parseColor("#000000"));
                text.setTypeface(null, Typeface.BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView text = (TextView) tab.getCustomView();
                text.setTextSize(18);
                text.setTextColor(Color.parseColor("#BFBCBC"));
                text.setTypeface(null, Typeface.NORMAL);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        return view;

    }
}
class HomeworkWeekAdapter extends FragmentPagerAdapter {

    int noOfTabs;

    public HomeworkWeekAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.noOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if(position == 0){
            fragment = new HomeworkWeekDayFragment();
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
