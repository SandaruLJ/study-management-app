package com.example.timetable;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudyStatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudyStatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudyStatisticsFragment newInstance(String param1, String param2) {
        StudyStatisticsFragment fragment = new StudyStatisticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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