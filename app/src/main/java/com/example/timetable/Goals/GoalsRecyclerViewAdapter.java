package com.example.timetable.Goals;

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

public class GoalsRecyclerViewAdapter extends RecyclerView.Adapter<GoalsRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "Course Recycler View";

    private ArrayList<String> GoalNames = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private  ArrayList<Integer> colours = new ArrayList<>();
    private  ArrayList<Integer> ids = new ArrayList<>();
    private ArrayList<String> due = new ArrayList<>();


    private Context mContext;

    public GoalsRecyclerViewAdapter(ArrayList<Integer> goalId,ArrayList<String> goals, ArrayList<Integer> colours, ArrayList<String> due, ArrayList<String> description, Context mContext) {


        this.GoalNames= goals;
        this.ids = goalId;
        this.colours = colours;
        this.due = due;
        this.description = description;
        this.mContext = mContext;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView gname;
        TextView description;
        TextView date;
        TextView day;
        TextView year;
        TextView remdays,dayText;

        FrameLayout goalLayout;
        RelativeLayout goalLay, viewBackground, goalColour;

//        RelativeLayout courseCard, viewBackground;

        public ViewHolder(View itemView){
            super(itemView);
            gname = (TextView) itemView.findViewById(R.id.goalName);
            goalLayout = (FrameLayout)itemView.findViewById(R.id.goalLayout);
                        description = itemView.findViewById(R.id.description);
            day = itemView.findViewById(R.id.day);
            date = itemView.findViewById(R.id.date);
            year = itemView.findViewById(R.id.year);
            remdays = itemView.findViewById(R.id.remdays);
            dayText = itemView.findViewById(R.id.daytext);
            goalColour = (RelativeLayout) itemView.findViewById(R.id.goalColour);
            viewBackground = (RelativeLayout) itemView.findViewById(R.id.view_background);
            goalLay = (RelativeLayout) itemView.findViewById(R.id.goalCard);


        }
    }
    @NonNull
    @Override
    public GoalsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_list,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.gname.setText(GoalNames.get(position));
        holder.description.setText(description.get(position));
        holder.goalColour.setBackgroundTintList(ColorStateList.valueOf(colours.get(position)));

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        Date d1 = null;
        try {
            d1 = dateFormat.parse(due.get(position));
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
        if(rem<=0){
            holder.remdays.setVisibility(View.INVISIBLE);
            holder.dayText.setVisibility(View.INVISIBLE);
        }
        holder.remdays.setText(String.valueOf(rem));

        holder.goalLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext,CourseNames.get(position),Toast.LENGTH_LONG).show();
                int id = ids.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id",  id);
                edit_goals cFragment = new edit_goals();
                cFragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return GoalNames.size();
    }

    public void removeItem(int position) {
        GoalNames.remove(position);
        description.remove(position);
        colours.remove(position);
        ids.remove(position);
        due.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }





}
