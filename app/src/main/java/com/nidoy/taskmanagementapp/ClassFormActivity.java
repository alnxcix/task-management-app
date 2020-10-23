package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.time.LocalTime;

public class ClassFormActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "REPLY";

    private MaterialTimePicker materialTimePicker;
    private MaterialTimePicker.Builder builder;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_form);
        // Initialize UI elements and variables
        Class c = getIntent().getSerializableExtra("class") == null ? null : (Class) getIntent().getSerializableExtra("class");
        assert c != null;
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        EditText editTextClassName = ((TextInputLayout) findViewById(R.id.txtInputClassName)).getEditText();
        EditText editTextInstructors = ((TextInputLayout) findViewById(R.id.txtInputInstructors)).getEditText();
        EditText editTextVenue = ((TextInputLayout) findViewById(R.id.txtInputVenue)).getEditText();
        AutoCompleteTextView dropdownDay = findViewById(R.id.dropdown_day);
        EditText editTextStartTime = ((TextInputLayout) findViewById(R.id.txtInputStartTime)).getEditText();
        EditText editTextEndTime = ((TextInputLayout) findViewById(R.id.txtInputEndTime)).getEditText();
        SpectrumPalette spectrumPalette = findViewById(R.id.spectrumPalette);
        assert editTextClassName != null;
        assert editTextInstructors != null;
        assert editTextVenue != null;
        assert editTextStartTime != null;
        assert editTextEndTime != null;
        // Setup
        topAppBar.setTitle(c.getName() == null ? getString(R.string.new_class) : c.getName());
        editTextClassName.setText(c.getName());
        editTextInstructors.setText(c.getInstructors());
        editTextVenue.setText(c.getVenue());
        editTextStartTime.setText(c.getStartTime() == null ? null : c.getStartTime().toString());
        editTextEndTime.setText(c.getEndTime() == null ? null : c.getEndTime().toString());
        dropdownDay.setAdapter(new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, getResources().getStringArray(R.array.arr_week)));
        // Event listeners
        dropdownDay.setOnItemClickListener((parent, view, position, id) -> c.setDayId(position));
        dropdownDay.setText(getResources().getStringArray(R.array.arr_week)[c.getDayId()], false);
        editTextStartTime.setOnClickListener(v1 -> {
            builder = new MaterialTimePicker.Builder();
            builder.setTimeFormat(TimeFormat.CLOCK_12H);
            builder.setTitleText(getString(R.string.select_time));
            builder.setHour(c.getStartTime() == null ? 0 : c.getStartTime().getHour());
            builder.setMinute(c.getStartTime() == null ? 0 : c.getStartTime().getMinute());
            materialTimePicker = builder.build();
            materialTimePicker.addOnPositiveButtonClickListener(v2 -> {
                c.setStartTime(LocalTime.of(materialTimePicker.getHour(), materialTimePicker.getMinute()));
                editTextStartTime.setText(c.getStartTime().toString());
            });
            materialTimePicker.show(getSupportFragmentManager(), "TIME_PICKER");
        });
        editTextEndTime.setOnClickListener(v1 -> {
            builder = new MaterialTimePicker.Builder();
            builder.setTimeFormat(TimeFormat.CLOCK_12H);
            builder.setTitleText(getString(R.string.select_time));
            builder.setHour(c.getEndTime() == null ? 12 : c.getEndTime().getHour());
            builder.setMinute(c.getEndTime() == null ? 0 : c.getEndTime().getMinute());
            materialTimePicker = builder.build();
            materialTimePicker.addOnPositiveButtonClickListener(v2 -> {
                c.setEndTime(LocalTime.of(materialTimePicker.getHour(), materialTimePicker.getMinute()));
                editTextEndTime.setText(c.getEndTime().toString());
            });
            materialTimePicker.show(getSupportFragmentManager(), "TIME_PICKER");
        });
        spectrumPalette.setOnColorSelectedListener(color -> {
            c.setThemeId(color);
            topAppBar.setBackgroundColor(color);
            topAppBar.setTitleTextColor(color == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE);
            topAppBar.getMenu().findItem(R.id.menuSave).getIcon().setColorFilter(new PorterDuffColorFilter(color == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE, PorterDuff.Mode.SRC_IN));
        });
        spectrumPalette.setSelectedColor(c.getThemeId() == -1 ? getResources().getIntArray(R.array.color_picker)[0] : c.getThemeId());
        findViewById(R.id.menuSave).setOnClickListener(v -> {
            c.setName(editTextClassName.getText().toString());
            c.setInstructors(editTextInstructors.getText().toString());
            c.setVenue(editTextVenue.getText().toString());
            setResult(RESULT_OK, new Intent().putExtra(EXTRA_REPLY, c));
            finish();
        });
    }
}