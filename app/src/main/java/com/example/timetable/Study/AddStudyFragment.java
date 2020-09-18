package com.example.timetable.Study;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetable.ColorPicker;
import com.example.timetable.Database.DBHandler;
import com.example.timetable.Database.SubjectMaster;
import com.example.timetable.R;
import com.example.timetable.SelectDateFragment;
import com.example.timetable.SelectTimeFragement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddStudyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddStudyFragment extends Fragment {

    DBHandler db;
    EditText studyTitle, studyNote;
    Button studyColour;
    TextView studyDate, studyStart, studyEnd;
    Spinner subject, repeat, reminderTime;
    SwitchCompat reminder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_study, container, false);

        studyTitle = (EditText) view.findViewById(R.id.study_title);
        subject = (Spinner) view.findViewById(R.id.subject);
        studyColour = view.findViewById(R.id.study_colour);
        studyDate = (TextView) view.findViewById(R.id.study_date);
        studyStart = (TextView) view.findViewById(R.id.study_start);
        studyEnd = (TextView) view.findViewById(R.id.study_end);
        repeat = (Spinner) view.findViewById(R.id.repeat);
        studyNote = (EditText) view.findViewById(R.id.study_note);
        reminder = (SwitchCompat) view.findViewById(R.id.reminder);
        reminderTime = (Spinner) view.findViewById(R.id.reminder_time);

        ImageView allStudiesBtn = view.findViewById(R.id.all_studies_btn);

        allStudiesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllStudiesFragment fragment = new AllStudiesFragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });


        // Subject Selector
        Cursor subjectsCursor = db.getAllSubjects();

        String[] adapterCols = new String[]{SubjectMaster.Subjects.COLUMN_NAME_SUBJECT_NAME};
        int[] adapterRowViews = new int[]{android.R.id.text1};

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_spinner_item, subjectsCursor, adapterCols, adapterRowViews, 0);
        cursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        subject.setAdapter(cursorAdapter);

        // Open subject selector if clicked anywhere on the surrounding layout
        LinearLayout subjectWrapper = (LinearLayout) view.findViewById(R.id.subject_wrapper);
        subjectWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subject.performClick();
            }
        });


        // Colour Picker
        final Button colourBtn = view.findViewById(R.id.colorbtn);
        colourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPicker cp = new ColorPicker();
                cp.openColorPicker(getChildFragmentManager(), colourBtn, studyColour);
            }
        });

        // Open colour picker if clicked anywhere on the surrounding layout
        LinearLayout colourWrapper = (LinearLayout) view.findViewById(R.id.colour_wrapper);
        colourWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colourBtn.performClick();
            }
        });


        // Date Picker
        LinearLayout studyDatePicker = (LinearLayout) view.findViewById(R.id.study_date_picker);
        // Set current date as default
        studyDate.setText(new SimpleDateFormat("d/M/yyyy", Locale.US).format(Calendar.getInstance().getTime()));

        studyDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass the TextView in order to set the value for the text
                DialogFragment selectDateFragment = new SelectDateFragment(studyDate);
                selectDateFragment.show(getFragmentManager(), "DatePicker");
            }
        });


        // Start Time Picker
        LinearLayout studyStartTimePicker = (LinearLayout) view.findViewById(R.id.study_start_time_picker);

        studyStartTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment selectStartTimeFragment = new SelectTimeFragement(studyStart);
                selectStartTimeFragment.show(getFragmentManager(), "TimePicker");
            }
        });


        // End Time Picker
        LinearLayout studyEndTimePicker = (LinearLayout) view.findViewById(R.id.study_end_time_picker);

        studyEndTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment selectStartTimeFragment = new SelectTimeFragement(studyEnd);
                selectStartTimeFragment.show(getFragmentManager(), "TimePicker");
            }
        });


        // Repeat Options Selector
        // Open repeat options selector if clicked anywhere on the surrounding layout
        LinearLayout repeatWrapper = (LinearLayout) view.findViewById(R.id.repeat_wrapper);
        repeatWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeat.performClick();
            }
        });


        // Reminder Time Selector
        reminderTime.setEnabled(false);  // Set initial state of reminder time selector as disabled

        // Open reminder time selector if clicked anywhere on the surrounding layout
        final LinearLayout reminderTimeWrapper = (LinearLayout) view.findViewById(R.id.reminder_time_wrapper);

        reminderTimeWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reminder.isChecked())
                    reminderTime.performClick();
            }
        });

        // Make the surrounding layout not clickable at start
        reminderTimeWrapper.setClickable(false);
        reminderTimeWrapper.setFocusable(false);


        // Reminder Toggle
        // Enable reminder time selector only if reminder is enabled
        reminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    reminderTime.setEnabled(true);
                    reminderTimeWrapper.setClickable(true);
                    reminderTimeWrapper.setFocusable(true);
                }
                else {
                    reminderTime.setEnabled(false);
                    reminderTimeWrapper.setClickable(false);
                    reminderTimeWrapper.setFocusable(false);
                }
            }
        });

        // Toggle reminder if clicked anywhere on the surrounding layout
        LinearLayout reminderWrapper = (LinearLayout) view.findViewById(R.id.reminder_wrapper);
        reminderWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reminder.toggle();
            }
        });


        // Submit Button
        Button addStudiesBtn = view.findViewById(R.id.add_study);

        addStudiesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate inputs before inserting to the database
                if (studyTitle.getText().toString().equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter a study title", Toast.LENGTH_LONG).show();
                }
//                else {
//                    boolean isInserted = db.addStudy(studyTitle.getText().toString(), (int) subject.getSelectedItemId(),
//                            ((ColorDrawable) studyColour.getBackground()).getColor(), studyDate.getText().toString(),
//                            studyStart.getText().toString(), studyEnd.getText().toString(), repeat.getSelectedItem().toString(),
//                            studyNote.getText().toString(), reminder.isChecked(), reminderTime.getSelectedItem().toString());
//
//                    if (isInserted) {
//                        Toast.makeText(getActivity().getApplicationContext(), "Study added successfully", Toast.LENGTH_LONG).show();
////                        AllSubjectsFragment fragment = new AllSubjectsFragment();
////                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
////                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
//                    } else
//                        Toast.makeText(getActivity().getApplicationContext(), "Insert failed, try again", Toast.LENGTH_LONG).show();
//                }
            }
        });

        return view;
    }
}