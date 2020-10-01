package com.example.timetable.Exam;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetable.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExamRecyclerView extends RecyclerView.Adapter<ExamRecyclerView.ViewHolder>{

    private static final String TAG = "Exam Recycler View";

    private ArrayList<String> exam = new ArrayList<>();
    private ArrayList<String> subject = new ArrayList<>();
    private ArrayList<String> location = new ArrayList<>();
    private  ArrayList<Integer> colours = new ArrayList<>();
    private  ArrayList<Integer> ids = new ArrayList<>();
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();


    private Context mContext;

    public ExamRecyclerView( ArrayList<Integer> ids,ArrayList<String> exam, ArrayList<String> subject, ArrayList<String> location, ArrayList<Integer> colours , ArrayList<String> date, ArrayList<String> time, Context mContext) {
        this.exam = exam;
        this.subject = subject;
        this.location = location;
        this.colours = colours;
        this.ids = ids;
        this.time = time;
        this.date = date;
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView exam;
        TextView subject;
        TextView location;
        TextView time;
        TextView date;
        TextView day;
        TextView year;
        TextView remdays,dayText;

        FrameLayout examLayout;
        RelativeLayout examCard, viewBackground, examColour;

//        RelativeLayout courseCard, viewBackground;

        public ViewHolder(View itemView){
            super(itemView);
            exam = (TextView) itemView.findViewById(R.id.exam);
            examLayout = (FrameLayout)itemView.findViewById(R.id.examLayout);
            subject = itemView.findViewById(R.id.subject);
            location = itemView.findViewById(R.id.location);
            day = itemView.findViewById(R.id.day);
            date = itemView.findViewById(R.id.date);
            year = itemView.findViewById(R.id.year);
            time = itemView.findViewById(R.id.time);
            remdays = itemView.findViewById(R.id.days);
//            dayText = itemView.findViewById(R.id.daytext);
            examColour = (RelativeLayout) itemView.findViewById(R.id.examColour);
            viewBackground = (RelativeLayout) itemView.findViewById(R.id.view_background);
            examCard = (RelativeLayout) itemView.findViewById(R.id.examCard);


        }
    }
    @NonNull
    @Override
    public ExamRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_list,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.exam.setText(exam.get(position));
        holder.subject.setText(subject.get(position));
        holder.examColour.setBackgroundTintList(ColorStateList.valueOf(colours.get(position)));
        holder.location.setText(location.get(position));
        holder.time.setText(time.get(position));
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        Date d1 = null;
        try {
            d1 = dateFormat.parse(date.get(position));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(d1);
        holder.date.setText(String.valueOf(cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH )) + " " + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        holder.day.setText(String.valueOf(cal.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG,Locale.ENGLISH)));
        holder.year.setText(String.valueOf(cal.get(Calendar.YEAR)));

        Calendar today = Calendar.getInstance();
        long rem = ChronoUnit.DAYS.between(today.toInstant(), cal.toInstant()) + 1;
        if(rem>10){
            holder.remdays.setPadding(40,40,40,40);
        }else{
            holder.remdays.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ED2424")));
        }
//        if(rem<=0){
//            holder.remdays.setVisibility(View.INVISIBLE);
//            holder.dayText.setVisibility(View.INVISIBLE);
//        }
        holder.remdays.setText(String.valueOf(rem));

        holder.examCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext,CourseNames.get(position),Toast.LENGTH_LONG).show();
                int id = ids.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id",  id);
                editExam cFragment = new editExam();
                cFragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return exam.size();
    }

    public void removeItem(int position) {
        exam.remove(position);
        subject.remove(position);
        colours.remove(position);
        ids.remove(position);
        location.remove(position);
        time.remove(position);
        date.remove(position);

        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }





}
