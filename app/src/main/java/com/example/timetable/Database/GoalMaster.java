package com.example.timetable.Database;

import android.provider.BaseColumns;

public class GoalMaster {

    public static class Goals implements BaseColumns {
        public  static final String TABLE_NAME = "goals";
        public  static final String COLUMN_NAME_GOAL_NAME = "goal_name";
        public  static final String COLUMN_NAME_COLOUR = "colour";
        public  static final String COLUMN_NAME_DUE = "due_date";
        public  static final String COLUMN_NAME_DESCRIPTION = "description";
        public  static final String COLUMN_NAME_REMINDER = "reminder";
        public  static final String COLUMN_NAME_SCHEDULED = "scheduled_reminder";
    }
}
