package com.nidoy.taskmanagementapp;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;
import java.util.Date;

public class ClassFormActivity extends AppCompatActivity {

    // Material pickers + builders
    private MaterialTimePicker materialTimePicker1;
    private MaterialTimePicker.Builder builder1;
    private MaterialTimePicker materialTimePicker2;
    private MaterialTimePicker.Builder builder2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_form);

        // Initialize UI elements
        EditText editTextCourse = ((TextInputLayout) findViewById(R.id.txtInputCourse)).getEditText();
        EditText editTextInstructors = ((TextInputLayout) findViewById(R.id.txtInputInstructors)).getEditText();
        EditText editTextVenue = ((TextInputLayout) findViewById(R.id.txtInputVenue)).getEditText();
        EditText editTextDay = ((TextInputLayout) findViewById(R.id.txtInputDay)).getEditText();
        EditText editTextStartTime = ((TextInputLayout) findViewById(R.id.txtInputStartTime)).getEditText();
        EditText editTextEndTime = ((TextInputLayout) findViewById(R.id.txtInputEndTime)).getEditText();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // For day picker [Monday - Sunday (?)], spinner can be implemented
        editTextStartTime.setOnClickListener(v1 -> {
            builder1 = new MaterialTimePicker.Builder();
            builder1.setTimeFormat(TimeFormat.CLOCK_12H);
            builder1.setTitleText("Select time");
            builder1.setHour(cal.get(Calendar.HOUR_OF_DAY));
            builder1.setMinute(cal.get(Calendar.MINUTE));
            materialTimePicker1 = builder1.build();
            materialTimePicker1.addOnPositiveButtonClickListener(v2 -> {
                cal.set(Calendar.HOUR_OF_DAY, materialTimePicker1.getHour());
                cal.set(Calendar.MINUTE, materialTimePicker1.getMinute());
                editTextStartTime.setText(DateFormat.format("hh:mm aa", cal));
            });
            materialTimePicker1.show(getSupportFragmentManager(), "TIME_PICKER");
        });
        editTextEndTime.setOnClickListener(v1 -> {
            builder2 = new MaterialTimePicker.Builder();
            builder2.setTimeFormat(TimeFormat.CLOCK_12H);
            builder2.setTitleText("Select time");
            builder2.setHour(cal.get(Calendar.HOUR_OF_DAY));
            builder2.setMinute(cal.get(Calendar.MINUTE));
            materialTimePicker2 = builder2.build();
            materialTimePicker2.addOnPositiveButtonClickListener(v2 -> {
                cal.set(Calendar.HOUR_OF_DAY, materialTimePicker2.getHour());
                cal.set(Calendar.MINUTE, materialTimePicker2.getMinute());
                editTextEndTime.setText(DateFormat.format("hh:mm aa", cal));
            });
            materialTimePicker2.show(getSupportFragmentManager(), "TIME_PICKER");
        });
    }
}