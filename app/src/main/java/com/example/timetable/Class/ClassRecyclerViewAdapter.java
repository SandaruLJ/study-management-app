package com.example.timetable.Class;

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

import java.util.ArrayList;

public class ClassRecyclerViewAdapter extends RecyclerView.Adapter<ClassRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "Class Recycler View";
    private  ArrayList<String> className = new ArrayList<>();
    private  ArrayList<String> teacher = new ArrayList<>();
    private  ArrayList<String> venue = new ArrayList<>();
    private  ArrayList<String> sTime = new ArrayList<>();
    private  ArrayList<String> eTime = new ArrayList<>();
    private  ArrayList<Integer> ids = new ArrayList<>();
    private  ArrayList<Integer> color = new ArrayList<>();

    private Context mContext;

    public ClassRecyclerViewAdapter(ArrayList<String> className, ArrayList<String> teacher, ArrayList<String> venue, ArrayList<String> sTime, ArrayList<String> eTime, ArrayList<Integer> ids, ArrayList<Integer> color, Context mContext) {
        this.className = className;
        this.teacher = teacher;
        this.venue = venue;
        this.sTime = sTime;
        this.eTime = eTime;
        this.ids = ids;
        this.color = color;
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView className,teacher,venue,sTime,eTime,sdate;
        FrameLayout classLayout;
        RelativeLayout classColor, viewBackground,classCard;

        public ViewHolder(View itemView){
            super(itemView);
            className = itemView.findViewById(R.id.className);
            teacher = itemView.findViewById(R.id.teacher);
            venue = itemView.findViewById(R.id.venue);
            sTime = itemView.findViewById(R.id.startTime);
            eTime = itemView.findViewById(R.id.endTime);

            classLayout = itemView.findViewById(R.id.classLayout);
            classColor = itemView.findViewById(R.id.classColor);
            classCard = (RelativeLayout) itemView.findViewById(R.id.classCard);
            viewBackground = (RelativeLayout) itemView.findViewById(R.id.view_background);
        }
    }
    @NonNull
    @Override
    public ClassRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.class_day,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.className.setText(className.get(position));
        holder.teacher.setText(teacher.get(position));
        holder.venue.setText(venue.get(position));
        holder.sTime.setText(sTime.get(position));
        holder.eTime.setText(eTime.get(position));

        holder.classColor.setBackgroundTintList(ColorStateList.valueOf(color.get(position)));
//        holder.courseCard.setBackgroundColor(colors.get(position));
//        holder.courseCard.setBackgroundTintList(ColorStateList.valueOf(-395151));
        holder.classLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext,CourseNames.get(position),Toast.LENGTH_LONG).show();
                int id = ids.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id",  id);
                editClass cFragment = new editClass();
                cFragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return className.size();
    }

    public void removeItem(int position) {
        ids.remove(position);
        className.remove(position);
        teacher.remove(position);
        venue.remove(position);
        sTime.remove(position);
        eTime.remove(position);

        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }





}
