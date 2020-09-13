package com.example.timetable.Course;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetable.ColorPicker;
import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;
import com.example.timetable.SelectDateFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link editCourse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class editCourse extends Fragment {

    DBHandler db;
    EditText course,instituition,description;
    int colour;
    TextView start,end;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());
    }
    
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_course, container, false);
        Button btn = view.findViewById(R.id.updateCourse);
        int id  = 0;
        Bundle bundle = this.getArguments();
        final Button colorbtn = (Button) view.findViewById(R.id.colorbtn);
        final Button bgBtn = (Button) view.findViewById(R.id.testbtn);
        colorbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ColorPicker cp = new ColorPicker() ;
                cp.openColorPicker(getChildFragmentManager(),colorbtn, bgBtn);

            }

        });


        if(bundle!=null){
            id = Integer.parseInt(bundle.get("id").toString());

        }
        if(id != 0){
            String cname = null,inst = null,des = null,startd = null,endd = null;
            Cursor c = db.getSingleCourse(id);
            while (c.moveToNext()){
                cname = c.getString(1);
                inst = c.getString(2);
                des = c.getString(3);
                colour = c.getInt(4);
                startd = c.getString(5);
                endd = c.getString(6);
//                Toast.makeText(getActivity().getApplicationContext(),cname,Toast.LENGTH_LONG).show();
            }
            course = (EditText) view.findViewById(R.id.course_name);
            instituition = (EditText) view.findViewById(R.id.institute_name);
            description = (EditText) view.findViewById(R.id.course_description);
            end = (TextView) view.findViewById(R.id.endDate);
            start = (TextView) view.findViewById(R.id.startDate);

            course.setText(cname);
            instituition.setText(inst);
            description.setText(des);
            start.setText(startd);
            end.setText(endd);
            colorbtn.setBackgroundTintList(ColorStateList.valueOf(colour));
            bgBtn.setBackgroundColor(colour);

            final int finalId = id;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    boolean isInserted = db.updateCourse(String.valueOf(finalId),course.getText().toString(),instituition.getText().toString(),description.getText().toString(), ((ColorDrawable) bgBtn.getBackground()).getColor(),start.getText().toString(),end.getText().toString());

                    if(isInserted == true) {
                        Toast.makeText(getActivity().getApplicationContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                        DisplayCourseFragment fragment = new DisplayCourseFragment();
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();

                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                    }
                    else
                        Toast.makeText(getActivity().getApplicationContext(),"Update Failed, Please Try again",Toast.LENGTH_LONG).show();
                }
            });



        }


        //Start Date

        LinearLayout pickDatebtn = (LinearLayout) view.findViewById(R.id.dateSelectorStart);
        pickDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectDateFragment(start);
                newFragment.show(getFragmentManager(), "DatePicker");

            }
        });
        //End Date

        LinearLayout pickDatebtn1 = (LinearLayout) view.findViewById(R.id.dateSelectorEnd);
        pickDatebtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectDateFragment(end);
                newFragment.show(getFragmentManager(), "DatePicker");

            }
        });



        return view;
    }
}