package com.ics26011.taskmanagementapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class NotebookIndividualActivity extends AppCompatActivity {
    static final int CREATE_NOTE_ACTIVITY_REQUEST_CODE = 1;
    static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    static Notebook notebook;
    static NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_individual);
        // Initialize UI elements and variables
        notebook = (Notebook) getIntent().getSerializableExtra("notebook");
        assert notebook != null;
        final int dynamicColor = notebook.getThemeId() == getResources().getIntArray(R.array.color_picker)[6] ? Color.BLACK : Color.WHITE;
        final PorterDuffColorFilter dynamicColorFilter = new PorterDuffColorFilter(dynamicColor, PorterDuff.Mode.SRC_IN);
        final MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final NoteListAdapter adapter = new NoteListAdapter(this);
        final ExtendedFloatingActionButton btnNew = findViewById(R.id.btnNew);
        // Setup
        topAppBar.getNavigationIcon().setColorFilter(dynamicColorFilter);
        topAppBar.setTitle(notebook.getName());
        topAppBar.setTitleTextColor(dynamicColor);
        topAppBar.setBackgroundColor(notebook.getThemeId());
        topAppBar.getMenu().findItem(R.id.menuDelete).getIcon().setColorFilter(new PorterDuffColorFilter(dynamicColor, PorterDuff.Mode.SRC_IN));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        btnNew.getIcon().setColorFilter(dynamicColorFilter);
        btnNew.setTextColor(dynamicColor);
        btnNew.setBackgroundColor(notebook.getThemeId());
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getNotes(notebook.getId()).observe(this, adapter::setNotes);
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
        btnNew.setOnClickListener(v -> startActivityForResult(new Intent(this, NoteFormActivity.class).putExtra("note", new Note(notebook.getId())), CREATE_NOTE_ACTIVITY_REQUEST_CODE));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            noteViewModel.insert((Note) data.getSerializableExtra(NoteFormActivity.EXTRA_REPLY));
            notebook.setNumNotes(notebook.getNumNotes() + 1);
            MainActivity.notebookViewModel.update(notebook);
        }
        if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK)
            noteViewModel.update((Note) data.getSerializableExtra(NoteFormActivity.EXTRA_REPLY));
    }
}