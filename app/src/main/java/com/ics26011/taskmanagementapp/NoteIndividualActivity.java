package com.ics26011.taskmanagementapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.format.DateTimeFormatter;

public class NoteIndividualActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_individual);
        // Initialize UI elements and variables
        Note note = getIntent().getSerializableExtra("note") == null ? null : (Note) getIntent().getSerializableExtra("note");
        assert note != null;
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        FloatingActionButton btnEdit = findViewById(R.id.btnEdit);
        int dynamicColor = note.getThemeId() == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE;
        // Setup
        topAppBar.setTitle(note.getTitle() == null ? getString(R.string.new_note) : note.getTitle());
        topAppBar.setBackgroundColor(note.getThemeId());
        topAppBar.setTitleTextColor(dynamicColor);
        topAppBar.getMenu().findItem(R.id.menuDelete).getIcon().setColorFilter(new PorterDuffColorFilter(dynamicColor, PorterDuff.Mode.SRC_IN));
        ((TextView) findViewById(R.id.imgTitle)).getCompoundDrawables()[0].setColorFilter(note.getThemeId(), PorterDuff.Mode.SRC_IN);
        ((TextView) findViewById(R.id.txtTitle)).setText(note.getTitle());
        ((TextView) findViewById(R.id.txtDateLastModified)).setText(note.getDateLastModified().format(DateTimeFormatter.ofPattern("MMM. dd, yyyy")));
        ((TextView) findViewById(R.id.txtContent)).setText(note.getContent());
        btnEdit.setBackgroundTintList(ColorStateList.valueOf(note.getThemeId()));
        btnEdit.getDrawable().setColorFilter(new PorterDuffColorFilter(dynamicColor, PorterDuff.Mode.SRC_IN));
        // Event listeners
        topAppBar.getMenu().findItem(R.id.menuDelete).setOnMenuItemClickListener(v -> {
            new MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.dialog_title_delete_note))
                    .setMessage(getString(R.string.dialog_message_delete_note))
                    .setPositiveButton(getString(R.string.delete), (dialog, which) -> {
                        NotebookIndividualActivity.noteViewModel.delete(note);
                        finish();
                    }).setNegativeButton(getString(R.string.cancel), null)
                    .show();
            return true;
        });
        btnEdit.setOnClickListener(v -> this.startActivityForResult(new Intent(this, NoteFormActivity.class).putExtra("note", note), NotebookIndividualActivity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NotebookIndividualActivity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            NotebookIndividualActivity.noteViewModel.update((Note) data.getSerializableExtra(NoteFormActivity.EXTRA_REPLY));
            finish();
        }
    }
}