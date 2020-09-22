package com.example.timetable.Study;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.timetable.Database.DBHandler;
import com.example.timetable.R;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyTimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyTimerFragment extends Fragment {

    private Long time;
    private String hh, mm, ss;
    private CountDownTimer timer;
    private TextView countdownText, studyTitle, subjectName;
    private Integer studyId;
    private DBHandler db;

    public StudyTimerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_study_timer, container, false);

        // Initialise dynamic text views
        countdownText = view.findViewById(R.id.countdown_text);
        studyTitle = view.findViewById(R.id.study_title);
        subjectName = view.findViewById(R.id.subject_name);

        // Initialise interactive buttons
        final Button startBtn = view.findViewById(R.id.start);
        final Button pauseBtn = view.findViewById(R.id.pause);
        final Button resetBtn = view.findViewById(R.id.reset);


        // Set Time Button
        final EditText timeInput = view.findViewById(R.id.time);
        final Button setTimeBtn = view.findViewById(R.id.set_time);

        setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                // Check if time input is empty
                if (!timeInput.getText().toString().equals("")) {
                    time = TimeUnit.MINUTES.toMillis(Long.parseLong(timeInput.getText().toString()));

                    ss = "00";
                    mm = String.format(Locale.US, "%02d", (time / (1000 * 60)) % 60);
                    hh = String.format(Locale.US, "%02d", (time / (1000 * 60 * 60)) % 24);

                    countdownText.setText(hh + ":" + mm + ":" + ss);

                    // Enable Start Timer Button if set time is greater than 0
                    if (time > 0)
                        startBtn.setEnabled(true);
                    else
                        startBtn.setEnabled(false);

                    countdownText.setTextColor(Color.BLACK);  // Set timer text colour to black
                    countdownText.clearAnimation();  // Stop blinking animation (when the timer has run out)
                }
            }
        });


        // Set study related values
        studyId = 0;
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            studyId = Integer.parseInt(bundle.get("studyId").toString());
        }

        if (studyId != 0) {  // Set study values only if study id is set
            Cursor study = db.getSingleStudy(studyId);

            while(study.moveToNext()) {
                String subjectNameString = null;

                Cursor subject = db.getSingleSubject(study.getInt(2));
                if (subject.moveToNext())
                    subjectNameString = subject.getString(1);

                studyTitle.setText(study.getString(1));
                subjectName.setText(subjectNameString);

                // Get time of study for timer
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime startTime = LocalTime.parse(study.getString(5), formatter);
                LocalTime endTime = LocalTime.parse(study.getString(6), formatter);

                time = Duration.between(startTime, endTime).toMinutes();  // Calculate study time in minutes

                if (time < 0)  // If duration is negative, adjust for a time overlapping two days
                    time += 1440L;

                timeInput.setText(String.format(Locale.US, "%d", time));  // Set calculated study time
                setTimeBtn.performClick();  // Emulate Set Time button click to set timer text
            }
        }
        else {
            studyTitle.setText("");
            subjectName.setText("");
        }


        // Start Timer Button
        if (studyId == 0)
            startBtn.setEnabled(false);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start Timer
                timer = new CountDownTimer(time, 1000) {
                    @Override
                    public void onTick(long l) {
                        time = l;  // Save remaining time

                        ss = String.format(Locale.US, "%02d", (time / 1000) % 60);
                        mm = String.format(Locale.US, "%02d", (time / (1000*60)) % 60);
                        hh = String.format(Locale.US, "%02d", (time / (1000*60*60)) % 24);

                        countdownText.setText(hh + ":" + mm + ":" + ss);
                    }

                    @Override
                    public void onFinish() {
                        countdownText.setTextColor(Color.RED);  // Set timer text color to red

                        // Create and start blinking animation
                        Animation blink = new AlphaAnimation(0.0f, 1.0f);
                        blink.setDuration(500);
                        blink.setRepeatMode(Animation.REVERSE);
                        blink.setRepeatCount(Animation.INFINITE);
                        countdownText.startAnimation(blink);

                        pauseBtn.setEnabled(false);
                        setTimeBtn.setEnabled(true);
                    }
                }.start();

                // Disable Set Time Button and Start Timer Button
                setTimeBtn.setEnabled(false);
                startBtn.setEnabled(false);
                // Enable Pause Timer Button and Reset Timer Button
                pauseBtn.setEnabled(true);
                resetBtn.setEnabled(true);
            }
        });


        // Pause Timer Button
        pauseBtn.setEnabled(false);

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pauseBtn.getText().toString().equalsIgnoreCase("pause")) {
                    timer.cancel();  // Pause timer
                    pauseBtn.setText(R.string.resume);  // Change Pause to Resume
                }
                else {
                    startBtn.performClick();  // Emulate start button click to resume the timer
                    pauseBtn.setText(R.string.pause);
                }
            }
        });


        // Reset Timer Button
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null)
                    timer.cancel();  // Cancel timer

                time = 0L;  // Reset time
                countdownText.setText(R.string.timer_time);  // Reset timer text
                setTimeBtn.setEnabled(true);  // Enable Set Time Button
                pauseBtn.setText(R.string.pause);  // Reset Pause Timer Button text
                pauseBtn.setEnabled(false);  // Disable Pause Timer Button
                startBtn.setEnabled(false);  // Disable Start Timer Button
                countdownText.setTextColor(Color.BLACK);  // Set timer text colour to black
                countdownText.clearAnimation();  // Stop blinking animation (when the timer has run out)
            }
        });


        return view;
    }
}