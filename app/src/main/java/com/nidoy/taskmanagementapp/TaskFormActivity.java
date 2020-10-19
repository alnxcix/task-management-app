package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@SuppressLint("SimpleDateFormat")
public class TaskFormActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "REPLY";

    // Material pickers + builders
    private MaterialDatePicker<Long> materialDatePicker;
    private MaterialDatePicker.Builder<Long> builder1;
    private MaterialTimePicker materialTimePicker;
    private MaterialTimePicker.Builder builder2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form);
        // Initialize task
        Task task = getIntent().getSerializableExtra("task") == null ? new Task() : (Task) getIntent().getSerializableExtra("task");
        // Initialize UI elements
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        EditText editTextLabel = ((TextInputLayout) findViewById(R.id.txtInputLabel)).getEditText();
        EditText editTextDueDate = ((TextInputLayout) findViewById(R.id.txtInputDueDate)).getEditText();
        EditText editTextTime = ((TextInputLayout) findViewById(R.id.txtInputTime)).getEditText();
        EditText editTextNotes = ((TextInputLayout) findViewById(R.id.txtInputNotes)).getEditText();
        ChipGroup chipGroup = findViewById(R.id.chipGroup);
        SpectrumPalette spectrumPalette = findViewById(R.id.spectrumPalette);
        // Set app bar
        topAppBar.setTitle(Objects.requireNonNull(task).getLabel() == null ? getString(R.string.new_task) : task.getLabel());
        // Set fields
        editTextLabel.setText(task.getLabel());
        editTextNotes.setText(task.getNotes());
        editTextDueDate.setText(task.getDue() == null ? null : DateFormat.format("MMM. dd, yyyy", task.getDue()));
        editTextTime.setText(task.getDue() == null ? null : DateFormat.format("hh:mm aa", task.getDue()));
        // Set calendar for date and time pickers
        Calendar cal = Calendar.getInstance();
        if (task.getDue() == null) {
            cal.setTime(new Date());
            cal.set(Calendar.HOUR, 12);
            cal.set(Calendar.MINUTE, 0);
        } else cal.setTime(task.getDue());
        // Set color picker
        spectrumPalette.setOnColorSelectedListener(color -> {
            task.setThemeId(color);
            topAppBar.setBackgroundColor(color);
            topAppBar.setTitleTextColor(color == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE);
            ((MenuItem) findViewById(R.id.menuSave)).getIcon().setColorFilter(new PorterDuffColorFilter(color == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE, PorterDuff.Mode.SRC_IN));
        });
        spectrumPalette.setSelectedColor(task.getThemeId() == -1 ? getResources().getIntArray(R.array.color_picker)[0] : task.getThemeId());
        // Add event listeners
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.chipOpen:
                    task.setStatusId(R.string.open);
                    break;
                case R.id.chipPending:
                    task.setStatusId(R.string.pending);
                    break;
                case R.id.chipFinished:
                    task.setStatusId(R.string.finished);
                    break;
            }
        });
        chipGroup.check(task.getStatusId() == R.string.open ? R.id.chipOpen : task.getStatusId() == R.string.pending ? R.id.chipPending : task.getStatusId() == R.string.finished ? R.id.chipFinished : R.id.chipOpen);
        editTextDueDate.setOnClickListener(v -> {
            builder1 = MaterialDatePicker.Builder.datePicker();
            builder1.setTitleText("Select date");
            builder1.setSelection(cal.getTimeInMillis());
            materialDatePicker = builder1.build();
            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                final Calendar c = Calendar.getInstance();
                c.setTimeInMillis(selection);
                cal.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                editTextDueDate.setText(DateFormat.format("MM/dd/yyyy", cal));
            });
            materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
        });
        editTextTime.setOnClickListener(v1 -> {
            builder2 = new MaterialTimePicker.Builder();
            builder2.setTimeFormat(TimeFormat.CLOCK_12H);
            builder2.setTitleText("Select time");
            builder2.setHour(cal.get(Calendar.HOUR_OF_DAY));
            builder2.setMinute(cal.get(Calendar.MINUTE));
            materialTimePicker = builder2.build();
            materialTimePicker.addOnPositiveButtonClickListener(v2 -> {
                cal.set(Calendar.HOUR_OF_DAY, materialTimePicker.getHour());
                cal.set(Calendar.MINUTE, materialTimePicker.getMinute());
                editTextTime.setText(DateFormat.format("hh:mm aa", cal));
            });
            materialTimePicker.show(getSupportFragmentManager(), "TIME_PICKER");
        });
        findViewById(R.id.menuSave).setOnClickListener(v -> {
            if (editTextLabel.getText().toString().isEmpty() || editTextDueDate.getText().toString().isEmpty() || editTextTime.getText().toString().isEmpty())
                Snackbar.make(findViewById(R.id.parent), getString(R.string.snack_fill_up), Snackbar.LENGTH_SHORT).show();
            else {
                task.setLabel(editTextLabel.getText().toString());
                task.setNotes(editTextNotes.getText().toString());
                task.setDue(cal.getTime());
                setResult(RESULT_OK, new Intent().putExtra(EXTRA_REPLY, task));
                finish();
            }
        });
    }
}