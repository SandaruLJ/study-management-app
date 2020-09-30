package com.example.timetable.Class;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;
import com.example.timetable.ReminderBroadcast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link mondayClass#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mondayClass extends Fragment  {


    DBHandler db;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monday_class, container, false);

        final ArrayList<String> className = new ArrayList<>();
        final ArrayList<String> teacher = new ArrayList<>();
        final ArrayList<String> venue = new ArrayList<>();
        final ArrayList<String> sTime = new ArrayList<>();
        final ArrayList<String> eTime = new ArrayList<>();
        final ArrayList<Integer> ids = new ArrayList<>();
        final ArrayList<Integer> color = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date currentDate = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(currentDate);
        cl.add(Calendar.DATE, 7);
        Date week = cl.getTime();
        Date d1 = null;

        final Cursor c = db.getAllClass();

        while (c.moveToNext()){

            try {
                d1 = dateFormat.parse(c.getString(13));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(d1.compareTo(week) < 0 || c.getString(10).equals("Monday")){
                ids.add(c.getInt(0));
                className.add(c.getString(1));
                teacher.add(c.getString(5));
                venue.add(c.getString(6));
                sTime.add(c.getString(11));
                eTime.add(c.getString(12));
                color.add(c.getInt(8));
            }


        }

        RecyclerView recyclerView = view.findViewById(R.id.class_recyler_view);
        final ClassRecyclerViewAdapter adapter1 = new ClassRecyclerViewAdapter(className,teacher,venue,sTime,eTime,ids,color,getActivity());
        recyclerView.setAdapter(adapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemTouchHelper(adapter1,ids,recyclerView,getActivity().getApplicationContext());





        // Inflate the layout for this fragment
        return view;
    }
    public void itemTouchHelper(final ClassRecyclerViewAdapter  adapter, final ArrayList<Integer> ids, final RecyclerView recyclerView, final Context cont){

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
                Intent intent = new Intent(getActivity().getApplicationContext(), ReminderBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(),ids.get(viewHolder.getAdapterPosition()),intent,0);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

                adapter.removeItem(viewHolder.getAdapterPosition());
                Toast.makeText(getActivity().getApplicationContext(), "Class Deleted Successfully", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (viewHolder != null) {
                    final View foregroundView = ((ClassRecyclerViewAdapter.ViewHolder) viewHolder).classCard;
                    getDefaultUIUtil().onSelected(foregroundView);
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                final View foregroundView = ((ClassRecyclerViewAdapter.ViewHolder) viewHolder).classCard;
                getDefaultUIUtil().clearView(foregroundView);
            }

            @Override
            public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                final View foregroundView = ((ClassRecyclerViewAdapter.ViewHolder) viewHolder).classCard;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // view the background view
                final View foregroundView = ((ClassRecyclerViewAdapter.ViewHolder) viewHolder).classCard;
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