package com.example.timetable.Subject;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.timetable.ColorPicker;
import com.example.timetable.Database.DBHandler;
import com.example.timetable.OptionsMenu;
import com.example.timetable.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddSubjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSubjectFragment extends Fragment {

    EditText subjectName, teacherName, subjectDesc;
    Button subjectColour;
    DBHandler db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_subject, container, false);

        subjectName = view.findViewById(R.id.subject_name);
        teacherName = view.findViewById(R.id.teacher_name);
        subjectColour = view.findViewById(R.id.subject_colour);
        subjectDesc = view.findViewById(R.id.subject_desc);

        final ImageView addIcon = view.findViewById(R.id.addIcon);
        final  ImageView calendaricon = view.findViewById(R.id.calendarIcon);
        LayoutInflater layoutInflater= (LayoutInflater)getActivity().getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.list_popup, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,450, ViewGroup.LayoutParams.WRAP_CONTENT);
        OptionsMenu.displayMenu(calendaricon,addIcon,popupWindow,popupView);

        // Colour Picker
        final Button colourBtn = view.findViewById(R.id.colorbtn);
        colourBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ColorPicker cp = new ColorPicker();
                cp.openColorPicker(getChildFragmentManager(), colourBtn, subjectColour);
            }

        });

        // Open colour picker if clicked anywhere on the surrounding layout
        LinearLayout colourWrapper = view.findViewById(R.id.colour_wrapper);
        colourWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colourBtn.performClick();
            }
        });


        // Submit Button
        Button addSubjectBtn = view.findViewById(R.id.add_subject);

        addSubjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // Validate inputs before inserting to the database
            if (subjectName.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Please enter a subject name", Toast.LENGTH_LONG).show();
            }
            else if (teacherName.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Please enter a teacher's name", Toast.LENGTH_LONG).show();
            }
            else {
                boolean isInserted = db.addSubject(subjectName.getText().toString(), teacherName.getText().toString(),
                        subjectDesc.getText().toString(), ((ColorDrawable) subjectColour.getBackground()).getColor());

                if (isInserted) {
                    Toast.makeText(getActivity(), "Subject Added Successfully", Toast.LENGTH_LONG).show();
                    AllSubjectsFragment allSubFragment = new AllSubjectsFragment();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_container, allSubFragment).
                            addToBackStack(null).commit();
                }
                else
                    Toast.makeText(getActivity(), "Insert failed, try again", Toast.LENGTH_LONG).show();
            }
            }
        });

        return view;
    }
}