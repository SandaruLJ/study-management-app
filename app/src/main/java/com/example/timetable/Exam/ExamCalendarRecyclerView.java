package com.example.timetable.Exam;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import java.util.concurrent.TimeUnit;

public class ExamCalendarRecyclerView extends RecyclerView.Adapter<ExamCalendarRecyclerView.ViewHolder>{

    private static final String TAG = "Exam Recycler View";
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> subject = new ArrayList<>();
    private ArrayList<String> due_date = new ArrayList<>();
    private ArrayList<String> stime = new ArrayList<>();
    private ArrayList<String> etime = new ArrayList<>();
    private ArrayList<Integer> color = new ArrayList<>();
    private ArrayList<Integer> ids = new ArrayList<>();

    private Context mContext;

    public ExamCalendarRecyclerView(ArrayList<Integer> Id,ArrayList<String> title, ArrayList<String> Sub,ArrayList<Integer>color ,ArrayList<String> due_date, ArrayList<String> stime,ArrayList<String> etime,Context mContext) {
        this.ids = Id;
        this.titles = title;
        this.subject = Sub;
        this.color = color;
        this.stime = stime;
        this.etime = etime;
        this.due_date= due_date;

        this.mContext = mContext;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView subject;
        TextView date;
        TextView stime,etime;
        FrameLayout ExamLayout;
        RelativeLayout  ExamCard,viewBackground,ExamColor;


        public ViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.ExamName);
            subject = itemView.findViewById(R.id.sub);
            date = itemView.findViewById(R.id.ExamDate);
            stime = itemView.findViewById(R.id.sTime);
            etime = itemView.findViewById(R.id.eTime);

            ExamLayout = (FrameLayout)itemView.findViewById(R.id.Examlayout);
            ExamCard = (RelativeLayout) itemView.findViewById(R.id.ExamCard);
            ExamColor = (RelativeLayout) itemView.findViewById(R.id.ExamColor);
            viewBackground = (RelativeLayout) itemView.findViewById(R.id.view_background);
        }
    }
    @NonNull
    @Override
    public ExamCalendarRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_calendar_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.title.setText(titles.get(position));
        holder.date.setText(due_date.get(position));
        holder.stime.setText(stime.get(position));
        holder.etime.setText(etime.get(position));
        holder.subject.setText(subject.get(position));
//        holder.ExamCard.setBackgroundTintList(Color.valueOf(colors.get(position)));
        holder.ExamColor.setBackgroundColor(color.get(position));

        holder.ExamLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext,CourseNames.get(position),Toast.LENGTH_LONG).show();
                int id = ids.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id",  id);
////
//                editExam cFragment = new editExam();
//                cFragment.setArguments(bundle);
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public void removeItem(int position) {
        titles.remove(position);
        subject.remove(position);
        due_date.remove(position);
        color.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }





}
