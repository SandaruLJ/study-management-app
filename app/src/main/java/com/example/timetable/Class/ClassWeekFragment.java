package com.example.timetable.Class;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.timetable.R;
import com.example.timetable.SelectDateFragment;
import com.example.timetable.SelectTimeFragement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassWeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassWeekFragment extends Fragment {

    public static TextView start,end,stime,etime;
    public static Spinner day,reminder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_week, container, false);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();


        day = (Spinner) view.findViewById(R.id.daySelect);
        reminder = (Spinner) view.findViewById(R.id.reminderSelect);

        String[] days= new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        // here you can use array or list
        ArrayAdapter dayAdapter= new ArrayAdapter(getActivity().getApplicationContext(), R.layout.spinner_item, days);
        final Spinner daySpinner= (Spinner) view.findViewById(R.id.daySelect);
        daySpinner.setAdapter(dayAdapter);

        String[] reminders= new String[]{"5 minutes","10 minutes","15 minutes","30 minutes","1 hour","2 hours", "6 hours", "12 hours","1 day"};
        ArrayAdapter reminderAdapter= new ArrayAdapter(getActivity().getApplicationContext(), R.layout.spinner_item, reminders);
        final Spinner reminderSpinner= (Spinner) view.findViewById(R.id.reminderSelect);
        reminderSpinner.setAdapter(reminderAdapter);



        //Start Date Picker
        start = (TextView) view.findViewById(R.id.startDate);
        LinearLayout pickDatebtn = (LinearLayout) view.findViewById(R.id.dateSelectorStart);
        pickDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pass the textView in order to set the value for the text
                DialogFragment newFragment = new SelectDateFragment(start);
                newFragment.show(getFragmentManager(), "DatePicker");

            }
        });
        final String today = new SimpleDateFormat("d/M/yyyy", Locale.US)
                .format(Calendar.getInstance().getTime());
        start.setText(today);
        //End Date Picker
        end = (TextView) view.findViewById(R.id.endDate);
        LinearLayout pickDatebtn1 = (LinearLayout) view.findViewById(R.id.dateSelectorEnd);
        pickDatebtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pass the textView in order to set the date for the text
                DialogFragment newFragment = new SelectDateFragment(end);
                newFragment.show(getFragmentManager(), "DatePicker");

            }
        });

        end.setText(today);

        //Start Time Picker
        stime = (TextView) view.findViewById(R.id.startTime);

        LinearLayout pickDatebtn2 = (LinearLayout) view.findViewById(R.id.timeSelectorStart);
        pickDatebtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pass the textView in order to set the date for the text
                DialogFragment newFragment = new SelectTimeFragement(stime);
                newFragment.show(getFragmentManager(), "TimePicker");

            }
        });
        //End Time Picker
        etime = (TextView) view.findViewById(R.id.endTime);

        LinearLayout pickTimeBtn2 = (LinearLayout) view.findViewById(R.id.timeSelectorEnd);
        pickTimeBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pass the textView in order to set the date for the text
                DialogFragment newFragment = new SelectTimeFragement(etime);
                newFragment.show(getFragmentManager(), "TimePicker");

            }
        });

        if(editClass.freqd != null) {
            if (editClass.freqd.equals("Weekly")) {

                etime.setText(editClass.endTimed);
                stime.setText(editClass.startTimed);
                start.setText(editClass.sDated);
                end.setText(editClass.eDated);
                day.setSelection(dayAdapter.getPosition(editClass.dayd));
                reminder.setSelection(reminderAdapter.getPosition(editClass.reminderd));

            } else {
                etime.setText(timeFormat.format(date));
                stime.setText(timeFormat.format(date));
                end.setText(dateFormat.format(date));
                start.setText(dateFormat.format(date));

            }
        }
        return  view;
    }
}