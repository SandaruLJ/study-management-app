package com.example.timetable.Class;

import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.threeten.bp.LocalDate;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassCalendarFragment extends Fragment {

    DBHandler db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DBHandler(getActivity().getApplicationContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_class_calendar, container, false);

        final ArrayList<String> className = new ArrayList<>();
        final ArrayList<String> teacher = new ArrayList<>();
        final ArrayList<String> venue = new ArrayList<>();
        final ArrayList<String> sTime = new ArrayList<>();
        final ArrayList<String> eTime = new ArrayList<>();
        final ArrayList<Integer> ids = new ArrayList<>();
        final ArrayList<Integer> color = new ArrayList<>();
        final ArrayList<String> date = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        MaterialCalendarView calendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        Calendar cal = Calendar.getInstance();


        Date d1 = null;

        final Cursor c = db.getSortClass();

        while (c.moveToNext()) {

            try {
                d1 = dateFormat.parse(c.getString(13));
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
            EventDecorator eventDecorator = new EventDecorator(c.getInt(8),CalendarDay.from(ld) );
            calendarView.addDecorator(eventDecorator);

            ids.add(c.getInt(0));
            className.add(c.getString(1));
            teacher.add(c.getString(5));
            venue.add(c.getString(6));
            sTime.add(c.getString(11));
            eTime.add(c.getString(12));
            color.add(c.getInt(8));
            date.add(c.getString(13));
        }



//        Toast.makeText(getActivity().getApplicationContext(), String.valueOf(db.getClassCount()), Toast.LENGTH_SHORT).show();

        RecyclerView recyclerView = view.findViewById(R.id.class_recyler_view);


        RelativeLayout rl = view.findViewById(R.id.recycler_relative);
       rl.getLayoutParams().height = db.getClassCount() * 200;


        final ClassCalendarRecyclerView adapter1 = new ClassCalendarRecyclerView(className,teacher,venue,sTime,eTime,date,ids,color,getActivity());
        recyclerView.setAdapter(adapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemTouchHelper(adapter1,ids,recyclerView,getActivity().getApplicationContext());



        return view;
    }
    public void itemTouchHelper(final ClassCalendarRecyclerView  adapter, final ArrayList<Integer> ids, final RecyclerView recyclerView, final Context cont){

        final ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter
                db.deleteClass(String.valueOf(ids.get(viewHolder.getAdapterPosition())));
                adapter.removeItem(viewHolder.getAdapterPosition());
                Toast.makeText(getActivity().getApplicationContext(), "Class Deleted Successfully", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (viewHolder != null) {
                    final View foregroundView = ((ClassCalendarRecyclerView.ViewHolder) viewHolder).classCard;
                    getDefaultUIUtil().onSelected(foregroundView);
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                final View foregroundView = ((ClassCalendarRecyclerView.ViewHolder) viewHolder).classCard;
                getDefaultUIUtil().clearView(foregroundView);
            }

            @Override
            public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                final View foregroundView = ((ClassCalendarRecyclerView.ViewHolder) viewHolder).classCard;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // view the background view
                final View foregroundView = ((ClassCalendarRecyclerView.ViewHolder) viewHolder).classCard;
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