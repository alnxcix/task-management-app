package com.nidoy.taskmanagementapp;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class NotebookIndividualActivity extends AppCompatActivity {

    static final int CREATE_NOTE_ACTIVITY_REQUEST_CODE = 1;
    static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    static Notebook notebook;
    // static NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_individual);
        // Initialize UI elements and variables
        notebook = (Notebook) getIntent().getSerializableExtra("notebook");
        assert notebook != null;
        int dynamicColor = notebook.getThemeId() == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE;
        PorterDuffColorFilter dynamicColorFilter = new PorterDuffColorFilter(dynamicColor, PorterDuff.Mode.SRC_IN);
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        ExtendedFloatingActionButton btnNew = findViewById(R.id.btnNew);
        // Setup
        topAppBar.getNavigationIcon().setColorFilter(dynamicColorFilter);
        topAppBar.setTitle(notebook.getName());
        topAppBar.setTitleTextColor(dynamicColor);
        topAppBar.setBackgroundColor(notebook.getThemeId());
        topAppBar.getMenu().findItem(R.id.menuDelete).getIcon().setColorFilter(new PorterDuffColorFilter(dynamicColor, PorterDuff.Mode.SRC_IN));
        btnNew.getIcon().setColorFilter(dynamicColorFilter);
        btnNew.setTextColor(dynamicColor);
        btnNew.setBackgroundColor(notebook.getThemeId());
        // Event listeners
        topAppBar.getMenu().findItem(R.id.menuDelete).setOnMenuItemClickListener(v -> {
            new MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.dialog_title_delete_notebook))
                    .setMessage(getString(R.string.dialog_message_delete_schedule))
                    .setPositiveButton(getString(R.string.delete), (dialog, which) -> {
                        MainActivity.notebookViewModel.delete(notebook);
                        finish();
                    }).setNegativeButton(getString(R.string.cancel), null)
                    .show();
            return true;
        });
        btnNew.setOnClickListener(v -> {/* TODO: Add logic here*/ });
    }
}