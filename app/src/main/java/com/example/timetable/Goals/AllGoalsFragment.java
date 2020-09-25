package com.example.timetable.Goals;

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

import com.example.timetable.Course.CourseRecyclerViewAdapter;
import com.example.timetable.Course.DisplayCourseFragment;
import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllGoalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllGoalsFragment extends Fragment {

    DBHandler db;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity().getApplicationContext());



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_all_goals,null);

        ImageView addBtn = (ImageView) view.findViewById(R.id.addIcon);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createevents fragment = new createevents();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });

            final TabLayout tabLayout1 = (TabLayout) view.findViewById(R.id.goalsTab);
            tabLayout1.addTab(tabLayout1.newTab().setText("Upcoming "));
            tabLayout1.addTab(tabLayout1.newTab().setText("Completed"));
            tabLayout1.setTabGravity(TabLayout.GRAVITY_CENTER);
            tabLayout1.setTabGravity(TabLayout.GRAVITY_FILL);

            final ViewPager viewPager1 = (ViewPager) view.findViewById(R.id.goals_pager);
            GoalsAdapter adapter = new GoalsAdapter(getChildFragmentManager(),tabLayout1.getTabCount());
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

        final ArrayList<String> goals = new ArrayList<>();
        final ArrayList<String> description = new ArrayList<>();
        final ArrayList<Integer> colours = new ArrayList<>();
        final ArrayList<Integer> ids = new ArrayList<>();
        final ArrayList<String> due = new ArrayList<>();
        final Cursor c = db.getAllGoals();

        while (c.moveToNext()){
            ids.add(c.getInt(0));
            goals.add(c.getString(1));
            colours.add(c.getInt(2));
            due.add(c.getString(3));
            description.add(c.getString(4));
        }

        RecyclerView recyclerView = view.findViewById(R.id.course_recyler_view);
        final GoalRecyclerView adapter1 = new GoalRecyclerView(ids,goals,colours,due,description,getActivity());
        recyclerView.setAdapter(adapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemTouchHelper(adapter1,ids,recyclerView,getActivity().getApplicationContext());

            return view;

        }
    public void itemTouchHelper(final CourseRecyclerViewAdapter adapter, final ArrayList<Integer> ids, final RecyclerView recyclerView, final Context cont) {

        final ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter

                db.deleteCourse(String.valueOf(ids.get(viewHolder.getAdapterPosition())));
                adapter.removeItem(viewHolder.getAdapterPosition());
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (viewHolder != null) {
                    final View foregroundView = ((CourseRecyclerViewAdapter.ViewHolder) viewHolder).courseCard;
                    getDefaultUIUtil().onSelected(foregroundView);
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                final View foregroundView = ((CourseRecyclerViewAdapter.ViewHolder) viewHolder).courseCard;
                getDefaultUIUtil().clearView(foregroundView);
            }

            @Override
            public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                final View foregroundView = ((CourseRecyclerViewAdapter.ViewHolder) viewHolder).courseCard;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // view the background view
                final View foregroundView = ((CourseRecyclerViewAdapter.ViewHolder) viewHolder).courseCard;
                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
            }

            @Override
            public int convertToAbsoluteDirection(int flags, int layoutDirection) {
                return super.convertToAbsoluteDirection(flags, layoutDirection);
            }
        };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }
}
class GoalsAdapter extends FragmentStatePagerAdapter {

    int noOfTabs;

    public GoalsAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.noOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;


        if (position == 0) {
            fragment = new upcoming_goals_fragment();
        }
        if (position == 1) {
            fragment = new GoalsCompletedFragment();
        }


        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }


}