package com.example.timetable;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;

import com.example.timetable.Database.DBHandler;
import com.example.timetable.Exam.ExamUpcomingFragment;
import com.example.timetable.Homework.HomeworkRecyclerView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class UnitTest {

    private DashboardFragment dashboardFragment;
    private HomeworkRecyclerView hr;
    private ExamUpcomingFragment ex;
    private DBHandler db;
    private MainActivity activity;


    @Before
    public void setup(){
        dashboardFragment = new DashboardFragment();
        ex= new ExamUpcomingFragment();
        hr = new HomeworkRecyclerView();
    }



    @Test
    public void isRemainingTimeCorrect() throws Exception{

        long result = ex.getRemainingTime(3500001,3500000);
        assertEquals(1,result);
        assertEquals(50000,ex.getRemainingTime(100000,50000));
        assertEquals(250000,ex.getRemainingTime(500000,250000));
    }

    @Test
    public void isRemainingTimeinHours_correct(){

        // This is tested with the current time, the time when tested is 1:50 AM
        long result;
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY,2);
        result = hr.getRemainingtimeinHours(cal);
        assertEquals(0,result);

        cal.set(Calendar.HOUR_OF_DAY,3);
        result = hr.getRemainingtimeinHours(cal);
        assertEquals(1,result);

        cal.set(Calendar.HOUR_OF_DAY,4);
        result = hr.getRemainingtimeinHours(cal);
        assertEquals(2,result);

        cal.set(Calendar.HOUR_OF_DAY,10);
        result = hr.getRemainingtimeinHours(cal);
        assertEquals(8,result);

        cal.set(Calendar.HOUR_OF_DAY,23);
        result = hr.getRemainingtimeinHours(cal);
        assertEquals(21,result);
    }

    @Test
    public void isRemainingTimeinminutesCorrect(){

        long result;
        Calendar cal = Calendar.getInstance();

        //Tested at 1:57 AM
        cal.set(Calendar.HOUR_OF_DAY,2);
        cal.set(Calendar.MINUTE,0);
        result = hr.getRemainingtimeinMinutes(cal);
        assertEquals(3,result);

        //Tested at 1:58 AM
        cal.set(Calendar.HOUR_OF_DAY,2);
        cal.set(Calendar.MINUTE,8);
        result = hr.getRemainingtimeinMinutes(cal);
        assertEquals(10,result);

        //Tested at 2:00 AM
        cal.set(Calendar.HOUR_OF_DAY,2);
        cal.set(Calendar.MINUTE,59);
        result = hr.getRemainingtimeinMinutes(cal);
        assertEquals(58,result);
    }


    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}