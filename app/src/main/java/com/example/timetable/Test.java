package com.example.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.timetable.Class.addClass;
import com.example.timetable.Database.DBHandler;
import com.example.timetable.Homework.edithomework;
import com.example.timetable.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Test extends Fragment {
    @Nullable
    DBHandler db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_popup, null);

        TextView t = view.findViewById(R.id.addCourse);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClass cFragment = new addClass();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }
}