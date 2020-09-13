package com.example.timetable.Database;

import android.provider.BaseColumns;

public class CourseMaster {

    public static class Courses implements BaseColumns{
        public  static final String TABLE_NAME = "courses";
        public  static final String COLUMN_NAME_COURSE_NAME = "course_name";
        public  static final String COLUMN_NAME_INSTITUTE = "institute";
        public  static final String COLUMN_NAME_DESCRIPTION = "description";
        public  static final String COLUMN_NAME_COLOUR = "colour";
        public  static final String COLUMN_NAME_START = "start_date";
        public  static final String COLUMN_NAME_END = "end_date";
    }
}
