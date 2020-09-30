package com.example.timetable.Goals;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetable.ColorPicker;
import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;
import com.example.timetable.ReminderBroadcast;
import com.example.timetable.SelectDateFragment;
import com.example.timetable.SelectTimeFragement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link edit_goals#newInstance} factory method to
 * create an instance of this fragment.
 */
public class edit_goals extends Fragment {

    EditText gname, due, gdescription;
    Button save;
    Spinner scheduled_reminder;
    SwitchCompat reminder;
    ColorStateList col;
    DBHandler db;
    int id;
    TextView stime;
    LinearLayout timeSelector;
    View timeSelectorView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_edit_goals2, container, false);

            Button save = view.findViewById(R.id.btnSave);
            gname = (EditText) view.findViewById(R.id.goalid);
            reminder = (SwitchCompat) view.findViewById(R.id.reminder);
            scheduled_reminder = (Spinner) view.findViewById(R.id.reminderSelect);
            gdescription = (EditText) view.findViewById(R.id.descriptionId);
            final Button colorbtn = (Button) view.findViewById(R.id.colorbtn);
            final Button bgBtn = (Button) view.findViewById(R.id.testbtn);
            final TextView end = (TextView) view.findViewById(R.id.endDate);
            LinearLayout pickDatebtn1 = (LinearLayout) view.findViewById(R.id.dateSelectorEnd);
            timeSelectorView = (View) view.findViewById(R.id.timeSelectorView);
        stime = (TextView) view.findViewById(R.id.sTime);

            String[] reminders= new String[]{"Hourly","Daily","Weekly","Monthly"};
            ArrayAdapter reminderAdapter= new ArrayAdapter(getActivity().getApplicationContext(), R.layout.spinner_item, reminders);
            final Spinner reminderSpinner= (Spinner) view.findViewById(R.id.reminderSelect);
            reminderSpinner.setAdapter(reminderAdapter);

        id  = 0;
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            id = Integer.parseInt(bundle.get("id").toString());

        }

        if(id != 0) {

            String ename = null, edate = null, subjectd, description = null,rtime = null,rswitch = null,rstime = null;
            int colourd = 0;
            Cursor c = db.getSingleGoal(id);

            while (c.moveToNext()) {

                ename = c.getString(1);
                edate = c.getString(3);
                description = c.getString(4);
                colourd = c.getInt(2);
                rtime = c.getString(6);
                rswitch = c.getString(5);
                rstime = c.getString(7);

            }

            gname.setText(ename);
            gdescription.setText(description);
            end.setText(edate);
            colorbtn.setBackgroundTintList(ColorStateList.valueOf(colourd));
            bgBtn.setBackgroundColor(colourd);
            scheduled_reminder.setSelection(reminderAdapter.getPosition(rtime));
            reminder.setChecked(Integer.parseInt(rswitch)==1?true:false);
            stime.setText(rstime);



        }

            //Colour Picker
            colorbtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ColorPicker cp = new ColorPicker();
                    cp.openColorPicker(getChildFragmentManager(), colorbtn, bgBtn);
                }

            });

            //End Date Picker
            pickDatebtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Pass the textView in order to set the date for the text
                    DialogFragment newFragment = new SelectDateFragment(end);
                    newFragment.show(getFragmentManager(), "DatePicker");

                }
            });

        //Start Time Picker


        timeSelector = (LinearLayout) view.findViewById(R.id.timeSelectorStart);
        timeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pass the textView in order to set the date for the text
                DialogFragment newFragment = new SelectTimeFragement(stime);
                newFragment.show(getFragmentManager(), "TimePicker");

            }
        });
        scheduled_reminder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(scheduled_reminder.getSelectedItem().toString().equals("Weekly") || scheduled_reminder.getSelectedItem().toString().equals("Daily") || scheduled_reminder.getSelectedItem().toString().equals("Monthly")){
                    timeSelector.setVisibility(View.VISIBLE);
                    timeSelectorView.setVisibility(View.VISIBLE);
                }else{
                    timeSelector.setVisibility(View.INVISIBLE);
                    timeSelectorView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String rtime = scheduled_reminder.getSelectedItem().toString();
                    String rstime = stime.getText().toString();
                    if(!reminder.isChecked()){
                        rtime = "";
                        rstime = "";
                    }else{
                        if(scheduled_reminder.getSelectedItem().toString().equals("Hourly")){
                            rstime =  "";
                        }
                    }

                    boolean isUpdated = db.updateGoal(id,gname.getText().toString(),((ColorDrawable) bgBtn.getBackground()).getColor(),end.getText().toString(),gdescription.getText().toString(),reminder.isChecked(),rtime,rstime);

                    if(isUpdated == true) {

                        if(reminder.isChecked()){

                            Intent intent = new Intent(getActivity().getApplicationContext(), ReminderBroadcast.class);
                            intent.putExtra("reminderType", "Goal Reminder");
                            intent.putExtra("text", "You have to do " + gname.getText().toString() + " before " + end.getText().toString() );
                            DateFormat timeFormat = new SimpleDateFormat("HH:mm");
                            Calendar cald = Calendar.getInstance();
                            Calendar cal = Calendar.getInstance();
                            Date startTime = null;
                            String sttime = null;

                            sttime = stime.getText().toString();

                            try {
                                startTime = timeFormat.parse(sttime);
                                cald.setTime(startTime);
                                cal.set(Calendar.HOUR_OF_DAY,cald.get(Calendar.HOUR_OF_DAY));
                                cal.set(Calendar.MINUTE,cald.get(Calendar.MINUTE));

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            String rem = scheduled_reminder.getSelectedItem().toString();

                            int interval = 0;
                            if(rem.equals("Daily")){
                                interval = 24*60*60*1000;
                            }else if(rem.equals("Hourly")){
                                interval = 60*60*1000;
                                cal.set(Calendar.HOUR_OF_DAY,Calendar.HOUR_OF_DAY + 1);
                                cal.set(Calendar.MINUTE,Calendar.MINUTE - Calendar.MINUTE);
                            }else if(rem.equals("Weekly")){
                                interval = 7*24*60*60*1000;
                            }else if(rem.equals("Monthly")){
                                interval = 30*24*60*60*1000;
                            }

                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(),id,intent,0);
                            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                            alarmManager.cancel(pendingIntent);
                            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),interval,pendingIntent);

                        }

                        
                        Toast.makeText(getActivity().getApplicationContext(), "Goal Updated Successfully", Toast.LENGTH_LONG).show();
                        AllGoalsFragment fragment= new AllGoalsFragment();
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

                    }else
                        Toast.makeText(getActivity().getApplicationContext(),"Update Failed, Try again",Toast.LENGTH_LONG).show();
                }
            });




            return view;
        }

}