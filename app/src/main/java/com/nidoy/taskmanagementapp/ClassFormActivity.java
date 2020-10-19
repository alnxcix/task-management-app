package com.nidoy.taskmanagementapp;

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
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.util.Calendar;
import java.util.Date;

public class ClassFormActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "REPLY";

    // Material pickers + builders
    private MaterialTimePicker materialTimePicker1;
    private MaterialTimePicker.Builder builder1;
    private MaterialTimePicker materialTimePicker2;
    private MaterialTimePicker.Builder builder2;

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
        EditText editTextDay = ((TextInputLayout) findViewById(R.id.txtInputDay)).getEditText();
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
        // Setup calendar for time pickers
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // Event listeners
        spectrumPalette.setOnColorSelectedListener(color -> {
            c.setThemeId(color);
            topAppBar.setBackgroundColor(color);
            topAppBar.setTitleTextColor(color == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE);
            ((MenuItem) findViewById(R.id.menuSave)).getIcon().setColorFilter(new PorterDuffColorFilter(color == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE, PorterDuff.Mode.SRC_IN));
        });
        spectrumPalette.setSelectedColor(c.getThemeId() == -1 ? getResources().getIntArray(R.array.color_picker)[0] : c.getThemeId());
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
        findViewById(R.id.menuSave).setOnClickListener(v -> {
            c.setName(editTextClassName.getText().toString());
            c.setInstructors(editTextInstructors.getText().toString());
            c.setVenue(editTextVenue.getText().toString());
            setResult(RESULT_OK, new Intent().putExtra(EXTRA_REPLY, c));
            finish();
        });
    }
}