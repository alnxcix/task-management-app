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

public class NewTaskActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        // get the intent from the previous activity
        Task task = (Task) getIntent().getSerializableExtra("task");

        // Initialize UI elements
        final TextInputLayout textInputLayoutLabel = findViewById(R.id.textInputLayoutLabel);
        final TextInputLayout textInputLayoutDescription = findViewById(R.id.textInputLayoutDescription);
        final TextInputLayout textInputLayoutDate = findViewById(R.id.textInputLayoutDate);
        final TextInputLayout textInputLayoutTime = findViewById(R.id.textInputLayoutTime);

        // Initialize label and description fields
        assert task != null;
        Objects.requireNonNull(textInputLayoutLabel.getEditText()).setText(task.getmLabel());
        Objects.requireNonNull(textInputLayoutDescription.getEditText()).setText(task.getmDescription());

        // Initialize calendar for pickers
        Calendar cal = Calendar.getInstance();
        cal.setTime(task.getmDue());

        // Add event listeners
        textInputLayoutDate.setEndIconOnClickListener(v -> new DatePickerDialog(NewTaskActivity.this,
                (view, year, month, dayOfMonth) -> {
                    cal.set(year, month, dayOfMonth);
                    Objects.requireNonNull(textInputLayoutDate.getEditText()).setText(DateFormat.format("MM/dd/yyyy", cal));
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show());

        textInputLayoutTime.setEndIconOnClickListener(v -> new TimePickerDialog(NewTaskActivity.this,
                (view, hourOfDay, minute) -> {
                    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                    Objects.requireNonNull(textInputLayoutTime.getEditText()).setText(DateFormat.format("hh:mm aa", cal));
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show());

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            Intent replyIntent = new Intent();
            if (Objects.requireNonNull(textInputLayoutLabel.getEditText()).getText().toString().isEmpty()) {
                // setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(this, "Fill up all necessary fields.", Toast.LENGTH_SHORT).show();
            } else {
                task.setmLabel(textInputLayoutLabel.getEditText().getText().toString());
                task.setmDescription(textInputLayoutDescription.getEditText().getText().toString());
                task.setmDue(cal.getTime());

                replyIntent.putExtra(EXTRA_REPLY, task);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }
}