package com.example.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.timetable.R;

public class DashboardFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard,null);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        FragmentManager fragmentManager = getChildFragmentManager();
        viewPager.setAdapter(new MyAdapter(fragmentManager));
        return view;
    }
}
class MyAdapter extends FragmentStatePagerAdapter {

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;


        if(position == 0){
            fragment = new AllFragment();
        }
        if(position == 1){
            fragment = new AllFragment();
        }
        if(position == 2){
            fragment = new AllFragment();
        }
        if(position == 3){
            fragment = new AllFragment();
        }
        if(position == 4){
            fragment = new AllFragment();
        }
        if(position == 5){
            fragment = new AllFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 6;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if(position ==0)
        {
            return "All";
        }
        if (position==1)
        {
            return "Class";
        }
        if (position==2)
        {
            return "Homework";
        }
        if (position==3)
        {
            return "Exam";
        }
        if (position==4)
        {
            return "Study";
        }
        if (position==5)
        {
            return "Goals";
        }
        return null;
    }
}


