package com.nidoy.taskmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Objects;

public class NewTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        // Initialize UI elements
        final TextInputLayout textInputLayoutLabel = findViewById(R.id.textInputLayoutLabel);
        final TextInputLayout textInputLayoutDescription = findViewById(R.id.textInputLayoutDescription);
        final TextInputLayout textInputLayoutDate = findViewById(R.id.textInputLayoutDate);
        final TextInputLayout textInputLayoutTime = findViewById(R.id.textInputLayoutTime);

        // Initialize calendar for pickers
        final Calendar cal = Calendar.getInstance();

        // Add event listeners
        textInputLayoutDate.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                cal.set(year, month, dayOfMonth);
                                Objects.requireNonNull(textInputLayoutDate.getEditText()).setText(DateFormat.format("MM/dd/yyyy", cal));
                            }
                        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        textInputLayoutTime.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(NewTaskActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                                Objects.requireNonNull(textInputLayoutTime.getEditText()).setText(DateFormat.format("hh:mm aa", cal));
                            }
                        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show();
            }
        });

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Temporary
                Toast.makeText(NewTaskActivity.this, "Label: " + !Objects.requireNonNull(textInputLayoutLabel.getEditText()).getText().toString().isEmpty() + ", Description: " + !textInputLayoutDescription.getEditText().getText().toString().isEmpty() + ", Date: " + !textInputLayoutDate.getEditText().getText().toString().isEmpty() + ", Time: " + !textInputLayoutTime.getEditText().getText().toString().isEmpty(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}