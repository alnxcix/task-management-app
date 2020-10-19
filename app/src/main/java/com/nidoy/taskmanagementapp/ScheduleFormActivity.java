package com.nidoy.taskmanagementapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.thebluealliance.spectrum.SpectrumPalette;

public class ScheduleFormActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_form);
        Schedule schedule = getIntent().getSerializableExtra("schedule") == null ? new Schedule() : (Schedule) getIntent().getSerializableExtra("schedule");
        // Initialize UI elements
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        EditText editTextName = ((TextInputLayout) findViewById(R.id.txtInputName)).getEditText();
        SpectrumPalette spectrumPalette = findViewById(R.id.spectrumPalette);
        // Setup
        topAppBar.setTitle(schedule.getName() == null ? getString(R.string.new_schedule) : schedule.getName());
        editTextName.setText(schedule.getName());
        // Event listeners
        spectrumPalette.setOnColorSelectedListener(color -> {
            schedule.setThemeId(color);
            topAppBar.setBackgroundColor(color);
            topAppBar.setTitleTextColor(color == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE);
            topAppBar.getMenu().findItem(R.id.menuSave).getIcon().setColorFilter(new PorterDuffColorFilter(color == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE, PorterDuff.Mode.SRC_IN));
        });
        spectrumPalette.setSelectedColor(schedule.getThemeId() == -1 ? getResources().getIntArray(R.array.color_picker)[0] : schedule.getThemeId());
        findViewById(R.id.menuSave).setOnClickListener(v -> {
            if (editTextName.getText().toString().isEmpty())
                Snackbar.make(findViewById(R.id.parent), getString(R.string.snack_fill_up), Snackbar.LENGTH_SHORT).show();
            else {
                schedule.setName(editTextName.getText().toString());
                setResult(RESULT_OK, new Intent().putExtra(EXTRA_REPLY, schedule));
                finish();
            }
        });
    }
}