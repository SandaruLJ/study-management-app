package com.example.timetable.Class;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.timetable.ColorPicker;
import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;
import com.example.timetable.ReminderBroadcast;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link editClass#newInstance} factory method to
 * create an instance of this fragment.
 */
public class editClass extends Fragment {

    EditText className,type,teacher,classRoom,note;
    Spinner course,subject,day;
    Button colorbtn,save;
    DBHandler db ;
    int selectedTab,id;
    String stab;
    public static String  dayd = null, startTimed = null, endTimed =null, sDated = null, eDated = null, reminderd = null,freqd = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());
    }   

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_class, container, false);
         id  = 0;
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            id = Integer.parseInt(bundle.get("id").toString());

        }



        if(id != 0) {

            String name = null, coursed, subjectd, classTyped = null, teacherd = null, classroomd = null, noted = null;
            int colourd = 0;
            Cursor c = db.getSingleClass(id);

            while (c.moveToNext()) {
                name = c.getString(1);
                coursed = c.getString(2);
                subjectd = c.getString(3);
                classTyped = c.getString(4);
                teacherd = c.getString(5);
                classroomd = c.getString(6);
                noted = c.getString(7);
                colourd = c.getInt(8);
                freqd = c.getString(9);
                dayd = c.getString(10);
                startTimed = c.getString(11);
                endTimed = c.getString(12);
                sDated = c.getString(13);
                eDated = c.getString(14);
                reminderd = c.getString(15);
            }


            className = (EditText) view.findViewById(R.id.className);
            type = (EditText) view.findViewById(R.id.type);
            teacher = (EditText) view.findViewById(R.id.teacher);
            classRoom = (EditText) view.findViewById(R.id.classRoom);
            note = (EditText) view.findViewById(R.id.note);
            subject = (Spinner) view.findViewById(R.id.subjectSelect);
            course = (Spinner) view.findViewById(R.id.courseSelect);
            day = (Spinner) view.findViewById(R.id.daySelect);
            save = (Button) view.findViewById(R.id.saveClass);


            
            //Colour Picker
            final Button colorbtn = (Button) view.findViewById(R.id.colorbtn);
            final Button bgBtn = (Button) view.findViewById(R.id.testbtn);
            colorbtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ColorPicker cp = new ColorPicker();
                    cp.openColorPicker(getChildFragmentManager(), colorbtn, bgBtn);
                }

            });

            className.setText(name);
            type.setText(classTyped);
            teacher.setText(teacherd);
            classRoom.setText(classroomd);
            note.setText(noted);
            colorbtn.setBackgroundTintList(ColorStateList.valueOf(colourd));
            bgBtn.setBackgroundColor(colourd);

            ImageView add = (ImageView) view.findViewById(R.id.addIcon);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addClass fragment = new addClass();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity().getApplicationContext(), ReminderBroadcast.class);
                    intent.putExtra("reminderType", "Class Reminder");
                    intent.putExtra("title", className.getText().toString());
                    DateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String date = null;
                    Date startTime = null;
                    Date sdate = null;
                    String stime;
                    String rtype;
                    Calendar cald = Calendar.getInstance();
                    Calendar calt = Calendar.getInstance();
                    if(selectedTab == 0) {
                        intent.putExtra("date", ClassWeekFragment.start.getText().toString());
                        intent.putExtra("time", ClassWeekFragment.stime.getText().toString());
                        stime = ClassWeekFragment.stime.getText().toString();
                        date = ClassWeekFragment.start.getText().toString();
                        rtype = ClassWeekFragment.reminder.getSelectedItem().toString();
                    }else{
                        intent.putExtra("date", ClassDateFragment.sdate.getText().toString());
                        intent.putExtra("time", ClassDateFragment.stime.getText().toString());
                        stime = ClassDateFragment.stime.getText().toString();
                        date = ClassDateFragment.sdate.getText().toString();
                        rtype = ClassDateFragment.reminder.getSelectedItem().toString();
                    }

                    int min = 0;
                    int day;

                    if(rtype.equals("5 minutes")){
                        min = -5;
                    }else  if(rtype.equals("10 minutes")){
                        min = -10;
                    } else  if(rtype.equals("15 minutes")){
                        min = -15;
                    } else  if(rtype.equals("30 minutes")){
                        min = -30;
                    } else  if(rtype.equals("1 hour")){
                        min = -60;
                    } else  if(rtype.equals("2 hours")){
                        min = -120;
                    } else  if(rtype.equals("6 hours")){
                        min = -360;
                    }else  if(rtype.equals("12 hours")){
                        min = -720;
                    }else  if(rtype.equals("1 day")){
                        min = 1440;
                    }
                    try {
                        startTime = timeFormat.parse(stime);
                        sdate = dateFormat.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    cald.setTime(sdate);

                    calt.setTime(startTime);

                    cald.add(Calendar.HOUR_OF_DAY,calt.get(Calendar.HOUR_OF_DAY));
                    cald.add(Calendar.MINUTE,calt.get(Calendar.MINUTE));
                    cald.add(Calendar.MINUTE,min);

                    calt.add(Calendar.MINUTE,min);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(),id,intent,0);
                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    long timeAtButtonClick = cald.getTimeInMillis();

                    //Used to cancelthe previous alarm
                    alarmManager.cancel(pendingIntent);
                    //Sets the new alarm
                    alarmManager.set(AlarmManager.RTC_WAKEUP,
                            timeAtButtonClick ,
                            pendingIntent);

                    boolean isUpdated;
                    if (selectedTab == 0) {
                        stab = "Weekly";
                        isUpdated = db.updateClass(id,className.getText().toString(), course.getSelectedItem().toString(), subject.getSelectedItem().toString(), type.getText().toString(), teacher.getText().toString(), classRoom.getText().toString(), note.getText().toString(), ((ColorDrawable) bgBtn.getBackground()).getColor(), stab, ClassWeekFragment.day.getSelectedItem().toString(), ClassWeekFragment.stime.getText().toString(), ClassWeekFragment.etime.getText().toString(), ClassWeekFragment.start.getText().toString(), ClassWeekFragment.end.getText().toString(), ClassWeekFragment.reminder.getSelectedItem().toString());
                    } else {
                        stab = "Date";
                        isUpdated = db.updateClass(id,className.getText().toString(), course.getSelectedItem().toString(), subject.getSelectedItem().toString(), type.getText().toString(), teacher.getText().toString(), classRoom.getText().toString(), note.getText().toString(), ((ColorDrawable) bgBtn.getBackground()).getColor(), stab, "", ClassDateFragment.stime.getText().toString(), ClassDateFragment.etime.getText().toString(), ClassDateFragment.sdate.getText().toString(), "", ClassDateFragment.reminder.getSelectedItem().toString());
                    }
                    if (isUpdated == true) {
                        Toast.makeText(getActivity().getApplicationContext(), "Class Updated Successfully", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getActivity().getApplicationContext(), "Update Failed, Try again", Toast.LENGTH_LONG).show();
                }
            });
        }

        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayoutClass);
        tabLayout.addTab(tabLayout.newTab().setText("Weekly"));
        tabLayout.addTab(tabLayout.newTab().setText("Date"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.add_class_pager);
        PgAdapter pgadapter = new PgAdapter(getChildFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pgadapter);

        if(freqd.equals("Date")){
            viewPager.setCurrentItem(1);
            selectedTab = 1;
        }else{
            viewPager.setCurrentItem(0);
            selectedTab = 0;
        }

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                selectedTab = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }
}