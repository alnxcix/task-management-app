package com.nidoy.taskmanagementapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Objects;

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Task sched = (Task) getIntent().getSerializableExtra("sched");

        TextInputLayout txtInputActivity = findViewById(R.id.txtInputActivity);
        TextInputLayout txtInputVenue = findViewById(R.id.txtInputVenue);
        TextInputLayout txtInputDate = findViewById(R.id.txtInputDate);
        TextInputLayout txtInputTimeFrom = findViewById(R.id.txtInputTimeFrom);
        TextInputLayout txtInputTimeTo = findViewById(R.id.txtInputTimeTo);

        assert sched != null;

        Objects.requireNonNull(txtInputActivity.getEditText()).setText(sched.getmLabel());
        Objects.requireNonNull(txtInputVenue.getEditText()).setText(sched.getmNotes());
        Objects.requireNonNull(txtInputDate.getEditText()).setText(Utils.dateFormat.format(sched.getmDue()));
        Objects.requireNonNull(txtInputTimeFrom.getEditText()).setText(Utils.timeFormat.format(sched.getmDue()));
        Objects.requireNonNull(txtInputTimeTo.getEditText()).setText(Utils.timeFormat.format(sched.getmDue()));

        Calendar cal = Calendar.getInstance();
        cal.setTime(sched.getmDue());

        txtInputDate.setEndIconOnClickListener(v -> new DatePickerDialog(ScheduleActivity.this,
                (view, year, month, dayOfMonth) -> {
                    cal.set(year, month, dayOfMonth);
                    Objects.requireNonNull(txtInputDate.getEditText()).setText(DateFormat.format("MM/dd/yyyy", cal));
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show());

        txtInputTimeFrom.setEndIconOnClickListener(v -> new TimePickerDialog(ScheduleActivity.this,
                (view, hourOfDay, minute) -> {
                    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                    Objects.requireNonNull(txtInputTimeFrom.getEditText()).setText(DateFormat.format("hh:mm aa", cal));
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show());
        txtInputTimeTo.setEndIconOnClickListener(v -> new TimePickerDialog(ScheduleActivity.this,
                (view, hourOfDay, minute) -> {
                    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                    Objects.requireNonNull(txtInputTimeTo.getEditText()).setText(DateFormat.format("hh:mm aa", cal));
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show());
    }
}