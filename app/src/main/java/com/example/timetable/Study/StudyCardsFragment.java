package com.example.timetable.Study;

import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;
import com.example.timetable.Subject.SubjectRecyclerViewAdapter;

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
        final ArrayList<String> studyDays = new ArrayList<>();
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
            studyDays.add(c.getString(8));
        }

        RecyclerView studyRecyclerView = view.findViewById(R.id.study_recycler_view);
        final StudyRecyclerViewAdapter studyAdapter = new StudyRecyclerViewAdapter(studyIds, studyTitles,
                subjectNames, studyColours, studyDates, studyDays, studyStartTimes, studyEndTimes, getActivity());
        studyRecyclerView.setAdapter(studyAdapter);
        studyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        itemTouchHelper(studyAdapter, studyIds, studyRecyclerView);

        return view;
    }

    public void itemTouchHelper(final StudyRecyclerViewAdapter adapter, final ArrayList<Integer> studyIds,
                                final RecyclerView recyclerView){
        final ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper
                .SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // When the row is swiped, remove the item from adapter
                db.deleteStudy(String.valueOf(studyIds.get(viewHolder.getAdapterPosition())));
                adapter.removeItem(viewHolder.getAdapterPosition());
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (viewHolder != null) {
                    final View foregroundView = ((StudyRecyclerViewAdapter.ViewHolder) viewHolder).studyCard;
                    getDefaultUIUtil().onSelected(foregroundView);
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                final View foregroundView = ((StudyRecyclerViewAdapter.ViewHolder) viewHolder).studyCard;
                getDefaultUIUtil().clearView(foregroundView);
            }

            @Override
            public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                        RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                        int actionState, boolean isCurrentlyActive) {
                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                final View foregroundView = ((StudyRecyclerViewAdapter.ViewHolder) viewHolder).studyCard;

                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // Show the background view
                final View foregroundView = ((StudyRecyclerViewAdapter.ViewHolder) viewHolder).studyCard;
                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
            }

            @Override
            public int convertToAbsoluteDirection(int flags, int layoutDirection) {
                return super.convertToAbsoluteDirection(flags, layoutDirection);
            }
        };

        // Attach touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }
}