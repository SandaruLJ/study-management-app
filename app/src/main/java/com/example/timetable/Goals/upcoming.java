package com.example.timetable.Goals;

import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link upcoming#newInstance} factory method to
 * create an instance of this fragment.
 */
public class upcoming extends Fragment {

    DBHandler db;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        final ArrayList<String> goals = new ArrayList<>();
        final ArrayList<String> description = new ArrayList<>();
        final ArrayList<Integer> colours = new ArrayList<>();
        final ArrayList<Integer> ids = new ArrayList<>();
        final ArrayList<String> due = new ArrayList<>();
        final Cursor c = db.getAllGoals();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        Date d1 = null;


        Calendar today = Calendar.getInstance();

        while (c.moveToNext()) {
            try {
                d1 = dateFormat.parse(c.getString(3));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cal.setTime(d1);
            long rem = ChronoUnit.DAYS.between(today.toInstant(), cal.toInstant()) ;
            if(rem>=0) {

                ids.add(c.getInt(0));
                goals.add(c.getString(1));
                colours.add(c.getInt(2));
                due.add(c.getString(3));
                description.add(c.getString(4));

            }


        }

        RecyclerView recyclerView = view.findViewById(R.id.goal_recycler_view);
        final GoalsRecyclerViewAdapter adapter = new GoalsRecyclerViewAdapter(ids, goals, colours, due, description, getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemTouchHelper(adapter, ids, recyclerView, getActivity().getApplicationContext());


        return view;
    }

    public void itemTouchHelper(final GoalsRecyclerViewAdapter adapter, final ArrayList<Integer> ids, final RecyclerView recyclerView, final Context cont) {

        final ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter

                db.deleteGoal(String.valueOf(ids.get(viewHolder.getAdapterPosition())));
                adapter.removeItem(viewHolder.getAdapterPosition());
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (viewHolder != null) {
                    final View foregroundView = ((GoalsRecyclerViewAdapter.ViewHolder) viewHolder).goalLay;
                    getDefaultUIUtil().onSelected(foregroundView);
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                final View foregroundView = ((GoalsRecyclerViewAdapter.ViewHolder) viewHolder).goalLay;
                getDefaultUIUtil().clearView(foregroundView);
            }

            @Override
            public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                final View foregroundView = ((GoalsRecyclerViewAdapter.ViewHolder) viewHolder).goalLay;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // view the background view
                final View foregroundView = ((GoalsRecyclerViewAdapter.ViewHolder) viewHolder).goalLay;
                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
            }

            @Override
            public int convertToAbsoluteDirection(int flags, int layoutDirection) {
                return super.convertToAbsoluteDirection(flags, layoutDirection);
            }
        };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }
}
