package com.example.timetable.Class;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.example.timetable.ColorPicker;
import com.example.timetable.Course.DisplayCourseFragment;
import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addClass#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addClass extends Fragment  {

    Button colorbtn;
    DBHandler db ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_class, container, false);
//        boolean isInserted = db.addClass("Test1","scsd","dwd","scsc","cdsc","403","dcdcs",-24531,"Daily","Monday","05:30","08:30","01/01/2020","03/01/2020",5);
//        db.create();
//       if(isInserted == true) {
//            Toast.makeText(getActivity().getApplicationContext(), "Class Added Successfully", Toast.LENGTH_LONG).show();
//
//
//        }else
//        Toast.makeText(getActivity().getApplicationContext(),"Insert Failed, Try again",Toast.LENGTH_LONG).show();

//        LinearLayout pickDatebtn = (LinearLayout) view.findViewById(R.id.dateSelector);
//        pickDatebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogFragment newFragment = new SelectDateFragment();
//                newFragment.show(getFragmentManager(), "DatePicker");
//
//            }
//        });
        colorbtn = (Button) view.findViewById(R.id.colorbtn);
        colorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPicker cp = new ColorPicker() ;
//                cp.openColorPicker(getChildFragmentManager(),colorbtn);
            }
        });

//        colorbtn.setBackgroundColor(Color.parseColor("#ff0000"));

//        Spinner spinner = (Spinner) view.findViewById(R.id.subject_spinner);
////        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),R.array.subjects_array,android.R.layout.simple_spinner_item);
//        String[] plants = new String[]{
//                "Subject",
//                "California sycamore",
//                "Mountain mahogany",
//                "Butterfly weed",
//                "Carrot weed"
//        };
//
//        final List<String> plantsList = new ArrayList<>(Arrays.asList(plants));
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item,plantsList){
//            @Override
//            public boolean isEnabled(int position){
//                if(position == 0)
//                {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                }
//                else
//                {
//                    return true;
//                }
//            }
//            @Override
//            public View getDropDownView(int position, View convertView,
//                                        ViewGroup parent) {
//                View view = super.getDropDownView(position, convertView, parent);
//                TextView tv = (TextView) view;
//                if(position == 0){
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.parseColor("#CD5C5C"));
//                }
//                else {
//                    tv.setTextColor(Color.BLACK);
//                }
//                return view;
//            }
//
//        };


//        adapter.setDropDownViewResource(R.layout.spinner_item);
//        spinner.setAdapter(adapter);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItemText = (String) parent.getItemAtPosition(position);
//                // If user change the default selection
//                // First item is disable and it is used for hint
//                if(position > 0){
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        spinner.setAdapter(adapter);

        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayoutClass);
        tabLayout.addTab(tabLayout.newTab().setText("Weekly"));
        tabLayout.addTab(tabLayout.newTab().setText("Date"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

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
            fragment = new ClassWeekFragment();
        }
        if (position == 1) {
            fragment = new ClassDateFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
