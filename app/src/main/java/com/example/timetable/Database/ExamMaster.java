package com.example.timetable.Database;

import android.provider.BaseColumns;

public class ExamMaster {

    public static class Exam implements BaseColumns{
        public  static final String TABLE_NAME = "Exam";
        public  static final String COLUMN_NAME_TITLE = "title";
        public  static final String COLUMN_NAME_LOCATION = "location";
        public  static final String COLUMN_NAME_SUBJECT = "subject";
        public  static final String COLUMN_NAME_DUE_DATE = "due_date";
        public  static final String COLUMN_NAME_START_TIME = "start_time";
        public  static final String COLUMN_NAME_END_TIME = "end_time";
        public  static final String COLUMN_NAME_REMINDER = "reminder";
        public  static final String COLUMN_NAME_COLOUR = "colour";
        public  static final String COLUMN_NAME_NOTE = "note";

    }

}
