package com.example.timetable.Homework;

import android.content.res.ColorStateList;
import android.database.Cursor;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetable.ColorPicker;
import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;
import com.example.timetable.SelectDateFragment;
import com.example.timetable.SelectTimeFragement;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link edithomework#newInstance} factory method to
 * create an instance of this fragment.
 */
public class edithomework extends Fragment {

    EditText title,note;
    Button save;
    TextView end;
    Spinner subject,reminder;
    ColorStateList col;
    DBHandler db;
    int id;
    TextView stime;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());
//        db.create();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edithomework, container, false);

        Button btn = view.findViewById(R.id.save);
        id = 0;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = Integer.parseInt(bundle.get("id").toString());

        }

        if (id != 0) {
            String etitle = null, esubject = null, edate = null, etime = null, ereminder = null, enote = null;
            int ecolour = 0;
            Cursor c = db.getSingleHomework(id);
            while (c.moveToNext()) {
                etitle = c.getString(1);
                esubject = c.getString(2);
                edate = c.getString(3);
                ecolour = c.getInt(6);
                etime = c.getString(4);
                enote = c.getString(5);
//                Toast.makeText(getActivity().getApplicationContext(),cname,Toast.LENGTH_LONG).show();
            }

            title = (EditText) view.findViewById(R.id.title);
            subject = (Spinner) view.findViewById(R.id.subjectSelect);
            reminder = (Spinner) view.findViewById(R.id.reminder);
            note = (EditText) view.findViewById(R.id.note);
            end = (TextView) view.findViewById(R.id.endDate);
            final Button colorbtn = (Button) view.findViewById(R.id.colorbtn);
            final Button bgBtn = (Button) view.findViewById(R.id.testbtn);
            stime = (TextView) view.findViewById(R.id.time);

            title.setText(etitle);
//            subject.setSelected(esubject);
            note.setText(enote);
            end.setText(edate);
            stime.setText(etime);
            colorbtn.setBackgroundTintList(ColorStateList.valueOf(ecolour));
            bgBtn.setBackgroundColor(ecolour);


            //Colour Picker

            colorbtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ColorPicker cp = new ColorPicker();
                    cp.openColorPicker(getChildFragmentManager(), colorbtn, bgBtn);
                }

            });

            //End Date Picker

            LinearLayout pickDatebtn1 = (LinearLayout) view.findViewById(R.id.dateSelectorEnd);
            pickDatebtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Pass the textView in order to set the date fo
                    // r the text
                    DialogFragment newFragment = new SelectDateFragment(end);
                    newFragment.show(getFragmentManager(), "DatePicker");

                }
            });

            //Start Time Picker


            LinearLayout pickDatebtn2 = (LinearLayout) view.findViewById(R.id.timeSelectorEnd);
            pickDatebtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Pass the textView in order to set the date for the text
                    DialogFragment newFragment = new SelectTimeFragement(stime);
                    newFragment.show(getFragmentManager(), "TimePicker");

                }
            });


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    boolean isInserted = db.addHomework(title.getText().toString(), subject.getSelectedItem().toString(), end.getText().toString(), "10.30", reminder.getSelectedItem().toString(), ((ColorDrawable) bgBtn.getBackground()).getColor(), note.getText().toString());

                    if (isInserted == true) {
                        Toast.makeText(getActivity().getApplicationContext(), "Homework Added Successfully", Toast.LENGTH_LONG).show();
                        displayHomework fragment = new displayHomework();
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

                    } else
                        Toast.makeText(getActivity().getApplicationContext(), "Insert Failed, Try again", Toast.LENGTH_LONG).show();
                }
            });
        }


        return view;

    }}

