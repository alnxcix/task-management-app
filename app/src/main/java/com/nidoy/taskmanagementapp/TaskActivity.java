package com.nidoy.taskmanagementapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Objects;

public class TaskActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // Get intent from previous activity
        Task task = (Task) getIntent().getSerializableExtra("task");

        // Initialize UI elements
        TextInputLayout textInputLayoutLabel = findViewById(R.id.textInputLayoutLabel);
        TextInputLayout textInputLayoutDescription = findViewById(R.id.textInputLayoutDescription);
        TextInputLayout textInputLayoutDate = findViewById(R.id.textInputLayoutDate);
        TextInputLayout textInputLayoutTime = findViewById(R.id.textInputLayoutTime);

        // Set fields
        assert task != null;
        Objects.requireNonNull(textInputLayoutLabel.getEditText()).setText(task.getmLabel());
        Objects.requireNonNull(textInputLayoutDescription.getEditText()).setText(task.getmDescription());
        Objects.requireNonNull(textInputLayoutDate.getEditText()).setText(Utils.dateFormat.format(task.getmDue()));
        Objects.requireNonNull(textInputLayoutTime.getEditText()).setText(Utils.timeFormat.format(task.getmDue()));

        // Set calendar for pickers
        Calendar cal = Calendar.getInstance();
        cal.setTime(task.getmDue());

        // Set the chip group
        ((Chip) findViewById(R.id.chipOpen)).setChecked(task.getmStatus().equals("Open"));
        ((Chip) findViewById(R.id.chipPending)).setChecked(task.getmStatus().equals("Pending"));
        ((Chip) findViewById(R.id.chipFinished)).setChecked(task.getmStatus().equals("Finished"));

        // Add event listeners
        textInputLayoutDate.setEndIconOnClickListener(v -> new DatePickerDialog(TaskActivity.this,
                (view, year, month, dayOfMonth) -> {
                    cal.set(year, month, dayOfMonth);
                    Objects.requireNonNull(textInputLayoutDate.getEditText()).setText(DateFormat.format("MM/dd/yyyy", cal));
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show());

        textInputLayoutTime.setEndIconOnClickListener(v -> new TimePickerDialog(TaskActivity.this,
                (view, hourOfDay, minute) -> {
                    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                    Objects.requireNonNull(textInputLayoutTime.getEditText()).setText(DateFormat.format("hh:mm aa", cal));
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show());

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            if (Objects.requireNonNull(textInputLayoutLabel.getEditText()).getText().toString().isEmpty())
                Toast.makeText(this, "Fill up all necessary fields.", Toast.LENGTH_SHORT).show();
            else {
                task.setmLabel(textInputLayoutLabel.getEditText().getText().toString());
                task.setmStatus((String) ((Chip) findViewById(((ChipGroup) findViewById(R.id.chipGroup)).getCheckedChipId())).getText());
                task.setmDescription(textInputLayoutDescription.getEditText().getText().toString());
                task.setmDue(cal.getTime());
                finish();
            }
        });
    }
}