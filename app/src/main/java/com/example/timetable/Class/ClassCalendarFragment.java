package com.example.timetable.Class;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.threeten.bp.LocalDate;

import java.util.Calendar;
import java.util.Date;

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

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        MaterialCalendarView calendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        Calendar cal = Calendar.getInstance();


        Date d1 = null;

        final Cursor c = db.getAllClass();

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
        }


        return view;
    }
}