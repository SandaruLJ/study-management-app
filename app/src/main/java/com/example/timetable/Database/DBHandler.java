package com.example.timetable.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
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

    private static final String SQL_CREATE_GOAL_ENTRIES =
            "CREATE TABLE " + GoalMaster.Goals.TABLE_NAME + "("+
                    GoalMaster.Goals._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    GoalMaster.Goals.COLUMN_NAME_GOAL_NAME + " TEXT," +
                    GoalMaster.Goals.COLUMN_NAME_COLOUR + " INTEGER," +
                    GoalMaster.Goals.COLUMN_NAME_DUE + " TEXT," +
                    GoalMaster.Goals.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    GoalMaster.Goals.COLUMN_NAME_REMINDER + " INTEGER," +
                    GoalMaster.Goals.COLUMN_NAME_SCHEDULED + " TEXT," +
                     GoalMaster.Goals.COLUMN_NAME_REMINDER_TIME + " TEXT)";

    private static final String SQL_CREATE_SUBJECT_ENTRIES =
            "CREATE TABLE " + SubjectMaster.Subjects.TABLE_NAME + "("+
                    SubjectMaster.Subjects._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    SubjectMaster.Subjects.COLUMN_NAME_SUBJECT_NAME + " TEXT," +
                    SubjectMaster.Subjects.COLUMN_NAME_TEACHER_NAME + " TEXT," +
                    SubjectMaster.Subjects.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    SubjectMaster.Subjects.COLUMN_NAME_COLOUR + " INTEGER )";

    private static final String SQL_CREATE_STUDY_ENTRIES =
            "CREATE TABLE " + StudyMaster.Studies.TABLE_NAME + "(" +
                    StudyMaster.Studies._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    StudyMaster.Studies.COLUMN_NAME_STUDY_TITLE + " TEXT," +
                    StudyMaster.Studies.COLUMN_NAME_SUBJECT + " INTEGER," +
                    StudyMaster.Studies.COLUMN_NAME_COLOUR + " INTEGER," +
                    StudyMaster.Studies.COLUMN_NAME_DATE + " TEXT," +
                    StudyMaster.Studies.COLUMN_NAME_START + " TEXT," +
                    StudyMaster.Studies.COLUMN_NAME_END + " TEXT," +
                    StudyMaster.Studies.COLUMN_NAME_REPEAT + " TEXT," +
                    StudyMaster.Studies.COLUMN_NAME_DAY + " TEXT," +
                    StudyMaster.Studies.COLUMN_NAME_NOTE + " TEXT," +
                    StudyMaster.Studies.COLUMN_NAME_REMINDER + " INTEGER," +
                    StudyMaster.Studies.COLUMN_NAME_REMINDER_TIME + " TEXT," +
                    "FOREIGN KEY(" + StudyMaster.Studies.COLUMN_NAME_SUBJECT + ") " +
                    "REFERENCES " + SubjectMaster.Subjects.TABLE_NAME + "(" + SubjectMaster.Subjects._ID + ") " +
                    "ON DELETE CASCADE)";

    private static final String SQL_CREATE_STUDY_TIME_ENTRIES =
            "CREATE TABLE " + SubjectMaster.StudyTimes.TABLE_NAME + "(" +
                    SubjectMaster.StudyTimes._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SubjectMaster.StudyTimes.COLUMN_NAME_SUBJECT + " INTEGER," +
                    SubjectMaster.StudyTimes.COLUMN_NAME_DATE + " TEXT," +
                    SubjectMaster.StudyTimes.COLUMN_NAME_STUDY_TIME + " INTEGER," +
                    "FOREIGN KEY(" + SubjectMaster.StudyTimes.COLUMN_NAME_SUBJECT + ") " +
                    "REFERENCES " + SubjectMaster.StudyTimes.TABLE_NAME + "(" + SubjectMaster.Subjects._ID + ") " +
                    "ON DELETE CASCADE)";

    public DBHandler(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 7);
        SQLiteDatabase db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
                db.execSQL(SQL_CREATE_COURSE_ENTRIES);
                db.execSQL(SQL_CREATE_CLASS_ENTRIES);
                db.execSQL(SQL_CREATE_SUBJECT_ENTRIES);
                db.execSQL(SQL_CREATE_STUDY_ENTRIES);
                db.execSQL(SQL_CREATE_STUDY_TIME_ENTRIES);
                db.execSQL(SQL_CREATE_EXAM_ENTRIES);
                db.execSQL(SQL_CREATE_HOMEWORK_ENTRIES);
                db.execSQL(SQL_CREATE_GOAL_ENTRIES);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + CourseMaster.Courses.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ClassMaster.Classes.TABLE_NAME_CLASS);
        db.execSQL("DROP TABLE IF EXISTS " + SubjectMaster.Subjects.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + StudyMaster.Studies.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SubjectMaster.StudyTimes.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GoalMaster.Goals.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ExamMaster.Exam.TABLE_NAME);
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


    //    CRUD operations for Goals

    public boolean  addGoals(String name, Integer colour, String due, String description, boolean reminder, String scheduled, String stime ){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GoalMaster.Goals.COLUMN_NAME_GOAL_NAME,name);
        values.put(GoalMaster.Goals.COLUMN_NAME_COLOUR,colour);
        values.put(GoalMaster.Goals.COLUMN_NAME_DUE,due);
        values.put(GoalMaster.Goals.COLUMN_NAME_DESCRIPTION,description);
        values.put(GoalMaster.Goals.COLUMN_NAME_REMINDER,reminder);
        values.put(GoalMaster.Goals.COLUMN_NAME_SCHEDULED,scheduled);
        values.put(GoalMaster.Goals.COLUMN_NAME_REMINDER_TIME,stime);
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

    public boolean  updateGoal(int id,String name, Integer colour, String due, String description, boolean reminder, String scheduled, String stime ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GoalMaster.Goals.COLUMN_NAME_GOAL_NAME,name);
        values.put(GoalMaster.Goals.COLUMN_NAME_COLOUR,colour);
        values.put(GoalMaster.Goals.COLUMN_NAME_DUE,due);
        values.put(GoalMaster.Goals.COLUMN_NAME_DESCRIPTION,description);
        values.put(GoalMaster.Goals.COLUMN_NAME_REMINDER,reminder);
        values.put(GoalMaster.Goals.COLUMN_NAME_SCHEDULED,scheduled);
        values.put(GoalMaster.Goals.COLUMN_NAME_REMINDER_TIME,stime);
        db.update(GoalMaster.Goals.TABLE_NAME,values,"_id = ?",new String[]{String.valueOf(id)});
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

    public int getLastGoalIndex(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select MAX(_id) from "+ GoalMaster.Goals.TABLE_NAME,null);
        int id = 0;
        while(res.moveToNext()){
            id = res.getInt(0);
        }
        return id;
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
    public Integer getTodayClassCount(){

        SQLiteDatabase db = this.getReadableDatabase();
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        String temp = dd+"/"+(mm+1)+"/"+yy;

        Cursor res = db.rawQuery("Select COUNT(_id) from "+ ClassMaster.Classes.TABLE_NAME_CLASS + " WHERE start_date = 1/10/2020",null);
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
    public boolean  updateHomework(int id,String title, String subject, String due_date,String time,String reminder, Integer colour, String note ){


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HomeworkMaster.Homework.COLUMN_NAME_TITLE,title);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_SUBJECT,subject);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_DUE_DATE,due_date);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_TIME,time);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_REMINDER,reminder);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_COLOUR,colour);
        values.put(HomeworkMaster.Homework.COLUMN_NAME_NOTE,note);
        db.update(HomeworkMaster.Homework.TABLE_NAME,values,"_id = ?",new String[]{String.valueOf(id)});
        return true;
    }

    public int getHomeworkCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select COUNT(_id) from "+ HomeworkMaster.Homework.TABLE_NAME,null);
        int count = 0;
        while(res.moveToNext()){
            count = res.getInt(0);
        }
        return count;
    }
    public int getLastHomeworkIndex(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select MAX(_id) from "+ HomeworkMaster.Homework.TABLE_NAME,null);
        int id = 0;
        while(res.moveToNext()){
            id = res.getInt(0);
        }
        return id;
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

        long result = db.insert(ExamMaster.Exam.TABLE_NAME,null,values);
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
    public int getExamCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select COUNT(_id) from "+ ExamMaster.Exam.TABLE_NAME,null);
        int count = 0;
        while(res.moveToNext()){
            count = res.getInt(0);
        }
        return count;
    }
    public boolean  updateExam(int id,String title, String subject, String location,String due_date,String time,String end_time ,String reminder, Integer colour, String note ){

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

        db.update(ExamMaster.Exam.TABLE_NAME,values,"_id = ?",new String[]{String.valueOf(id)});
        return true;
    }
    public int getLastExamIndex(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select MAX(_id) from "+ ExamMaster.Exam.TABLE_NAME,null);
        int id = 0;
        while(res.moveToNext()){
            id = res.getInt(0);
        }
        return id;
    }



    // CRUD operations for Subject

    public boolean addSubject(String subjectName, String teacherName, String subjectDesc, Integer colour) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SubjectMaster.Subjects.COLUMN_NAME_SUBJECT_NAME, subjectName);
        values.put(SubjectMaster.Subjects.COLUMN_NAME_TEACHER_NAME, teacherName);
        values.put(SubjectMaster.Subjects.COLUMN_NAME_DESCRIPTION, subjectDesc);
        values.put(SubjectMaster.Subjects.COLUMN_NAME_COLOUR, colour);

        long result = db.insert(SubjectMaster.Subjects.TABLE_NAME, null, values);

        return result != -1;
    }

    public boolean updateSubject(String subjectId, String subjectName, String teacherName, String subjectDesc, Integer colour) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SubjectMaster.Subjects.COLUMN_NAME_SUBJECT_NAME, subjectName);
        values.put(SubjectMaster.Subjects.COLUMN_NAME_TEACHER_NAME, teacherName);
        values.put(SubjectMaster.Subjects.COLUMN_NAME_DESCRIPTION, subjectDesc);
        values.put(SubjectMaster.Subjects.COLUMN_NAME_COLOUR, colour);

        db.update(SubjectMaster.Subjects.TABLE_NAME, values, "_id = ?", new String[]{subjectId});

        return true;
    }

    public Cursor getAllSubjects() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+ SubjectMaster.Subjects.TABLE_NAME, null);
    }

    public Cursor getSingleSubject(int subjectId) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + SubjectMaster.Subjects.TABLE_NAME + " WHERE "+
                SubjectMaster.Subjects._ID + " = " + subjectId, null);
    }

    public Integer deleteSubject(String subjectId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SubjectMaster.Subjects.TABLE_NAME, " _id = ? ", new String[]{subjectId});
    }


    // CRUD operations for Study

    public boolean addStudy(String studyTitle, Integer subject, Integer colour, String date, String startTime,
                            String endTime, String repeat, String day, String note, Boolean reminderBool,
                            String reminderTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        int reminder = reminderBool ? 1 : 0;  // Convert reminder toggle value from boolean to integer
        reminderTime = reminderBool ? reminderTime : null;  // Set reminder time to null if reminder is not enabled

        values.put(StudyMaster.Studies.COLUMN_NAME_STUDY_TITLE, studyTitle);
        values.put(StudyMaster.Studies.COLUMN_NAME_SUBJECT, subject);
        values.put(StudyMaster.Studies.COLUMN_NAME_COLOUR, colour);
        values.put(StudyMaster.Studies.COLUMN_NAME_DATE, date);
        values.put(StudyMaster.Studies.COLUMN_NAME_START, startTime);
        values.put(StudyMaster.Studies.COLUMN_NAME_END, endTime);
        values.put(StudyMaster.Studies.COLUMN_NAME_REPEAT, repeat);
        values.put(StudyMaster.Studies.COLUMN_NAME_DAY, day);
        values.put(StudyMaster.Studies.COLUMN_NAME_NOTE, note);
        values.put(StudyMaster.Studies.COLUMN_NAME_REMINDER, reminder);
        values.put(StudyMaster.Studies.COLUMN_NAME_REMINDER_TIME, reminderTime);

        long result = db.insert(StudyMaster.Studies.TABLE_NAME, null, values);

        return result != -1;
    }

    public boolean updateStudy(String studyId, String studyTitle, Integer subject, Integer colour, String date,
                               String startTime, String endTime, String repeat, String day, String note,
                               Boolean reminderBool, String reminderTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        int reminder = reminderBool ? 1 : 0;  // Convert reminder toggle value from boolean to integer
        reminderTime = reminderBool ? reminderTime : null;  // Set reminder time to null if reminder is not enabled

        values.put(StudyMaster.Studies.COLUMN_NAME_STUDY_TITLE, studyTitle);
        values.put(StudyMaster.Studies.COLUMN_NAME_SUBJECT, subject);
        values.put(StudyMaster.Studies.COLUMN_NAME_COLOUR, colour);
        values.put(StudyMaster.Studies.COLUMN_NAME_DATE, date);
        values.put(StudyMaster.Studies.COLUMN_NAME_START, startTime);
        values.put(StudyMaster.Studies.COLUMN_NAME_END, endTime);
        values.put(StudyMaster.Studies.COLUMN_NAME_REPEAT, repeat);
        values.put(StudyMaster.Studies.COLUMN_NAME_DAY, day);
        values.put(StudyMaster.Studies.COLUMN_NAME_NOTE, note);
        values.put(StudyMaster.Studies.COLUMN_NAME_REMINDER, reminder);
        values.put(StudyMaster.Studies.COLUMN_NAME_REMINDER_TIME, reminderTime);

        db.update(StudyMaster.Studies.TABLE_NAME, values, "_id = ?", new String[]{studyId});

        return true;
    }

    public Cursor getAllStudies() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+ StudyMaster.Studies.TABLE_NAME, null);
    }

    public Cursor getSingleStudy(int studyId) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + StudyMaster.Studies.TABLE_NAME + " WHERE " +
                StudyMaster.Studies._ID + " = " + studyId, null);
    }

    public int getLatestStudyId() {
        int latestStudyId = 0;

        SQLiteDatabase db = getReadableDatabase();
        Cursor latestStudy = db.rawQuery("SELECT * FROM " + StudyMaster.Studies.TABLE_NAME + " ORDER BY " +
                StudyMaster.Studies._ID + " DESC LIMIT 1", null);

        if (latestStudy.moveToFirst()) {
            latestStudyId = latestStudy.getInt(0);
            latestStudy.close();
        }

        return latestStudyId;
    }

    public Integer deleteStudy(String studyId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(StudyMaster.Studies.TABLE_NAME, " _id = ? ", new String[]{studyId});
    }


    // DB Operations for saving study times

    public boolean addStudyTime(Integer subject, String date, String studyTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SubjectMaster.StudyTimes.COLUMN_NAME_SUBJECT, subject);
        values.put(SubjectMaster.StudyTimes.COLUMN_NAME_DATE, date);
        values.put(SubjectMaster.StudyTimes.COLUMN_NAME_STUDY_TIME, studyTime);

        long result = db.insert(SubjectMaster.StudyTimes.TABLE_NAME, null, values);

        return result != -1;
    }

    public Cursor getRelevantStudyTimes(String[] dateQueryArray) {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(SubjectMaster.StudyTimes.TABLE_NAME, null,
                "date IN (?, ?, ?, ?, ?, ?, ?)", dateQueryArray, null, null, null);
    }
}
