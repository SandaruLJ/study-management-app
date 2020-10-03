package com.example.timetable.Course;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetable.App;
import com.example.timetable.ColorPicker;
import com.example.timetable.Database.DBHandler;
import com.example.timetable.OptionsMenu;
import com.example.timetable.R;
import com.example.timetable.ReminderBroadcast;
import com.example.timetable.SelectDateFragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCourseFragment extends Fragment {

    EditText cname,instituition,description,start,end;
    ColorStateList col;
    DBHandler db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());

        createNotificationChannels();


//        db.create();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_course, container, false);

        final ImageView addIcon = view.findViewById(R.id.addIcon);
        final  ImageView calendaricon = view.findViewById(R.id.calendarIcon);
        LayoutInflater layoutInflater= (LayoutInflater)getActivity().getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.list_popup, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,450, ViewGroup.LayoutParams.WRAP_CONTENT);
        OptionsMenu.displayMenu(calendaricon,addIcon,popupWindow,popupView);

        Button btn = view.findViewById(R.id.addCourse);
        cname = (EditText) view.findViewById(R.id.course_name);
        instituition = (EditText) view.findViewById(R.id.institute_name);
        description = (EditText) view.findViewById(R.id.course_description);

        //Colour Picker
        final Button colorbtn = (Button) view.findViewById(R.id.colorbtn);
        final Button bgBtn = (Button) view.findViewById(R.id.testbtn);
        colorbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ColorPicker cp = new ColorPicker() ;
                cp.openColorPicker(getChildFragmentManager(),colorbtn, bgBtn);
            }

        });

        //Start Date Picker
        final TextView start = (TextView) view.findViewById(R.id.startDate);
        LinearLayout pickDatebtn = (LinearLayout) view.findViewById(R.id.dateSelectorStart);
        pickDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pass the textView in order to set the value for the text
                DialogFragment newFragment = new SelectDateFragment(start);
                newFragment.show(getFragmentManager(), "DatePicker");

            }
        });
        //End Date Picker
        final TextView end = (TextView) view.findViewById(R.id.endDate);
        LinearLayout pickDatebtn1 = (LinearLayout) view.findViewById(R.id.dateSelectorEnd);
        pickDatebtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pass the textView in order to set the date for the text
                DialogFragment newFragment = new SelectDateFragment(end);
                newFragment.show(getFragmentManager(), "DatePicker");

            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cname.getText().toString().length() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter a course", Toast.LENGTH_LONG).show();
                } else if (instituition.getText().toString().length() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter an Institute", Toast.LENGTH_LONG).show();
                } else {

                    boolean isInserted = db.addCourse(cname.getText().toString(), instituition.getText().toString(), description.getText().toString(), ((ColorDrawable) bgBtn.getBackground()).getColor(), start.getText().toString(), end.getText().toString());

                    if (isInserted == true) {
                        Toast.makeText(getActivity().getApplicationContext(), "Course Added Successfully", Toast.LENGTH_LONG).show();
                        DisplayCourseFragment fragment = new DisplayCourseFragment();
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

                    } else
                        Toast.makeText(getActivity().getApplicationContext(), "Insert Failed, Try again", Toast.LENGTH_LONG).show();
                }
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    public void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    "Channel1",
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1 ");

            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);

        }
    }
}