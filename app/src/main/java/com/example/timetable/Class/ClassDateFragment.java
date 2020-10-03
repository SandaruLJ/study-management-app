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
 * Use the {@link ClassDateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassDateFragment extends Fragment {

    public static TextView sdate,stime,etime;
    public static Spinner reminder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_date, container, false);

        reminder = (Spinner) view.findViewById(R.id.reminderSelect);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();


        //Start Date Picker
        sdate = (TextView) view.findViewById(R.id.startDate);
        LinearLayout pickDatebtn = (LinearLayout) view.findViewById(R.id.dateSelectorStart);
        pickDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pass the textView in order to set the value for the text
                DialogFragment newFragment = new SelectDateFragment(sdate);
                newFragment.show(getFragmentManager(), "DatePicker");

            }
        });
        final String today = new SimpleDateFormat("d/M/yyyy", Locale.US)
                .format(Calendar.getInstance().getTime());
        sdate.setText(today);

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
        String[] reminders= new String[]{"5 minutes","10 minutes","15 minutes","30 minutes","1 hour","2 hours", "6 hours", "12 hours","1 day"};
        ArrayAdapter reminderAdapter= new ArrayAdapter(getActivity().getApplicationContext(), R.layout.spinner_item, reminders);
        final Spinner reminderSpinner= (Spinner) view.findViewById(R.id.reminderSelect);
        reminderSpinner.setAdapter(reminderAdapter);

        if (editClass.freqd != null) {
            if (editClass.freqd.equals("Date")) {

                etime.setText(editClass.endTimed);
                stime.setText(editClass.startTimed);
                sdate.setText(editClass.sDated);
                reminder.setSelection(reminderAdapter.getPosition(editClass.reminderd));

            } else {
                etime.setText(timeFormat.format(date));
                stime.setText(timeFormat.format(date));
                sdate.setText(dateFormat.format(date));

            }
        }

        return  view;
    }
}