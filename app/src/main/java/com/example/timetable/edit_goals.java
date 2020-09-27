package com.example.timetable;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
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

import com.example.timetable.Database.DBHandler;
import com.example.timetable.Goals.AllGoalsFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link edit_goals#newInstance} factory method to
 * create an instance of this fragment.
 */
public class edit_goals extends Fragment {

    EditText gname, due, description;
    Button save;
    Spinner scheduled_reminder;
    SwitchCompat reminder;
    ColorStateList col;
    DBHandler db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_createevents, container, false);

            Button save = view.findViewById(R.id.btnSave);
            gname = (EditText) view.findViewById(R.id.goalid);
            reminder = (SwitchCompat) view.findViewById(R.id.reminder);
            scheduled_reminder = (Spinner) view.findViewById(R.id.subjectInput);
            description = (EditText) view.findViewById(R.id.descriptionId);

            //Colour Picker
            final Button colorbtn = (Button) view.findViewById(R.id.colorbtn);
            final Button bgBtn = (Button) view.findViewById(R.id.testbtn);
            colorbtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ColorPicker cp = new ColorPicker();
                    cp.openColorPicker(getChildFragmentManager(), colorbtn, bgBtn);
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

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isInserted = db.addGoals(gname.getText().toString(),((ColorDrawable) bgBtn.getBackground()).getColor(),end.getText().toString(),description.getText().toString(),reminder.isChecked(),scheduled_reminder.getSelectedItem().toString());

                    if(isInserted == true) {
                        Toast.makeText(getActivity().getApplicationContext(), "Goal Added Successfully", Toast.LENGTH_LONG).show();
                        AllGoalsFragment fragment= new AllGoalsFragment();
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

                    }else
                        Toast.makeText(getActivity().getApplicationContext(),"Insert Failed, Try again",Toast.LENGTH_LONG).show();
                }
            });




            return view;
        }

}