package com.example.timetable;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassWeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassWeekFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_week, container, false);
        LinearLayout pickDatebtn = (LinearLayout) view.findViewById(R.id.dateSelector);
        pickDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");

            }
        });

        LinearLayout classStartTimebtn = (LinearLayout) view.findViewById(R.id.classStartTimebtn);
        classStartTimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectTimeFragement();
                newFragment.show(getFragmentManager(), "TimePicker");

            }
        });
        LinearLayout classEndTimebtn = (LinearLayout) view.findViewById(R.id.classEndTimebtn);
        classEndTimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectTimeFragement();
                newFragment.show(getFragmentManager(), "TimePicker");

            }
        });
        return  view;
    }
}