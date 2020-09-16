package com.example.timetable.Database;

import android.provider.BaseColumns;

public class SubjectMaster {

    public static class Subjects implements BaseColumns {
        public static final String TABLE_NAME = "subjects";
        public static final String COLUMN_NAME_SUBJECT_NAME = "subject_name";
        public static final String COLUMN_NAME_TEACHER_NAME = "teacher_name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_COLOUR = "colour";
    }
}
