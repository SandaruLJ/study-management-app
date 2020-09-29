package com.example.timetable.Exam;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.timetable.Goals.createevents;
import com.example.timetable.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link displayExams#newInstance} factory method to
 * create an instance of this fragment.
 */
public class displayExams extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_exams,null);

        ImageView addBtn = (ImageView) view.findViewById(R.id.addIcon);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exam fragment = new exam();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });

        final TabLayout tabLayout1 = (TabLayout) view.findViewById(R.id.tabLayoutWeek);
        tabLayout1.addTab(tabLayout1.newTab().setText("Upcoming"));
        tabLayout1.addTab(tabLayout1.newTab().setText("Completed"));
        tabLayout1.addTab(tabLayout1.newTab().setText("Calendar"));

        tabLayout1.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout1.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager1 = (ViewPager) view.findViewById(R.id.class_pager_week);
        ExamList adapter = new ExamList(getChildFragmentManager(),tabLayout1.getTabCount());
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
class   ExamList extends FragmentStatePagerAdapter {

    int noOfTabs;

    public ExamList(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.noOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;


        if (position == 0) {
            fragment = new ExamUpcomingFragment();
        }
        if (position == 1) {
            fragment = new DisplayCompletedExams();
        }
        if (position == 2) {
            fragment = new ExamCalendarFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}