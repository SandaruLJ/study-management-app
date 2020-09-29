package com.example.timetable.Exam;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetable.ColorPicker;
import com.example.timetable.Database.DBHandler;
import com.example.timetable.Homework.displayHomework;
import com.example.timetable.R;
import com.example.timetable.SelectDateFragment;
import com.example.timetable.SelectTimeFragement;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link exam#newInstance} factory method to
 * create an instance of this fragment.
 */
public class exam extends Fragment {

    EditText title,note,location;
    Button save;
    TextView stime,etime;
    Spinner subject,reminder;
    ColorStateList col;
    DBHandler db;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());
//        db.create();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_exam, container, false);


        Button btn = view.findViewById(R.id.save);
        title = (EditText) view.findViewById(R.id.title);
        location = (EditText) view.findViewById(R.id.location);
        subject = (Spinner) view.findViewById(R.id.subjectSelect);
        reminder = (Spinner) view.findViewById(R.id.reminder);
        note = (EditText) view.findViewById(R.id.note);




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
        //Start Time Picker
        stime = (TextView) view.findViewById(R.id.sTime);

        LinearLayout pickTimebtn1 = (LinearLayout) view.findViewById(R.id.timeSelectorStart);
        pickTimebtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pass the textView in order to set the date for the text
                DialogFragment newFragment = new SelectTimeFragement(stime);
                newFragment.show(getFragmentManager(), "TimePicker");

            }
        });

        //End Time Picker
        etime = (TextView) view.findViewById(R.id.eTime);

        LinearLayout pickDatebtn2 = (LinearLayout) view.findViewById(R.id.timeSelectorEnd);
        pickDatebtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pass the textView in order to set the date for the text
                DialogFragment newFragment = new SelectTimeFragement(etime);
                newFragment.show(getFragmentManager(), "TimePicker");

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean isInserted = db.addExam(title.getText().toString(),subject.getSelectedItem().toString(),location.getText().toString(),end.getText().toString(),stime.getText().toString(),etime.getText().toString(),reminder.getSelectedItem().toString(),((ColorDrawable) bgBtn.getBackground()).getColor(),note.getText().toString());

                if(isInserted == true) {
                    Toast.makeText(getActivity().getApplicationContext(), "Exam Added Successfully", Toast.LENGTH_LONG).show();
                    displayExams fragment = new displayExams();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

                }else
                    Toast.makeText(getActivity().getApplicationContext(),"Insert Failed, Try again",Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }
}