package com.example.timetable.Subject;

import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.timetable.ColorPicker;
import com.example.timetable.Database.DBHandler;
import com.example.timetable.OptionsMenu;
import com.example.timetable.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditSubjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditSubjectFragment extends Fragment {

    DBHandler db;
    Integer subjectId;
    EditText subjectName, teacherName , subjectDesc;
    Button subjectColour;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity());
    }
    
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_subject, container, false);

        final ImageView addIcon = view.findViewById(R.id.addIcon);
        final  ImageView calendaricon = view.findViewById(R.id.calendarIcon);
        LayoutInflater layoutInflater= (LayoutInflater)getActivity().getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.list_popup, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,450, ViewGroup.LayoutParams.WRAP_CONTENT);
        OptionsMenu.displayMenu(calendaricon,addIcon,popupWindow,popupView);

        subjectId  = 0;
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            subjectId = Integer.parseInt(bundle.get("id").toString());
        }

        if (subjectId != 0) {
            String subjectNameTemp = null, teacherNameTemp = null, subjectDescTemp = null;
            int subjectColourTemp = 0;

            Cursor c = db.getSingleSubject(subjectId);

            while (c.moveToNext()){
                subjectNameTemp = c.getString(1);
                teacherNameTemp = c.getString(2);
                subjectDescTemp = c.getString(3);
                subjectColourTemp = c.getInt(4);
            }

            subjectName = view.findViewById(R.id.subject_name);
            teacherName = view.findViewById(R.id.teacher_name);
            subjectDesc = view.findViewById(R.id.subject_desc);
            subjectColour = view.findViewById(R.id.subject_colour);


            // Pre-fill form inputs with existing database values
            subjectName.setText(subjectNameTemp);
            teacherName.setText(teacherNameTemp);
            subjectDesc.setText(subjectDescTemp);
            subjectColour.setBackgroundColor(subjectColourTemp);

            // Set Colour Button background tint
            final Button colourBtn = view.findViewById(R.id.colorbtn);
            colourBtn.setBackgroundTintList(ColorStateList.valueOf(subjectColourTemp));





            // Colour Button
            colourBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ColorPicker cp = new ColorPicker() ;
                    cp.openColorPicker(getChildFragmentManager(),colourBtn, subjectColour);
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
            Button updateSubjectBtn = view.findViewById(R.id.update_subject);

            updateSubjectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                // Validate inputs before updating the database
                if (subjectName.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter a subject name", Toast.LENGTH_LONG).show();
                }
                else if (teacherName.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter a teacher's name", Toast.LENGTH_LONG).show();
                }
                else {
                    boolean isInserted = db.updateSubject(String.valueOf(subjectId), subjectName.getText().toString(),
                            teacherName.getText().toString(), subjectDesc.getText().toString(),
                            ((ColorDrawable) subjectColour.getBackground()).getColor());

                    if (isInserted) {
                        Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_LONG).show();
                        AllSubjectsFragment fragment = new AllSubjectsFragment();
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();

                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack(null).commit();
                    } else
                        Toast.makeText(getActivity(), "Update Failed, Please Try again", Toast.LENGTH_LONG).show();
                }
                }
            });
        }

        return view;
    }
}