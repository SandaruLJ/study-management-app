package com.example.timetable;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.timetable.Database.DBHandler;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class ReminderBroadcast extends BroadcastReceiver {

    private NotificationManagerCompat notificationManager ;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        String text = intent.getExtras().getString("text");
        String reminderType = intent.getExtras().getString("reminderType");


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"channel1")
                .setSmallIcon(R.drawable.notification_new)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.studentplanner))
                .setContentTitle(reminderType)
//                .setContentText(title + " starts at "+time + " on "+date)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200,builder.build());

//         Delete Set Repeating Alarm on End date for Class
        DBHandler db = new DBHandler(context);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Cursor c = db.getAllClass();
        Calendar cald = Calendar.getInstance();
        Date edate = null,endTime = null;
        Date currentDate = new Date();
        while(c.moveToNext()){
            String freq = c.getString(9);
            if(freq.equals("Weekly")){
                try {
                    edate = dateFormat.parse(c.getString(14));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cald.setTime(edate);
                if(currentDate.compareTo(edate) >= 0){
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context,c.getInt(0),intent,0);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    //Used to cancelthe previous alarm
                    alarmManager.cancel(pendingIntent);
                }

            }
        }
//         Delete Set Repeating Alarm on End date for Goals
        Cursor c1 = db.getAllGoals();
        Calendar calg = Calendar.getInstance();
        while(c1.moveToNext()){

                try {
                    edate = dateFormat.parse(c1.getString(3));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                calg.setTime(edate);
                if(currentDate.compareTo(edate) >= 0){
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context,c1.getInt(0),intent,0);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    //Used to cancel the previous alarm
                    alarmManager.cancel(pendingIntent);
                }


        }

    }

}
