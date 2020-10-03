package com.example.timetable;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timetable.Class.addClass;
import com.example.timetable.Course.AddCourseFragment;
import com.example.timetable.Exam.exam;
import com.example.timetable.Goals.createevents;
import com.example.timetable.Homework.AddHomeworkFragment;
import com.example.timetable.Study.AddStudyFragment;
import com.example.timetable.Subject.AddSubjectFragment;

public  class OptionsMenu {

    public static void displayMenu(ImageView calendarIcon, final ImageView addIcon, final PopupWindow popupWindow, View popupView){


        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllEventCalendar fragment = new AllEventCalendar();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                popupWindow.dismiss();
            }
        });



        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow.isShowing()){
                    popupWindow.dismiss();
                } else
                    popupWindow.showAsDropDown(addIcon, 50, 0);
            }
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                }
                return false;
            }
        });

        TextView addCourse = popupView.findViewById(R.id.addCourse);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCourseFragment fragment = new AddCourseFragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                popupWindow.dismiss();
            }
        });

        TextView addClass = popupView.findViewById(R.id.addClass);
        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.example.timetable.Class.addClass fragment = new addClass();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                popupWindow.dismiss();
            }
        });

        TextView addExam = popupView.findViewById(R.id.addExam);
        addExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exam fragment = new exam();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                popupWindow.dismiss();
            }
        });

        TextView addHomework = popupView.findViewById(R.id.addHomework);
        addHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddHomeworkFragment fragment = new AddHomeworkFragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                popupWindow.dismiss();
            }
        });

        TextView addSubject = popupView.findViewById(R.id.addSubject);
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSubjectFragment fragment = new AddSubjectFragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                popupWindow.dismiss();
            }
        });

        TextView addStudy = popupView.findViewById(R.id.addStudy);
        addStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddStudyFragment fragment = new AddStudyFragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                popupWindow.dismiss();
            }
        });

        TextView addGoal = popupView.findViewById(R.id.addGoal);
        addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createevents fragment = new createevents();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                popupWindow.dismiss();
            }
        });


    }
}
