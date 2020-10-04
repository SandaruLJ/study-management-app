package com.example.timetable;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetable.Class.ClassDayFragment;
import com.example.timetable.Database.DBHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AllFragment extends Fragment {

    DBHandler db;
    public static Fragment newInstance(int tab){
        AllFragment cl = new AllFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("stab", tab);
        cl.setArguments(arguments);
        return cl;
    }
    int stab;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());

        Bundle extras = getArguments();
        stab = extras.getInt("stab");
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.dashboard_events,container,false);

        final ArrayList<Integer> ids = new ArrayList<>();
        final ArrayList<String> event = new ArrayList<>();
        final ArrayList<String> subject = new ArrayList<>();
        final ArrayList<String> location = new ArrayList<>();
        final  ArrayList<Integer> colours = new ArrayList<>();
        final ArrayList<String> stime = new ArrayList<>();
        final ArrayList<String> etime = new ArrayList<>();
        final ArrayList<String> type  = new ArrayList<>();

        Calendar today = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = null;

        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        String currentDate = dd+"/"+(mm+1)+"/"+yy;
        String todayDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.ENGLISH);

        //                if (c.getString(3).length()> 22){
//                    String sub = c.getString(2);
//                    subject.add(sub.substring(0,20));
//                }else{
//                    subject.add(c.getString(3));
//                }

        Cursor c;

        if(stab == 0) {
            c= db.getAllClass();
            while (c.moveToNext()) {
                String date = c.getString(13);
                if (date.equals(currentDate) || todayDay.equals(c.getString(10))) {

                    ids.add(c.getInt(0));
                    event.add(c.getString(1));
                    subject.add(c.getString(3));
                    location.add(c.getString(6));
                    stime.add(c.getString(11));
                    etime.add(c.getString(12));
                    colours.add(c.getInt(8));
                    type.add("Class");

                }
            }
            c = db.getAllHomework();

            while (c.moveToNext()) {
                String date = c.getString(3);
                if (date.equals(currentDate)) {

                    ids.add(c.getInt(0));
                    event.add(c.getString(1));
                    subject.add(c.getString(2));
                    location.add("");
                    stime.add(c.getString(4));
                    etime.add("");
                    colours.add(c.getInt(6));
                    type.add("Homework");

                }
            }

            c = db.getAllExam();

            while (c.moveToNext()) {
                String date = c.getString(4);
                if (date.equals(currentDate)) {

                    ids.add(c.getInt(0));
                    event.add(c.getString(1));
                    subject.add(c.getString(2));
                    location.add(c.getString(3));
                    stime.add(c.getString(5));
                    etime.add(c.getString(6));
                    colours.add(c.getInt(8));
                    type.add("Exam");

                }
            }
            c = db.getAllStudies();
            Cursor c1 = db.getAllSubjects();
            while (c.moveToNext()) {
                String date = c.getString(4);
                if (date.equals(currentDate)) {

                    ids.add(c.getInt(0));
                    event.add(c.getString(1));
                    while(c1.moveToNext()){
                        if(c1.getInt(0) == c.getInt(0)){
                            subject.add(c1.getString(1));
                        }
                    }
                    location.add("");
                    stime.add(c.getString(5));
                    etime.add(c.getString(6));
                    colours.add(c.getInt(3));
                    type.add("Study");

                }
            }

            c = db.getAllGoals();
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
                    event.add(c.getString(1));
                    subject.add("");
                    location.add("");
                    stime.add("");
                    etime.add("");
                    colours.add(c.getInt(2));
                    type.add("Goals");

                }
            }
        }else if(stab == 1){
            c= db.getAllClass();
            while (c.moveToNext()) {
                String date = c.getString(13);
                if (date.equals(currentDate) || todayDay.equals(c.getString(10))) {

                    ids.add(c.getInt(0));
                    event.add(c.getString(1));
                    subject.add(c.getString(3));
                    location.add(c.getString(6));
                    stime.add(c.getString(11));
                    etime.add(c.getString(12));
                    colours.add(c.getInt(8));
                    type.add("Class");

                }
            }
        }else if(stab == 2){
            c = db.getAllHomework();

            while (c.moveToNext()) {
                String date = c.getString(3);
                if (date.equals(currentDate)) {

                    ids.add(c.getInt(0));
                    event.add(c.getString(1));
                    subject.add(c.getString(2));
                    location.add("");
                    stime.add(c.getString(4));
                    etime.add("");
                    colours.add(c.getInt(6));
                    type.add("Homework");

                }
            }
        }else if(stab == 3){
            c = db.getAllExam();

            while (c.moveToNext()) {
                String date = c.getString(4);
                if (date.equals(currentDate)) {

                    ids.add(c.getInt(0));
                    event.add(c.getString(1));
                    subject.add(c.getString(2));
                    location.add(c.getString(3));
                    stime.add(c.getString(5));
                    etime.add(c.getString(6));
                    colours.add(c.getInt(8));
                    type.add("Exam");

                }
            }
        }else if(stab == 5){
            c = db.getAllGoals();
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
                    event.add(c.getString(1));
                    subject.add("");
                    location.add("");
                    stime.add("");
                    etime.add("");
                    colours.add(c.getInt(2));
                    type.add("Goals");

                }
            }
        }else if(stab == 4){
            c = db.getAllStudies();
            Cursor c1 = db.getAllSubjects();
            while (c.moveToNext()) {
                String date = c.getString(4);
                if (date.equals(currentDate)) {

                    ids.add(c.getInt(0));
                    event.add(c.getString(1));
                    while(c1.moveToNext()){
                        if(c1.getInt(0) == c.getInt(2)){
                            subject.add(c1.getString(1));
                        }else {
                            subject.add("");
                        }
                    }
                    location.add("");
                    stime.add(c.getString(5));
                    etime.add(c.getString(6));
                    colours.add(c.getInt(3));
                    type.add("Study");

                }
            }
        }
//        RelativeLayout rl = view.findViewById(R.id.recycler_relative);
//        rl.getLayoutParams().height = 3000;

        RecyclerView recyclerView = view.findViewById(R.id.dashboard_recycler_view);
        final DashboardRecyclerView adapter1 = new DashboardRecyclerView(ids, event, subject, location, colours,stime,etime,type, getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        itemTouchHelper(adapter1, ids, recyclerView, getActivity().getApplicationContext());


        return view;
    }
    public void itemTouchHelper(final DashboardRecyclerView adapter, final ArrayList<Integer> ids, final RecyclerView recyclerView, final Context cont) {

        final ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter

                db.deleteExam(String.valueOf(ids.get(viewHolder.getAdapterPosition())));
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
                    final View foregroundView = ((DashboardRecyclerView.ViewHolder) viewHolder).eventCard;
                    getDefaultUIUtil().onSelected(foregroundView);
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                final View foregroundView = ((DashboardRecyclerView.ViewHolder) viewHolder).eventCard;
                getDefaultUIUtil().clearView(foregroundView);
            }

            @Override
            public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                final View foregroundView = ((DashboardRecyclerView.ViewHolder) viewHolder).eventCard;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // view the background view
                final View foregroundView = ((DashboardRecyclerView.ViewHolder) viewHolder).eventCard;
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
