package com.example.timetable.Class;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;

import com.example.timetable.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {

    private final int color;
//    private final HashSet<CalendarDay> dates;
    private final CalendarDay d;
    private Drawable drawable;
    public EventDecorator(int color, CalendarDay d) {
        this.color = color;
//        this.dates = new HashSet<>(dates);
        this.d = d;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
//        return dates.contains(day);
        if(d.equals(day))
            return true;
        else
            return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
//        view.addSpan(new BackgroundColorSpan(Color.YELLOW));
        view.setSelectionDrawable(new ColorDrawable(color));
    }
}

