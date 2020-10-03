package com.example.timetable.Homework;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
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


import com.example.timetable.Class.ClassDayFragment;
import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;
import com.example.timetable.ReminderBroadcast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeworkWeekDayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeworkWeekDayFragment extends Fragment {

    DBHandler db;
    int stab;
    public static Fragment newInstance(int tab){
       HomeworkWeekDayFragment cl = new HomeworkWeekDayFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("stab", tab);
        cl.setArguments(arguments);
        return cl;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());
        Bundle extras = getArguments();
        stab = extras.getInt("stab");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homework_week_day, container, false);



        final ArrayList<String> titles = new ArrayList<>();
        final ArrayList<String> subjects = new ArrayList<>();
        final ArrayList<String> due_dates = new ArrayList<>();
        final ArrayList<String> time = new ArrayList<>();
        final ArrayList<Integer> colors = new ArrayList<>();
        final ArrayList<Integer> ids = new ArrayList<>();

        final Cursor c = db.getAllHomework();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date currentDate = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(currentDate);
        cl.add(Calendar.DATE, 7);
        Date week = cl.getTime();
        Calendar now = Calendar.getInstance();
        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 1; //add 2 if your week start on monday
        now.add(Calendar.DAY_OF_MONTH, delta );
        Date mon = now.getTime();
        Date d1 = null;

        while (c.moveToNext()){

            try {
                d1 = dateFormat.parse(c.getString(3));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            LocalDate today = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            DayOfWeek day = today.getDayOfWeek();
            int dayValue = day.getValue();


//            ids.add(c.getInt(0));
//            titles.add(c.getString(1));
//            subjects.add(c.getString(2));
//            due_dates.add(c.getString(3));
//            colors.add(c.getInt(6));
//            Toast.makeText(getActivity().getApplicationContext(), String.valueOf(stab ), Toast.LENGTH_LONG).show();


            if(( d1.compareTo(mon)>=0 && d1.compareTo(week) < 0 && dayValue == 1 && stab == 0) ){

                ids.add(c.getInt(0));
                titles.add(c.getString(1));
                if (c.getString(2).length()> 22){
                    String sub = c.getString(2);
                    subjects.add(sub.substring(0,22));
                }else{
                    subjects.add(c.getString(2));
                }
                time.add(c.getString(4));
                due_dates.add(c.getString(3));
                colors.add(c.getInt(6));


            }else if(( d1.compareTo(mon)>=0 && d1.compareTo(week) < 0 && dayValue == 2 && stab == 1)  ){

                ids.add(c.getInt(0));
                titles.add(c.getString(1));
                if (c.getString(2).length()> 22){
                    String sub = c.getString(2);
                    subjects.add(sub.substring(0,22));
                }else{
                    subjects.add(c.getString(2));
                }
                time.add(c.getString(4));
                due_dates.add(c.getString(3));
                colors.add(c.getInt(6));

            }else if(( d1.compareTo(mon)>=0 && d1.compareTo(week) < 0 && dayValue == 3 && stab == 2)){

                ids.add(c.getInt(0));
                titles.add(c.getString(1));
                if (c.getString(2).length()> 22){
                    String sub = c.getString(2);
                    subjects.add(sub.substring(0,22));
                }else{
                    subjects.add(c.getString(2));
                }
                time.add(c.getString(4));
                due_dates.add(c.getString(3));
                colors.add(c.getInt(6));

            }else if(( d1.compareTo(mon)>=0 && d1.compareTo(week) < 0 && dayValue == 4 && stab == 3)  ){

                ids.add(c.getInt(0));
                titles.add(c.getString(1));
                if (c.getString(2).length()> 22){
                    String sub = c.getString(2);
                    subjects.add(sub.substring(0,22));
                }else{
                    subjects.add(c.getString(2));
                }
                time.add(c.getString(4));
                due_dates.add(c.getString(3));
                colors.add(c.getInt(6));

            }else if(( d1.compareTo(mon)>=0 && d1.compareTo(week) < 0 && dayValue == 5 && stab == 4) ){

                ids.add(c.getInt(0));
                titles.add(c.getString(1));
                if (c.getString(2).length()> 22){
                    String sub = c.getString(2);
                    subjects.add(sub.substring(0,22));
                }else{
                    subjects.add(c.getString(2));
                }
                time.add(c.getString(4));
                due_dates.add(c.getString(3));
                colors.add(c.getInt(6));

            }else if(( d1.compareTo(mon)>=0 && d1.compareTo(week) < 0 && dayValue == 6 && stab == 5)){

                ids.add(c.getInt(0));
                titles.add(c.getString(1));
                if (c.getString(2).length()> 22){
                    String sub = c.getString(2);
                    subjects.add(sub.substring(0,22));
                }else{
                    subjects.add(c.getString(2));
                }
                time.add(c.getString(4));
                due_dates.add(c.getString(3));
                colors.add(c.getInt(6));

            }else if(( d1.compareTo(mon)>=0 && d1.compareTo(week) < 0 && dayValue == 7 && stab == 6)){

                ids.add(c.getInt(0));
                titles.add(c.getString(1));
                if (c.getString(2).length()> 22){
                    String sub = c.getString(2);
                    subjects.add(sub.substring(0,22));
                }else{
                    subjects.add(c.getString(2));
                }
                time.add(c.getString(4));
                due_dates.add(c.getString(3));
                colors.add(c.getInt(6));

            }



        }

        RecyclerView recyclerView = view.findViewById(R.id.homework_recyler_view);
        final HomeworkRecyclerView adapter1 = new HomeworkRecyclerView(ids,titles,subjects,colors,due_dates,time,getActivity());
        recyclerView.setAdapter(adapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemTouchHelper(adapter1,ids,recyclerView,getActivity().getApplicationContext());



        return view;
    }


    public void itemTouchHelper(final HomeworkRecyclerView  adapter, final ArrayList<Integer> ids, final RecyclerView recyclerView, final Context cont){

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
                Intent intent = new Intent(getActivity().getApplicationContext(), ReminderBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(),ids.get(viewHolder.getAdapterPosition()),intent,0);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
                adapter.removeItem(viewHolder.getAdapterPosition());
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (viewHolder != null) {
                    final View foregroundView = ((HomeworkRecyclerView.ViewHolder) viewHolder).homeworkCard;
                    getDefaultUIUtil().onSelected(foregroundView);
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                final View foregroundView = ((HomeworkRecyclerView.ViewHolder) viewHolder).homeworkCard;
                getDefaultUIUtil().clearView(foregroundView);
            }

            @Override
            public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                final View foregroundView = ((HomeworkRecyclerView.ViewHolder) viewHolder).homeworkCard;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // view the background view
                final View foregroundView = ((HomeworkRecyclerView.ViewHolder) viewHolder).homeworkCard;
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