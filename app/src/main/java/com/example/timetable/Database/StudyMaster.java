package com.example.timetable.Database;

import android.provider.BaseColumns;

public class StudyMaster {

    public static class Studies implements BaseColumns{
        public static final String TABLE_NAME = "studies";
        public static final String COLUMN_NAME_STUDY_TITLE = "study_title";
        public static final String COLUMN_NAME_SUBJECT = "subject_id";
        public static final String COLUMN_NAME_COLOUR = "colour";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_START = "start_time";
        public static final String COLUMN_NAME_END = "end_time";
        public static final String COLUMN_NAME_REPEAT = "repeat";
        public static final String COLUMN_NAME_DAY = "day";
        public static final String COLUMN_NAME_NOTE = "note";
        public static final String COLUMN_NAME_REMINDER = "reminder";
        public static final String COLUMN_NAME_REMINDER_TIME = "reminder_time";
    }
}
