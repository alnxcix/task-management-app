package com.nidoy.taskmanagementapp;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;
import com.thebluealliance.spectrum.SpectrumPalette;

public class ClassFormActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "REPLY";

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
        // Setup
        topAppBar.setTitle(c.getName() == null ? getString(R.string.new_class) : c.getName());
        editTextClassName.setText(c.getName());
        editTextInstructors.setText(c.getInstructors());
        dropdownDay.setAdapter(new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, getResources().getStringArray(R.array.week)));
        // Event listeners
        spectrumPalette.setOnColorSelectedListener(color -> {
            c.setThemeId(color);
            topAppBar.setBackgroundColor(color);
            topAppBar.setTitleTextColor(color == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE);
            topAppBar.getMenu().findItem(R.id.menuSave).getIcon().setColorFilter(new PorterDuffColorFilter(color == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE, PorterDuff.Mode.SRC_IN));
        });
        spectrumPalette.setSelectedColor(c.getThemeId() == -1 ? getResources().getIntArray(R.array.color_picker)[0] : c.getThemeId());
        findViewById(R.id.menuSave).setOnClickListener(v -> {
            Toast.makeText(this, dropdownDay.getText().toString(), Toast.LENGTH_SHORT).show();
//            c.setName(editTextClassName.getText().toString());
//            c.setInstructors(editTextInstructors.getText().toString());
//            setResult(RESULT_OK, new Intent().putExtra(EXTRA_REPLY, c));
//            finish();
        });
    }
}