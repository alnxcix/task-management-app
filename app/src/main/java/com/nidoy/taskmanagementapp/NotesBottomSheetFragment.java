package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.time.format.DateTimeFormatter;

public class NotesBottomSheetFragment extends BottomSheetDialogFragment {

    private static final String ARG_NOTE = "note";

    private Note note;

    public static NotesBottomSheetFragment newInstance(Note note) {
        final NotesBottomSheetFragment fragment = new NotesBottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        note = getArguments() == null ? null : (Note) getArguments().getSerializable(ARG_NOTE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_bottom_sheet, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize UI elements and variables
        final TextView txtHeading = view.findViewById(R.id.txtHeading);
        final TextView txtSubheading = view.findViewById(R.id.txtSubHeading);
        // Setup
        txtHeading.setText(note.getTitle());
        txtHeading.getCompoundDrawables()[0].setColorFilter(new PorterDuffColorFilter(note.getThemeId(), PorterDuff.Mode.SRC_IN));
        txtSubheading.setText(getString(R.string.modified_last) + " " + note.getDateLastModified().format(DateTimeFormatter.ofPattern("MMM. dd, yyyy")));
        view.findViewById(R.id.txtOpen).setOnClickListener(v -> {
            requireActivity().startActivity(new Intent(requireActivity(), NoteIndividualActivity.class).putExtra("note", note));
            this.dismiss();
        });
        view.findViewById(R.id.txtEdit).setOnClickListener(v -> {
            requireActivity().startActivityForResult(new Intent(requireActivity(), NoteFormActivity.class).putExtra("note", note), NotebookIndividualActivity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE);
            this.dismiss();
        });
        view.findViewById(R.id.txtDelete).setOnClickListener(v -> new MaterialAlertDialogBuilder(view.getContext())
                .setTitle(getString(R.string.dialog_title_delete_note))
                .setMessage(getString(R.string.dialog_message_delete_note))
                .setPositiveButton(getString(R.string.delete), (dialog, which) -> {
                    NotebookIndividualActivity.noteViewModel.delete(note);
                    NotebookIndividualActivity.notebook.setNumNotes(NotebookIndividualActivity.notebook.getNumNotes() - 1);
                    MainActivity.notebookViewModel.update(NotebookIndividualActivity.notebook);
                    this.dismiss();
                }).setNegativeButton(getString(R.string.cancel), null)
                .show());
    }
}