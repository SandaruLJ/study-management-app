package com.example.timetable.Subject;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetable.R;

import java.util.ArrayList;

public class SubjectRecyclerViewAdapter extends RecyclerView.Adapter<SubjectRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "Subject Recycler View";
    private ArrayList<Integer> subjectIds;
    private ArrayList<String> subjectNames;
    private ArrayList<String> teacherNames;
    private ArrayList<Integer> subjectColours;
    private Context context;

    public SubjectRecyclerViewAdapter(ArrayList<Integer> subjectIds, ArrayList<String> subjectNames,
                                      ArrayList<String> teacherNames, ArrayList<Integer> subjectColours,
                                      Context context) {
        this.subjectIds = subjectIds;
        this.subjectNames = subjectNames;
        this.teacherNames = teacherNames;
        this.subjectColours = subjectColours;
        this.context = context;
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
        View view = LayoutInflater.from(context).inflate(R.layout.subject_list, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.subjectName.setText(subjectNames.get(position));
        holder.teacherName.setText(teacherNames.get(position));
        holder.subjectColour.setBackgroundColor(subjectColours.get(position));

        holder.subjectCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int subjectId = subjectIds.get(holder.getAdapterPosition());
                Bundle bundle = new Bundle();
                bundle.putInt("id", subjectId);

                EditSubjectFragment editSubject = new EditSubjectFragment();
                editSubject.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, editSubject)
                        .addToBackStack(null).commit();
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
        subjectColours.remove(position);

        notifyItemRemoved(position);
    }





}
