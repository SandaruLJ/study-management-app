package com.example.timetable.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "StudentPlanner.db";


    private static final String SQL_CREATE_COURSE_ENTRIES =
            "CREATE TABLE " + CourseMaster.Courses.TABLE_NAME + "("+
                    CourseMaster.Courses._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    CourseMaster.Courses.COLUMN_NAME_COURSE_NAME + " TEXT," +
                    CourseMaster.Courses.COLUMN_NAME_INSTITUTE + " TEXT," +
                    CourseMaster.Courses.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    CourseMaster.Courses.COLUMN_NAME_COLOUR + " INTEGER," +
                    CourseMaster.Courses.COLUMN_NAME_START + " TEXT," +
                    CourseMaster.Courses.COLUMN_NAME_END + " TEXT)" ;

    private static final String SQL_CREATE_CLASS_ENTRIES =
            "CREATE TABLE " + ClassMaster.Classes.TABLE_NAME_CLASS + "( "+
                    ClassMaster.Classes._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    ClassMaster.Classes.COLUMN_NAME_CLASS_NAME + " TEXT," +
                    ClassMaster.Classes.COLUMN_NAME_COURSE + " TEXT," +
                    ClassMaster.Classes.COLUMN_NAME_SUBJECT + " TEXT," +
                    ClassMaster.Classes.COLUMN_NAME_CLASS_TYPE + " INTEGER," +
                    ClassMaster.Classes.COLUMN_NAME_CLASS_TEACHER + " TEXT," +
                    ClassMaster.Classes.COLUMN_NAME_CLASS_ROOM + " TEXT," +
                    ClassMaster.Classes.COLUMN_NAME_NOTE + " TEXT," +
                    ClassMaster.Classes.COLUMN_NAME_COLOR + " INTEGER," +
                    ClassMaster.Classes.COLUMN_NAME_FREQUENCY + " TEXT," +
                    ClassMaster.Classes.COLUMN_NAME_DAY + " TEXT," +
                    ClassMaster.Classes.COLUMN_NAME_START_TIME + " TEXT," +
                    ClassMaster.Classes.COLUMN_NAME_END_TIME + " TEXT," +
                    ClassMaster.Classes.COLUMN_NAME_START_DATE + " TEXT," +
                    ClassMaster.Classes.COLUMN_NAME_END_DATE + " TEXT," +
                    ClassMaster.Classes.COLUMN_NAME_REMINDER + " INTEGER )" ;

    private static final String SQL_CREATE_GOAL_ENTRIES =
            "CREATE TABLE " + GoalMaster.Goals.TABLE_NAME + "("+
                    GoalMaster.Goals._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    GoalMaster.Goals.COLUMN_NAME_GOAL_NAME + " TEXT," +
                    GoalMaster.Goals.COLUMN_NAME_COLOUR + " INTEGER," +
                    GoalMaster.Goals.COLUMN_NAME_DUE + " TEXT," +
                    GoalMaster.Goals.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    GoalMaster.Goals.COLUMN_NAME_REMINDER + " INTEGER," +
                    GoalMaster.Goals.COLUMN_NAME_SCHEDULED + " TEXT)" ;

    public DBHandler(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 5);
        SQLiteDatabase db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
                db.execSQL(SQL_CREATE_COURSE_ENTRIES);
                db.execSQL(SQL_CREATE_CLASS_ENTRIES);
                db.execSQL(SQL_CREATE_GOAL_ENTRIES);
