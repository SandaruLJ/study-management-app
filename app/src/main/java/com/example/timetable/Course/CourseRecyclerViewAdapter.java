package com.example.timetable.Course;

import android.content.Context;
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

public class CourseRecyclerViewAdapter extends RecyclerView.Adapter<CourseRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "Course Recycler View";
    private ArrayList<Integer> CourseId = new ArrayList<>();
    private ArrayList<String> CourseNames = new ArrayList<>();
    private  ArrayList<String> institutions = new ArrayList<>();
    private  ArrayList<Integer> colors = new ArrayList<>();
    private Context mContext;

    public CourseRecyclerViewAdapter(ArrayList<Integer> courseId,ArrayList<String> courseNames, ArrayList<String> institutions,ArrayList<Integer> colors, Context mContext) {
        CourseNames = courseNames;
        this.CourseId = courseId;
        this.institutions = institutions;
        this.colors = colors;
        this.mContext = mContext;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cname;
        TextView institution;
        FrameLayout courseLayout;
        RelativeLayout courseCard, viewBackground;

        public ViewHolder(View itemView){
            super(itemView);
            cname = itemView.findViewById(R.id.courseName);
            institution = itemView.findViewById(R.id.institute);
            courseLayout = (FrameLayout)itemView.findViewById(R.id.courseLayout);
            courseCard = (RelativeLayout) itemView.findViewById(R.id.course_card);
            viewBackground = (RelativeLayout) itemView.findViewById(R.id.view_background);
        }
    }
    @NonNull
    @Override
    public CourseRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.cname.setText(CourseNames.get(position));
        holder.institution.setText(institutions.get(position));
//        holder.courseCard.setBackgroundTintList(Color.valueOf(colors.get(position)));
        holder.courseCard.setBackgroundColor(colors.get(position));
//        holder.courseCard.setBackgroundTintList(ColorStateList.valueOf(-395151));
        holder.courseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext,CourseNames.get(position),Toast.LENGTH_LONG).show();
                int id = CourseId.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id",  id);

                editCourse cFragment = new editCourse();
                cFragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return CourseNames.size();
    }

    public void removeItem(int position) {
        CourseId.remove(position);
        CourseNames.remove(position);
        institutions.remove(position);
        colors.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }





}
