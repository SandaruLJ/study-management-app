package com.example.timetable.Exam;

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

import com.example.timetable.Database.DBHandler;

import com.example.timetable.Homework.HomeworkWeekDayFragment;
import com.example.timetable.R;
import com.example.timetable.ReminderBroadcast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExamUpcomingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExamUpcomingFragment extends Fragment {

    DBHandler db;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_exam_upcoming, container, false);

        final ArrayList<String> exam = new ArrayList<>();
        final ArrayList<String> subject = new ArrayList<>();
        final ArrayList<String> location = new ArrayList<>();
        final  ArrayList<Integer> colours = new ArrayList<>();
        final  ArrayList<Integer> ids = new ArrayList<>();
        final ArrayList<String> time = new ArrayList<>();
        final ArrayList<String> date = new ArrayList<>();
        final Cursor c = db.getAllExam();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Date d1 = null;
        Date d2 = null;

        while (c.moveToNext()) {
            try {
                d1 = dateFormat.parse(c.getString(4));
                d2 = timeFormat.parse(c.getString(5));
                cal.setTime(d1);
                cal2.setTime(d2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cal.set(Calendar.HOUR_OF_DAY,cal2.get(Calendar.HOUR_OF_DAY));
            cal.set(Calendar.MINUTE,cal2.get(Calendar.MINUTE));

            long rem = getRemainingTime(cal.getTime().getTime() , System.currentTimeMillis());


            if(rem>=0) {
                ids.add(c.getInt(0));

                if (c.getString(1).length()> 16){
                    String sub = c.getString(1);
                    exam.add(sub.substring(0,16));
                }else{
                    exam.add(c.getString(1));
                }
                colours.add(c.getInt(8));
                if (c.getString(2).length()> 22){
                    String sub = c.getString(2);
                    subject.add(sub.substring(0,22));
                }else{
                    subject.add(c.getString(2));
                }

                location.add(c.getString(3));
                date.add(c.getString(4));
                time.add(c.getString(5) + "-" + c.getString(6));

            }


        }



        RecyclerView recyclerView = view.findViewById(R.id.exam_recycler_view);
        final ExamRecyclerView adapter = new ExamRecyclerView(ids, exam, subject, location, colours,date,time, getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemTouchHelper(adapter, ids, recyclerView, getActivity().getApplicationContext());


        return view;
    }
    public long getRemainingTime(long ntime, long ctime){
        long rem = ntime - ctime;
        return rem;
    }
    public void itemTouchHelper(final ExamRecyclerView adapter, final ArrayList<Integer> ids, final RecyclerView recyclerView, final Context cont) {

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
                    final View foregroundView = ((ExamRecyclerView.ViewHolder) viewHolder).examCard;
                    getDefaultUIUtil().onSelected(foregroundView);
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                final View foregroundView = ((ExamRecyclerView.ViewHolder) viewHolder).examCard;
                getDefaultUIUtil().clearView(foregroundView);
            }

            @Override
            public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                final View foregroundView = ((ExamRecyclerView.ViewHolder) viewHolder).examCard;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // view the background view
                final View foregroundView = ((ExamRecyclerView.ViewHolder) viewHolder).examCard;
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


