package com.example.timetable.Study;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.timetable.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllStudiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllStudiesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_studies, null);

        final TabLayout studyTab = (TabLayout) view.findViewById(R.id.study_tab);
        studyTab.addTab(studyTab.newTab().setText("Statistics"));
        studyTab.addTab(studyTab.newTab().setText("Study"));

        studyTab.setTabGravity(TabLayout.GRAVITY_CENTER);
        studyTab.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager studyPager = (ViewPager) view.findViewById(R.id.study_pager);
        StudyList adapter = new StudyList(getChildFragmentManager(), studyTab.getTabCount());
        studyPager.setAdapter(adapter);
        studyPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(studyTab));
        studyTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                studyPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ImageView addStudyBtn = view.findViewById(R.id.add_study_btn);

        addStudyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddStudyFragment fragment = new AddStudyFragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });
        
        return view;
    }
}
