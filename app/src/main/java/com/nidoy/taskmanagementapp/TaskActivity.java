package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class TaskActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "REPLY";

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // Get intent from previous activity
        Task task = (Task) getIntent().getSerializableExtra("task");

        // Initialize UI elements
        TextInputLayout txtInputLabel = findViewById(R.id.txtInputLabel);
        TextInputLayout txtInputNotes = findViewById(R.id.txtInputNotes);
        TextInputLayout txtInputDate = findViewById(R.id.txtInputDate);
        TextInputLayout txtInputTime = findViewById(R.id.txtInputTime);

        // Set fields
        assert task != null;
        Objects.requireNonNull(txtInputLabel.getEditText()).setText(task.getmLabel());
        Objects.requireNonNull(txtInputNotes.getEditText()).setText(task.getmNotes());
        Objects.requireNonNull(txtInputDate.getEditText()).setText(new SimpleDateFormat("MMM. dd, yyyy").format(task.getmDue()));
        Objects.requireNonNull(txtInputTime.getEditText()).setText(new SimpleDateFormat("hh:mm aa").format(task.getmDue()));

        // Set the chip group
        ((Chip) findViewById(R.id.chipOpen)).setChecked(task.getmStatus().equals("Open"));
        ((Chip) findViewById(R.id.chipPending)).setChecked(task.getmStatus().equals("Pending"));
        ((Chip) findViewById(R.id.chipFinished)).setChecked(task.getmStatus().equals("Finished"));

        // Set calendar for pickers
        Calendar cal = Calendar.getInstance();
        cal.setTime(task.getmDue());

        // Add event listeners
        txtInputDate.setEndIconOnClickListener(v -> new DatePickerDialog(TaskActivity.this,
                (view, year, month, dayOfMonth) -> {
                    cal.set(year, month, dayOfMonth);
                    Objects.requireNonNull(txtInputDate.getEditText()).setText(DateFormat.format("MM/dd/yyyy", cal));
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show());

        txtInputTime.setEndIconOnClickListener(v -> new TimePickerDialog(TaskActivity.this,
                (view, hourOfDay, minute) -> {
                    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                    Objects.requireNonNull(txtInputTime.getEditText()).setText(DateFormat.format("hh:mm aa", cal));
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show());

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            if (Objects.requireNonNull(txtInputLabel.getEditText()).getText().toString().isEmpty())
                Snackbar.make(findViewById(R.id.parent), getString(R.string.snack_fill_up), Snackbar.LENGTH_SHORT).show();
            else {
                task.setmLabel(txtInputLabel.getEditText().getText().toString());
                task.setmNotes(txtInputNotes.getEditText().getText().toString());
                task.setmDue(cal.getTime());
                task.setmStatus((String) ((Chip) findViewById(((ChipGroup) findViewById(R.id.chipGroup)).getCheckedChipId())).getText());

                setResult(RESULT_OK, new Intent().putExtra(EXTRA_REPLY, task));
                finish();
            }
        });
    }
}