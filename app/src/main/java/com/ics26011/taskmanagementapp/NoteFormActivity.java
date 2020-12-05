package com.ics26011.taskmanagementapp;

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

import java.time.LocalDateTime;

public class NoteFormActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_form);
        // Initialize UI elements and variables
        Note note = getIntent().getSerializableExtra("note") == null ? null : (Note) getIntent().getSerializableExtra("note");
        assert note != null;
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        EditText editTextTitle = ((TextInputLayout) findViewById(R.id.txtInputTitle)).getEditText();
        EditText editTextContent = ((TextInputLayout) findViewById(R.id.txtInputContent)).getEditText();
        SpectrumPalette spectrumPalette = findViewById(R.id.spectrumPalette);
        assert editTextTitle != null;
        assert editTextContent != null;
        // Setup
        topAppBar.setTitle(note.getTitle() == null ? getString(R.string.new_note) : note.getTitle());
        editTextTitle.setText(note.getTitle());
        editTextContent.setText(note.getContent());
        spectrumPalette.setOnColorSelectedListener(color -> {
            note.setThemeId(color);
            topAppBar.setBackgroundColor(color);
            topAppBar.setTitleTextColor(color == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE);
            topAppBar.getMenu().findItem(R.id.menuSave).getIcon().setColorFilter(new PorterDuffColorFilter(color == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE, PorterDuff.Mode.SRC_IN));
        });
        spectrumPalette.setSelectedColor(note.getThemeId() == -1 ? getResources().getIntArray(R.array.color_picker)[0] : note.getThemeId());
        findViewById(R.id.menuSave).setOnClickListener(v -> {
            if (editTextTitle.getText().toString().isEmpty())
                Snackbar.make(findViewById(R.id.parent), getString(R.string.snack_fill_up), Snackbar.LENGTH_SHORT).show();
            else {
                note.setTitle(editTextTitle.getText().toString());
                note.setContent(editTextContent.getText().toString());
                note.setDateLastModified(LocalDateTime.now());
                setResult(RESULT_OK, new Intent().putExtra(EXTRA_REPLY, note));
                finish();
            }
        });
    }
}