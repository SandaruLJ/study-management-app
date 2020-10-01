package com.example.timetable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetable.Class.editClass;
import com.example.timetable.Exam.editExam;
import com.example.timetable.Goals.edit_goals;
import com.example.timetable.Homework.edithomework;
import com.example.timetable.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashboardRecyclerView extends RecyclerView.Adapter<DashboardRecyclerView.ViewHolder>{

    private static final String TAG = "Dashnboard Recycler View";

    private  ArrayList<Integer> ids = new ArrayList<>();
    private ArrayList<String> event = new ArrayList<>();
    private ArrayList<String> subject = new ArrayList<>();
    private ArrayList<String> location = new ArrayList<>();
    private  ArrayList<Integer> colours = new ArrayList<>();
    private ArrayList<String> stime = new ArrayList<>();
    private ArrayList<String> etime = new ArrayList<>();
    private ArrayList<String> type  = new ArrayList<>();
    private Context mContext;

    public DashboardRecyclerView(ArrayList<Integer> ids, ArrayList<String> event, ArrayList<String> subject, ArrayList<String> location, ArrayList<Integer> colours, ArrayList<String> stime, ArrayList<String> etime, ArrayList<String> type, Context mContext) {
        this.ids = ids;
        this.event = event;
        this.subject = subject;
        this.location = location;
        this.colours = colours;
        this.stime = stime;
        this.etime = etime;
        this.type = type;
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView event;
        TextView subject;
        TextView location;
        TextView stime;
       TextView etime;
       TextView type;
       ImageView classIcon, subjectIcon;


        FrameLayout eventLayout;
        RelativeLayout eventCard, viewBackground;

//        RelativeLayout courseCard, viewBackground;

        public ViewHolder(View itemView){
            super(itemView);
            event = (TextView) itemView.findViewById(R.id.event);
            eventLayout = (FrameLayout)itemView.findViewById(R.id.eventlayout);
            subject = itemView.findViewById(R.id.sub);
            location = itemView.findViewById(R.id.location);
            type = itemView.findViewById(R.id.eventType);
            stime = itemView.findViewById(R.id.sTime);
            etime = itemView.findViewById(R.id.eTime);
            classIcon = itemView.findViewById(R.id.classIcon);
            subjectIcon = itemView.findViewById(R.id.subjectIcon);
            viewBackground = (RelativeLayout) itemView.findViewById(R.id.view_background);
            eventCard = (RelativeLayout) itemView.findViewById(R.id.eventCard);

        }
    }
    @NonNull
    @Override
    public DashboardRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboardlist,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.event.setText(event.get(position));
        holder.subject.setText(subject.get(position));
        holder.eventCard.setBackgroundColor(colours.get(position));
        holder.location.setText(location.get(position));
        holder.stime.setText(stime.get(position));
        holder.etime.setText(etime.get(position));
        holder.type.setText(type.get(position));


        if(type.get(position).equals("Homework")){
            holder.location.setVisibility(View.INVISIBLE);
            holder.classIcon.setVisibility(View.INVISIBLE);
            holder.stime.setPadding(0,50,50,10);
        }else if(type.get(position).equals("Goals")){
            holder.location.setVisibility(View.INVISIBLE);
            holder.classIcon.setVisibility(View.INVISIBLE);
            holder.subjectIcon.setVisibility(View.INVISIBLE);
            holder.event.setPadding(50,50,0,0);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(30,50,10,10);
            params.addRule(RelativeLayout.RIGHT_OF, holder.event.getId());
            holder.type.setLayoutParams(params);

        }


        holder.eventCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext,CourseNames.get(position),Toast.LENGTH_LONG).show();
                int id = ids.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id",  id);

                if(type.get(position).equals("Homework")){
                    edithomework cFragment = new edithomework();
                    cFragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cFragment).addToBackStack(null).commit();
                }else if(type.get(position).equals("Exams")){
                    editExam cFragment = new editExam();
                    cFragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cFragment).addToBackStack(null).commit();

                }else if(type.get(position).equals("Class")){
                    editClass cFragment = new editClass();
                    cFragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cFragment).addToBackStack(null).commit();
                }else if(type.get(position).equals("Goals")){
                    edit_goals cFragment = new edit_goals();
                    cFragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cFragment).addToBackStack(null).commit();

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return event.size();
    }

    public void removeItem(int position) {
        event.remove(position);
        subject.remove(position);
        colours.remove(position);
        ids.remove(position);
        location.remove(position);
        stime.remove(position);
        etime.remove(position);
        type.remove(position);

        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }





}
