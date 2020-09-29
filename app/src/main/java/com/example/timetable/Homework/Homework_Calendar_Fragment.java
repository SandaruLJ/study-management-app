package com.example.timetable.Homework;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.timetable.Class.ClassCalendarRecyclerView;
import com.example.timetable.Class.EventDecorator;
import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Homework_Calendar_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Homework_Calendar_Fragment extends Fragment {

    DBHandler db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DBHandler(getActivity().getApplicationContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_homework__calendar_, container, false);

        final ArrayList<String> titles = new ArrayList<>();
        final ArrayList<String> subjects = new ArrayList<>();
        final ArrayList<String> due_dates = new ArrayList<>();
        final ArrayList<String> time = new ArrayList<>();
        final ArrayList<Integer> colors = new ArrayList<>();
        final ArrayList<Integer> ids = new ArrayList<>();

        final Cursor c = db.getAllHomework();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        MaterialCalendarView calendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        Calendar cal = Calendar.getInstance();


        Date d1 = null;

//        final Cursor c = db.getSortClass();

        while (c.moveToNext()) {

            try {
                d1 = dateFormat.parse(c.getString(3));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Convert the date to Calendar object
            cal.setTime(d1);
            //Convert the date to local object
            LocalDate ld = LocalDate.of(cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH));

            //Set the selcted dates
            calendarView.setDateSelected(CalendarDay.from(ld),true);
            EventDecorator eventDecorator = new EventDecorator(c.getInt(6),CalendarDay.from(ld) );
            calendarView.addDecorator(eventDecorator);

            ids.add(c.getInt(0));
            titles.add(c.getString(1));
            subjects.add(c.getString(2));
            due_dates.add(c.getString(3));
            time.add(c.getString(4));
            colors.add(c.getInt(6));
        }



//        Toast.makeText(getActivity().getApplicationContext(),titles.get(1), Toast.LENGTH_SHORT).show();

        RecyclerView recyclerView = view.findViewById(R.id.homework_recyler_view);


        RelativeLayout rl = view.findViewById(R.id.recycler_relative);
        rl.getLayoutParams().height = db.getHomeworkCount() * 200;


        final HomeworkCalendarRecyclerView adapter1 = new HomeworkCalendarRecyclerView(ids,titles,subjects,colors,due_dates,time,getActivity());
        recyclerView.setAdapter(adapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemTouchHelper(adapter1,ids,recyclerView,getActivity().getApplicationContext());


        return view;
    }

    public void itemTouchHelper(final HomeworkCalendarRecyclerView  adapter, final ArrayList<Integer> ids, final RecyclerView recyclerView, final Context cont){

        final ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter
                db.deleteHomework(String.valueOf(ids.get(viewHolder.getAdapterPosition())));
                adapter.removeItem(viewHolder.getAdapterPosition());
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (viewHolder != null) {
                    final View foregroundView = ((HomeworkCalendarRecyclerView.ViewHolder) viewHolder).homeworkCard;
                    getDefaultUIUtil().onSelected(foregroundView);
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                final View foregroundView = ((HomeworkCalendarRecyclerView.ViewHolder) viewHolder).homeworkCard;
                getDefaultUIUtil().clearView(foregroundView);
            }

            @Override
            public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                final View foregroundView = ((HomeworkCalendarRecyclerView.ViewHolder) viewHolder).homeworkCard;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // view the background view
                final View foregroundView = ((HomeworkCalendarRecyclerView.ViewHolder) viewHolder).homeworkCard;
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