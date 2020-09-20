package com.example.timetable.Study;

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

public class StudyRecyclerViewAdapter extends RecyclerView.Adapter<StudyRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "Study Recycler View";
    private ArrayList<Integer> studyIds;
    private ArrayList<String> studyTitles;
    private ArrayList<String> subjectNames;
    private ArrayList<Integer> studyColours;
    private ArrayList<String> studyDates;
    private ArrayList<String> studyDays;
    private ArrayList<String> studyStartTimes;
    private ArrayList<String> studyEndTimes;
    private Context context;

    public StudyRecyclerViewAdapter(ArrayList<Integer> studyIds, ArrayList<String> studyTitles,
                                    ArrayList<String> subjectNames, ArrayList<Integer> studyColours,
                                    ArrayList<String> studyDates, ArrayList<String> studyDays,
                                    ArrayList<String> studyStartTimes, ArrayList<String> studyEndTimes,
                                    Context context) {
        this.studyIds = studyIds;
        this.studyTitles = studyTitles;
        this.subjectNames = subjectNames;
        this.studyColours = studyColours;
        this.studyDates = studyDates;
        this.studyDays = studyDays;
        this.studyStartTimes = studyStartTimes;
        this.studyEndTimes = studyEndTimes;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView studyTitle, subjectName, studyColour, studyDate, studyStartTime, studyEndTime;
        FrameLayout studyLayout;
        CardView studyCard;

        public ViewHolder(View itemView){
            super(itemView);
            studyTitle = itemView.findViewById(R.id.study_title);
            subjectName = itemView.findViewById(R.id.subject_name);
            studyColour = itemView.findViewById(R.id.study_colour);
            studyDate = itemView.findViewById(R.id.study_date);
            studyStartTime = itemView.findViewById(R.id.study_start);
            studyEndTime = itemView.findViewById(R.id.study_end);
            studyLayout = itemView.findViewById(R.id.study_layout);
            studyCard = itemView.findViewById(R.id.study_card);
        }
    }

    @NonNull
    @Override
    public StudyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.study_list, parent,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.studyTitle.setText(studyTitles.get(position));
        holder.subjectName.setText(subjectNames.get(position));
        holder.studyColour.setBackgroundColor(studyColours.get(position));
        holder.studyStartTime.setText(studyStartTimes.get(position));
        holder.studyEndTime.setText(studyEndTimes.get(position));

        // If study day is null, set text as study date
        if (studyDays.get(position) == null) {
            holder.studyDate.setText(studyDates.get(position));
        } else {
            holder.studyDate.setText(studyDays.get(position));
        }

        holder.studyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int studyId = studyIds.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id", studyId);

                EditStudyFragment editStudyFragment = new EditStudyFragment();
                editStudyFragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        editStudyFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return studyIds.size();
    }

    public void removeItem(int position) {
        studyIds.remove(position);
        studyTitles.remove(position);
        subjectNames.remove(position);
        studyColours.remove(position);
        studyDates.remove(position);
        studyDays.remove(position);
        studyStartTimes.remove(position);
        studyEndTimes.remove(position);

        notifyItemRemoved(position);
    }
}
