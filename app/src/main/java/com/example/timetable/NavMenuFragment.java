package com.example.timetable;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.timetable.Class.ViewClassFragment;
import com.example.timetable.Course.DisplayCourseFragment;
import com.example.timetable.Exam.displayExams;
import com.example.timetable.Goals.AllGoalsFragment;
import com.example.timetable.Homework.displayHomework;
import com.example.timetable.Study.AllStudiesFragment;
import com.example.timetable.Study.StudyTimerFragment;
import com.example.timetable.Subject.AllSubjectsFragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavMenuFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_menu, container, false);

        final ImageView addIcon = view.findViewById(R.id.addIcon);
        final  ImageView calendaricon = view.findViewById(R.id.calendarIconTop);
        LayoutInflater layoutInflater= (LayoutInflater)getActivity().getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.list_popup, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,450, ViewGroup.LayoutParams.WRAP_CONTENT);
        OptionsMenu.displayMenu(calendaricon,addIcon,popupWindow,popupView);


        LinearLayout allCourse = (LinearLayout) view.findViewById(R.id.coursebtn);
        allCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayCourseFragment fragment = new DisplayCourseFragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });

        LinearLayout allSubject = (LinearLayout) view.findViewById(R.id.subjectIcon);
        allSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllSubjectsFragment fragment = new AllSubjectsFragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });

        LinearLayout allClass = (LinearLayout) view.findViewById(R.id.classIcon);
        allClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewClassFragment fragment = new ViewClassFragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });

        LinearLayout allHomework = (LinearLayout) view.findViewById(R.id.homeworkIcon);
        allHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayHomework fragment = new displayHomework();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });

        LinearLayout allExams = (LinearLayout) view.findViewById(R.id.examIcon);
        allExams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayExams fragment = new displayExams();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });

        LinearLayout allStudy = (LinearLayout) view.findViewById(R.id.studyIcon);
        allStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllStudiesFragment fragment = new AllStudiesFragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });

        LinearLayout allGoals = (LinearLayout) view.findViewById(R.id.goalsIcon);
        allGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllGoalsFragment fragment = new AllGoalsFragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });

        LinearLayout allTimer = (LinearLayout) view.findViewById(R.id.studyTimerIcon);
        allTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudyTimerFragment fragment = new StudyTimerFragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });
        LinearLayout calendar = (LinearLayout) view.findViewById(R.id.calendarIcon);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllEventCalendar fragment = new AllEventCalendar();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });



        return view;
    }
}