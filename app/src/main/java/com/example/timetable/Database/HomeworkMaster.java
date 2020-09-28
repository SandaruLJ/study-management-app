package com.example.timetable.Database;

import android.provider.BaseColumns;

public class HomeworkMaster {

    public static class Homework implements BaseColumns{
        public  static final String TABLE_NAME = "homework";
        public  static final String COLUMN_NAME_TITLE = "title";
        public  static final String COLUMN_NAME_SUBJECT = "subject";
        public  static final String COLUMN_NAME_DUE_DATE = "due_date";
        public  static final String COLUMN_NAME_TIME = "time";
        public  static final String COLUMN_NAME_REMINDER = "reminder";
        public  static final String COLUMN_NAME_COLOUR = "colour";
        public  static final String COLUMN_NAME_NOTE = "note";

    }

}
