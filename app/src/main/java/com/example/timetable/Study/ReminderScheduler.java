package com.example.timetable.Study;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ReminderScheduler {

    public static final String EXTRA_STUDY_ID = "com.example.timetable.STUDY_ID";
    public static final String EXTRA_STUDY_TITLE = "com.example.timetable.STUDY_TITLE";
    public static final String EXTRA_SUBJECT_NAME = "com.example.timetable.SUBJECT_NAME";
    private static final long INTERVAL_WEEK = 604800000L;
    private Context context;
    private Intent intent;
    private int studyId;
    private String repeat;
    private Long dateTimeInMillis;
    private AlarmManager alarmManager;

    public ReminderScheduler(Context context, int studyId, String studyTitle, String subjectName,
                             String studyDate, String studyDay, String startTime, String repeat,
                             String reminderTime) {
        this.context = context;
        this.studyId = studyId;
        this.repeat = repeat;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        // Put data as extras to use in the notification
        intent = new Intent(this.context, ReminderBroadcastReceiver.class);
        intent.putExtra(EXTRA_STUDY_ID, studyId);
        intent.putExtra(EXTRA_STUDY_TITLE, studyTitle);
        intent.putExtra(EXTRA_SUBJECT_NAME, subjectName);


        // Parse given parameters and set reminder date and time to the temporary calendar object
        try {
            SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
            Date dateTime = dateTimeFormatter.parse(studyDate + " " + startTime);
            calendar.setTime(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // Adjust reminder date and time if repeat is set to "Weekly"
        if (repeat.equalsIgnoreCase("weekly")) {
            LocalDate tempDate = calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if(studyDay.equals("Monday"))
                tempDate = tempDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
            else if(studyDay.equals("Tuesday"))
                tempDate = tempDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));
            else if(studyDay.equals("Wednesday"))
                tempDate = tempDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY));
            else if(studyDay.equals("Thursday"))
                tempDate = tempDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
            else if(studyDay.equals("Friday"))
                tempDate = tempDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
            else if(studyDay.equals("Saturday"))
                tempDate = tempDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
            else if(studyDay.equals("Sunday"))
                tempDate = tempDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

            calendar.set(Calendar.DAY_OF_MONTH, tempDate.getDayOfMonth());
            calendar.set(Calendar.MONTH, tempDate.getMonthValue() - 1);
            calendar.set(Calendar.YEAR, tempDate.getYear());
        }


        // Set reminder date and time in milliseconds
        dateTimeInMillis = calendar.getTimeInMillis();


        // Adjust reminder date and time according to reminder time
        long reminderTimeInMillis;
        String reminderTimeUnit = reminderTime.split(" ")[1].startsWith("m") ? "m" : "h";

        if (reminderTimeUnit.equals("m"))
            reminderTimeInMillis = Long.parseLong(reminderTime.split(" ")[0]) * 60 * 1000;
        else
            reminderTimeInMillis = Long.parseLong(reminderTime.split(" ")[0]) * 60 * 60 * 1000;

        dateTimeInMillis -= reminderTimeInMillis;
    }

    public void scheduleReminder() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, studyId, intent, 0);

        if (repeat.equalsIgnoreCase("daily"))
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, dateTimeInMillis,
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        else if (repeat.equalsIgnoreCase("weekly"))
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, dateTimeInMillis,
                    INTERVAL_WEEK, pendingIntent);
        else
            alarmManager.set(AlarmManager.RTC_WAKEUP, dateTimeInMillis, pendingIntent);
    }

    public static void removeReminder(Context context, int studyId) {
        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, studyId, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
