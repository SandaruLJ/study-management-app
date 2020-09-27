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

    private static final String SQL_CREATE_HOMEWORK_ENTRIES =
            "CREATE TABLE " + HomeworkMaster.Homework.TABLE_NAME + "("+
                    HomeworkMaster.Homework._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    HomeworkMaster.Homework.COLUMN_NAME_TITLE + " TEXT," +
                    HomeworkMaster.Homework.COLUMN_NAME_SUBJECT + " TEXT," +
                    HomeworkMaster.Homework.COLUMN_NAME_DUE_DATE + " TEXT," +
                    HomeworkMaster.Homework.COLUMN_NAME_TIME + " TEXT," +
                    HomeworkMaster.Homework.COLUMN_NAME_REMINDER + " INTEGER," +
                    HomeworkMaster.Homework.COLUMN_NAME_COLOUR + " INTEGER," +
                    HomeworkMaster.Homework.COLUMN_NAME_NOTE + " TEXT)" ;


    private static final String SQL_CREATE_EXAM_ENTRIES =
            "CREATE TABLE " + ExamMaster.Exam.TABLE_NAME + "("+
                 ExamMaster.Exam._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    ExamMaster.Exam.COLUMN_NAME_TITLE + " TEXT," +
                    ExamMaster.Exam.COLUMN_NAME_SUBJECT + " TEXT," +
                    ExamMaster.Exam.COLUMN_NAME_LOCATION + " TEXT," +
                    ExamMaster.Exam.COLUMN_NAME_DUE_DATE + " TEXT," +
                    ExamMaster.Exam.COLUMN_NAME_START_TIME + " TEXT," +
                    ExamMaster.Exam.COLUMN_NAME_END_TIME + " TEXT," +
                    ExamMaster.Exam.COLUMN_NAME_REMINDER + " INTEGER," +
                    ExamMaster.Exam.COLUMN_NAME_COLOUR + " INTEGER," +
                    ExamMaster.Exam.COLUMN_NAME_NOTE + " TEXT)" ;


    public DBHandler(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 4);
        SQLiteDatabase db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
                db.execSQL(SQL_CREATE_COURSE_ENTRIES);
                db.execSQL(SQL_CREATE_CLASS_ENTRIES);
//        db.execSQL("DROP TABLE IF EXISTS "+ CourseMaster.Courses.TABLE_NAME);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + CourseMaster.Courses.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ClassMaster.Classes.TABLE_NAME_CLASS);
        db.execSQL("DROP TABLE IF EXISTS " + HomeworkMaster.Homework.TABLE_NAME);
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

    //CRUD Operations for CLass
    public boolean addClass(String name, String course, String subject, String classType, String teacher, String classroom,String note, Integer colour,String freq, String day, String startTime,String endTime, String sDate,String eDate,String reminder ){

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
    //CRUD operations for  HOMEWORK

    public boolean  addHomework(String title, String subject, String due_date,String time,String reminder, Integer colour, String note ){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HomeworkMaster.Homework.COLUMN_NAME_TITLE,title);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_SUBJECT,subject);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_DUE_DATE,due_date);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_TIME,time);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_REMINDER,reminder);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_COLOUR,colour);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_NOTE,note);

        long result = db.insert(HomeworkMaster.Homework.TABLE_NAME,null,values);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllHomework(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("Select * from "+ HomeworkMaster.Homework.TABLE_NAME,null);
        return res;
    }

    public Integer deleteHomework(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(HomeworkMaster.Homework.TABLE_NAME, " _id = ? ",new String[]{id});
    }
    public Cursor getSingleHomework(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("Select * from " + HomeworkMaster.Homework.TABLE_NAME + " WHERE "+ CourseMaster.Courses._ID + " = " + id, null);
        return res;
    }
    public boolean  updateHomework(String id,String title, String subject, String due_date,String time,String reminder, Integer colour, String note ){


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HomeworkMaster.Homework.COLUMN_NAME_TITLE,title);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_SUBJECT,subject);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_DUE_DATE,due_date);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_TIME,time);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_REMINDER,reminder);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_COLOUR,colour);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_NOTE,note);
        db.update(HomeworkMaster.Homework.TABLE_NAME,values,"_id = ?",new String[]{id});
        return true;
    }
    //CRUD operations for  EXAM

    public boolean  addExam(String title, String subject, String location,String due_date,String time,String end_time ,String reminder, Integer colour, String note ){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExamMaster.Exam.COLUMN_NAME_TITLE,title);
        values.put(ExamMaster.Exam.COLUMN_NAME_SUBJECT,subject);
        values.put(ExamMaster.Exam.COLUMN_NAME_DUE_DATE,due_date);
        values.put(ExamMaster.Exam.COLUMN_NAME_START_TIME,time);
        values.put(ExamMaster.Exam.COLUMN_NAME_END_TIME,end_time);
        values.put(ExamMaster.Exam.COLUMN_NAME_LOCATION,location);
        values.put(ExamMaster.Exam.COLUMN_NAME_REMINDER,reminder);
        values.put(ExamMaster.Exam.COLUMN_NAME_COLOUR,colour);
        values.put(ExamMaster.Exam.COLUMN_NAME_NOTE,note);

        long result = db.insert(HomeworkMaster.Homework.TABLE_NAME,null,values);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllExam(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("Select * from "+ ExamMaster.Exam.TABLE_NAME,null);
        return res;
    }

    public Integer deleteExam(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ExamMaster.Exam.TABLE_NAME, " _id = ? ",new String[]{id});
    }
    public Cursor getSingleExam(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("Select * from " + ExamMaster.Exam.TABLE_NAME + " WHERE "+ CourseMaster.Courses._ID + " = " + id, null);
        return res;
    }

}
