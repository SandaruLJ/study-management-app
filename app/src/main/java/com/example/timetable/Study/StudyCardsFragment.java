package com.example.timetable.Study;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyCardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyCardsFragment extends Fragment {
    private DBHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_study_cards, container, false);

        final ArrayList<Integer> studyIds = new ArrayList<>();
        final ArrayList<String> studyTitles = new ArrayList<>();
        final ArrayList<String> subjectNames = new ArrayList<>();
        final ArrayList<Integer> studyColours = new ArrayList<>();
        final ArrayList<String> studyDates = new ArrayList<>();
        final ArrayList<String> studyStartTimes = new ArrayList<>();
        final ArrayList<String> studyEndTimes = new ArrayList<>();
        final Cursor c = db.getAllStudies();

        while (c.moveToNext()){
            String subjectName = null;

            Cursor subject = db.getSingleSubject(c.getInt(2));  // Get corresponding subject
            if (subject.moveToFirst())
                subjectName = subject.getString(1);  // Get subject name

            studyIds.add(c.getInt(0));
            studyTitles.add(c.getString(1));
            subjectNames.add(subjectName);
            studyColours.add(c.getInt(3));
            studyDates.add(c.getString(4));
            studyStartTimes.add(c.getString(5));
            studyEndTimes.add(c.getString(6));
        }

        RecyclerView studyRecyclerView = view.findViewById(R.id.study_recycler_view);
        final StudyRecyclerViewAdapter studyAdapter = new StudyRecyclerViewAdapter(studyIds, studyTitles,
                subjectNames, studyColours, studyDates, studyStartTimes, studyEndTimes, getActivity());
        studyRecyclerView.setAdapter(studyAdapter);
        studyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}