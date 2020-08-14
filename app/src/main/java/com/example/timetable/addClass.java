package com.example.timetable;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import me.jfenn.colorpickerdialog.dialogs.ColorPickerDialog;
import me.jfenn.colorpickerdialog.interfaces.OnColorPickedListener;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addClass#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addClass extends Fragment  {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_class, container, false);
//        LinearLayout pickDatebtn = (LinearLayout) view.findViewById(R.id.dateSelector);
//        pickDatebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogFragment newFragment = new SelectDateFragment();
//                newFragment.show(getFragmentManager(), "DatePicker");
//
//            }
//        });
        Button colorbtn = (Button) view.findViewById(R.id.colorbtn);
        colorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();

            }
        });

//       LinearLayout classStartTimebtn = (LinearLayout) view.findViewById(R.id.classStartTimebtn);
//        classStartTimebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogFragment newFragment = new SelectTimeFragement();
//                newFragment.show(getFragmentManager(), "TimePicker");
//
//            }
//        });
//        LinearLayout classEndTimebtn = (LinearLayout) view.findViewById(R.id.classEndTimebtn);
//        classEndTimebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogFragment newFragment = new SelectTimeFragement();
//                newFragment.show(getFragmentManager(), "TimePicker");
//
//            }
//        });

        Spinner spinner = (Spinner) view.findViewById(R.id.subject_spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),R.array.subjects_array,android.R.layout.simple_spinner_item);
        String[] plants = new String[]{
                "Subject",
                "California sycamore",
                "Mountain mahogany",
                "Butterfly weed",
                "Carrot weed"
        };

        final List<String> plantsList = new ArrayList<>(Arrays.asList(plants));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.parseColor("#CD5C5C"));
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };


        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(adapter);

        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayoutClass);
        tabLayout.addTab(tabLayout.newTab().setText("Weekly"));
        tabLayout.addTab(tabLayout.newTab().setText("Date"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.add_class_pager);
        PgAdapter pgadapter = new PgAdapter(getChildFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pgadapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }

    public void openColorPicker() {
        int colors [] = new int []{
                Color.parseColor("#CD5C5C"),
                Color.parseColor("#F08080"),
                Color.parseColor("#FA8072"),
                Color.parseColor("#E9967A"),
                Color.parseColor("#FFA07A"),
                Color.parseColor("#177181"),
                Color.parseColor("#009091"),
                Color.parseColor("#2DAF93"),
                Color.parseColor("#6ACB89"),
                Color.parseColor("#ADE47B"),
                Color.parseColor("#F9F871"),
                Color.parseColor("#297598"),
                Color.parseColor("#5275A8"),
                Color.parseColor("#7E72AC"),
                Color.parseColor("#A76DA3"),
                Color.parseColor("#C56B8E"),
                Color.parseColor("#BFFAFF"),
                Color.parseColor("#FC8F3A"),
                Color.parseColor("#009F7E"),
                Color.parseColor("#8CF8B8"),
                Color.parseColor("#52BF83"),
                Color.parseColor("#028951"),
                Color.parseColor("#00A8D3"),
                Color.parseColor("#A04B6B"),
                Color.parseColor("#4ACBB1"),
                Color.parseColor("#1A9E9F"),
                Color.parseColor("#005B6A"),
                Color.parseColor("#001621"),
                Color.parseColor("#84D1E2"),
                Color.parseColor("#84D1E2"),
                Color.parseColor("#FF6F91"),
                Color.parseColor("#FF6F91"),
                Color.parseColor("#F9F871"),
                Color.parseColor("#2C73D2"),
                Color.parseColor("#0081CF"),
                Color.parseColor("#C34A36"),
                Color.parseColor("#FF8066"),
                Color.parseColor("#F3C5FF"),
                Color.parseColor("#845EC2"),
                Color.parseColor("#A178DF"),
                Color.parseColor("#BE93FD"),
                Color.parseColor("#DCB0FF"),
                Color.parseColor("#FACCFF"),
                Color.parseColor("#FF9671"),
                Color.parseColor("#D3704D"),
                Color.parseColor("#A74B2B"),
                Color.parseColor("#7D270B"),
                Color.parseColor("#550000"),
                Color.parseColor("#F01E1E"),
                Color.parseColor("#CE0004"),
                Color.parseColor("#AC0000"),
                Color.parseColor("#8C0000"),
                Color.parseColor("#6E0000"),
                Color.parseColor("#F01E1E"),
                Color.parseColor("#FFA281"),
                Color.parseColor("#F32320"),
                Color.parseColor("#BE0000"),
//                Color.parseColor(""),
//                Color.parseColor(""),
//                Color.parseColor(""),
//                Color.parseColor(""),
//                Color.parseColor(""),
//                Color.parseColor(""),s
//                Color.parseColor(""),

        };
        new ColorPickerDialog()
                .withPresets(colors)

                // the default / initial color
                .withListener(new OnColorPickedListener<ColorPickerDialog>() {
                    @Override
                    public void onColorPicked(@Nullable ColorPickerDialog dialog, int color) {
                        // a color has been picked; use it
                    }
                })
                .show(getChildFragmentManager(), "colorPicker");

    }


}
class PgAdapter extends FragmentStatePagerAdapter {

    int noOfTabs;

    public PgAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.noOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;


        if (position == 0) {
            fragment = new ClassDateFragment();
        }
        if (position == 1) {
            fragment = new AllFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
