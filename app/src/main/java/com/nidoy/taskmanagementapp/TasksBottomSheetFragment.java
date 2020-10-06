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
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;

public class TasksBottomSheetFragment extends BottomSheetDialogFragment {

    private static final String ARG_TASK = "task";

    private Task mTask;

    public static TasksBottomSheetFragment newInstance(Task task) {
        final TasksBottomSheetFragment fragment = new TasksBottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTask = (getArguments() == null ? null : (Task) getArguments().getSerializable(ARG_TASK));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tasks_bottom_sheet, container, false);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Chip chipOpen = view.findViewById(R.id.chipOpen);
        final Chip chipPending = view.findViewById(R.id.chipPending);
        final Chip chipFinished = view.findViewById(R.id.chipFinished);
        final TextView txtLabel = view.findViewById(R.id.txtLabel);
        final TextView txtDueDate = view.findViewById(R.id.txtDueDate);
        final TextView txtTime = view.findViewById(R.id.txtTime);
        final TextView txtOpen = view.findViewById(R.id.txtOpen);
        final TextView txtEdit = view.findViewById(R.id.txtEdit);
        final TextView txtDelete = view.findViewById(R.id.txtDelete);

        txtLabel.setText(mTask.getmLabel());
        txtDueDate.setText(new SimpleDateFormat("MMM. dd, yyyy").format(mTask.getmDue()));
        txtTime.setText(new SimpleDateFormat("hh:mm aa").format(mTask.getmDue()));
        chipOpen.setChecked(mTask.getmStatus().equals("Open"));
        chipPending.setChecked(mTask.getmStatus().equals("Pending"));
        chipFinished.setChecked(mTask.getmStatus().equals("Finished"));
        chipOpen.setCheckable(mTask.getmStatus().equals("Open"));
        chipPending.setCheckable(mTask.getmStatus().equals("Pending"));
        chipFinished.setCheckable(mTask.getmStatus().equals("Finished"));

        chipOpen.setOnClickListener(v -> {
            if (!mTask.getmStatus().equals("Open")) {
                new AlertDialog.Builder(requireContext()).setMessage(requireContext().getText(R.string.prompt_open_task)).setPositiveButton(requireContext().getText(R.string.yes), (dialog, which) -> {
                    mTask.setmStatus("Open");
                    MainActivity.mTaskViewModel.update(mTask);
                    Snackbar.make(view, requireContext().getString(R.string.snack_task_open), Snackbar.LENGTH_SHORT).setAction(R.string.snack_dismiss, v1 -> {
                    }).show();
                    this.dismiss();
                }).setNegativeButton(requireContext().getText(R.string.no), null).create().show();
            }
        });
        chipPending.setOnClickListener(v -> {
            if (!mTask.getmStatus().equals("Pending")) {
                new AlertDialog.Builder(requireContext()).setMessage(requireContext().getText(R.string.prompt_open_task)).setPositiveButton(requireContext().getText(R.string.yes), (dialog, which) -> {
                    mTask.setmStatus("Pending");
                    MainActivity.mTaskViewModel.update(mTask);
                    Snackbar.make(view, requireContext().getString(R.string.snack_task_pending), Snackbar.LENGTH_SHORT).setAction(R.string.snack_dismiss, v1 -> {
                    }).show();
                    this.dismiss();
                }).setNegativeButton(requireContext().getText(R.string.no), null).create().show();
            }
        });
        chipFinished.setOnClickListener(v -> {
            if (!mTask.getmStatus().equals("Finished")) {
                new AlertDialog.Builder(requireContext()).setMessage(requireContext().getText(R.string.prompt_finished_task)).setPositiveButton(requireContext().getText(R.string.yes), (dialog, which) -> {
                    mTask.setmStatus("Finished");
                    MainActivity.mTaskViewModel.update(mTask);
                    Snackbar.make(view, requireContext().getString(R.string.snack_task_open), Snackbar.LENGTH_SHORT).setAction(R.string.snack_dismiss, v1 -> {
                    }).show();
                    this.dismiss();
                }).setNegativeButton(requireContext().getText(R.string.no), null).create().show();
            }
        });
        txtOpen.setOnClickListener(v -> {
            // TODO Create dedicated task activity
            Snackbar.make(view, "Navigate to task activity.", Snackbar.LENGTH_SHORT).show();
            this.dismiss();
        });
        txtEdit.setOnClickListener(v -> {
            requireActivity().startActivityForResult(new Intent(requireActivity(), TaskActivity.class).putExtra("task", mTask), MainActivity.UPDATE_TASK_ACTIVITY_REQUEST_CODE);
            this.dismiss();
        });
        txtDelete.setOnClickListener(v -> new AlertDialog.Builder(requireContext()).setMessage(requireContext().getText(R.string.prompt_delete_task)).setPositiveButton(requireContext().getText(R.string.yes), (dialog, which) -> {
            MainActivity.mTaskViewModel.delete(mTask);
            Snackbar.make(view, requireContext().getString(R.string.snack_task_deleted), Snackbar.LENGTH_SHORT).setAction(R.string.snack_dismiss, v1 -> {
            }).show();
            this.dismiss();
        }).setNegativeButton(requireContext().getText(R.string.no), null).create().show());
    }
}