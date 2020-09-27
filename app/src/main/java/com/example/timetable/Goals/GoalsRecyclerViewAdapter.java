package com.example.timetable.Goals;

import android.content.Context;
import android.content.res.ColorStateList;
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

import java.util.ArrayList;

public class GoalsRecyclerViewAdapter extends RecyclerView.Adapter<GoalsRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "Goal Recycler View";
    private ArrayList<String> goals = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private  ArrayList<Integer> colours = new ArrayList<>();
    private  ArrayList<Integer> ids = new ArrayList<>();
    private ArrayList<String> due = new ArrayList<>();
    private Context mContext;

    public GoalsRecyclerViewAdapter(ArrayList<Integer> goalId,ArrayList<String> goals, ArrayList<Integer> colours, ArrayList<String> due, ArrayList<String> description, Context mContext) {

        this.ids = goalId;
        goals = goals;
        this.colours = colours;
        this.due = due;
        this.description = description;
        this.mContext = mContext;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView goal;
        TextView description;
        TextView date;
        TextView day;
        TextView year;

        FrameLayout goalLayout;
        RelativeLayout goalLay, viewBackground, goalColour;

        public ViewHolder(View itemView){
            super(itemView);
            goal = itemView.findViewById(R.id.goal);
//            description = itemView.findViewById(R.id.description);
//            day = itemView.findViewById(R.id.day);
//            date = itemView.findViewById(R.id.date);
//            year = itemView.findViewById(R.id.year);
            goalColour = (RelativeLayout) itemView.findViewById(R.id.goalColour);
            viewBackground = (RelativeLayout) itemView.findViewById(R.id.view_background);
            goalLay = (RelativeLayout) itemView.findViewById(R.id.goalCard);
            goalLayout = (FrameLayout) itemView.findViewById(R.id.goalLayout);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.goal.setText(goals.get(position));
//        holder.description.setText(description.get(position));
//        holder.goalColour.setBackgroundColor(colours.get(position));
        holder.goalColour.setBackgroundTintList(ColorStateList.valueOf(colours.get(position)));
//        holder.day.setText(due.get(position));
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public void removeItem(int position) {
//        CourseId.remove(position);
//        CourseNames.remove(position);
//        institutions.remove(position);
//        colors.remove(position);
//        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }





}
