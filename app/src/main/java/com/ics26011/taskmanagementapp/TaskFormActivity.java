package com.ics26011.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@SuppressLint("SimpleDateFormat")
public class TaskFormActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "REPLY";
    // Material pickers + builders
    private LocalDate localDate;
    private LocalTime localTime;
    private MaterialDatePicker<Long> materialDatePicker;
    private MaterialDatePicker.Builder<Long> builder1;
    private MaterialTimePicker materialTimePicker;
    private MaterialTimePicker.Builder builder2;

    @SuppressLint("NonConstantResourceId")
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
        editTextDueDate.setText(task.getDue() == null ? null : task.getDue().format(DateTimeFormatter.ofPattern("MMM. dd, yyyy")));
        editTextTime.setText(task.getDue() == null ? null : task.getDue().format(DateTimeFormatter.ofPattern("MMM. dd, yyyy")));
        // Set localDate and localTime for date and time pickers
        localDate = task.getDue() == null ? LocalDate.now() : task.getDue().toLocalDate();
        localTime = task.getDue() == null ? LocalTime.of(12, 0) : task.getDue().toLocalTime();
        // Set color picker
        spectrumPalette.setOnColorSelectedListener(color -> {
            task.setThemeId(color);
            topAppBar.setBackgroundColor(color);
            topAppBar.setTitleTextColor(color == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE);
            topAppBar.getMenu().findItem(R.id.menuSave).getIcon().setColorFilter(new PorterDuffColorFilter(color == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE, PorterDuff.Mode.SRC_IN));
        });
        spectrumPalette.setSelectedColor(task.getThemeId() == -1 ? getResources().getIntArray(R.array.color_picker)[0] : task.getThemeId());
        // Add event listeners
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.chipOpen:
                    task.setStatusId(0);
                    break;
                case R.id.chipPending:
                    task.setStatusId(1);
                    break;
                case R.id.chipFinished:
                    task.setStatusId(2);
                    task.setFinish(LocalDateTime.now());
                    break;
            }
        });
        chipGroup.check(task.getStatusId() == 0 ? R.id.chipOpen : task.getStatusId() == 1 ? R.id.chipPending : R.id.chipFinished);
        editTextDueDate.setOnClickListener(v -> {
            builder1 = MaterialDatePicker.Builder.datePicker();
            builder1.setTitleText("Select date");
            builder1.setSelection(task.getDue() == null ? Instant.now().toEpochMilli() : (long) task.getDue().getNano());
            materialDatePicker = builder1.build();
            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                localDate = Instant.ofEpochMilli(selection).atZone(ZoneId.systemDefault()).toLocalDate();
                editTextDueDate.setText(localDate.format(DateTimeFormatter.ofPattern("MMM. dd, yyyy")));
            });
            materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
        });
        editTextTime.setOnClickListener(v1 -> {
            builder2 = new MaterialTimePicker.Builder();
            builder2.setTimeFormat(TimeFormat.CLOCK_12H);
            builder2.setTitleText("Select time");
            builder2.setHour(localTime.getHour());
            builder2.setMinute(localTime.getMinute());
            materialTimePicker = builder2.build();
            materialTimePicker.addOnPositiveButtonClickListener(v2 -> {
                localTime = localTime.withHour(materialTimePicker.getHour()).withMinute(materialTimePicker.getMinute());
                editTextTime.setText(localTime.format(DateTimeFormatter.ofPattern("hh:mm a")));
            });
            materialTimePicker.show(getSupportFragmentManager(), "TIME_PICKER");
        });
        findViewById(R.id.menuSave).setOnClickListener(v -> {
            if (editTextLabel.getText().toString().isEmpty() || editTextDueDate.getText().toString().isEmpty() || editTextTime.getText().toString().isEmpty())
                Snackbar.make(findViewById(R.id.parent), getString(R.string.snack_fill_up), Snackbar.LENGTH_SHORT).show();
            else {
                task.setLabel(editTextLabel.getText().toString());
                task.setNotes(editTextNotes.getText().toString());
                task.setDue(LocalDateTime.of(localDate, localTime));
                setResult(RESULT_OK, new Intent().putExtra(EXTRA_REPLY, task));
                finish();
            }
        });
    }
}