package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.timetable.Class.ViewClassFragment;
import com.example.timetable.Class.addClass;
import com.example.timetable.Course.DisplayCourseFragment;
import com.example.timetable.Exam.displayExams;
import com.example.timetable.Homework.displayHomework;
import com.example.timetable.Study.ReminderBroadcastReceiver;
import com.example.timetable.Study.StudyTimerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment selectedFragment = new DashboardFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        studyReminderClickAction();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = new DashboardFragment();

                    switch (item.getItemId()){
                        case R.id.nav_dashboard :
                            selectedFragment = new DashboardFragment();
                            break;
                        case R.id.nav_class :
//                            selectedFragment = new ViewClassFragment();
                            selectedFragment = new ViewClassFragment();
                            break;
                        case R.id.nav_homework :
                            selectedFragment = new displayHomework();
                            break;
                        case R.id.nav_test :
                            selectedFragment = new displayExams();
                            break;
                        case R.id.nav_menu :
                            selectedFragment = new NavMenuFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    private void studyReminderClickAction() {
        int studyId = getIntent().getIntExtra(ReminderBroadcastReceiver.EXTRA_STUDY_ID, 0);

        if (studyId != 0) {
            Bundle bundle = new Bundle();
            bundle.putInt("studyId", studyId);

            StudyTimerFragment studyTimerFragment = new StudyTimerFragment();
            studyTimerFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    studyTimerFragment).commit();
        }
    }
}
