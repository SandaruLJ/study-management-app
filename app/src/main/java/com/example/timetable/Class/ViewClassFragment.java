package com.example.timetable.Class;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.timetable.Class.ClassRecyclerViewAdapter;
import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewClassFragment extends Fragment{

    DBHandler db;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_class,null);
//
//        final ArrayList<String> className = new ArrayList<>();
//        final ArrayList<String> teacher = new ArrayList<>();
//        final ArrayList<String> venue = new ArrayList<>();
//        final ArrayList<String> sTime = new ArrayList<>();
//        final ArrayList<String> eTime = new ArrayList<>();
//        final ArrayList<Integer> ids = new ArrayList<>();
//        final ArrayList<Integer> color = new ArrayList<>();
//        final Cursor c = db.getAllClass();
//
//        while (c.moveToNext()){
//            ids.add(c.getInt(0));
//            className.add(c.getString(1));
//            teacher.add(c.getString(5));
//            venue.add(c.getString(6));
//            sTime.add(c.getString(11));
//            eTime.add(c.getString(12));
//            color.add(c.getInt(8));
//
//        }


        ImageView addc = (ImageView) view.findViewById(R.id.addIcon2);
        addc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClass fragment = new addClass();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });
        final TabLayout tabLayout1 = (TabLayout) view.findViewById(R.id.tabLayoutWeek);
        tabLayout1.addTab(tabLayout1.newTab().setText("Week"));
        tabLayout1.addTab(tabLayout1.newTab().setText("List"));
        tabLayout1.addTab(tabLayout1.newTab().setText("Calendar"));

        tabLayout1.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout1.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager1 = (ViewPager) view.findViewById(R.id.class_pager_week);
        ClassList adapter = new ClassList(getChildFragmentManager(),tabLayout1.getTabCount());
        viewPager1.setAdapter(adapter);
        viewPager1.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout1));
        tabLayout1.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager1.setCurrentItem(tab.getPosition());
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
class ClassList extends FragmentStatePagerAdapter {

    int noOfTabs;

    public ClassList(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.noOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;


        if (position == 0) {
            fragment = new ClassFragment();
        }
        if (position == 1) {
            fragment = new ClassListFragment();
        }
        if (position == 2) {
            fragment = new ClassCalendarFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}