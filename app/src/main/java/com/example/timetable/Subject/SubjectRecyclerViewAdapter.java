package com.example.timetable.Subject;

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetable.Course.editCourse;
import com.example.timetable.R;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLDisplay;
import javax.security.auth.Subject;

public class SubjectRecyclerViewAdapter extends RecyclerView.Adapter<SubjectRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "Subject Recycler View";
    private ArrayList<Integer> subjectIds = new ArrayList<>();
    private ArrayList<String> subjectNames = new ArrayList<>();
    private  ArrayList<String> teacherNames = new ArrayList<>();
    private  ArrayList<Integer> colours = new ArrayList<>();
    private Context mContext;

    public SubjectRecyclerViewAdapter(ArrayList<Integer> subjectIds, ArrayList<String> subjectNames, ArrayList<String> teacherNames, ArrayList<Integer> colours, Context mContext) {
        this.subjectIds = subjectIds;
        this.subjectNames = subjectNames;
        this.teacherNames = teacherNames;
        this.colours = colours;
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView subjectName;
        TextView teacherName;
        TextView subjectColour;
        FrameLayout subjectLayout;
        CardView subjectCard;

        public ViewHolder(View itemView){
            super(itemView);
            subjectName = itemView.findViewById(R.id.subject_name);
            teacherName = itemView.findViewById(R.id.teacher_name);
            subjectColour = itemView.findViewById(R.id.subject_colour);
            subjectLayout = itemView.findViewById(R.id.subject_layout);
            subjectCard = itemView.findViewById(R.id.subject_card);
        }
    }

    @NonNull
    @Override
    public SubjectRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_list, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.subjectName.setText(subjectNames.get(position));
        holder.teacherName.setText(teacherNames.get(position));
        holder.subjectColour.setBackgroundColor(colours.get(position));

        holder.subjectCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = subjectIds.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);

                EditSubjectFragment editSubject = new EditSubjectFragment();
                editSubject.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, editSubject).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectIds.size();
    }

    public void removeItem(int position) {
        subjectIds.remove(position);
        subjectNames.remove(position);
        teacherNames.remove(position);
        colours.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }





}
