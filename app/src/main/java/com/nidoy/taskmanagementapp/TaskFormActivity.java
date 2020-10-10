package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@SuppressLint("SimpleDateFormat")
public class TaskFormActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form);

        // Get intent from previous activity
        Task task = getIntent().getSerializableExtra("task") == null ? new Task() : (Task) getIntent().getSerializableExtra("task");

        // Initialize UI elements
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        TextInputLayout txtInputLabel = findViewById(R.id.txtInputLabel);
        TextInputLayout txtInputDate = findViewById(R.id.txtInputDueDate);
        TextInputLayout txtInputTime = findViewById(R.id.txtInputTime);
        TextInputLayout txtInputNotes = findViewById(R.id.txtInputNotes);
        ChipGroup chipGroup = findViewById(R.id.chipGroup);
        SpectrumPalette spectrumPalette = findViewById(R.id.spectrumPalette);

        // Set fields
        topAppBar.setTitle(Objects.requireNonNull(task).getLabel() == null ? getString(R.string.new_task) : task.getLabel());
        Objects.requireNonNull(txtInputLabel.getEditText()).setText(task.getLabel());
        Objects.requireNonNull(txtInputNotes.getEditText()).setText(task.getNotes());
        Objects.requireNonNull(txtInputDate.getEditText()).setText(task.getDue() == null ? null : new SimpleDateFormat("MMM. dd, yyyy").format(task.getDue()));
        Objects.requireNonNull(txtInputTime.getEditText()).setText(task.getDue() == null ? null : new SimpleDateFormat("hh:mm aa").format(task.getDue()));

        // Set calendar for date and time pickers
        Calendar cal = Calendar.getInstance();
        if (task.getDue() == null) {
            cal.setTime(new Date());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
        } else cal.setTime(task.getDue());

        // Set color picker
        spectrumPalette.setOnColorSelectedListener(task::setLegendColor);
        spectrumPalette.setSelectedColor(task.getLegendColor() == -1 ? getResources().getIntArray(R.array.color_picker)[0] : task.getLegendColor());

        // Add event listeners
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.chipOpen:
                    task.setStatus(R.string.open);
                    break;
                case R.id.chipPending:
                    task.setStatus(R.string.pending);
                    break;
                case R.id.chipFinished:
                    task.setStatus(R.string.finished);
                    break;
            }
        });
        chipGroup.check(task.getStatus() == R.string.open ? R.id.chipOpen : task.getStatus() == R.string.pending ? R.id.chipPending : task.getStatus() == R.string.finished ? R.id.chipFinished : R.id.chipOpen);

        txtInputDate.getEditText().setOnClickListener(v -> new DatePickerDialog(TaskFormActivity.this,
                (view, year, month, dayOfMonth) -> {
                    cal.set(year, month, dayOfMonth);
                    Objects.requireNonNull(txtInputDate.getEditText()).setText(DateFormat.format("MM/dd/yyyy", cal));
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show());

        txtInputTime.getEditText().setOnClickListener(v -> new TimePickerDialog(TaskFormActivity.this,
                (view, hourOfDay, minute) -> {
                    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                    Objects.requireNonNull(txtInputTime.getEditText()).setText(DateFormat.format("hh:mm aa", cal));
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show());
        findViewById(R.id.save).setOnClickListener(v -> {
            if (Objects.requireNonNull(txtInputLabel.getEditText()).getText().toString().isEmpty() || txtInputDate.getEditText().getText().toString().isEmpty() || txtInputTime.getEditText().getText().toString().isEmpty())
                Snackbar.make(findViewById(R.id.parent), getString(R.string.snack_fill_up), Snackbar.LENGTH_SHORT).show();
            else {
                task.setLabel(txtInputLabel.getEditText().getText().toString());
                task.setNotes(txtInputNotes.getEditText().getText().toString());
                task.setDue(cal.getTime());

                setResult(RESULT_OK, new Intent().putExtra(EXTRA_REPLY, task));
                finish();
            }
        });
    }
}