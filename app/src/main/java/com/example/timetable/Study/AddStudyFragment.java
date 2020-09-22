package com.example.timetable.Study;

import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.AdapterView;
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
    Spinner subject, studyDay, repeat, reminderTime;
    SwitchCompat reminder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_study, container, false);

        studyTitle = view.findViewById(R.id.study_title);
        subject = view.findViewById(R.id.subject);
        studyColour = view.findViewById(R.id.study_colour);
        studyDate = view.findViewById(R.id.study_date);
        studyDay = view.findViewById(R.id.study_day);
        studyStart = view.findViewById(R.id.study_start);
        studyEnd = view.findViewById(R.id.study_end);
        repeat = view.findViewById(R.id.repeat);
        studyNote = view.findViewById(R.id.study_note);
        reminder = view.findViewById(R.id.reminder);
        reminderTime = view.findViewById(R.id.reminder_time);


        // All Studies Button
        ImageView allStudiesBtn = view.findViewById(R.id.all_studies_btn);

        allStudiesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllStudiesFragment fragment = new AllStudiesFragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null).commit();
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
        LinearLayout subjectWrapper = view.findViewById(R.id.subject_wrapper);
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
        LinearLayout colourWrapper = view.findViewById(R.id.colour_wrapper);
        colourWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colourBtn.performClick();
            }
        });


        // Date Picker
        final LinearLayout studyDatePicker = view.findViewById(R.id.study_date_picker);

        // Set current date as default
        final String today = new SimpleDateFormat("d/M/yyyy", Locale.US)
                .format(Calendar.getInstance().getTime());
        studyDate.setText(today);

        studyDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass the TextView in order to set the value for the text
                DialogFragment selectDateFragment = new SelectDateFragment(studyDate);
                selectDateFragment.show(getFragmentManager(), "DatePicker");
            }
        });


        // Start Time Picker
        LinearLayout studyStartTimePicker = view.findViewById(R.id.study_start_time_picker);

        studyStartTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment selectStartTimeFragment = new SelectTimeFragement(studyStart);
                selectStartTimeFragment.show(getFragmentManager(), "TimePicker");
            }
        });


        // End Time Picker
        LinearLayout studyEndTimePicker = view.findViewById(R.id.study_end_time_picker);

        studyEndTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment selectStartTimeFragment = new SelectTimeFragement(studyEnd);
                selectStartTimeFragment.show(getFragmentManager(), "TimePicker");
            }
        });


        // Study Day Selector
        // Open study day selector if clicked anywhere on the surrounding layout
        final LinearLayout studyDaySelector = view.findViewById(R.id.study_day_selector);
        studyDaySelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studyDay.performClick();
            }
        });


        // Repeat Options Selector
        final TextView studyDateLabel = view.findViewById(R.id.study_date_label);
        final View studyDaySeparator = view.findViewById(R.id.study_day_separator);

        repeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedOption = repeat.getSelectedItem().toString();

                if (selectedOption.equalsIgnoreCase("daily")) {
                    studyDateLabel.setHint(R.string.start_date);
                    studyDaySelector.setVisibility(View.GONE);
                    studyDaySeparator.setVisibility(View.GONE);
                }
                else if (selectedOption.equalsIgnoreCase("weekly")) {
                    studyDateLabel.setHint(R.string.start_date);
                    studyDaySelector.setVisibility(View.VISIBLE);
                    studyDaySeparator.setVisibility(View.VISIBLE);
                }
                else {
                    studyDateLabel.setHint(R.string.select_a_date);
                    studyDaySelector.setVisibility(View.GONE);
                    studyDaySeparator.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Open repeat options selector if clicked anywhere on the surrounding layout
        final LinearLayout repeatWrapper = view.findViewById(R.id.repeat_wrapper);
        repeatWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeat.performClick();
            }
        });


        // Reminder Time Selector
        reminderTime.setEnabled(false);  // Set initial state of reminder time selector as disabled

        // Open reminder time selector if clicked anywhere on the surrounding layout
        final LinearLayout reminderTimeWrapper = view.findViewById(R.id.reminder_time_wrapper);

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
        LinearLayout reminderWrapper = view.findViewById(R.id.reminder_wrapper);
        reminderWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reminder.toggle();
            }
        });


        // Submit Button
        Button addStudyBtn = view.findViewById(R.id.add_study);

        addStudyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // Validate inputs before inserting to the database
            if (studyTitle.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Please enter a study title", Toast.LENGTH_LONG).show();
            }
            else {
                String studyDayString;

                // Determine whether study day or date to be inserted
                if (repeat.getSelectedItem().toString().equalsIgnoreCase("weekly"))
                    studyDayString = studyDay.getSelectedItem().toString();
                else if (repeat.getSelectedItem().toString().equalsIgnoreCase("daily"))
                    studyDayString = "Everyday";
                else
                    studyDayString = null;

                boolean isInserted = db.addStudy(studyTitle.getText().toString(), (int) subject.getSelectedItemId(),
                        ((ColorDrawable) studyColour.getBackground()).getColor(), studyDate.getText().toString(),
                        studyStart.getText().toString(), studyEnd.getText().toString(), repeat.getSelectedItem().toString(),
                        studyDayString, studyNote.getText().toString(), reminder.isChecked(),
                        reminderTime.getSelectedItem().toString());

                if (isInserted) {
                    Toast.makeText(getActivity(), "Study added successfully", Toast.LENGTH_LONG).show();
                    AllStudiesFragment allStudiesFragment = new AllStudiesFragment();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, allStudiesFragment)
                            .addToBackStack(null).commit();
                } else
                    Toast.makeText(getActivity(), "Insert failed, try again", Toast.LENGTH_LONG).show();
            }
            }
        });

        return view;
    }
}