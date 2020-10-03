package com.example.timetable.Homework;

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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HomeworkRecyclerView extends RecyclerView.Adapter<HomeworkRecyclerView.ViewHolder>{

    private static final String TAG = "Homework Recycler View";
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> subject = new ArrayList<>();
    private ArrayList<String> due_date = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();
    private ArrayList<Integer> color = new ArrayList<>();
    private ArrayList<Integer> ids = new ArrayList<>();

    private Context mContext;

    public HomeworkRecyclerView(ArrayList<Integer> Id,ArrayList<String> title, ArrayList<String> Sub,ArrayList<Integer>color ,ArrayList<String> due_date,ArrayList<String> time, Context mContext) {
        this.ids = Id;
        this.titles = title;
        this.subject = Sub;
        this.color = color;
        this.time = time;
        this.due_date= due_date;

        this.mContext = mContext;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView subject;
        TextView date,time,dmore;
        TextView remHomework;
        FrameLayout homeworkLayout;
        RelativeLayout homeworkCard, viewBackground;

        public ViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.homeworkName);
            subject = itemView.findViewById(R.id.sub);
            date = itemView.findViewById(R.id.homeworkDate);
            time = itemView.findViewById(R.id.homeworkTime);
            dmore = itemView.findViewById(R.id.dmore);
            remHomework = itemView.findViewById(R.id.rem_days);
            homeworkLayout = (FrameLayout)itemView.findViewById(R.id.homeworklayout);
            homeworkCard = (RelativeLayout) itemView.findViewById(R.id.homeworkCard);
            viewBackground = (RelativeLayout) itemView.findViewById(R.id.view_background);
        }
    }
    @NonNull
    @Override
    public HomeworkRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.homeworklist,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.title.setText(titles.get(position));
        holder.date.setText(due_date.get(position));
        holder.time.setText(time.get(position));
        holder.subject.setText(subject.get(position));
//        holder.homeworkCard.setBackgroundTintList(Color.valueOf(colors.get(position)));
        holder.homeworkCard.setBackgroundColor(color.get(position));

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Date d1 = null;
        Date d2 = null;
        String date= String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":"+String.valueOf(cal.get(Calendar.MINUTE));
        Date now = null;
        try {
            now = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {

            d1 = dateFormat.parse(due_date.get(position));
            d2 = timeFormat.parse(time.get(position));
            cal.setTime(d1);
            cal2.setTime(d2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        cal.set(Calendar.HOUR_OF_DAY,cal2.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE,cal2.get(Calendar.MINUTE));

        Calendar today = Calendar.getInstance();
        long rem = ChronoUnit.DAYS.between(today.toInstant(), cal.toInstant()) ;

        if(rem>10){
            holder.remHomework.setPadding(40,40,40,40);
        }
        if(rem == 0){

            long diffHours = getRemainingtimeinHours(cal);

            if(diffHours>10){
                holder.remHomework.setPadding(40,40,40,40);
            }
            holder.remHomework.setText(String.valueOf(diffHours));
            holder.dmore.setText("Hours more");
            if(diffHours<0){
                holder.remHomework.setVisibility(View.INVISIBLE);
                holder.dmore.setVisibility(View.INVISIBLE);

            }if(diffHours== 0){
                long diffMins =  getRemainingtimeinMinutes(cal);
                if(diffMins>10){
                    holder.remHomework.setPadding(40,40,40,40);
                }
                    holder.remHomework.setText(String.valueOf(diffMins));
                    holder.dmore.setText("Minutes more");
            }
        }else if (rem>0){
            holder.remHomework.setText(String.valueOf(rem));
        }else if(rem<0){
            holder.remHomework.setVisibility(View.INVISIBLE);
            holder.dmore.setVisibility(View.INVISIBLE);
        }

        holder.homeworkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = ids.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id",  id);
                edithomework cFragment = new edithomework();
                cFragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cFragment).addToBackStack(null).commit();
            }
        });
    }
    public long getRemainingtimeinHours(Calendar cal){
        long diff = cal.getTime().getTime() - System.currentTimeMillis();
        long diffHours = ((diff / (60 * 60 * 1000) )% 24);

        return diffHours;
    }

    public long getRemainingtimeinMinutes(Calendar cal){
        long diff = cal.getTime().getTime() - System.currentTimeMillis();
        long diffMinutes = diff / (60 * 1000) % 60;

        return diffMinutes;
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public void removeItem(int position) {
        titles.remove(position);
        subject.remove(position);
        due_date.remove(position);
        time.remove(position);
        color.remove(position);

        notifyItemRemoved(position);
    }





}
