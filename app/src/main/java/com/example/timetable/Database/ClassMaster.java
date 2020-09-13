package com.example.timetable.Database;

import android.provider.BaseColumns;

public class ClassMaster {

    public static class Classes implements BaseColumns {
        public  static final String TABLE_NAME_CLASS = "classes";
        public  static final String COLUMN_NAME_CLASS_NAME = "class_name";
        public  static final String COLUMN_NAME_COURSE = "course";
        public  static final String COLUMN_NAME_SUBJECT = "subject";
        public  static final String COLUMN_NAME_CLASS_TYPE = "class_type";
        public  static final String COLUMN_NAME_CLASS_TEACHER = "teacher";
        public  static final String COLUMN_NAME_CLASS_ROOM = "class_room";
        public  static final String COLUMN_NAME_NOTE = "note";
        public  static final String COLUMN_NAME_COLOR = "colour";
        public  static final String COLUMN_NAME_FREQUENCY = "frequency";
        public  static final String COLUMN_NAME_DAY = "day";
        public  static final String COLUMN_NAME_START_TIME = "start_time";
        public  static final String COLUMN_NAME_END_TIME = "end_time";
        public  static final String COLUMN_NAME_START_DATE = "start_date";
        public  static final String COLUMN_NAME_END_DATE = "end_date";
        public  static final String COLUMN_NAME_REMINDER = "reminder";
    }
}
