package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class NotebooksBottomSheetFragment extends BottomSheetDialogFragment {

    private static final String ARG_NOTEBOOK = "notebook";

    private Notebook notebook;

    public static NotebooksBottomSheetFragment newInstance(Notebook notebook) {
        final NotebooksBottomSheetFragment fragment = new NotebooksBottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTEBOOK, notebook);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notebook = getArguments() == null ? null : (Notebook) getArguments().getSerializable(ARG_NOTEBOOK);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notebooks_bottom_sheet, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize UI elements
        final TextView txtHeading = view.findViewById(R.id.txtHeading);
        final TextView txtSubheading = view.findViewById(R.id.txtSubHeading);
        // Setup
        txtHeading.setText(notebook.getName());
        txtSubheading.setText(notebook.getNumNotes() + " " + getString(R.string.notes));
        view.findViewById(R.id.txtOpen).setOnClickListener(v -> {
            requireActivity().startActivity(new Intent(requireActivity(), NotebookIndividualActivity.class).putExtra("notebook", notebook));
            this.dismiss();
        });
        view.findViewById(R.id.txtEdit).setOnClickListener(v -> {
            requireActivity().startActivityForResult(new Intent(requireActivity(), NotebookFormActivity.class).putExtra("notebook", notebook), MainActivity.UPDATE_NOTEBOOK_ACTIVITY_REQUEST_CODE);
            this.dismiss();
        });
        view.findViewById(R.id.txtDelete).setOnClickListener(v -> new MaterialAlertDialogBuilder(view.getContext())
                .setTitle(getString(R.string.dialog_title_delete_notebook))
                .setMessage(getString(R.string.dialog_message_delete_notebook))
                .setPositiveButton(getString(R.string.delete), (dialog, which) -> {
                    MainActivity.notebookViewModel.delete(notebook);
                    this.dismiss();
                }).setNegativeButton(getString(R.string.cancel), null)
                .show());
    }
}