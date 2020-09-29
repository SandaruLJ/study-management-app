package com.example.timetable.Homework;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeworkListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeworkListFragment extends Fragment {


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
        View view =  inflater.inflate(R.layout.fragment_homework_list, container, false);

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
            now.add(Calendar.DAY_OF_MONTH, 1);
            tv[i].setText(format.format(now.getTime()));
        }

        Date sdate = null;
        Date stime = null;
        Date etime = null;
        String st = null;
        String homework = null;

        Date currentDate = new Date();
        Calendar current = Calendar.getInstance();
        current.setTime(currentDate);
        current.add(Calendar.DATE, 7);
        Date week = current.getTime();



        final Cursor c = db.getAllHomework();
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);

        String arr[] = null;
        int mt;
        int colour = 0;
        int i = 1;
        String t,r,tp,rp;
        while (c.moveToNext()) {

            try {
                homework = c.getString(1);
                sdate = dateFormat.parse(c.getString(3));
                stime = timeFormat.parse(c.getString(4));

                colour = c.getInt(6);
                st = c.getString(4);
                arr = st.split(":");

            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Convert the date to Calendar object
            cal.setTime(sdate);

            LocalDate ld = sdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            DayOfWeek day = ld.getDayOfWeek();
            int dayValue = day.getValue();
//            long difference = (etime.getTime() - stime.getTime()) / 100000;
//            int height = (int) (difference * 5);

            mt = 250 + Integer.parseInt(arr[0]) * 180 + Integer.parseInt(arr[1]) * 3;
            TextView tw = new TextView(getActivity().getApplicationContext());
            if (homework.length() > 4) {
                tw.setText(homework.substring(0, 4));
            } else {
                tw.setText(homework);
            }
            if (sdate.compareTo(mon) >= 0 && sdate.compareTo(week) < 0){

                RelativeLayout.LayoutParams tvparams = new RelativeLayout.LayoutParams(130, 180);
                tw.setTextColor(Color.WHITE);
                tw.setTypeface(null, Typeface.BOLD);
                tw.setGravity(Gravity.CENTER);
                final RelativeLayout relativeLayout = new RelativeLayout(getActivity().getApplicationContext());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(130, 180);
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

        return view;
    }
}