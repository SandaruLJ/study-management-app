package com.example.timetable.Class;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.print.PrintAttributes;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;


import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassListFragment extends Fragment {
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
        View view =  inflater.inflate(R.layout.fragment_class_list, container, false);

        TextView date1 = view.findViewById(R.id.date1);
        TextView date2 = view.findViewById(R.id.date2);
        TextView date3 = view.findViewById(R.id.date3);
        TextView date4 = view.findViewById(R.id.date4);
        TextView date5 = view.findViewById(R.id.date5);
        TextView date6 = view.findViewById(R.id.date6);
        TextView date7 = view.findViewById(R.id.date7);

        TextView tv[] = new TextView[7];
        tv[0] = date1;
        tv[1] = date2;
        tv[2] = date3;
        tv[3] = date4;
        tv[4] = date5;
        tv[5] = date6;
        tv[6] = date7;


        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        MaterialCalendarView calendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        Calendar cal = Calendar.getInstance();

        Calendar now = Calendar.getInstance();
        //        String[] days = new String[7];

        SimpleDateFormat format = new SimpleDateFormat("dd/MM");
        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 1; //add 2 if your week start on monday
        now.add(Calendar.DAY_OF_MONTH, delta );
        Date mon = now.getTime();
        for (int i = 0; i < 7; i++)
        {

//            days[i] = format.format(now.getTime());
            now.add(Calendar.DAY_OF_MONTH, 1);
            tv[i].setText(format.format(now.getTime()));
        }



        Date sdate = null;
        Date stime = null;
        Date etime = null;
        String st = null;
        String cname = null;

        Date currentDate = new Date();
        Calendar current = Calendar.getInstance();
        current.setTime(currentDate);
        current.add(Calendar.DATE, 7);
        Date week = current.getTime();



        final Cursor c = db.getAllClass();
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
       
        String arr[] = null;
        int mt;
        int colour = 0;
        int i = 1;
        String t,r,tp,rp;
        while (c.moveToNext()) {

            try {
                cname = c.getString(1);
                sdate = dateFormat.parse(c.getString(13));
                stime = timeFormat.parse(c.getString(11));
                etime = timeFormat.parse(c.getString(12));
                colour = c.getInt(8);
                st = c.getString(11);
                arr = st.split(":");

            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Convert the date to Calendar object
            cal.setTime(sdate);

            LocalDate ld = sdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            DayOfWeek day = ld.getDayOfWeek();
            int dayValue = day.getValue();
            long difference = (etime.getTime() - stime.getTime()) / 100000;
            int height = (int) (difference * 5);

            mt = 250 + Integer.parseInt(arr[0]) * 180 + Integer.parseInt(arr[1]) * 3;
            TextView tw = new TextView(getActivity().getApplicationContext());
            if (cname.length() > 4) {
                tw.setText(cname.substring(0, 4));
            } else {
                tw.setText(cname);
            }
            if (sdate.compareTo(mon) >= 0 && sdate.compareTo(week) < 0){

            RelativeLayout.LayoutParams tvparams = new RelativeLayout.LayoutParams(130, height);
            tw.setTextColor(Color.WHITE);
            tw.setTypeface(null, Typeface.BOLD);
            tw.setGravity(Gravity.CENTER);
            final RelativeLayout relativeLayout = new RelativeLayout(getActivity().getApplicationContext());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(130, height);
            params.topMargin = mt;
            relativeLayout.setBackgroundColor(colour);


                if (dayValue == 1) {
                    params.leftMargin = 130;
                } else if (dayValue == 2) {
                    params.leftMargin = 260;
                } else if (dayValue == 3) {
                    params.leftMargin = 390;
                } else if (dayValue == 4) {
                    params.leftMargin = 520;
                } else if (dayValue == 5) {
                    params.leftMargin = 670;
                } else if (dayValue == 6) {
                    params.leftMargin = 800;
                } else if (dayValue == 7) {
                    params.leftMargin = 930;
                }


            relativeLayout.setLayoutParams(params);
            relativeLayout.addView(tw, tvparams);
            parent.addView(relativeLayout);
        }

//                Toast.makeText(getActivity().getApplicationContext(), arr[0], Toast.LENGTH_LONG).show();



        }


//
//        final RelativeLayout relativeLayout2 = new RelativeLayout(getActivity().getApplicationContext());
//        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(130,180);
//        params2.topMargin= 250;
//        params2.leftMargin= 260;
//        relativeLayout2.setLayoutParams(params2);
//        relativeLayout2.setBackgroundColor(Color.YELLOW);
//        parent.addView(relativeLayout2);
//
//        final RelativeLayout relativeLayout3 = new RelativeLayout(getActivity().getApplicationContext());
//        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(130,180);
//        params3.topMargin= 250;
//        params3.leftMargin= 380;
//        relativeLayout3.setLayoutParams(params3);
//        relativeLayout3.setBackgroundColor(Color.BLUE);
//        parent.addView(relativeLayout3);



        return view;
    }
//    MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
//        @Override
//        public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
            // Populate the week view with some events.
//            List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
//            DateFormat timeFormat = new SimpleDateFormat("HH:mm");
//            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            Calendar startTime = Calendar.getInstance();
//            Calendar sdate = Calendar.getInstance();
//            Calendar edate = Calendar.getInstance();
//            Calendar endTime = Calendar.getInstance();
//            Date stime = null;
//            Date etime = null;
//            Date classDate = null;
//            final Cursor c = db.getAllClass();
//
//            while (c.moveToNext()) {
//
//                try {
//                    classDate = dateFormat.parse(c.getString(13));
//                    stime = timeFormat.parse(c.getString(11));
//                    etime = timeFormat.parse(c.getString(12));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                sdate.setTime(classDate);
//                edate.setTime(classDate);
//                startTime.setTime(stime);
//                endTime.setTime(etime);
//
//                sdate.set(Calendar.HOUR_OF_DAY,startTime.get(Calendar.HOUR_OF_DAY));
//                sdate.set(Calendar.MINUTE,startTime.get(Calendar.MINUTE));
//                edate.set(Calendar.HOUR_OF_DAY,endTime.get(Calendar.HOUR_OF_DAY));
//                edate.set(Calendar.MINUTE,endTime.get(Calendar.MINUTE));
//
//                WeekViewEvent event = new WeekViewEvent(1,c.getString(1),startTime,endTime);
//                event.setColor(c.getInt(8));
//                events.add(event);
//
//                startTime.get(Calendar.HOUR_OF_DAY);
//            }
//            return events;

//            List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
//
//            Calendar startTime = Calendar.getInstance();
//            startTime.set(Calendar.DAY_OF_MONTH, 19);
//            startTime.set(Calendar.HOUR_OF_DAY, 1);
//            startTime.set(Calendar.MINUTE, 0);
//            startTime.set(Calendar.MONTH, 10);
//            startTime.set(Calendar.YEAR, 2015);
//            Calendar endTime = (Calendar) startTime.clone();
//            endTime.set(Calendar.DAY_OF_MONTH, 19);
//            endTime.set(Calendar.HOUR_OF_DAY, 5);
//            endTime.set(Calendar.MINUTE, 0);
//            endTime.set(Calendar.MONTH, 10);
//            endTime.set(Calendar.YEAR, 2015);
//            WeekViewEvent event = new WeekViewEvent(0, "Epoca", startTime, endTime);
//            event.setColor(getResources().getColor(R.color.lightBlue1));
//            events.add(event);
//
//
//            startTime = Calendar.getInstance();
//            startTime.set(2015, 10, 19, 6, 00);
//            endTime = Calendar.getInstance();
//            endTime.set(2015, 10, 19, 9, 00);
//            WeekViewEvent event2 = new WeekViewEvent(0,"00kjbhjbhjbjbhj",startTime, endTime);
//            event2.setColor(getResources().getColor(R.color.lightBlue1));
//            events.add(event2);
//
//
//            return events;
//
//        }
//    };
}