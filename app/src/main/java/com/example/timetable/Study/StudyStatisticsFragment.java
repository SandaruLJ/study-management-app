package com.example.timetable.Study;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetable.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyStatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyStatisticsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_study_statistics, container, false);

        // Initialize HorizontalBarChart from xml
        BarChart studyStatisticsChart = (BarChart) view.findViewById(R.id.study_statistics_chart);

        // Add static chart data for design purposes
        List<BarEntry> entriesMAD = new ArrayList<>();
        List<BarEntry> entriesDSA = new ArrayList<>();

        float[] hoursMAD = {0f, 2f, 0f, 1f, 0f, 4f, 0f};
        float[] hoursDSA = {0f, 1f, 0f, 0f, 3f, 0f, 0f};
        int startDate = 8;

        for (int i = 0; i < hoursMAD.length; i++) {
            entriesMAD.add(new BarEntry(i + startDate, hoursMAD[i]));
            entriesDSA.add(new BarEntry(i + startDate, hoursDSA[i]));
        }

        BarDataSet setMAD = new BarDataSet(entriesMAD, "Mobile Application Development");
        BarDataSet setDSA = new BarDataSet(entriesDSA, "Data Structures & Algorithms");

        // Set colours for data sets
        setMAD.setColor(Color.rgb(76, 175, 80));
        setDSA.setColor(Color.rgb(52, 101, 164));

        BarData data = new BarData(setMAD, setDSA);
        data.setBarWidth(0.45f);  // Set custom bar width

        studyStatisticsChart.setData(data);
        studyStatisticsChart.groupBars(startDate, 0.06f, 0.02f);  // Perform "explicit" grouping
        studyStatisticsChart.setFitBars(true);  // Make the x-axis fit exactly all bars

        studyStatisticsChart.setDescription(null);

        studyStatisticsChart.invalidate();  // Refresh

        return view;
    }
}