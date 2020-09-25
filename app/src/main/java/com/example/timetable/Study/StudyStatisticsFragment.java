package com.example.timetable.Study;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.timetable.Database.DBHandler;
import com.example.timetable.Database.SubjectMaster;
import com.example.timetable.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyStatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyStatisticsFragment extends Fragment {

    private DBHandler db;
    private DateTimeFormatter dateTimeFormatter, dateRangeFormatter;
    private Spinner subject;
    private BarChart studyStatisticsChart;
    private LocalDateTime startDate, endDate;
    private TextView dateRangeText, totalStudyTime;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DBHandler(getContext());
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateRangeFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        startDate = LocalDateTime.now().minusDays(6);
        endDate = LocalDateTime.now();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_study_statistics, container, false);


        // Text Views
        dateRangeText = view.findViewById(R.id.date_range_text);
        totalStudyTime = view.findViewById(R.id.total_study_time);


        // Previous Week Button
        AppCompatImageButton prevWeekBtn = view.findViewById(R.id.previous_week_btn);

        prevWeekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDate = startDate.minusDays(7);
                endDate = endDate.minusDays(7);

                generateChart((int) subject.getSelectedItemId());  // Refresh statistics chart
            }
        });

        // Next Week Button
        AppCompatImageButton nextWeekBtn = view.findViewById(R.id.next_week_btn);

        nextWeekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Make Next Week Button function only if the end date is not equal to current date
                if (!dateTimeFormatter.format(endDate).equals(dateTimeFormatter.format(LocalDateTime.now()))) {
                    startDate = startDate.plusDays(7);
                    endDate = endDate.plusDays(7);

                    generateChart((int) subject.getSelectedItemId());  // Refresh statistics chart
                }
            }
        });


        // Statistics Chart
        studyStatisticsChart = view.findViewById(R.id.study_statistics_chart);


        // Subject Selector
        subject = view.findViewById(R.id.subject);
        Cursor subjectsCursor = db.getAllSubjects();

        String[] adapterCols = new String[]{SubjectMaster.Subjects.COLUMN_NAME_SUBJECT_NAME};
        int[] adapterRowViews = new int[]{android.R.id.text1};

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_spinner_item, subjectsCursor, adapterCols, adapterRowViews, 0);
        cursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        subject.setAdapter(cursorAdapter);

        subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                generateChart((int) subject.getSelectedItemId());  // Refresh statistics chart
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void generateChart(int selectedSubject) {
        int totalStudyTimeInt = 0;

        // Set date range text
        dateRangeText.setText(dateRangeFormatter.format(startDate) + " - " +
                dateRangeFormatter.format(endDate));

        // Create string to be used in database query for study times on the relevant time range
        String[] dateQueryArray = new String[7];
        for (int day = 0; day < 7; day++) {
            // Assign current temp date as a query
            dateQueryArray[day] = dateTimeFormatter.format(startDate.plusDays(day));
        }

        Cursor studyTimes = db.getRelevantStudyTimes(dateQueryArray);  // Get relevant study times from database

        float[] studyMinutes = {0f, 0f, 0f, 0f, 0f, 0f, 0f};  // Initialise X axis for the chart with default values

        // Check database cursor for study times within relevant time range (last week)
        while (studyTimes.moveToNext()) {
            if (selectedSubject == studyTimes.getInt(1)) {  // Check for matching subject
                for (int day = 0; day < 7; day++) {
                    // Check if date from database matches with required date
                    if (dateTimeFormatter.format(startDate.plusDays(day)).equals(studyTimes.getString(2))) {
                        studyMinutes[day] += (float) studyTimes.getInt(3);  // Set study time for relevant date
                        totalStudyTimeInt += studyTimes.getInt(3);
                    }
                }
            }
        }

        String subjectName = null;
        int subjectColour = 0;  // Subject colour to be used when setting colour to data sets

        Cursor subjectCursor = db.getSingleSubject((int) subject.getSelectedItemId());
        if (subjectCursor.moveToFirst()) {
            subjectName = subjectCursor.getString(1);
            subjectColour = subjectCursor.getInt(4);
        }

        // Create bar entry list
        List<BarEntry> entries = new ArrayList<>();

        for (int x = 0; x < 7; x++) {
            // Add entry for each day of the week (X axis) with study time as Y axis value
            entries.add(new BarEntry(x + startDate.getDayOfMonth(), studyMinutes[x]));
        }

        BarDataSet dataSet = new BarDataSet(entries, subjectName);

        // Set colour to data set
        dataSet.setColor(subjectColour);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.45f);  // Set custom bar width

        studyStatisticsChart.setData(data);  // Set data set to chart
        studyStatisticsChart.setFitBars(true);  // Make the x-axis fit exactly all bars
        studyStatisticsChart.setDescription(null);
        studyStatisticsChart.invalidate();  // Refresh

        // Calculate and set total study time
        int minutes = totalStudyTimeInt % 60;
        int hours = (totalStudyTimeInt / 60) % 24;

        if (hours > 0 && minutes > 0)
            totalStudyTime.setText(hours + " hr " + minutes + " min");
        else if (hours > 0 && minutes == 0)
            totalStudyTime.setText(hours + " hr");
        else
            totalStudyTime.setText(minutes + " min");
    }
}