//        db.execSQL("DROP TABLE IF EXISTS "+ CourseMaster.Courses.TABLE_NAME);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + CourseMaster.Courses.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ClassMaster.Classes.TABLE_NAME_CLASS);
        db.execSQL("DROP TABLE IF EXISTS " + GoalMaster.Goals.TABLE_NAME);
        onCreate(db);
    }
    public void create(){
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

//    CRUD operations for Course

    public boolean  addCourse(String name, String institute, String description, Integer colour, String start, String end ){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CourseMaster.Courses.COLUMN_NAME_COURSE_NAME,name);
        values.put(CourseMaster.Courses.COLUMN_NAME_DESCRIPTION,description);
        values.put(CourseMaster.Courses.COLUMN_NAME_COLOUR,colour);
        values.put(CourseMaster.Courses.COLUMN_NAME_INSTITUTE,institute);
        values.put(CourseMaster.Courses.COLUMN_NAME_START,start);
        values.put(CourseMaster.Courses.COLUMN_NAME_END,end);
        long result = db.insert(CourseMaster.Courses.TABLE_NAME,null,values);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean  updateCourse(String id,String name, String institute, String description, Integer colour, String start, String end ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CourseMaster.Courses.COLUMN_NAME_COURSE_NAME,name);
        values.put(CourseMaster.Courses.COLUMN_NAME_DESCRIPTION,description);
        values.put(CourseMaster.Courses.COLUMN_NAME_COLOUR,colour);
        values.put(CourseMaster.Courses.COLUMN_NAME_INSTITUTE,institute);
        values.put(CourseMaster.Courses.COLUMN_NAME_START,start);
        values.put(CourseMaster.Courses.COLUMN_NAME_END,end);
        db.update(CourseMaster.Courses.TABLE_NAME,values,"_id = ?",new String[]{id});
        return true;
    }
    public Cursor getAllCourse(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("Select * from "+ CourseMaster.Courses.TABLE_NAME,null);
        return res;
    }
    public Cursor getSingleCourse(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("Select * from " + CourseMaster.Courses.TABLE_NAME + " WHERE "+ CourseMaster.Courses._ID + " = " + id, null);
        return res;
    }
    public Integer deleteCourse(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CourseMaster.Courses.TABLE_NAME, " _id = ? ",new String[]{id});
    }

    //    CRUD operations for Goals

    public boolean  addGoals(String name, Integer colour, String due, String description, boolean reminder, String scheduled ){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GoalMaster.Goals.COLUMN_NAME_GOAL_NAME,name);
        values.put(GoalMaster.Goals.COLUMN_NAME_COLOUR,colour);
        values.put(GoalMaster.Goals.COLUMN_NAME_DUE,due);
        values.put(GoalMaster.Goals.COLUMN_NAME_DESCRIPTION,description);
        values.put(GoalMaster.Goals.COLUMN_NAME_REMINDER,reminder);
        values.put(GoalMaster.Goals.COLUMN_NAME_SCHEDULED,scheduled);
        long result = db.insert(GoalMaster.Goals.TABLE_NAME,null,values);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllGoals(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("Select * from "+ GoalMaster.Goals.TABLE_NAME,null);
        return res;
    }

    public boolean  updateGoal(String id,String name, Integer colour, String due, String description, String reminder, String scheduled ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GoalMaster.Goals.COLUMN_NAME_GOAL_NAME,name);
        values.put(GoalMaster.Goals.COLUMN_NAME_COLOUR,colour);
        values.put(GoalMaster.Goals.COLUMN_NAME_DUE,due);
        values.put(GoalMaster.Goals.COLUMN_NAME_DESCRIPTION,description);
        values.put(GoalMaster.Goals.COLUMN_NAME_REMINDER,reminder);
        values.put(GoalMaster.Goals.COLUMN_NAME_SCHEDULED,scheduled);
        db.update(GoalMaster.Goals.TABLE_NAME,values,"_id = ?",new String[]{id});
        return true;
    }

    public Cursor getAllGoal(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("Select * from "+ GoalMaster.Goals.TABLE_NAME,null);
        return res;
    }
    public Cursor getSingleGoal(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("Select * from " + GoalMaster.Goals.TABLE_NAME + " WHERE "+ GoalMaster.Goals._ID + " = " + id, null);
        return res;
    }
    public Integer deleteGoal(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(GoalMaster.Goals.TABLE_NAME, " _id = ? ",new String[]{id});
    }



    //CRUD Operations for CLass
    public boolean addClass(String name, String course, String subject, String classType, String teacher, String classroom,String note, Integer colour,String freq, String day, String startTime,String endTime, String sDate,String eDate,Integer reminder ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ClassMaster.Classes.COLUMN_NAME_CLASS_NAME,name);
        values.put(ClassMaster.Classes.COLUMN_NAME_COURSE,course);
        values.put(ClassMaster.Classes.COLUMN_NAME_SUBJECT,subject);
        values.put(ClassMaster.Classes.COLUMN_NAME_CLASS_TYPE,classType);
        values.put(ClassMaster.Classes.COLUMN_NAME_CLASS_TEACHER,teacher);
        values.put(ClassMaster.Classes.COLUMN_NAME_CLASS_ROOM,classroom);
        values.put(ClassMaster.Classes.COLUMN_NAME_NOTE,note);
        values.put(ClassMaster.Classes.COLUMN_NAME_COLOR,colour);
        values.put(ClassMaster.Classes.COLUMN_NAME_FREQUENCY,freq);
        values.put(ClassMaster.Classes.COLUMN_NAME_DAY,day);
        values.put(ClassMaster.Classes.COLUMN_NAME_START_TIME,startTime);
        values.put(ClassMaster.Classes.COLUMN_NAME_END_TIME,endTime);
        values.put(ClassMaster.Classes.COLUMN_NAME_START_DATE,sDate);
        values.put(ClassMaster.Classes.COLUMN_NAME_END_DATE,eDate);
        values.put(ClassMaster.Classes.COLUMN_NAME_REMINDER,reminder);

        long result = db.insert(ClassMaster.Classes.TABLE_NAME_CLASS,null,values);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllClass(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("Select * from "+ ClassMaster.Classes.TABLE_NAME_CLASS,null);
        return res;
    }
    public Cursor getSortClass(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("Select * from "+ ClassMaster.Classes.TABLE_NAME_CLASS + " ORDER BY " + ClassMaster.Classes.COLUMN_NAME_START_DATE,null);
        return res;
    }
    public Integer deleteClass(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ClassMaster.Classes.TABLE_NAME_CLASS, " _id = ? ",new String[]{id});
    }

    public Cursor getSingleClass(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("Select * from " + ClassMaster.Classes.TABLE_NAME_CLASS + " WHERE "+ ClassMaster.Classes._ID + " = " + id, null);
        return res;
    }

    public boolean updateClass(int id,String name, String course, String subject, String classType, String teacher, String classroom,String note, Integer colour,String freq, String day, String startTime,String endTime, String sDate,String eDate,String reminder ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ClassMaster.Classes.COLUMN_NAME_CLASS_NAME,name);
        values.put(ClassMaster.Classes.COLUMN_NAME_COURSE,course);
        values.put(ClassMaster.Classes.COLUMN_NAME_SUBJECT,subject);
        values.put(ClassMaster.Classes.COLUMN_NAME_CLASS_TYPE,classType);
        values.put(ClassMaster.Classes.COLUMN_NAME_CLASS_TEACHER,teacher);
        values.put(ClassMaster.Classes.COLUMN_NAME_CLASS_ROOM,classroom);
        values.put(ClassMaster.Classes.COLUMN_NAME_NOTE,note);
        values.put(ClassMaster.Classes.COLUMN_NAME_COLOR,colour);
        values.put(ClassMaster.Classes.COLUMN_NAME_FREQUENCY,freq);
        values.put(ClassMaster.Classes.COLUMN_NAME_DAY,day);
        values.put(ClassMaster.Classes.COLUMN_NAME_START_TIME,startTime);
        values.put(ClassMaster.Classes.COLUMN_NAME_END_TIME,endTime);
        values.put(ClassMaster.Classes.COLUMN_NAME_START_DATE,sDate);
        values.put(ClassMaster.Classes.COLUMN_NAME_END_DATE,eDate);
        values.put(ClassMaster.Classes.COLUMN_NAME_REMINDER,reminder);

        db.update(ClassMaster.Classes.TABLE_NAME_CLASS,values,"_id = ?",new String[]{String.valueOf(id)});
        return true;
    }
    public int getLastClassIndex(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select MAX(_id) from "+ ClassMaster.Classes.TABLE_NAME_CLASS,null);
        int id = 0;
        while(res.moveToNext()){
            id = res.getInt(0);
        }
        return id;
    }
    public int getClassCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select COUNT(_id) from "+ ClassMaster.Classes.TABLE_NAME_CLASS,null);
        int count = 0;
        while(res.moveToNext()){
            count = res.getInt(0);
        }
        return count;
    }
}
